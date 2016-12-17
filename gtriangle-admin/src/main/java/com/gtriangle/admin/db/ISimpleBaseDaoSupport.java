package com.gtriangle.admin.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.gtriangle.admin.db.query.BaseQuery;

/**
 * 简单对象CRUD
 * @author Brian
 *
 * @param <T>
 * @param <ID>
 */
public interface ISimpleBaseDaoSupport {
	
	/**
     * 新增(不会将序列生成的ID,注入)
     * 效率较save(T t)高
     * @param t
     */
	<T> int create(T t);
     
    /**
     * 新增(会将序列生成的ID,注入)
     * @param t
     */
	<T> int save(T t);
     
     
    /**
     * 根据ID进行删除,支持物理删除和逻辑删除(字段中是否存在delFlag)
     * @param id
     */
	<T,ID extends Serializable> int deleteById(ID id,Class<T> clazz);
     
	/**
	 * 条件删除,支持物理删除和逻辑删除(字段中是否存在delFlag)
	 * 查询条件非空，如果为空报异常，如果条件确实为空，调用deleteAll
	 * @param query
	 * @return
	 */
	<T> int deleteByQuery(BaseQuery query, Class<T> clazz);
     
    /**
     * 更新,字段为空，则不进行更新，默认 MIDDLE 方式更新
     * @param t
     */
    <T> int update(T t);
    /**
     * 更新，指定字段更新方式
     * @param t
     */
    <T> int update(T t, UpdateMode updateMode);
    /**
     * 根据ID获取对象
     * @param id
     * @return
     */
    <T,ID extends Serializable> T queryById(ID id,Class<T> clazz);
    
    /**
     * 根据IDs获取对象列表
     * @param ids
     * @return
     */
    <T,ID extends Serializable> List<T> queryByIds(List<ID> ids, Class<T> clazz);

	 /**
     * 按照条件获取记录数
     * @return
     */
	<T> Integer queryForInt(BaseQuery query,Class<T> clazz);
    /**
     * 将符合query条件的数据全部更新为t的数据，其中t中为null的字段不更新
     * @param t      可以new一个bean出来 按照query去更新
     * @param query  更新条件
     * @return
     */
	<T> int updateByQuery(T t, BaseQuery query);
    /**
     * 按照条件查询单一对象
     * @param query
     * @return
     */
	<T> T query(BaseQuery query,Class<T> clazz);
    /**
     * 按照条件查询多个对象
     * @param query
     * @return
     */
	<T> List<T> queryList(BaseQuery query,Class<T> clazz);
    /**
     * 批量新增(不会将序列生成的ID,注入)
     * 效率较saveOfBatch(List<T> tList)高
     * @param tList
     */
	<T> void batchCreate(List<T> tList,Class<T> clazz);
    /**
     * 批量新增(会将序列生成的ID,注入)
     * @param tList
     */
	<T> void batchSave(List<? extends T> tList,Class<T> clazz);
    /**
     * 根据ids进行批量删除
     * @param ids
     * @param clazz
     * @return 返回结果为 该sql处理的行数，包含有影响和没有影响的行数 ，
     * 	                 如果需要返回结果，则ids需要去重，否则对处理返回结果有影响
     */
	<T,ID extends Serializable> int batchDelete(List<ID> ids,Class<T> clazz);

    /**
     * 批量更新
     * @param tList
     * @return 返回结果为该sql处理的行数，包含有影响和没有影响的行数
     */
	<T> int batchUpdate(List<T> tList,Class<T> clazz);

	/**
	 * 更新对象类
	 * 
	 * 提供三种更新模式：MAX, MIN, MIDDLE
	 * <ul>
	 * <li>MIDDLE：默认模式。除了null外，都更新。exclude和include例外。</li>
	 * <li>MAX：最大化更新模式。所有字段都更新（包括null）。exclude例外。</li>
	 * <li>MIN：最小化更新模式。所有字段都不更新。include例外。</li>
	 * </ul>
	 */
	public static enum UpdateMode {
		MAX, MIDDLE
	}
	
	/**
	 * 批量更新 封装参数
	 * @param sql
	 * @param batchArgs
	 * @return
	 */
	public boolean batchUpdateSql(String sql, List<Object[]> batchArgs);
	
	/**
	 * 原始sql查询
	 * @param sql
	 * @param args
	 * @return
	 */
	public Map<String, Object> querySql(String sql, Object[] args);
	
	/**
	 * 原始sql查询 list
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Map<String, Object>> queryListSql(String sql, Object[] args);

	

}
