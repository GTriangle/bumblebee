/**
 * 
 */
package com.gtriangle.admin;

/**
 * GTriangle appId
* @author Brian   
* @date 2016年2月25日 上午10:57:07
 */
public enum GTriangleAppId {
	
	/**
	 * GTriangle admin系统
	 */
	gtriangle_mgt("gtriangle_mgt");

	private final String value;
	
	private GTriangleAppId(final String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
