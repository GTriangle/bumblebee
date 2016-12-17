package com.gtriangle.admin.permission.auth.entity;

import java.util.ArrayList;
import java.util.List;

public class MenuVo extends SimpleMenuVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String icon;
	
	private List<SimpleMenuVo> sub;
	
	private String key;
	
	
	public MenuVo() {
		this.sub = new ArrayList<SimpleMenuVo>();
	}
	
	public MenuVo(String name, String icon, String key) {
		this();
		super.setName(name);
		this.icon = icon;
		this.key = key;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<SimpleMenuVo> getSub() {
		return sub;
	}

	public void setSub(List<SimpleMenuVo> sub) {
		this.sub = sub;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
	
	

}
