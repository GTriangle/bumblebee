package com.gtriangle.admin.db.jdbc;

import com.gtriangle.admin.db.*;
import com.gtriangle.admin.db.query.BaseQuery;
import com.gtriangle.admin.db.query.PropertyValue;
import com.gtriangle.admin.db.query.expression.Criterion;
import com.gtriangle.admin.util.DateFormatUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * SQL生成器
 * @author Brian
 *
 */
public class JdbcSQLGenerator implements ISQLGenerator {

	/**
	 * db 字段 set
	 */
	private Set<String>    columnSet;
	/**
	 * 表名
	 */
	private String        tableName;
	/**
	 * 字段 分割,
	 * FIXME Brian 字段中穿着数据库关键词时会出问题，应使用 ` 包起来
	 */
	private String        columnsStr;
	/**
	 * 数据库主键名称
	 */
	private String        dbPrimaryKeyName;

	public static final String DEFAULT_VALUE = "?";
	/**
	 * 逻辑删除字段
	 */
	public static final String LOGIC_DELETE_COLUMN = "dataStatus";

	public JdbcSQLGenerator(String tableName, String dbPrimaryKeyName,Set<String> columnSet) {
		super();
		this.columnSet = columnSet;
		this.tableName = tableName;
		this.dbPrimaryKeyName = dbPrimaryKeyName;
		this.columnsStr = StringUtils.join(this.columnSet, ",");
	}

	/**
	 * 生成新增的SQL
	 * 
	 * @param t
	 * @return
	 */
	public <T> CreateSqlResult sql_create(T t) {
		
		Map<String,Object> columnValues = buildInsertColumn(t);
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("INSERT INTO ").append(tableName).append("(")
		.append(StringUtils.join(columnValues.keySet(), ",")).append(") VALUES (")
		.append(getDefaultValueStr(columnValues.size())).append(")");
		String sql = sql_build.toString();
		
		return new JdbcCreateSqlResult(sql, columnValues.values());
	}


	/**
	 * 提供给生成新增SQL 使用
	 * 
	 * @param t
	 * @return
	 */
	private <T> Map<String,Object> buildInsertColumn(T t) {
		Map<String,Object> colValMap = new LinkedHashMap<String,Object>();
		for (String column : columnSet) {
			Object value = ReflectionUtil.obtainFieldValue(t,column);
			if (value != null ) {
				colValMap.put("`" +column + "`", handleValue(value));
			}
		}
		return colValMap;
	}

	/**
	 * 处理value
	 * 
	 * @param value
	 * @return
	 */
	private Object handleValue(Object value) {
			if (value instanceof Date) {
			Date date = (Date) value;
			String dateStr = DateFormatUtils.formatDate(date, null);
			value = dateStr;
		} 
		else if (value instanceof Boolean) {
			Boolean v = (Boolean) value;
			value = v ? 1 : 0;
		}
		return value;
	}

	/**
	 * 生成根据ID删除的SQL
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public <PK> String sql_deleteById(PK id) {
		
		StringBuilder sql_build = new StringBuilder();
		
		if(isLogicDelete()) {
			sql_build.append("UPDATE ").append(this.tableName)
			.append(" SET ").append(LOGIC_DELETE_COLUMN).append(" =0")
			.append(" WHERE ").append(dbPrimaryKeyName).append(" = ").append(DEFAULT_VALUE);
		} else {
			sql_build.append("DELETE FROM ").append(this.tableName)
			.append(" WHERE ").append(dbPrimaryKeyName).append(" = ").append(DEFAULT_VALUE);
		}

		String sql = sql_build.toString();

		return sql;
	}
	
	@Override
	public <PK> CreateSqlResult sql_deleteById(List<PK> ids) {
		String idSqlStr = "";
		for (PK idSplit : ids) {
			idSqlStr = idSqlStr + DEFAULT_VALUE+",";
		}
		idSqlStr = idSqlStr.substring(0,idSqlStr.lastIndexOf(","));
		StringBuilder sql_build = new StringBuilder();
		if(isLogicDelete()) {
			sql_build.append("UPDATE ").append(this.tableName)
			.append(" SET ").append(LOGIC_DELETE_COLUMN).append(" =0")
			.append(" WHERE ").append(dbPrimaryKeyName).append(" IN( ").append(idSqlStr).append(" )");
		} else {
			sql_build.append("DELETE FROM ").append(this.tableName)
			.append(" WHERE ").append(dbPrimaryKeyName).append(" IN( ").append(idSqlStr).append(" )");
		}
		
		String sql = sql_build.toString();
		List<Object> finalValues = new ArrayList<Object>();
		finalValues.addAll(ids);

		return new JdbcCreateSqlResult(sql, finalValues);
	}

	@Override
	public CreateSqlResult sql_deleteByQuery(BaseQuery builder) {
		
		StringBuilder sql_build = new StringBuilder();
		if(isLogicDelete()) {
			sql_build.append("UPDATE ").append(this.tableName)
			.append(" SET ").append(LOGIC_DELETE_COLUMN).append(" = 0");
		} else {
			sql_build.append("DELETE FROM ").append(this.tableName);
		}
		
		List<Object> finalValues = new ArrayList<Object>();
		
		String whereBuilder = buildWhereCondition(builder, finalValues);
	
		if(whereBuilder.length() > 0) {
			sql_build.append(" WHERE ").append(whereBuilder);
		}else{
			throw new DBRuntimeException("数据库运行异常", sql_build.toString());
		}
		
		String sql = sql_build.toString();
		return new JdbcCreateSqlResult(sql, finalValues);
	}
	/**
	 * 生成更新的SQL
	 * 
	 * @param t
	 * @return
	 */
	public <T> CreateSqlResult sql_update(T t) {
		return sql_update(t, ISimpleBaseDaoSupport.UpdateMode.MIDDLE);
	}
	
	@Override
	public <T> CreateSqlResult sql_update(T t, ISimpleBaseDaoSupport.UpdateMode updateMode) {

		Map<String,Object> columnValues = buildUpdateColumn(t, updateMode);
		Object id = ReflectionUtil.obtainFieldValue(t,dbPrimaryKeyName);
		id = handleValue(id);

		StringBuilder sql_build = new StringBuilder();
		sql_build.append("UPDATE ").append(tableName).append(" SET ")
		.append(StringUtils.join(columnValues.keySet(), ",")).append(" WHERE ")
		.append(dbPrimaryKeyName).append(" = ").append(DEFAULT_VALUE);

		Collection<Object> coll = columnValues.values();
		List<Object> values = new ArrayList<Object>(coll);
		values.add(id);
		String sql = sql_build.toString();
		return new JdbcCreateSqlResult(sql, values);
	}

	
	@Override
	public <T> CreateSqlResult sql_update(T t, BaseQuery builder) {
		
		Map<String,Object> columnValues = buildUpdateColumn(t, ISimpleBaseDaoSupport.UpdateMode.MIDDLE);
		List<Object> columnValueList = new ArrayList<Object>( columnValues.values());
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("UPDATE ").append(tableName).append(" SET ")
		.append(StringUtils.join(columnValues.keySet(), ","));
		
		String whereBuilder = buildWhereCondition(builder, columnValueList);

		//封装where条件,既无主键又没有paramMap 抛异常处理
		if(whereBuilder.length() > 0){
			sql_build.append(" WHERE ").append(whereBuilder);
		}else{
			throw new DBRuntimeException("数据库运行异常", sql_build.toString());
		}

		String sql = sql_build.toString();

		return new JdbcCreateSqlResult(sql, columnValueList);
	}

	/**
	 * 提供给生成更新SQL使用
	 * 
	 * @param t
	 * @param updateMode
	 * @return
	 */
	private <T> Map<String,Object> buildUpdateColumn(T t, ISimpleBaseDaoSupport.UpdateMode updateMode) {
		Map<String,Object> colValMap = new LinkedHashMap<String,Object>();
		for (String column : columnSet) {
			Object value = ReflectionUtil.obtainFieldValue(t,column);
			//主键和租户ID不参与更新
			//FIXME Brian 是否需要处理有待观察 return "'" + value.replaceAll("'", "''") + "'";
			if(!StringUtils.equalsIgnoreCase(column, dbPrimaryKeyName)) {
				if(updateMode == ISimpleBaseDaoSupport.UpdateMode.MAX) {
					colValMap.put("`" +column + "`=" + DEFAULT_VALUE, value);
				} else if(value != null) {
					colValMap.put("`" +column + "`=" + DEFAULT_VALUE, value);
				}
			}
		}
		return colValMap;
	}
	
	/**
	 * 生成根据ID查询的SQL
	 * 
	 * @param id
	 * @return
	 */
	public <PK> String sql_queryById(PK id) {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT ").append(columnsStr).append(" FROM ").append(this.tableName)
		.append(" WHERE ").append(dbPrimaryKeyName).append(" = ").append(DEFAULT_VALUE);
		return sql_build.toString();
	}
	
	/**
	 * 生成根据ID查询的SQL
	 * 
	 * @param ids
	 * @return
	 */
	public <PK> String sql_queryByIds(List<PK> ids) {
		String idSqlStr = "";
		for (PK idSplit : ids) {
			idSqlStr = idSqlStr + DEFAULT_VALUE+",";
		}
		idSqlStr = idSqlStr.substring(0,idSqlStr.lastIndexOf(","));
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT ").append(columnsStr).append(" FROM ").append(this.tableName)
		.append(" WHERE ").append(dbPrimaryKeyName).append(" IN (").append(idSqlStr).append(" )");
		return sql_build.toString();
	}
	/**
	 * 生成查询的SQL
	 * 
	 * @param builder
	 * @return
	 */
	@Override
	public CreateSqlResult sql_queryByQueryBuilder(BaseQuery builder) {

		List<Object> finalValues = new ArrayList<Object>();
		String sql = toStatementString(columnsStr
				                     , buildWhereCondition(builder, finalValues)
				                     , buildOrderByCondition(builder));
		
		return new JdbcCreateSqlResult(sql, finalValues);
	}
	
	/**
	 * 组建查询sql
	 * @param selectClause
	 * @param whereCondition
	 * @return
	 */
	private String toStatementString(String selectClause,String whereCondition) {
		return toStatementString(selectClause, whereCondition, "");
	}
	/**
	 * 组建查询sql
	 * @param selectClause
	 * @param whereCondition
	 * @param orderby
	 * @return
	 */
	private String toStatementString(String selectClause,String whereCondition, String orderby) {
		int guesstimatedBufferSize = 20;
		guesstimatedBufferSize += selectClause.length() + whereCondition.length() + orderby.length();
		StringBuilder buf = new StringBuilder(guesstimatedBufferSize);
		
		buf.append("SELECT ").append(selectClause)
		.append(" FROM ").append(this.tableName);
		
		if(StringUtils.isNotBlank(whereCondition)) {
			buf.append(" WHERE " ).append(whereCondition);
		}
		
		if(StringUtils.isNotBlank(orderby)) {
			buf.append(" ORDER BY " ).append(orderby);
		}
		
		return buf.toString();
	}
	/**
	 * 构建where添加
	 * @param builder
	 * @param finalValues
	 * @return
	 */
	private String buildWhereCondition(BaseQuery builder,List<Object> finalValues) {
		
		
		List<Criterion> criterionList = builder.getJbdcCriterionList();
		StringBuilder whereBuilder = new StringBuilder(30);
		Iterator<Criterion> iteratorCriterion = criterionList.iterator();
		while(iteratorCriterion.hasNext()) {
			Criterion c = iteratorCriterion.next();
			PropertyValue propertyValues = c.getPropertyValues();
			if(columnSet.contains(propertyValues.getPropertyName())) {
				whereBuilder.append(c.toSqlString()).append( " AND " );
				if(!Criterion.NO_VALUES.equals(propertyValues.getValue())) {
					Object value = propertyValues.getValue();
					if(value.getClass().isArray()) {
						Object[] arrValues = (Object[])value;
						for(Object o : arrValues) 
							finalValues.add(o);
					}
					else 
						finalValues.add(value);
				}
			}
		}

		if(whereBuilder.length() > 0) {
			return whereBuilder.substring(0, whereBuilder.length() - 4);
		}
		
		return whereBuilder.toString();
	}
	/**
	 * 构建orderby 条件
	 * @param builder
	 * @return
	 */
	private String buildOrderByCondition(BaseQuery builder) {
		
		List<BaseQuery.OrderBy> orderBys = builder.getOrderBys();
		StringBuilder orderByBuilder = new StringBuilder();
		if(orderBys != null) {
			Iterator<BaseQuery.OrderBy> it = orderBys.iterator();
			while(it.hasNext()) {
				BaseQuery.OrderBy ob = it.next();
				String column = ob.getColumn();
				if(columnSet.contains(column)) {
					orderByBuilder.append("`").append(column).append("`").append(ob.getOt() == BaseQuery.OrderByType.desc ? " DESC" : " ASC");
					if(it.hasNext()) orderByBuilder.append(" , ");
				}
			}
		}
		return orderByBuilder.toString();
	}
	/**
	 * 生成查询数量的SQL
	 * 
	 * @return
	 */
	@Override
	public CreateSqlResult sql_queryForNumber(BaseQuery builder) {
		
		List<Object> finalValues = new ArrayList<Object>();
		
		String sql = toStatementString("COUNT(1)"
                , buildWhereCondition(builder, finalValues));

		return new JdbcCreateSqlResult(sql, finalValues);
	}
	
	
	private String getDefaultValueStr(int count) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<count; i++) {
			if(i != count - 1)
				sb.append(DEFAULT_VALUE).append(",");
			else
				sb.append(DEFAULT_VALUE);
		}
		
		return sb.toString();
	}
	
	private boolean isLogicDelete() {
		return columnSet.contains(LOGIC_DELETE_COLUMN);
		
	}

	
}
