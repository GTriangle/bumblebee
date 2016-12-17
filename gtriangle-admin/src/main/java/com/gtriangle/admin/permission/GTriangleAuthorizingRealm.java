package com.gtriangle.admin.permission;


import java.util.HashSet;
import java.util.Set;

import com.gtriangle.admin.Constants;
import com.gtriangle.admin.bis.emp.entity.Employee;
import com.gtriangle.admin.bis.sso.entity.SSOEmployee;
import com.gtriangle.admin.bis.sso.service.ISSOEmpService;
import com.gtriangle.admin.permission.auth.GTriangleSimpleByteSource;
import com.gtriangle.admin.permission.auth.service.IPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Employee: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class GTriangleAuthorizingRealm extends AuthorizingRealm {

	@Autowired
	private ISSOEmpService ssoEmpService;
	@Autowired
	private IPermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String empName = (String)principals.getPrimaryPrincipal();

        Subject subject = SecurityUtils.getSubject();
		//没有session肯定不能到运行到这里
		Session session = subject.getSession();
		Employee employee = (Employee)session.getAttribute(Constants.CRM_EMP_SESSION);

		String empType = employee.getEmpType();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<String>();
        roles.add(empType);
        authorizationInfo.setRoles(roles);
        Set<String> perms = permissionService.queryEmpPerm(employee.getId());
        authorizationInfo.addStringPermissions(perms);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String empName = (String)token.getPrincipal();
        SSOEmployee ssoEmployee = ssoEmpService.queryByEmpName(empName);

        if(ssoEmployee == null) {
            throw new UnknownAccountException();//没找到帐号
        } else {
        	 SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                     ssoEmployee.getEmpName(), //用户名
                     ssoEmployee.getPassword(), //密码
                     new GTriangleSimpleByteSource(ssoEmployee.getSalt()),//ByteSource.Util.bytes(emp.getResetKey()),//salt=username+salt
                      getName()  //realm name
              );
        	 return authenticationInfo;
        }
    }


	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
    	Object obj = principals.getPrimaryPrincipal();
    	return "authz_" + obj;
		//return super.getAuthorizationCacheKey(principals);
	}

	/**
	 * 清理登录状态，也就是把用户踢出系统，重新登录
	 * @param username   用户名
	 */
    public void clearCachedLoginInfo(String username) {
    	SimplePrincipalCollection pc = new SimplePrincipalCollection();
    	pc.add(username, super.getName());
    	//清除登录状态
    	clearCachedAuthenticationInfo(pc);
    	//清除权限缓存
    	clearCachedAuthorizationInfo(pc);
    	
		//ssoUserService.deleteSSOToken(appId.value(), userId);
    	
    }

}
