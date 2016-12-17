/**
 * 
 */
package com.gtriangle.admin.db.page;

/**
 * 分页基类信息
 * @author gaoyan_scj    
 * @version 1.0  
 * @created 2014-11-20 上午10:54:50
 */

public class PageInfo {
	
	/**
	 * 第几页
	 */
	private Integer pageNo;
	
	/**
	 * 
	 * 待查询条数
	 */
	private  Integer pageSize;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
}
