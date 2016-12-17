package com.gtriangle.admin.permission.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GTrianglePermAuthzFilter extends AuthorizationFilter {
	
	/**
	 * 
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SESSION_TIME = "_gt_session_status";
	public static final String SESSION_TIME_VALUE = "_timeout";
	
	public static final String NO_PERMISSION = "_gt_no_permission";
	public static final String NO_PERMISSION_VALUE = "_no";
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		String[] perms = (String[]) mappedValue;

		boolean isPermitted = true;
		if (perms != null && perms.length > 0) {
			if (perms.length == 1) {
				if (!subject.isPermitted(perms[0])) {
					isPermitted = false;
				}
			} else {
				if (!subject.isPermittedAll(perms)) {
					isPermitted = false;
				}
			}
		}

		return isPermitted;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
	    HttpServletResponse httpResponse = (HttpServletResponse)response;
	        
		Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
        	httpResponse.addHeader(SESSION_TIME, SESSION_TIME_VALUE);
        	if (isAjaxRequest(httpRequest)) {
        		super.saveRequest(httpRequest);
        	} else {
        		saveRequestAndRedirectToLogin(request, response);
        	}
            
        } else {
        	httpResponse.addHeader(NO_PERMISSION, NO_PERMISSION_VALUE);
        	//httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        	if (isAjaxRequest(httpRequest)) {
        		//httpResponse.addHeader(NO_PERMISSION, NO_PERMISSION_VALUE);
        		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				 // If subject is known but not authorized, redirect to the unauthorized URL if there is one
	            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
	            String unauthorizedUrl = getUnauthorizedUrl();
	            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
	            if (StringUtils.hasText(unauthorizedUrl)) {
	                WebUtils.issueRedirect(request, response, unauthorizedUrl);
	            } else {
	            	httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	            }
			}
           
        }
        return false;
	}


	private boolean isAjaxRequest(HttpServletRequest httpRequest) {
//		Enumeration<String> headers = httpRequest.getHeaderNames();
//		while(headers.hasMoreElements()) {
//			String key = headers.nextElement();
//			logger.debug(headers.nextElement() + " value=" + httpRequest.getHeader(key));
//		}
		return httpRequest.getHeader("x-requested-with") != null    
		        && httpRequest.getHeader("x-requested-with").equalsIgnoreCase(    //ajax超时处理     
		                "XMLHttpRequest");
	}


}
