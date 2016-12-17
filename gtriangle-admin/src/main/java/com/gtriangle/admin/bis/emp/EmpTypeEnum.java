/**
 * 
 */
package com.gtriangle.admin.bis.emp;

/**
 * 用户类型
* @author Brian   
* @date 2016年2月25日 上午10:57:07
 */
public enum EmpTypeEnum {
	
	/**
	 * 租户管理员
	 */
	TENANT_ADMIN("S_YHLX_ADMIN"),
	/**
	 * 员工
	 */
	EMPLOYEE("S_YHLX_EMP");
	
	
	private final String value;
	
	private EmpTypeEnum(final String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
