package com.gtriangle.admin.bis.emp.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UpdateEmpVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 员工id
     */
	@NotNull(message = "{emp.roleId.null}")
	private Long empId;

	/**
	 * 登陆账号
	 */
	@NotEmpty(message = "{emp.empName.invalid}")
	@Pattern(regexp="^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$",message="{account.username.invalid}")
	private String empName;

	/** 
	 * 用户名称
	 */
	@NotEmpty(message = "{emp.realName.invalid}")
	private String realName;

	/**
	 * 密码
	 */
	private String password;

	/** 
	 * 角色
	 */
	@NotNull(message = "{emp.roleId.null}")
	private Long roleId;

	/**
	 * 是否异地提醒 1:是 0:否
	 */
	@NotNull(message = "{emp.isRemoteWarn.null}")
	private Boolean isRemoteWarn;

	/**
	 * 是否禁用 1:是 0:否
	 */
	@NotNull(message = "{emp.locked.null}")
	private Integer locked;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsRemoteWarn() {
		return isRemoteWarn;
	}

	public void setIsRemoteWarn(Boolean remoteWarn) {
		isRemoteWarn = remoteWarn;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}
}
