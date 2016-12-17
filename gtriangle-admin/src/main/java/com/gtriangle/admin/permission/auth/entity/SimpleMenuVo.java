package com.gtriangle.admin.permission.auth.entity;

import java.io.Serializable;

public class SimpleMenuVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
//	private String permUrl;
	
	private String router;
	
	public SimpleMenuVo() {}
	
	public SimpleMenuVo(String name, String router) {
		this.name = name;
		//this.permUrl = permUrl;
		this.router = router;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getPermUrl() {
//		return permUrl;
//	}
//
//	public void setPermUrl(String permUrl) {
//		this.permUrl = permUrl;
//	}

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

}
