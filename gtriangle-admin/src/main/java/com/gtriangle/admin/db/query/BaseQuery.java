package com.gtriangle.admin.db.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gtriangle.admin.db.query.expression.Criterion;
import org.springframework.util.Assert;

/**
 * 基础查询
* @author Brian   
* @date 2016年1月19日 下午4:38:56
 */
public abstract class BaseQuery {
	
	
	protected final String sqlMapId;
	
	protected Map<String, Object> paramsMap;
	
	protected List<OrderBy> orderBys;


	protected BaseQuery() {
		this.sqlMapId = null;
	}
	
	protected BaseQuery(String sqlMapId) {
		Assert.hasText(sqlMapId);
		this.sqlMapId = sqlMapId;
	}

	/**
	 * 已vo方式查询时 将VO对象转换成map
	 * @return
	 */
	protected abstract Map<String, Object> voToMap();
	/**
	 * 获取jdbc查询参数即where条件
	 * 由子类重写处理
	 * @return
	 */
	public List<Criterion> getJbdcCriterionList() {
		return null;
	}
	/**
	 * 将vo对象及其他查询参数转换成map
	 * @return
	 */
	public Map<String,Object> queryToMap() {
		Map<String, Object> map = this.getParamsMap();
		Map<String, Object> voMap = voToMap();
		if(voMap != null) {
			map.putAll(voMap);
		}
		
		return map;
	}
	
	/**
	 * 设置参数
	 * 
	 * @param param  数据库字段
	 * @param value  值
	 * @return
	 */
	protected BaseQuery setParam(String param, Object value) {
		getParamsMap().put(param, value);
		return this;
	}
	
	/**
	 * 设置参数。
	 * 
	 * @param paramMap  <数据库字段,值>
	 * @return
	 */
	protected BaseQuery setParams(Map<String, Object> paramMap) {
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			setParam(entry.getKey(), entry.getValue());
		}
		return this;
	}
	
	/**
	 * 设置排序方式
	 * 
	 * @param param
	 * @param value
	 * @return
	 */
//	protected BaseQuery setOrderBys(String column, OrderByType orderByType) {
//		getOrderBys().add(new OrderBy(column,orderByType));
//		return this;
//	}
	
	public Map<String, Object> getParamsMap() {
		if (paramsMap == null) {
			paramsMap = new HashMap<String, Object>();
		}
		return paramsMap;
	}

//	public List<Object> getValues() {
//		if (values == null) {
//			values = new ArrayList<Object>();
//		}
//		return values;
//	}

	public List<OrderBy> getOrderBys() {
		if (orderBys == null) {
			orderBys = new ArrayList<OrderBy>();
		}
		return orderBys;
	}

	public String getSqlMapId() {
		return sqlMapId;
	}

//	public void setSqlMapId(String sqlMapId) {
//		this.sqlMapId = sqlMapId;
//	}

	/**
	 * 排序方式
	 */
	public enum OrderByType {asc,desc};
	
	public class OrderBy implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String column;
		
		private OrderByType ot;

		public OrderBy(String column, OrderByType ot) {
			this.column = column;
			this.ot = ot;
		}

		public String getColumn() {
			return column;
		}

		public void setColumn(String column) {
			this.column = column;
		}

		public OrderByType getOt() {
			return ot;
		}

		public void setOt(OrderByType ot) {
			this.ot = ot;
		}
	}

}
