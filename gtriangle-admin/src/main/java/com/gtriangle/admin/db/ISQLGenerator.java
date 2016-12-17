package com.gtriangle.admin.db;

import java.util.List;

import com.gtriangle.admin.db.query.BaseQuery;

public interface ISQLGenerator {
	
	/**
	 * insert sql
	 * @param t
	 * @return
	 */
	public <T> CreateSqlResult sql_create(T t);
	/**
	 * delete by id sql
	 * @param id
	 * @return
	 */
	public <PK> String sql_deleteById(PK id);
	
	/**
	 * delete by ids sql
	 * @param ids
	 * @return
	 */
	public <PK> CreateSqlResult sql_deleteById(List<PK> ids);
	
	/**
	 * delete by query sql
	 * 删除条件非空
	 * @param t
	 * @return
	 */
	public CreateSqlResult sql_deleteByQuery(BaseQuery builder);
	/**
	 * update by id sql
	 * @param t
	 * @return
	 */
	public <T> CreateSqlResult sql_update(T t);
	/**
	 * update by id sql
	 * @param t
	 * @return
	 */
	public <T> CreateSqlResult sql_update(T t, ISimpleBaseDaoSupport.UpdateMode updateMode);
	/**
	 * update by query sql
	 * @param t
	 * @return
	 */
	public <T> CreateSqlResult sql_update(T t, BaseQuery builder);
	/**
	 * 批量更新所采用的sql拼装接口
	 * @param t
	 * @param builder
	 * @return
	 */
	//public <T> CreateSqlResult sql_batch_update(T t, BaseQuery builder);
	/**
	 * selete by id sql
	 * @param id
	 * @return
	 */
	public <PK> String sql_queryById(PK id);
	/**
	 * selete by ids sql
	 * @param ids
	 * @return
	 */
	public <PK> String sql_queryByIds(List<PK> ids);
	
	/**
	 * 条件查询
	 * 
	 * @param builder
	 * @return
	 */
	public CreateSqlResult sql_queryByQueryBuilder(BaseQuery builder);
	/**
	 * 生成查询数量的SQL
	 * 
	 * @return
	 */
	public CreateSqlResult sql_queryForNumber(BaseQuery query);
}
