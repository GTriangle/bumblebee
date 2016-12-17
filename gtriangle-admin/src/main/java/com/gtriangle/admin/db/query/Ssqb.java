package com.gtriangle.admin.db.query;

import java.util.Map;

/**
 * mybatis方式 查询
* @author Brian
* @date 2016年1月19日 下午4:42:13
 */
public class Ssqb extends BaseQuery {
	
	protected Ssqb() {
		
	}
	
	protected Ssqb(String sqlMapId) {
		super(sqlMapId);
	}
	
	public static Ssqb create(String sqlMapId) {
		return new Ssqb(sqlMapId);
	}

	/**
	 * 设置参数
	 * 
	 * @param param  数据库字段
	 * @param value  值
	 * @return
	 */
	public Ssqb setParam(String param, Object value) {
		super.setParam(param, value);
		return this;
	}
	
	/**
	 * 设置参数。
	 * 
	 * @param paramMap  <数据库字段,值>
	 * @return
	 */
	public Ssqb setParams(Map<String, Object> paramMap) {
		super.setParams(paramMap);
		return this;
	}
	
	@Override
	protected Map<String, Object> voToMap() {
		return null;
	}
}
