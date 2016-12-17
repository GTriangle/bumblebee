package com.gtriangle.admin.permission.auth.entity;

import java.io.Serializable;

public class PermUrlVo implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	
	private Long funcId;
	/** 
	 * 权限名称 
	 */
	private String permName;
	/** 
	 * 菜单路径 
	 */
	private String permUrl;
	/**
	 * 权限key值
	 */
	private String funcKey;
	
	
	public Long getFuncId() {
		return funcId;
	}
	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public String getPermUrl() {
		return permUrl;
	}
	public void setPermUrl(String permUrl) {
		this.permUrl = permUrl;
	}
	public String getFuncKey() {
		return funcKey;
	}
	public void setFuncKey(String funcKey) {
		this.funcKey = funcKey;
	}
	
	
	
}
