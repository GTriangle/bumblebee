package com.gtriangle.admin.permission.filter;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

/**
 * 退出后续处理，可以释放其他资源，跳转到不同url
* @author Brian   
* @date 2016年1月16日 下午5:50:34
 */
public class GTriangleLogoutFilter extends LogoutFilter {

	@Override
	protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
		return super.getRedirectUrl(request, response, subject);
	}
}
