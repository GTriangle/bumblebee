package com.gtriangle.admin.bis.sso.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gtriangle.admin.db.annotation.NotDbField;
import com.gtriangle.admin.db.annotation.Table;
import com.gtriangle.admin.db.annotation.PrimaryKeyField;

@Table(tableName="sso_employee")
public class SSOEmployee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// constructors
	public SSOEmployee () {
	}

	/**
	 * Constructor for primary key
	 */
	public SSOEmployee (Long ssoEmpId) {
		this.setSsoEmpId(ssoEmpId);
	}

	/**
	 * Constructor for required fields
	 */
	public SSOEmployee (
			Long id,
			String empName,
			String password,
			java.util.Date registerTime,
			String registerIp,
			java.lang.Integer loginCount,
			java.lang.Integer errorCount) {

		this.setSsoEmpId(id);
		this.setEmpName(empName);
		this.setPassword(password);
		this.setRegisterTime(registerTime);
		this.setRegisterIp(registerIp);
		this.setLoginCount(loginCount);
		this.setErrorCount(errorCount);
	}

	public void initialize () {
		this.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		this.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
		this.setLoginCount(0);
		this.setErrorCount(0);
	}


	@NotDbField
	private int hashCode = Integer.MIN_VALUE;

	@PrimaryKeyField
	private Long ssoEmpId;

	// fields
	private String empName;
	private String password;
	private String salt;
	private java.util.Date registerTime;
	private String registerIp;
	private java.util.Date lastLoginTime;
	private String lastLoginIp;
	private java.lang.Integer loginCount;
	private java.util.Date errorTime;
	private java.lang.Integer errorCount;
	private String errorIp;

	public Long getSsoEmpId() {
		return ssoEmpId;
	}

	public void setSsoEmpId(Long ssoEmpId) {
		this.ssoEmpId = ssoEmpId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * Return the value associated with the column: password
	 */
	public String getPassword () {
		return password;
	}

	/**
	 * Set the value related to the column: password
	 * @param password the password value
	 */
	public void setPassword (String password) {
		this.password = password;
	}


	/**
	 * Return the value associated with the column: register_time
	 */
	public java.util.Date getRegisterTime () {
		return registerTime;
	}

	/**
	 * Set the value related to the column: register_time
	 * @param registerTime the register_time value
	 */
	public void setRegisterTime (java.util.Date registerTime) {
		this.registerTime = registerTime;
	}


	/**
	 * Return the value associated with the column: register_ip
	 */
	public String getRegisterIp () {
		return registerIp;
	}

	/**
	 * Set the value related to the column: register_ip
	 * @param registerIp the register_ip value
	 */
	public void setRegisterIp (String registerIp) {
		this.registerIp = registerIp;
	}


	/**
	 * Return the value associated with the column: last_login_time
	 */
	public java.util.Date getLastLoginTime () {
		return lastLoginTime;
	}

	/**
	 * Set the value related to the column: last_login_time
	 * @param lastLoginTime the last_login_time value
	 */
	public void setLastLoginTime (java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	/**
	 * Return the value associated with the column: last_login_ip
	 */
	public String getLastLoginIp () {
		return lastLoginIp;
	}

	/**
	 * Set the value related to the column: last_login_ip
	 * @param lastLoginIp the last_login_ip value
	 */
	public void setLastLoginIp (String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}


	/**
	 * Return the value associated with the column: login_count
	 */
	public java.lang.Integer getLoginCount () {
		return loginCount;
	}

	/**
	 * Set the value related to the column: login_count
	 * @param loginCount the login_count value
	 */
	public void setLoginCount (java.lang.Integer loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * Return the value associated with the column: error_time
	 */
	public java.util.Date getErrorTime () {
		return errorTime;
	}

	/**
	 * Set the value related to the column: error_time
	 * @param errorTime the error_time value
	 */
	public void setErrorTime (java.util.Date errorTime) {
		this.errorTime = errorTime;
	}


	/**
	 * Return the value associated with the column: error_count
	 */
	public java.lang.Integer getErrorCount () {
		return errorCount;
	}

	/**
	 * Set the value related to the column: error_count
	 * @param errorCount the error_count value
	 */
	public void setErrorCount (java.lang.Integer errorCount) {
		this.errorCount = errorCount;
	}


	/**
	 * Return the value associated with the column: error_ip
	 */
	public String getErrorIp () {
		return errorIp;
	}

	/**
	 * Set the value related to the column: error_ip
	 * @param errorIp the error_ip value
	 */
	public void setErrorIp (String errorIp) {
		this.errorIp = errorIp;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof SSOEmployee)) return false;
		else {
			SSOEmployee unifiedEmp = (SSOEmployee) obj;
			if (null == this.getSsoEmpId() || null == unifiedEmp.getSsoEmpId()) return false;
			else return (this.getSsoEmpId().equals(unifiedEmp.getSsoEmpId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getSsoEmpId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getSsoEmpId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString () {
		return super.toString();
	}


}
