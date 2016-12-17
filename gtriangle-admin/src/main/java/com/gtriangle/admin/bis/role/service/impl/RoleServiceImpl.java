package com.gtriangle.admin.bis.role.service.impl;

import com.gtriangle.admin.BizErrorConstant;
import com.gtriangle.admin.bis.role.entity.Role;
import com.gtriangle.admin.bis.role.entity.RoleFunction;
import com.gtriangle.admin.bis.role.service.IRoleService;
import com.gtriangle.admin.bis.role.vo.EmpRole;
import com.gtriangle.admin.bis.role.vo.RoleFunctionResultVo;
import com.gtriangle.admin.bis.role.vo.RoleVo;
import com.gtriangle.admin.db.jdbc.BaseDaoSupport;
import com.gtriangle.admin.db.query.QueryBuilder;
import com.gtriangle.admin.db.query.Ssqb;
import com.gtriangle.admin.db.query.expression.Restrictions;
import com.gtriangle.admin.exception.BizCoreRuntimeException;
import com.gtriangle.admin.util.DateFormatUtils;
import com.gtriangle.admin.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Override
	public List<RoleVo> queryRoleList() {
		QueryBuilder queryRoleQb = QueryBuilder.where(Restrictions.eq("dataStatus",1))
				.and(Restrictions.ne("id",0));
		List<Role> roles = baseDaoSupport.queryList(queryRoleQb, Role.class);
		List<RoleVo> roleVos = new ArrayList<>();
		if(ListUtil.isNotEmpty(roles)){
			for (Role a : roles) {
				RoleVo roleVo = new RoleVo(a.getId(),a.getRoleName(),a.getRoleMemo());
				roleVos.add(roleVo);
			}
		}
		return roleVos;
	}

	@Override
	public List<RoleFunctionResultVo> queryRoleFuncList(Long roleId) {
		Ssqb ssqb = Ssqb.create("com.gtriangle.admin.role.queryRoleFuncList")
				.setParam("roleId",roleId);
		List<RoleFunctionResultVo> roleFuncList = baseDaoSupport.findForList(ssqb,RoleFunctionResultVo.class);
		List<RoleFunctionResultVo> finalRoleFuncList = new ArrayList<RoleFunctionResultVo>();
		buildFuncRoleList(0L, roleFuncList, finalRoleFuncList);
		return finalRoleFuncList;
	}

	private void buildFuncRoleList(Long parentId,List<RoleFunctionResultVo> roleFuncList,List<RoleFunctionResultVo> finalRoleFuncList){
		for(RoleFunctionResultVo roleFuncResultVo:roleFuncList){
			if(roleFuncResultVo.getPid().equals(parentId)){
				finalRoleFuncList.add(roleFuncResultVo);
				if(roleFuncResultVo.getSubRoleFucList() == null){
					roleFuncResultVo.setSubRoleFucList(new ArrayList<RoleFunctionResultVo>());
				}
				buildFuncRoleList(roleFuncResultVo.getFunctionId(), roleFuncList, roleFuncResultVo.getSubRoleFucList());
			}
		}
	}

	@Override
	public void saveRole(String roleName) {
		Role saveRole = new Role();
		saveRole.setRoleName(roleName);
		saveRole.setCreated(DateFormatUtils.getNow());
		baseDaoSupport.save(saveRole);
	}

	@Override
	public void updateRole(Long roleId, String roleName) {
		Role updateRole = new Role();
		updateRole.setId(roleId);
		updateRole.setRoleName(roleName);
		updateRole.setUpdated(DateFormatUtils.getNow());
		baseDaoSupport.update(updateRole);
	}

	@Override
	public void deleteRole(Long roleId) {
		int count = validateEmpRole(roleId);
		if(count > 0 ){
			throw new BizCoreRuntimeException(BizErrorConstant.EMP_ROLE_EXIST);
		}
		//删除角色
		QueryBuilder deleteRoleQb = QueryBuilder.where(Restrictions.eq("id",roleId));
		baseDaoSupport.deleteByQuery(deleteRoleQb,Role.class);
	}

	@Override
	public int validateEmpRole(Long roleId) {
		QueryBuilder queryEmpRoleCount  = QueryBuilder.where(Restrictions.eq("roleId",roleId));
		return baseDaoSupport.queryForInt(queryEmpRoleCount, EmpRole.class);
	}

	@Override
	public void updateRoleFunc(Long roleId, List<Long> funcIds) {
		Assert.isTrue(ListUtil.isNotEmpty(funcIds));
		//删除角色下的所有权限
		QueryBuilder deleteRoleFuncQb = QueryBuilder.where(Restrictions.eq("roleId",roleId));
		baseDaoSupport.deleteByQuery(deleteRoleFuncQb,RoleFunction.class);
		//增加角色对应的 权限
		List<RoleFunction> roleFunctions = new ArrayList<>();
		for (Long a : funcIds) {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setFuncId(a);
			roleFunction.setRoleId(roleId);
			roleFunctions.add(roleFunction);
		}
		baseDaoSupport.batchSave(roleFunctions,RoleFunction.class);
	}
}
