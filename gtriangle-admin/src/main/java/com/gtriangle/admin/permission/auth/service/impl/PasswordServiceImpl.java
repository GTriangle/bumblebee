package com.gtriangle.admin.permission.auth.service.impl;

import com.gtriangle.admin.BizErrorConstant;
import com.gtriangle.admin.bis.sso.entity.SSOEmployee;
import com.gtriangle.admin.exception.BizCoreRuntimeException;
import com.gtriangle.admin.permission.auth.GTriangleSimpleByteSource;
import com.gtriangle.admin.permission.auth.service.IPasswordService;
import com.gtriangle.admin.bis.sso.service.ISSOEmpService;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PasswordServiceImpl implements IPasswordService {

	@Autowired
	private ISSOEmpService ssoEmpService;
	@Autowired
	private CredentialsMatcher credentialsMatcher;

	@Override
	public boolean isPasswordMatched(Long empId, String password) {
		SSOEmployee ssoEmployee = ssoEmpService.queryById(empId);
		String dbPassword = ssoEmployee.getPassword();
		String salt = ssoEmployee.getSalt();

		UsernamePasswordToken token = new UsernamePasswordToken(ssoEmployee.getEmpName(), password);

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				ssoEmployee.getEmpName(), //用户名
				dbPassword, //密码
				new GTriangleSimpleByteSource(salt),//ByteSource.Util.bytes(emp.getResetKey()),//salt=username+salt
				"");

		return credentialsMatcher.doCredentialsMatch(token, authenticationInfo);
	}

	@Override
	public void updatePassword(Long empId, String rawPassowrd, String newPassword) {
		Boolean isMatched = isPasswordMatched(empId, rawPassowrd);
		if(isMatched) {
			String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
			int hashIterations = 1;
			SimpleHash hash = new SimpleHash("md5", newPassword.trim(), salt2, hashIterations);
			String encodedPassword = hash.toHex();

			ssoEmpService.updatePassword(empId, encodedPassword, salt2);
		} else {
			//返回异常
			throw new BizCoreRuntimeException(BizErrorConstant.ACCOUNT_PASSWORD_NOT_MATCHED);
		}
	}

	@Override
	public void resetPassword(String empName, String smsCode, String password)
			throws BizCoreRuntimeException {

		String[] passwordAndSalt = getEncodedPassword(password);

		ssoEmpService.resetPassword(empName, smsCode, passwordAndSalt[0], passwordAndSalt[1]);
	}

	@Override
	public String[] getEncodedPassword(String password) {
		String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
		int hashIterations = 1;
		SimpleHash hash = new SimpleHash("md5", password.trim(), salt2, hashIterations);
		String encodedPassword = hash.toHex();
		String[] returnObj = {encodedPassword,salt2}; 
		return returnObj;
	}

}
