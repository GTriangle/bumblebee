package com.gtriangle.admin.permission.filter;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * <p>Employee: Zhang Kaitao
 * <p>Date: 14-3-3
 * <p>Version: 1.0
 */
public class JCaptchaLoginFormAuthenticationFilter extends FormAuthenticationFilter {
	
	//DefaultFilterChainManager filterChainManager
	
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }
}
