package com.gtriangle.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gtriangle.admin.WebResult;
import com.gtriangle.admin.bis.emp.service.IEmpService;
import com.gtriangle.admin.bis.emp.vo.SaveEmpVo;
import com.gtriangle.admin.bis.emp.vo.UpdateEmpVo;
import com.gtriangle.admin.db.StringUtil;
import com.gtriangle.admin.permission.GTriangleAuthorizingRealm;
import com.gtriangle.admin.spring.validation.ValidateFiled;
import com.gtriangle.admin.spring.validation.ValidateGroup;
import com.gtriangle.admin.util.RequestUtils;
import com.gtriangle.admin.util.WebEmpUtils;

/**
 * 员工act
 */
@Controller
@RequestMapping(value = "/emp")
public class EmpAct {
	private static final Logger log = LoggerFactory.getLogger(EmpAct.class);

	@Autowired
	private IEmpService empService;
	@Autowired
	private GTriangleAuthorizingRealm GTriangleAuthorizingRealm;
	/**
	 * 员工列表
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void queryEmplist(Integer pageNo,Integer pageSize,
							   HttpServletRequest request,HttpServletResponse response) {
		WebResult.renderJsonDataDefault(response,empService.queryEmplist(pageNo,pageSize));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveEmp(@RequestBody @Valid SaveEmpVo saveEmpVo, BindingResult result,
						HttpServletRequest request, HttpServletResponse response) {
		if(result.hasErrors()) {
			WebResult.renderJsonFailResult(response, result);
			return;
		}
		if(!StringUtil.validPassword(saveEmpVo.getPassword())){
			WebResult.renderJsonDataFail(response,"密码格式错误，请输入10-50位数字和大小写字母,可以含有其他特殊字符");
			return;
		}
		empService.saveEmp(saveEmpVo, RequestUtils.getIpAddr(request));
		WebResult.renderJsonSucMsg(response);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateEmp(@RequestBody @Valid UpdateEmpVo updateEmpVo, BindingResult result,
						  HttpServletRequest request, HttpServletResponse response) {
		if(result.hasErrors()) {
			WebResult.renderJsonFailResult(response, result);
			return;
		}
		if(!StringUtil.validPassword(updateEmpVo.getPassword())){
			WebResult.renderJsonDataFail(response,"密码格式错误，请输入10-50位数字和大小写字母,可以含有其他特殊字符");
			return;
		}
		empService.updateEmp(updateEmpVo);
		WebResult.renderJsonSucMsg(response);
	}

	@ValidateGroup(fileds = { @ValidateFiled(index = 0, notNull = true,desc = "员工id")})
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public void queryEmp(Long empId,HttpServletRequest request,HttpServletResponse response) {
		WebResult.renderJsonDataDefault(response,empService.queryEmpDetail(empId));
	}

	@ValidateGroup(fileds = { @ValidateFiled(index = 0, notEmpty = true,desc = "员工旧密码"),
			@ValidateFiled(index = 1, notEmpty = true,desc = "员工新密码")})
	@RequestMapping(value = "/password/update", method = RequestMethod.POST)
	public void udpatePassword(String oldPassword,String newPassword,
						   HttpServletRequest request,HttpServletResponse response) {

		if(!StringUtil.validPassword(newPassword)){
			WebResult.renderJsonDataFail(response,"密码格式错误，请输入10-50位数字和大小写字母,可以含有其他特殊字符");
			return;
		}
		empService.udpatePassword(WebEmpUtils.getEmpId(request),oldPassword,newPassword);
		GTriangleAuthorizingRealm.clearCachedLoginInfo(WebEmpUtils.getEmp(request).getEmpName());
		WebResult.renderJsonSucMsg(response);
	}
}
