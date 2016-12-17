package com.gtriangle.admin;

/**
 * 核心业务错误定义,错误码第一位为模块标识参见sys_module
 * 账号模块 
 * @author Brian   
 * @date 2016年2月26日 上午10:51:14
 */
public class BizErrorConstant {
	/**
	 * 短信验证码错误
	 */
	public static final String ACCOUNT_ACTIVATION_CODE_ERROR = "05000";
	/**
	 * 短信验证码过期
	 */
	public static final String ACCOUNT_ACTIVATION_CODE_EXPIRED = "05001";
	/**
	 * 账号不存在，请先注册
	 */
	public static final String ACCOUNT_EMP_NAME_NOT_EXISTED = "05002";
	/**
	 * 密码不匹配
	 */
	public static final String ACCOUNT_PASSWORD_NOT_MATCHED = "05003";
	/**
	 * 账号处于停用状态，联系店铺管理员启用
	 */
	public static final String ACCOUNT_IS_Locked = "05004";
	/**
	 * 账号未经过短信验证
	 */
	public static final String ACCOUNT_not_actived = "05005";
	/**
	 * 您还不是高级版用户，请先续费成为高级版
	 */
	public static final String ACCOUNT_not_advance_version = "05006";
	/**
	 * 您的高级版已到期，请续费后再使用
	 */
	public static final String ACCOUNT_is_expired = "05007";
	/**
	 * 您没有开通该系统，无法正常登录，请先注册或开通
	 */
	public static final String ACCOUNT_app_id_not_exist = "05008";

	/**
	 * 员工手机号已存在
	 */
	public static final String ACCOUNT_MOBILE_EXIST = "01000";

	/**
	 * 角色下存在员工
	 */
	public static final String EMP_ROLE_EXIST = "01001";

	/**
	 * 字典名称重复
	 */
	public static final String DICT_NAME_EXIST = "01002";

	/**
	 * 账号密码错误
	 */
	public static final String ACCOUNT_PASSWORD_ERROR = "01003";

	/**
	 * 用户图片审核失败
	 */
	public static final String USER_APPROVE_FAIL = "01004";


}
