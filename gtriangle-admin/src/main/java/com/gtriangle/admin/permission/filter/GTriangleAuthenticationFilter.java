package com.gtriangle.admin.permission.filter;


import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gtriangle.admin.BizErrorConstant;
import com.gtriangle.admin.Constants;
import com.gtriangle.admin.bis.emp.entity.Employee;
import com.gtriangle.admin.bis.emp.service.IEmpService;
import com.gtriangle.admin.bis.sso.entity.SSOEmployee;
import com.gtriangle.admin.bis.sso.entity.SSOEmployeeApp;
import com.gtriangle.admin.bis.sso.service.ISSOEmpService;
import com.gtriangle.admin.exception.BizCoreRuntimeException;
import com.gtriangle.admin.exception.CaptchaRequiredException;
import com.gtriangle.admin.jcaptcha.JCaptcha;
import com.gtriangle.admin.permission.JCaptchaConstants;
import com.gtriangle.admin.permission.auth.AccountAppNotExsitException;
import com.gtriangle.admin.permission.auth.AccountExpiredException;
import com.gtriangle.admin.permission.auth.UnActivedException;
import com.gtriangle.admin.util.DateFormatUtils;
import com.gtriangle.admin.util.RequestUtils;
import com.gtriangle.admin.exception.CaptchaErrorException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



public class GTriangleAuthenticationFilter extends FormAuthenticationFilter {

	private Logger logger = LoggerFactory.getLogger(GTriangleAuthenticationFilter.class);
	
	private static final String ACCOUNT_EXPIRED_JUMP_URL 
								= "/account/product/v_buy?productId=3211352658257920&reason={0}&shopCode={1}&appId={2}";
	
	/**
	 * appID
	 */
	public static final String APPID_PARAM = "appId";

    private String jcaptchaParam = "jcaptchaCode";//前台提交的验证码参数名

    private JCaptcha jcaptcha;

	@Autowired
	private IEmpService empService;

	public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }
    public void setJcaptcha(JCaptcha jcaptcha) {
		this.jcaptcha = jcaptcha;
	}
	
	@Autowired
	private ISSOEmpService ssoEmpService;


	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		
		
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			String msg = "create AuthenticationToken error";
			throw new IllegalStateException(msg);
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String empName = (String) token.getPrincipal();
		SSOEmployee ssoEmployee = ssoEmpService.queryByEmpName(empName);
		String loginUrl = super.getLoginUrl();
		//验证码校验
		if (isCaptchaRequired(ssoEmployee,req,res)) {
			String captcha = request.getParameter(jcaptchaParam);
			
			if (captcha != null) {
				if (!jcaptcha.validateResponse(req, captcha)) {
					return onLoginFailure(token, null,loginUrl,new CaptchaErrorException(), request, response);
				}
			} else {
				return onLoginFailure(token, null, loginUrl,new CaptchaRequiredException(),request, response);
			}
		}
		
		String appId = req.getParameter(APPID_PARAM);
		SSOEmployeeApp app = null;
		try {
			if(ssoEmployee != null) {
				app = ssoEmpService.checkAccountState(ssoEmployee, appId, true);
			}
			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token,subject, request, response, ssoEmployee, app);
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return onLoginFailure(token,ssoEmployee ,loginUrl, e, request, response);
		} catch (BizCoreRuntimeException e) {
			String errorCode = e.getErrorCode();
			switch (errorCode) {
			case BizErrorConstant.ACCOUNT_EMP_NAME_NOT_EXISTED:
				return onLoginFailure(token, ssoEmployee, loginUrl,new UnknownAccountException(),request, response);
			case BizErrorConstant.ACCOUNT_IS_Locked:
				return onLoginFailure(token, ssoEmployee ,loginUrl,new LockedAccountException(),request, response);
			case BizErrorConstant.ACCOUNT_not_actived:
				return onLoginFailure(token,ssoEmployee ,loginUrl,new UnActivedException(),request, response);
			case BizErrorConstant.ACCOUNT_app_id_not_exist:
				return onLoginFailure(token,ssoEmployee ,loginUrl,new AccountAppNotExsitException(),request, response);
			case BizErrorConstant.ACCOUNT_not_advance_version:
				app = (SSOEmployeeApp) e.getExtraInformation();
				String link = getExpiredRedirceUrl(app, errorCode);
				return onLoginFailure(token,ssoEmployee ,link ,new AccountExpiredException(),request, response);
			case BizErrorConstant.ACCOUNT_is_expired:
				app = (SSOEmployeeApp)e.getExtraInformation();
				link = getExpiredRedirceUrl(app, errorCode);
				return onLoginFailure(token,ssoEmployee , link,new AccountExpiredException(),request, response);
			default:
				return onLoginFailure(token,ssoEmployee ,loginUrl,new UnknownAccountException(),request, response);
			}
		}
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		super.issueSuccessRedirect(request, response);
	}
	
	private String getExpiredRedirceUrl(SSOEmployeeApp app, String reason) {
		return MessageFormat.format(ACCOUNT_EXPIRED_JUMP_URL, reason, null,app.getAppId());
	}
	/**
	 * 登录成功
	 */
	private boolean onLoginSuccess(AuthenticationToken token,Subject subject,
			ServletRequest request, ServletResponse response, SSOEmployee ssoEmployee, SSOEmployeeApp app)
					throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		String empName = (String) subject.getPrincipal();
		Employee employee = empService.queryByEmpName(empName);
		if(employee == null) {
			 throw new BizCoreRuntimeException(BizErrorConstant.ACCOUNT_EMP_NAME_NOT_EXISTED);//没找到帐号
		}
		SSOEmployee lastSsoEmployee = ssoEmpService.queryById(employee.getId());
		String lastLoginDate = "-";
		if(ssoEmployee.getLastLoginTime() != null){
			lastLoginDate = DateFormatUtils.getDateTimeCS(ssoEmployee.getLastLoginTime());
			subject.getSession().setAttribute(Constants.CRM_EMP_LAST_LOGIN_SESSION, lastLoginDate);
		}

		String ip = RequestUtils.getIpAddr(req);
		ssoEmpService.updateLoginSuccess(ssoEmployee, ip);
		//保存登录日志
		subject.getSession().setAttribute(Constants.CRM_EMP_SESSION, employee);
		return super.onLoginSuccess(token, subject, request, response);
	}
	
	/**
	 * 登录失败
	 */
	private boolean onLoginFailure(AuthenticationToken token,SSOEmployee ssoEmployee,String failureUrl
			,AuthenticationException e, ServletRequest request,ServletResponse response) {
		//String username = (String) token.getPrincipal();
		HttpServletRequest req = (HttpServletRequest) request;
		
		if(ssoEmployee != null) {
			String ip = RequestUtils.getIpAddr(req);
			ssoEmpService.updateLoginError(ssoEmployee, ip);
		}
		//保存登录日志
		return onLoginFailure(failureUrl,token, e, request, response);
	}

	private boolean onLoginFailure(String failureUrl,AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String className = e.getClass().getName();
		request.setAttribute(getFailureKeyAttribute(), className);
		if(failureUrl!=null||StringUtils.isNotBlank(failureUrl)){
			try {
				String loginUrl = super.getLoginUrl();
				if(failureUrl.equals(loginUrl)) {
					//登录错误 forward到login.jsp
					request.getRequestDispatcher(failureUrl).forward(request, response);
				} else {
					WebUtils.issueRedirect(request, response, failureUrl);
				}
				
			}  catch (Exception e1) {
				//e1.printStackTrace();
			}
		}
		return true;
	}
	
	private boolean isCaptchaRequired(SSOEmployee ssoEmployee,ServletRequest request,
			ServletResponse response) {
		Integer errorRemaining = errorRemaining(ssoEmployee, request);
		String captcha = request.getParameter(jcaptchaParam);
		// 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
        return !StringUtils.isBlank(captcha) || (errorRemaining != null && errorRemaining < 0);
    }
	
	private Integer errorRemaining(SSOEmployee ssoEmployee, ServletRequest request) {
		
		if (ssoEmployee == null) {
			return null;
		}
		long now = System.currentTimeMillis();
		int maxErrorTimes = 3;
		int maxErrorInterval = 1 * 60 * 1000;
		Integer errorCount = ssoEmployee.getErrorCount();
		Date errorTime = ssoEmployee.getErrorTime();
		if (errorCount <= 0 || errorTime == null) {
			return maxErrorTimes;
		} else if(maxErrorTimes - errorCount <= 1) {
			 request.setAttribute(JCaptchaConstants.jcaptchaEnabled_request_attr, true);
		}
		return maxErrorTimes - errorCount;
	}
}
