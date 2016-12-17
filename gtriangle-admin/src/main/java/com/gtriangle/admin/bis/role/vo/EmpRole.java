package com.gtriangle.admin.bis.role.vo;import com.gtriangle.admin.db.annotation.PrimaryKeyField;import com.gtriangle.admin.db.annotation.Table;import java.io.Serializable;@Table(tableName = "t_b_emp_role")public class EmpRole implements Serializable {	private static final long serialVersionUID = 1L;	//field	/** 	 * id 	 */	@PrimaryKeyField	private Long id;	/**	 * 账号ID 	 */	private Long empId;	/** 	 * 角色ID 	 */	private Long roleId;	public Long getId() {		return id;	}	public void setId(Long id) {		this.id = id;	}	public Long getEmpId() {		return empId;	}	public void setEmpId(Long empId) {		this.empId = empId;	}	public Long getRoleId() {		return roleId;	}	public void setRoleId(Long roleId) {		this.roleId = roleId;	}	@Override	public String toString() {		final StringBuilder sb = new StringBuilder("EmpRole{");		sb.append("id=").append(id);		sb.append(", empId=").append(empId);		sb.append(", roleId=").append(roleId);		sb.append('}');		return sb.toString();	}}