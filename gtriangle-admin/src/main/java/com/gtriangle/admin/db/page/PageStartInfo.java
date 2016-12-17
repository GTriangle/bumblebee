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

public class PageStartInfo {
	/**
	 * 开始
	 */
	private Integer start;
	
	/**
	 * 第几页
	 */
	private Integer pageNo;
	
	/**
	 * 
	 * 待查询条数
	 */
	private  Integer pageSize;

	/**
	 * 
	 * 将pageNo转换为start
	 * @param pageNo
	 * @param pageSize
	 */
	public void setPageInfo(Integer pageNo,Integer pageSize){
		if(null!=pageSize){
			this.start = (pageNo == null ? 0 : (pageNo-1)*pageSize);
		}
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		if(null!=pageNo){
			this.start = (pageNo == null ? 0 : (pageNo-1)*pageSize);
		}
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		if(null!=pageSize){
			this.start = (pageNo == null ? 0 : (pageNo-1)*pageSize);
		}
	}
	
}
