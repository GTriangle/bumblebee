package com.gtriangle.admin.bis.sso.service;

import com.gtriangle.admin.bis.sso.entity.SSOEmployee;
import com.gtriangle.admin.bis.sso.entity.SSOEmployeeApp;
import com.gtriangle.admin.exception.BizCoreRuntimeException;

public interface ISSOEmpService {

	SSOEmployee queryByEmpName(String empName);

	SSOEmployee queryById(Long ssoEmpId);

	int saveSSOEmployee(SSOEmployee bean);

	int updateSSOEmployee(SSOEmployee bean);

	SSOEmployeeApp querySSOEmpAppBySSOEmpId(Long ssoEmpId, String appId);

	int saveSSOEmployeeApp(SSOEmployeeApp bean);

	/**
	 * 登录成功后更新信息
	 * @param ssoEmployee
	 * @param ip
	 */
	void updateLoginSuccess(SSOEmployee ssoEmployee,String ip);
	/**
	 * 登录失败后更新信息
	 * @param ssoEmployee
	 * @param ip
	 */
	 void updateLoginError(SSOEmployee ssoEmployee,String ip);

	/**
	 * 忘记密码，重置密码
	 * @param empName
	 * @param smsCode
	 * @param password          登录密码 已经过MD5加密的
	 * @param salt
	 * @throws BizCoreRuntimeException
	 */
	 void resetPassword(String empName,String smsCode,String password,String salt)  throws BizCoreRuntimeException;
	/**
	 * 更新用户密码
	 * @param ssoEmpId
	 * @param password          登录密码 已经过MD5加密的
	 * @param salt
	 */
	 void updatePassword(Long ssoEmpId, String password, String salt);
		/**
	 * 检查账号状态
	 * @param ssoEmployee
	 * @param appId
	 * @param isCheckExpired 是否要检查租户店铺到期情况
	 * @return
	 */
	SSOEmployeeApp checkAccountState(SSOEmployee ssoEmployee, String appId, Boolean isCheckExpired) throws BizCoreRuntimeException;
}
