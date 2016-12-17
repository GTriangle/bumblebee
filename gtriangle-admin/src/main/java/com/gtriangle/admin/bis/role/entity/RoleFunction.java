package com.gtriangle.admin.bis.role.entity;


import com.gtriangle.admin.db.annotation.PrimaryKeyField;
import com.gtriangle.admin.db.annotation.Table;

import java.io.Serializable;

@Table(tableName = "t_b_role_function")
public class RoleFunction implements Serializable {

	private static final long serialVersionUID = 1L;

	//field

	/** 
	 * id 
	 */
	@PrimaryKeyField
	private Long id;

	/** 
	 * 角色ID 
	 */
	private Long roleId;

	/** 
	 * 权限ID 
	 */
	private Long funcId;

	//method
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFuncId() {
		return funcId;
	}
	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

	//override toString Method 
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("'id':'"+this.getId()+"',");
		sb.append("'roleId':'"+this.getRoleId()+"',");
		sb.append("'funcId':'"+this.getFuncId()+"'");
		sb.append("}");
		return sb.toString();
	}
}