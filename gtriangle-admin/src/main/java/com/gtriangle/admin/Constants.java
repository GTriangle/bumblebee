package com.gtriangle.admin;

/**
 * web常量
 */
public abstract class Constants {
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	/**
	 * cookie中的JSESSIONID名称
	 */
	public static final String JSESSION_COOKIE = "JSESSIONID";
	/**
	 * HTTP POST请求
	 */
	public static final String POST = "POST";
	/**
	 * HTTP GET请求
	 */
	public static final String GET = "GET";
	/**
	 * CRM emp 最近登陆时间在session中的key值
	 */
	public static final String CRM_EMP_LAST_LOGIN_SESSION = "_gt_emp_last_login";

	/**
	 * CRM emp 在session中的key值
	 */
	public static final String CRM_EMP_SESSION = "_gt_emp";

	/**
	 * 当前request中session信息的key值
	 */
	public static final String CURRENT_REQUEST_SESSION = "_current_req_session";

}
