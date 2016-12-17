package com.gtriangle.admin.action;

import com.gtriangle.admin.bis.emp.entity.Employee;
import com.gtriangle.admin.bis.sso.entity.SSOEmployee;
import com.gtriangle.admin.bis.sso.service.ISSOEmpService;
import com.gtriangle.admin.exception.ResultParseException;
import com.gtriangle.admin.json.JsonHandler;
import com.gtriangle.admin.permission.auth.entity.MenuVo;
import com.gtriangle.admin.util.WebEmpUtils;
import com.gtriangle.admin.permission.auth.service.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Controller 
public class IndexAct {
	//--加载本地模版的jsp页面
	private static final Logger log = LoggerFactory.getLogger(IndexAct.class);

	@Autowired
	private IPermissionService permissionService;
	@Autowired
	private ISSOEmpService ssoEmpService;
	@Autowired
	private JsonHandler json;

	@RequestMapping(value = "/v_index", method = RequestMethod.GET)
	public String v_index(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		log.debug("--------------/mgt/v_index({})--------------------");


		Employee employee = WebEmpUtils.getEmp(request);
		model.addAttribute("empId", employee.getId());
		model.addAttribute("realName", employee.getRealName());
		model.addAttribute("empType", employee.getEmpType());
		model.addAttribute("headImage", employee.getHeadImage());
		SSOEmployee ssoEmployee = ssoEmpService.queryById(employee.getId());
		String lastLoginDate = WebEmpUtils.getEmpLastLogin(request);
		if(lastLoginDate == null){
			lastLoginDate = "-";
		}
		model.addAttribute("lastLoginTime",lastLoginDate);

		Collection<MenuVo> menuList = permissionService.queryEmpMenu(employee.getId());
		try {
			model.addAttribute("menuList", json.marshaller(menuList));
		} catch (ResultParseException e) {
			e.printStackTrace();
		}
		return "/index";
	}
}
