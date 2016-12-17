package com.gtriangle.admin.db;

import java.util.List;

/**
 * 
 * 此类描述的是：
 * 
 * @author diceng
 */
public interface IBaseDao {

    /**
     * 
     * 此方法描述的是：
     * 
     * @param <T>
     *            类模板
     * @param statement
     *            执行的sql
     * @param clazz
     *            返回的类
     * @return T
     */
    public <T> T findObj(String statement, Class<T> clazz);

    /**
     * 根据statement语句查询，结果不为List，而是一个POJO对象。
     * 
     * @param <T>
     *            类模板
     * @param clazz
     *            返回的类
     * @param id
     *            id
     * @param statement
     *            sql
     * @return T
     */
    public <T> T findObjById(String statement, String id, Class<T> clazz);

    /**
     * 根据statement语句查询，结果不为List，而是一个POJO对象。
     * 
     * @param <T>
     *            类模板
     * @param clazz
     *            返回的类
     * @param id
     *            id
     * @param statement
     *            sql
     * @return T
     */
    public <T> T findObjById(String statement, Long id, Class<T> clazz);

    /**
     * 根据statement语句和parameter参数查询确定一个POJO对象。
     * 
     * @param <T>
     *            类模板
     * @param clazz
     *            返回的类
     * @param parameter
     *            输入的参数
     * @param statement
     *            sql
     * @return T
     */
    public <T> T findObjByParameter(String statement, Object parameter, Class<T> clazz);

    /**
     * 根据statement语句查询，返回结果为所有记录。
     * 
     * @param <T>
     *            类模板
     * @param clazz
     *            返回的类
     * @param statement
     *            sql
     * @return T
     */
    public <T> List<T> findAll(String statement, Class<T> clazz);

    /**
     * 根据statement语句和parameter参数查询，返回结果为符合条件的集合。
     * 
     * @param <T>
     *            类模板
     * @param clazz
     *            返回的类
     * @param parameter
     *            输入的参数
     * @param statement
     *            sql
     * @return List<T>
     */
    public <T> List<T> findListByParameter(String statement, Object parameter, Class<T> clazz);

    /**
     * 
     * 
     * @param parameter
     *            参数
     * @param statement
     *            sql
     * @return int
     */
    public int findCount(String statement, Object parameter);

    /**
     * 
     * @param statement
     *            sql
     * @return int
     */
    public int findCount(String statement);

    /**
     * 
     * 
     * @param parameter
     *            参数
     * @param statement
     *            sql
     * @return int
     */
    public int save(String statement, Object parameter);

    /**
     * 
     * 
     * @param parameter
     *            参数
     * @param statement
     *            sql
     * @return int
     */
    public int update(String statement, Object parameter);

    /**
     * 
     * @param statement
     *            sql
     * @return int
     */
    public int delete(String statement);

    /**
     * 
     * 
     * @param parameter
     *            参数
     * @param statement
     *            sql
     * @return int
     */
    public int delete(String statement, Object parameter);
}
