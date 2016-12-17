package com.gtriangle.admin.util;

import com.gtriangle.admin.db.StringUtil;
import com.gtriangle.admin.bis.emp.entity.Employee;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

/**
 * 获得登录用户信息，租户信息等
 */
public class WebEmpUtils {
	/**
	 * 用户KEY
	 */
	public static final String EMP_KEY = "_emp_key";


	/**
	 * 用户KEY
	 */
	public static final String EMP_LAST_LOGIN_KEY = "_emp_last_login_key";

	/**
	 * 获得用户
	 * 
	 * @param request
	 * @return
	 */
	public static Employee getEmp(HttpServletRequest request) {
		return (Employee) request.getAttribute(EMP_KEY);
	}
	
	/**
	 * 获得用户ID
	 * 
	 * @param request
	 * @return
	 */
	public static Long getEmpId(HttpServletRequest request) {
		Employee employee = getEmp(request);
		if (employee != null) {
			return employee.getId();
		} else {
			return null;
		}
	}

	/**
	 * 设置用户
	 * 
	 * @param request
	 * @param employee
	 */
	public static void setEmp(HttpServletRequest request, Employee employee) {
		request.setAttribute(EMP_KEY, employee);
	}

	/**
	 * 设置用户最近登陆时间
	 *
	 * @param request
	 * @param empLastLogin
	 */
	public static void setEmpLastLogin(HttpServletRequest request, String empLastLogin) {
		if(StringUtil.isNotEmpty(empLastLogin)){
			request.setAttribute(EMP_LAST_LOGIN_KEY, empLastLogin);
		}
	}

	/**
	 * 获得用户
	 *
	 * @param request
	 * @return
	 */
	public static String getEmpLastLogin(HttpServletRequest request) {
		Object lastEmpLogin = request.getAttribute(EMP_LAST_LOGIN_KEY);
		if(lastEmpLogin != null){
			return String.valueOf(lastEmpLogin);
		}else{
			return null;
		}

	}
	/**
	 * 获取session中的值
	 * @param key
	 * @return
	 */
	public static Object getSessionAttribute(Object key) {
		Subject subject = SecurityUtils.getSubject();
		return subject.getSession().getAttribute(key);
	}
	/**
	 * 设置session
	 * @param key
	 * @param value
	 */
	public static void setSessionAttribute(Object key, Object value) {
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute(key, value);
	}
}
