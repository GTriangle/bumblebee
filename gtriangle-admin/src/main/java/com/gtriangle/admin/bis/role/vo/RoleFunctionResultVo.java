package com.gtriangle.admin.bis.role.vo;

import java.util.List;

public class RoleFunctionResultVo {
	
	/**
	 * 功能Id
	 */
	private Long functionId;
	
	/**
	 * 功能名称
	 */
	private String funcName;
	
	/**
	 * 资源KEY
	 */
	private String funcKey;
	
	/**
	 * 是否选中
	 */
	private Boolean isSelected;
	
	/**
	 * 子分类
	 */
	private List<RoleFunctionResultVo> subRoleFucList;

	/**
	 * 父id
	 */
	private Long pid;
	
	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public List<RoleFunctionResultVo> getSubRoleFucList() {
		return subRoleFucList;
	}

	public void setSubRoleFucList(List<RoleFunctionResultVo> subRoleFucList) {
		this.subRoleFucList = subRoleFucList;
	}

	public String getFuncKey() {
		return funcKey;
	}

	public void setFuncKey(String funcKey) {
		this.funcKey = funcKey;
	}
}
