package com.gtriangle.admin.bis.emp.service.impl;

import com.gtriangle.admin.BizErrorConstant;
import com.gtriangle.admin.bis.emp.service.IEmpService;
import com.gtriangle.admin.bis.emp.vo.SaveEmpVo;
import com.gtriangle.admin.bis.emp.vo.UpdateEmpVo;
import com.gtriangle.admin.bis.role.vo.EmpRole;
import com.gtriangle.admin.bis.sso.entity.SSOEmployee;
import com.gtriangle.admin.bis.sso.entity.SSOEmployeeApp;
import com.gtriangle.admin.db.StringUtil;
import com.gtriangle.admin.db.jdbc.BaseDaoSupport;
import com.gtriangle.admin.db.page.Pagination;
import com.gtriangle.admin.db.query.QueryBuilder;
import com.gtriangle.admin.db.query.Ssqb;
import com.gtriangle.admin.db.query.expression.Restrictions;
import com.gtriangle.admin.exception.BizCoreRuntimeException;
import com.gtriangle.admin.permission.auth.service.IPasswordService;
import com.gtriangle.admin.util.DateFormatUtils;
import com.gtriangle.admin.GTriangleAppId;
import com.gtriangle.admin.permission.GTriangleAuthorizingRealm;
import com.gtriangle.admin.bis.emp.entity.Employee;
import com.gtriangle.admin.bis.sso.service.ISSOEmpService;
import com.gtriangle.admin.db.DBRuntimeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class EmpServiceImpl implements IEmpService {
	
	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Autowired
	private ISSOEmpService ssoEmpService;

	@Autowired
	private IPasswordService passwordService;
	
	@Autowired
	private GTriangleAuthorizingRealm GTriangleAuthorizingRealm;

	@Transactional(readOnly = true)
	@Override
	public Employee queryByEmpName(String empName) {
		Assert.hasText(empName, "empName is required");
		QueryBuilder query = QueryBuilder.create()
				.setParam("empName", empName);

		return baseDaoSupport.query(query, Employee.class);
	}

	@Override
	public Pagination queryEmplist(Integer pageNo, Integer pageSize) {
		Ssqb ssqb = Ssqb.create("com.gtriangle.admin.employee.queryEmplist")
				.setParam("pageNo", pageNo)
				.setParam("pageSize", pageSize);
		return baseDaoSupport.findForPage(ssqb);
	}

	@Override
	public void saveEmp(SaveEmpVo saveEmpVo, String requestIp) {
		Date now = DateFormatUtils.getNow();
		try {
			//保存ssoEmp
			SSOEmployee toSaveSSoEmployee = new SSOEmployee();
			toSaveSSoEmployee.setEmpName(saveEmpVo.getEmpName());
			toSaveSSoEmployee.setRegisterIp(requestIp);
			toSaveSSoEmployee.setRegisterTime(now);
			String[] passwordAndSalt = passwordService.getEncodedPassword(saveEmpVo.getPassword());
			toSaveSSoEmployee.setPassword(passwordAndSalt[0]);
			toSaveSSoEmployee.setSalt(passwordAndSalt[1]);
			ssoEmpService.saveSSOEmployee(toSaveSSoEmployee);
			Long empId = toSaveSSoEmployee.getSsoEmpId();

			//保存emp
			Employee toSaveEmployee = new Employee();
			toSaveEmployee.setId(empId);
			BeanUtils.copyProperties(saveEmpVo,toSaveEmployee);
			toSaveEmployee.setCreated(now);
			toSaveEmployee.setSource("pc-web");
			baseDaoSupport.create(toSaveEmployee);

			//保存 用户与应用关系 sso_employee_app
			SSOEmployeeApp app = new SSOEmployeeApp();
			app.setAppId(GTriangleAppId.gtriangle_mgt.value());
			app.setCreated(now);
			app.setLocked(0);
			app.setSsoEmpId(empId);
			ssoEmpService.saveSSOEmployeeApp(app);

			//保存用户角色关系
			EmpRole empRole = new EmpRole();
			empRole.setEmpId(empId);
			empRole.setRoleId(saveEmpVo.getRoleId());
			baseDaoSupport.save(empRole);


		} catch (DBRuntimeException e) {
			if ((e.getCause() != null) && e.getCause() instanceof DuplicateKeyException) {
				throw new BizCoreRuntimeException(BizErrorConstant.ACCOUNT_MOBILE_EXIST);
			}
		}
	}

	@Override
	public Map<String, Object> queryEmpDetail(Long empId) {
		Ssqb ssqb = Ssqb.create("com.gtriangle.admin.employee.queryEmpDetail")
				.setParam("empId",empId);
		return baseDaoSupport.findForMap(ssqb);
	}

	@Override
	public void updateEmp(UpdateEmpVo updateEmpVo) {
		
		Long empId = updateEmpVo.getEmpId();
		
		SSOEmployee ssoEmployee = ssoEmpService.queryById(empId);
		String oldEmpName = ssoEmployee.getEmpName();
		boolean isEmpNameChanged = false;
		boolean isPasswordChanged = false;
		if(!oldEmpName.equals(updateEmpVo.getEmpName())) {
			isEmpNameChanged = true;
		}
		//更新 ssoEmp (手机号和密码)
		SSOEmployee toUpdateSSoEmployee = new SSOEmployee();
		toUpdateSSoEmployee.setSsoEmpId(empId);
		toUpdateSSoEmployee.setEmpName(updateEmpVo.getEmpName());
		if(StringUtil.isNotEmpty(updateEmpVo.getPassword())){
			String[] passwordAndSalt = passwordService.getEncodedPassword(updateEmpVo.getPassword());
			toUpdateSSoEmployee.setPassword(passwordAndSalt[0]);
			toUpdateSSoEmployee.setSalt(passwordAndSalt[1]);
			
			isPasswordChanged = true;
		}
		ssoEmpService.updateSSOEmployee(toUpdateSSoEmployee);

		//更新 emp 登陆提醒
		Employee toUpdateEmployee = new Employee();
		toUpdateEmployee.setId(empId);
		toUpdateEmployee.setIsRemoteWarn(updateEmpVo.getIsRemoteWarn());
		toUpdateEmployee.setEmpName(updateEmpVo.getEmpName());
		toUpdateEmployee.setRealName(updateEmpVo.getRealName());
		baseDaoSupport.update(toUpdateEmployee);

		//更新 sso_employee_app 是否禁用账号
		SSOEmployeeApp app = new SSOEmployeeApp();
		app.setLocked(updateEmpVo.getLocked());
		QueryBuilder updateAppQb = QueryBuilder.where(Restrictions.eq("appId", GTriangleAppId.gtriangle_mgt.value()))
				.and(Restrictions.eq("ssoEmpId",empId));
		baseDaoSupport.updateByQuery(app,updateAppQb);

		//删除原有的用户角色关系
		QueryBuilder deleteRoleQb = QueryBuilder.where(Restrictions.eq("empId",empId));
		baseDaoSupport.deleteByQuery(deleteRoleQb,EmpRole.class);

		//保存用户角色关系
		EmpRole empRole = new EmpRole();
		empRole.setEmpId(empId);
		empRole.setRoleId(updateEmpVo.getRoleId());
		baseDaoSupport.save(empRole);
		//用户名或者密码变更 都需要清理登录状态
		if(isEmpNameChanged || isPasswordChanged)
			GTriangleAuthorizingRealm.clearCachedLoginInfo(oldEmpName);
	}

	
	@Override
	public void udpatePassword(Long empId, String oldPassword, String newPassword) {
		boolean isMatch = passwordService.isPasswordMatched(empId, oldPassword);
		if(isMatch){
			SSOEmployee toUpdateSsoEmployee = new SSOEmployee();
			String[] passwordAndSalt2 = passwordService.getEncodedPassword(newPassword);
			toUpdateSsoEmployee.setPassword(passwordAndSalt2[0]);
			toUpdateSsoEmployee.setSalt(passwordAndSalt2[1]);
			toUpdateSsoEmployee.setSsoEmpId(empId);
			baseDaoSupport.update(toUpdateSsoEmployee);
		}else{
			throw new BizCoreRuntimeException(BizErrorConstant.ACCOUNT_PASSWORD_NOT_MATCHED);
		}
	}
}
