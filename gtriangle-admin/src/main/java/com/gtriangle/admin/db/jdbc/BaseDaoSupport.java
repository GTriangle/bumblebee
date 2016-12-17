package com.gtriangle.admin.db.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.gtriangle.admin.db.ReflectionUtil;
import com.gtriangle.admin.db.init.AnnotationEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gtriangle.admin.db.CreateSqlResult;
import com.gtriangle.admin.db.DBRuntimeException;
import com.gtriangle.admin.db.ISimpleBaseDaoSupport;
import com.gtriangle.admin.db.init.InitAnnotationEntity;
import com.gtriangle.admin.db.mybatis.GenericMybatisDao;
import com.gtriangle.admin.db.query.BaseQuery;
import com.gtriangle.admin.db.saas.IIdWorker;

/**
 * jdbc方式对 对象进行CRUD操作
 * @author Brian
 *
 */
@Repository
public class BaseDaoSupport extends GenericMybatisDao implements ISimpleBaseDaoSupport {

	@Resource(name = "jdbcTemplate")
	private GTriangleJdbcTemplate jdbcTemplate;

	@Resource(name = "idWorker")
	protected IIdWorker idWorker;

	/**
	 * 批处理flush db 上限
	 */
	private static final int FLUSH_CRITICAL_VAL = 500;

	@Override
	public <T> int create(T t) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(t.getClass().getCanonicalName());
		CreateSqlResult sqlResult = annotationEntity.getSqlGenerator().sql_create(t);
		int result = 0;
		String sql = sqlResult.getSql();
		try {
			Object primaryKeyValue = ReflectionUtil.invokeGetterMethod(t, annotationEntity.getObjectPrimaryKeyName());
			if(primaryKeyValue == null) {
				//主键没有设置值的时候 采用数据库自增主键方式获取
				KeyHolder keyHolder = new GeneratedKeyHolder();
				result = jdbcTemplate.update(sql, keyHolder, sqlResult.getSqlValues().toArray());
				Long id = keyHolder.getKey().longValue();
				ReflectionUtil.setFieldValue(t, annotationEntity.getObjectPrimaryKeyName(), id);
				if(logger.isDebugEnabled()) {
					logger.debug(sqlResult.getSql());
		    		logger.debug("  jdbc params {}", sqlResult.getSqlValues());
				}
				return result;
			}else {
				result = jdbcTemplate.update(sql, sqlResult.getSqlValues().toArray());;
				if(logger.isDebugEnabled()) {
					logger.debug(sqlResult.getSql());
		    		logger.debug("  jdbc params {}", sqlResult.getSqlValues());
				}
				return result;
			}
		} catch(Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}

	@Override
	public <T> int save(T t) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(t.getClass().getCanonicalName());
		if(idWorker == null) throw new IllegalArgumentException("idWorker is required");
		Long nextval = idWorker.nextId();
		logger.debug("create saas table primaryKey = {}", nextval);
		ReflectionUtil.invokeSetterMethod(t, annotationEntity.getObjectPrimaryKeyName(), nextval);

		return this.create(t);
	}

	
	@Override
	public <T,ID extends Serializable> int deleteById(ID id,Class<T> clazz) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		try {
			String sql = annotationEntity.getSqlGenerator().sql_deleteById(id);
			int result = jdbcTemplate.update(sql, id);
			if(logger.isDebugEnabled()) {
				logger.debug(sql);
	    		logger.debug("  jdbc params primaryKey {}", id);
			}
			return result;
		} catch(Exception e) {
			throw new DBRuntimeException(e,annotationEntity.getSqlGenerator().sql_deleteById(id));
		}
	}

	@Override
	public <T> int deleteByQuery(BaseQuery query,Class<T> clazz) {
		String sql = null;
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		try {
			CreateSqlResult sqlResult = annotationEntity.getSqlGenerator().sql_deleteByQuery(query);
			sql = sqlResult.getSql();
			int result = jdbcTemplate.update(sql, sqlResult.getSqlValues().toArray());
			if(logger.isDebugEnabled()) {
				logger.debug(sqlResult.getSql());
	    		logger.debug("  jdbc params  {}", sqlResult.getSqlValues());
			}
			return result ;
			
		} catch(Exception e) {
			throw new DBRuntimeException(e,sql);
		}
	}

	@Override
	public <T> int update(T t) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(t.getClass().getCanonicalName());
		String sql = null;
		try {
			CreateSqlResult sqlResult = annotationEntity.getSqlGenerator().sql_update(t);
			sql = sqlResult.getSql();
			int result = jdbcTemplate.update(sql, sqlResult.getSqlValues().toArray());
			if(logger.isDebugEnabled()) {
				logger.debug(sqlResult.getSql());
	    		logger.debug("  jdbc params  {}", sqlResult.getSqlValues());
			}
			return result;
		} catch(Exception e) {
			throw new DBRuntimeException(e,sql);
		}

	}
	@Override
	public <T> int update(T t, UpdateMode updateMode) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(t.getClass().getCanonicalName());
		String sql = null;
		try {
			CreateSqlResult sqlResult = annotationEntity.getSqlGenerator().sql_update(t, updateMode);
			sql = sqlResult.getSql();
			int result = jdbcTemplate.update(sql, sqlResult.getSqlValues().toArray());
			if(logger.isDebugEnabled()) {
				logger.debug(sqlResult.getSql());
	    		logger.debug("  jdbc params  {}", sqlResult.getSqlValues());
			}
			return result;
		} catch(Exception e) {
			throw new DBRuntimeException(e,sql);
		}
	}
	/**
	 * 将符合query条件的数据全部更新为t的数据，其中t中为null的字段不更新
	 * @param t      可以new一个bean出来 按照query去更新
	 * @param query  更新条件
	 * @return
	 */
	@Override
	public <T> int updateByQuery(T t, BaseQuery query) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(t.getClass().getCanonicalName());
		String sql = null;
		try {
			CreateSqlResult sqlResult = annotationEntity.getSqlGenerator().sql_update(t, query);
			sql = sqlResult.getSql();
			int result = jdbcTemplate.update(sqlResult.getSql(), sqlResult.getSqlValues().toArray());
			
			if(logger.isDebugEnabled()) {
				logger.debug(sqlResult.getSql());
	    		logger.debug("  jdbc params {}", sqlResult.getSqlValues());
			}
			return result;
			
		} catch(Exception e) {
			throw new DBRuntimeException(e,sql);
		}
	}

	@Override
	public <T,ID extends Serializable> T queryById(ID id,Class<T> clazz) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		try {
			String sql = annotationEntity.getSqlGenerator().sql_queryById(id);
			Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, id);
			if(logger.isDebugEnabled()) {
				logger.debug(sql);
	    		logger.debug("  jdbc params primaryKey {}", id);
			}
			return handleResult(resultMap, clazz);
			//EmptyResultDataAccessException
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(RuntimeException e) {
			throw new DBRuntimeException(e, "sql:" + annotationEntity.getSqlGenerator().sql_queryById(id));
		}
	}
	
	@Override
	public <T,ID extends Serializable> List<T> queryByIds(List<ID> ids,Class<T> clazz) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		try {
			String sql = annotationEntity.getSqlGenerator().sql_queryByIds(ids);
			List<Map<String, Object>> resultMapList = jdbcTemplate.queryForList(sql, ids.toArray());
			if(logger.isDebugEnabled()) {
				logger.debug(sql);
	    		logger.debug("  jdbc params primaryKey {}", ids);
			}
			List<T> tList = new ArrayList<T>(resultMapList.size());
			for (Map<String, Object> resultMap : resultMapList) {
				T t = handleResult(resultMap, clazz, annotationEntity);
				tList.add(t);
			}
			return tList;
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(RuntimeException e) {
			throw new DBRuntimeException(e, "sql:" + annotationEntity.getSqlGenerator().sql_queryByIds(ids));
		}
	}

	@Override
	public <T> T query(BaseQuery query,Class<T> clazz) {
		String sql = null;
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		CreateSqlResult sqlResult = null;
		try {
			sqlResult = annotationEntity.getSqlGenerator().sql_queryByQueryBuilder(query);
			sql = sqlResult.getSql();
			Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, sqlResult.getSqlValues().toArray());
			if(logger.isDebugEnabled()) {
				logger.debug(sqlResult.getSql());
	    		logger.debug("  jdbc params {}", sqlResult.getSqlValues());
			}
			return handleResult(resultMap, clazz);
		} catch(EmptyResultDataAccessException e) {
			if(logger.isDebugEnabled() && sqlResult != null) {
	    		logger.debug("  jdbc params {}", sqlResult.getSqlValues());
			}
			return null;
		} catch(RuntimeException e) {
			throw new DBRuntimeException(e, sql);
		}

	}

	@Override
	public <T> Integer queryForInt(BaseQuery query,Class<T> clazz) {
		String sql = null;
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		try {
			CreateSqlResult sqlResult = annotationEntity.getSqlGenerator().sql_queryForNumber(query);
			sql = sqlResult.getSql();
			Integer count = jdbcTemplate.queryForObject(sql, sqlResult.getSqlValues().toArray(), Integer.class);
			if(logger.isDebugEnabled()) {
				logger.debug(sqlResult.getSql());
				logger.debug("  jdbc result {}", count);
			}
			return count;
		} catch(Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}
	
	@Override
	public <T> List<T> queryList(BaseQuery query,Class<T> clazz) {
		String sql = null;
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		try {
			CreateSqlResult sqlResult = annotationEntity.getSqlGenerator().sql_queryByQueryBuilder(query);
			sql = sqlResult.getSql();
			List<Map<String, Object>> resultMapList = jdbcTemplate.queryForList(sql, sqlResult.getSqlValues().toArray());
			if(logger.isDebugEnabled()) {
				logger.debug(sqlResult.getSql());
	    		logger.debug("  jdbc params {}", sqlResult.getSqlValues());
			}
			List<T> tList = new ArrayList<T>(resultMapList.size());
			for (Map<String, Object> resultMap : resultMapList) {
				T t = handleResult(resultMap, clazz, annotationEntity);
				tList.add(t);
			}
			return tList;
		} catch(Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}
	
	private <T> T handleResult(Map<String, Object> resultMap, Class<T> tClazz) {
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(tClazz.getCanonicalName());
		return handleResult(resultMap, tClazz, annotationEntity);
	}
	
	private <T> T handleResult(Map<String, Object> resultMap, Class<T> tClazz, AnnotationEntity annotationEntity) {
		T t = null;
		try {
			t = tClazz.newInstance();
		} catch (InstantiationException e) {
			logger.error("/********************************");
			logger.error("封装查询结果时，实例化对象(" + tClazz + ")时，出现异常!"
					+ e.getMessage());
			logger.error("/********************************");
		} catch (IllegalAccessException e) {
			logger.error("/********************************");
			logger.error("封装查询结果时，实例化对象(" + tClazz + ")时，出现异常!"
					+ e.getMessage());
			logger.error("/********************************");
		}
		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			ReflectionUtil.setFieldValue(t, key, val);
		}
		if(logger.isDebugEnabled()&& resultMap != null) {
			//logger.debug("RESULT columns = {}" ,resultMap.keySet());
			logger.debug("  jdbc result {}" ,resultMap.values());
		}
		return t;
	}



	@Override
	public <T> void batchCreate(List<T> tList,Class<T> clazz) {
		if(null == tList || tList.isEmpty()){
			return;
		}
		int len = tList.size(), i = 0;
		//获取列表的第一个对象的pk的value
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		CreateSqlResult sqlResult = annotationEntity.getSqlGenerator().sql_create(tList.get(0));
		List<Object[]> valueList = new ArrayList<Object[]>();
		for (; i < len; i++) {
			T t = tList.get(i);
			sqlResult= annotationEntity.getSqlGenerator().sql_create(t);
			valueList.add(sqlResult.getSqlValues().toArray());
			if (i > 0 && i % FLUSH_CRITICAL_VAL == 0) {
				
				jdbcTemplate.batchUpdate(sqlResult.getSql(), valueList);
				valueList = new ArrayList<Object[]>();
			}
		}
		if(valueList.size() > 0)
			jdbcTemplate.batchUpdate(sqlResult.getSql(), valueList);
	}

	@Override
	public <T> void batchSave(List<? extends T> tList,Class<T> clazz) {
		if(null == tList || tList.isEmpty()){
			return;
		}
		
		if(idWorker == null) throw new IllegalArgumentException("idWorker is required");
		
		int len = tList.size(), i = 0;
		//获取列表的第一个对象的pk的value
		CreateSqlResult sqlResult = null;
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		HashMap<String,List<Object[]>> sqlMap = new HashMap<String,List<Object[]>>();
		for (; i < len; i++) {
			T t = tList.get(i);
			
			Long nextval = idWorker.nextId();
			ReflectionUtil.invokeSetterMethod(t, annotationEntity.getObjectPrimaryKeyName(), nextval);
			sqlResult= annotationEntity.getSqlGenerator().sql_create(t);
			if(sqlMap.get(sqlResult.getSql())==null){
				List<Object[]> sqlValueList = new ArrayList<Object[]>();
				sqlValueList.add(sqlResult.getSqlValues().toArray());
				sqlMap.put(sqlResult.getSql(), sqlValueList);
			}else{
				sqlMap.get(sqlResult.getSql()).add(sqlResult.getSqlValues().toArray());
			}
		}
		if(!sqlMap.isEmpty()){
			for(Entry<String,List<Object[]>> entry: sqlMap.entrySet()){
				String handleSql = entry.getKey();
				List<Object[]> sqlValues = entry.getValue();
				int step = (sqlValues.size() / FLUSH_CRITICAL_VAL) + 1;
				for(int ii =0;ii<step;ii++){
					if(ii*FLUSH_CRITICAL_VAL <sqlValues.size()){
						List<Object[]> subSqlValues = 
								sqlValues.subList(ii*FLUSH_CRITICAL_VAL, sqlValues.size() > (ii+1)*FLUSH_CRITICAL_VAL?(ii+1)*FLUSH_CRITICAL_VAL:sqlValues.size());
						jdbcTemplate.batchUpdate(handleSql, subSqlValues);
					}
				}
			}
		}
	}
	
	@Override
	public <T,ID extends Serializable> int batchDelete(List<ID> ids,Class<T> clazz) {
		int result = 0;
		if(null == ids || ids.isEmpty()){
			return result;
		}
		
		int len = ids.size(), i = 0;
		//获取列表的第一个对象的pk的value
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		CreateSqlResult sqlResult = null;
		List<ID> tmpIds = new ArrayList<>();
		for (; i < len; i++) {
			tmpIds.add(ids.get(i));
			if (i > 0 && i % FLUSH_CRITICAL_VAL == 0) {
				sqlResult= annotationEntity.getSqlGenerator().sql_deleteById(tmpIds);
				result = result + jdbcTemplate.update(sqlResult.getSql(), sqlResult.getSqlValues().toArray());
				tmpIds = new ArrayList<>();
			}
		}
		if(tmpIds.size() > 0){
			sqlResult= annotationEntity.getSqlGenerator().sql_deleteById(tmpIds);
			result = result + jdbcTemplate.update(sqlResult.getSql(), sqlResult.getSqlValues().toArray());
		}
		return result;
	}
	
	@Override
	public <T> int batchUpdate(List<T> tList,Class<T> clazz) {
		int result = 0;
		if(null == tList || tList.isEmpty()){
			return result;
		}
		int len = tList.size(), i = 0;
		//获取列表的第一个对象的pk的value
		AnnotationEntity annotationEntity = InitAnnotationEntity.classesMap.get(clazz.getCanonicalName());
		CreateSqlResult sqlResult = null;
		HashMap<String,List<Object[]>> sqlMap = new HashMap<String,List<Object[]>>();
		for (; i < len; i++) {
			T t = tList.get(i);
			sqlResult= annotationEntity.getSqlGenerator().sql_update(t);
			if(sqlMap.get(sqlResult.getSql())==null){
				List<Object[]> sqlValueList = new ArrayList<Object[]>();
				sqlValueList.add(sqlResult.getSqlValues().toArray());
				sqlMap.put(sqlResult.getSql(), sqlValueList);
			}else{
				sqlMap.get(sqlResult.getSql()).add(sqlResult.getSqlValues().toArray());
			}
		}
		if(!sqlMap.isEmpty()){
			for(Entry<String,List<Object[]>> entry: sqlMap.entrySet()){
				String handleSql = entry.getKey();
				List<Object[]> sqlValues = entry.getValue();
				int step = (sqlValues.size() / FLUSH_CRITICAL_VAL) + 1;
				for(int ii =0;ii<step;ii++){
					if(ii*FLUSH_CRITICAL_VAL <sqlValues.size()){
						List<Object[]> subSqlValues = 
								sqlValues.subList(ii*FLUSH_CRITICAL_VAL, sqlValues.size() > (ii+1)*FLUSH_CRITICAL_VAL?(ii+1)*FLUSH_CRITICAL_VAL:sqlValues.size());
						int[] resultArray = jdbcTemplate.batchUpdate(handleSql, subSqlValues);
						result = result + changeBatchUpdateResult(resultArray);

					}
				}
			}
		}
		return result;
	}
	
	private int changeBatchUpdateResult(int[] batchUpdateResult){
		int effortRows = 0;
		if(batchUpdateResult == null || batchUpdateResult.length == 0){
			return 0;
		}
		for(int i:batchUpdateResult){
			if(i == 1){
				effortRows +=i;
			}
		}
		return effortRows;
	}

	@Override
	public boolean batchUpdateSql(String sql, List<Object[]> batchArgs) {
		int paraCount = batchArgs.size();
		int step = (paraCount / FLUSH_CRITICAL_VAL ) + 1;
		for(int ii =0;ii<step;ii++){
			if(ii*FLUSH_CRITICAL_VAL <batchArgs.size()){
				List<Object[]> subBatchArgs = 
						batchArgs.subList(ii*FLUSH_CRITICAL_VAL, batchArgs.size() > (ii+1)*FLUSH_CRITICAL_VAL?(ii+1)*FLUSH_CRITICAL_VAL:batchArgs.size());
				int[] result = jdbcTemplate.batchUpdate(sql, subBatchArgs);
				for(int i =0;i<result.length;i++){
					if(result[i] <=0){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public Map<String, Object> querySql(String sql, Object[] args) {
		return jdbcTemplate.queryForMap(sql, args);
	}
	
	@Override
	public List<Map<String, Object>> queryListSql(String sql, Object[] args) {
		return jdbcTemplate.queryForList(sql, args);
	}

}
