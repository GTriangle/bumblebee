package com.gtriangle.admin.permission.auth.service;


import com.gtriangle.admin.exception.BizCoreRuntimeException;

public interface IPasswordService {
	
	
	/**
	 * 密码是否与当前用户的匹配
	 * @param empId
	 * @param password  eg ： 123456
	 * @return
	 */
	 boolean isPasswordMatched(Long empId, String password);
	/**
	 * 更新密码
	 * @param empId
	 * @param rawPassowrd  原始密码
	 * @param newPassword  新密码
	 */
	 void updatePassword(Long empId, String rawPassowrd, String newPassword);
	
	/**
	 * 忘记密码，重置密码
	 * @param empName
	 * @param smsCode
	 * @param password          
	 * @throws BizCoreRuntimeException
	 */
	 void resetPassword(String empName,String smsCode,String password)  throws BizCoreRuntimeException;
	/**
	 * 密码加密
	 * @param password     原始明文密码
	 * @return  加密密码 和 盐
	 */
	 String[] getEncodedPassword(String password);
}
