package com.gtriangle.admin.db.query;

import com.gtriangle.admin.db.query.expression.Criterion;
import com.gtriangle.admin.db.query.expression.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * jdbc查询
* @author Brian   
* @date 2016年1月19日 下午4:46:16
 */
public class QueryBuilder extends BaseQuery {
	
	/**
	 * sql 条件
	 */
	private List<Criterion> criterionList;
	
	
	protected QueryBuilder() {
		this.criterionList = new ArrayList<>();
	}

	protected QueryBuilder(Criterion... criterions) {
		this();
		for(Criterion c : criterions) {
			this.criterionList.add(c);
		}
	}

	public static QueryBuilder create() {
		return new QueryBuilder();
	}

	/**
	 * 静态方法，多条件之间 使用 and 连接
	 * @param criterions
	 * @return
	 */
	public static QueryBuilder where(Criterion... criterions) {
		return new QueryBuilder(criterions);
	}

	/**
	 *  由以下用法代替
	 *  Criteria c = Criteria.where(
		 Restrictions.eq("id", 123));
		 SaasQueryBuilder query = SaasQueryBuilder.create();
		 query.addCriteria(c);
	 * 设置参数。
	 *
	 * @param param  数据库字段
	 * @param value  值
	 * @return
	 */
	@Deprecated
	public QueryBuilder setParam(String param, Object value) {
		and(Restrictions.eq(param, value));
		return this;
	}

	/**
	 * 添加条件 and 链接
	 * @param criterion
	 * @return
	 */
	public QueryBuilder and(Criterion criterion) {
		criterionList.add(criterion);
		return this;
	}
	
	/**
	 * 设置排序方式
	 * @param column
	 * @param orderByType
	 * @return
	 */
	public QueryBuilder setOrderBys(String column, OrderByType orderByType) {
		getOrderBys().add(new OrderBy(column,orderByType));
		return this;
	}
	
	@Override
	public Map<String, Object> voToMap() {
		return null;
	}
	
	@Override
	public List<Criterion> getJbdcCriterionList() {
		return this.criterionList;
	}
}
