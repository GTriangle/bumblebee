package com.gtriangle.admin.permission.filter;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gtriangle.admin.Constants;
import com.gtriangle.admin.GTriangleThreadContext;
import com.gtriangle.admin.util.RequestUtils;
import com.gtriangle.admin.util.WebEmpUtils;
import com.gtriangle.admin.bis.emp.entity.Employee;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 单店版用户过滤器
 * 用户登陆状态保持
 * GTriangleThreadContext 处理
 */
public class GTriangleUserFilter extends UserFilter {

	private static final Logger log = LoggerFactory.getLogger(GTriangleUserFilter.class);

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated() == false) {
			sessionTimeoutProcess(request, response);
			return false;
		}
		//没有session肯定不能到运行到这里
		Session session = subject.getSession();
		Employee employee = (Employee)session.getAttribute(Constants.CRM_EMP_SESSION);
		if(employee == null) {
			sessionTimeoutProcess(request, response);
			return false;
		}



		HttpServletRequest req = (HttpServletRequest) request;
		WebEmpUtils.setEmp(req, employee);
		Object lastLoginObj = session.getAttribute(Constants.CRM_EMP_LAST_LOGIN_SESSION);
		if(lastLoginObj != null){
			WebEmpUtils.setEmpLastLogin(req, String.valueOf(lastLoginObj));
		}
		GTriangleThreadContext.setEmp(employee);

		return super.preHandle(request, response);
	}
	
	
	@Override
	protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response);
	}

	@Override
	public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception)
			throws Exception {
		GTriangleThreadContext.removeEmp();
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		sessionTimeoutProcess(request, response);
        return false;
	}

	
	private void sessionTimeoutProcess(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
	    HttpServletResponse httpResponse = (HttpServletResponse)response;
	        
		Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
        	httpResponse.addHeader(GTrianglePermAuthzFilter.SESSION_TIME, GTrianglePermAuthzFilter.SESSION_TIME_VALUE);
        	if (RequestUtils.isAjaxRequest(httpRequest)) {
        		//super.saveRequest(httpRequest);
        	} else {
        		super.redirectToLogin(httpRequest, httpResponse);
        		//saveRequestAndRedirectToLogin(request, response);
        	}
        }
	}
}
