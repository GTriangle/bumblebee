package com.gtriangle.admin.bis.emp.vo;

import java.io.Serializable;
import java.util.Date;

public class EmpListVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 员工id
	 */
	private Long empId;

	/** 
	 * 员工名称
	 */
	private String realName;

	/** 
	 * 角色
	 */
	private String roleName;

	/**
	 * 是否禁用 1:是 0:否
	 */
	private Boolean locked;

	/**
	 * 创建时间
	 */
	private Date created;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
