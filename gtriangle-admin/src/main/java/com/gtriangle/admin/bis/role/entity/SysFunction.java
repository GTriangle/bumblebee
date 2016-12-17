package com.gtriangle.admin.bis.role.entity;import com.gtriangle.admin.db.annotation.PrimaryKeyField;import com.gtriangle.admin.db.annotation.Table;import java.io.Serializable;@Table(tableName = "sys_function")public class SysFunction implements Serializable {	private static final long serialVersionUID = 1L;	//field	/** 	 * id 	 */	@PrimaryKeyField	private Long id;	/** 	 * 资源名称 	 */	private String funcName;	/** 	 * 资源KEY 	 */	private String funcKey;	/** 	 * 原子标示 	 */	private Boolean isAtom;	/** 	 * 是否开放 	 */	private Boolean isOpen;	/** 	 * 模块ID 	 */	private Long moduleId;	/** 	 * 上级ID 	 */	private Long pid;	/**	 * 备注	 */	private String remark;	/** 	 * 删除标示 	 */	private Boolean dataStatus;	//method	public Long getId() {		return id;	}	public void setId(Long id) {		this.id = id;	}	public String getFuncName() {		return funcName;	}	public void setFuncName(String funcName) {		this.funcName = funcName;	}	public String getFuncKey() {		return funcKey;	}	public void setFuncKey(String funcKey) {		this.funcKey = funcKey;	}	public Boolean getIsAtom() {		return isAtom;	}	public void setIsAtom(Boolean isAtom) {		this.isAtom = isAtom;	}	public Boolean getIsOpen() {		return isOpen;	}	public void setIsOpen(Boolean isOpen) {		this.isOpen = isOpen;	}	public Long getModuleId() {		return moduleId;	}	public void setModuleId(Long moduleId) {		this.moduleId = moduleId;	}	public Long getPid() {		return pid;	}	public void setPid(Long pid) {		this.pid = pid;	}	public Boolean getDataStatus() {		return dataStatus;	}	public void setDataStatus(Boolean dataStatus) {		this.dataStatus = dataStatus;	}	public String getRemark() {		return remark;	}	public void setRemark(String remark) {		this.remark = remark;	}	@Override	public String toString() {		final StringBuilder sb = new StringBuilder("SysFunction{");		sb.append("id=").append(id);		sb.append(", funcName='").append(funcName).append('\'');		sb.append(", funcKey='").append(funcKey).append('\'');		sb.append(", isAtom=").append(isAtom);		sb.append(", isOpen=").append(isOpen);		sb.append(", moduleId=").append(moduleId);		sb.append(", pid=").append(pid);		sb.append(", remark='").append(remark).append('\'');		sb.append(", dataStatus=").append(dataStatus);		sb.append('}');		return sb.toString();	}}