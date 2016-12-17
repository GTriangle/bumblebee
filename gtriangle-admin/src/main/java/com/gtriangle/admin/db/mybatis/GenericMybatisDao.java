package com.gtriangle.admin.db.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gtriangle.admin.db.page.PageStartInfo;
import com.gtriangle.admin.db.page.Pagination;
import com.gtriangle.admin.db.query.BaseQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 基础dao层 不带泛型
 * @author Brian
 *
 */
public abstract class GenericMybatisDao {
	
	public static final String SQL_MAP_PAGE_SIZE = "pageSize";
	public static final String SQL_MAP_PAGE_NO = "start";
	public static final String SQL_MAP_PAGE_COUNT_SUFFIX = "_count";
	/**
	 * 日志，可用于子类
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "sqlSessionTemplate")
	protected SqlSessionTemplate  sqlSessionTemplateASS;
	
	/**
	 * 返回单一对象
	 * @param query
	 * @param resultType
	 * @return
	 */
	public <T> T findForObj(BaseQuery query, Class<T> resultType) {
		Assert.hasText(query.getSqlMapId());
		Map<String, Object> parameter = query.queryToMap();
		return sqlSessionTemplateASS.selectOne(query.getSqlMapId(), parameter);
	}
	
	/**
	 * 返回单一对象 Map类型
	 * @param query
	 * @return
	 */
	public <K, V> Map<K, V> findForMap(BaseQuery query) {
		Assert.hasText(query.getSqlMapId());
		
		Map<String, Object> parameter = query.queryToMap();
		return sqlSessionTemplateASS.selectOne(query.getSqlMapId(), parameter);
	}
	
	public <T> List<T> findForList(BaseQuery query, Class<T> resultType) {
		Assert.hasText(query.getSqlMapId());
		Map<String, Object> parameter = query.queryToMap();
		return sqlSessionTemplateASS.selectList(query.getSqlMapId(), parameter);
	}
	
	public <K, V> List<Map<K, V>> findForMapList(BaseQuery query) {
		Assert.hasText(query.getSqlMapId());
		Map<String, Object> parameter = query.queryToMap();
		return sqlSessionTemplateASS.selectList(query.getSqlMapId(), parameter);
	}
	
	public <T> List<T> findForPageList(BaseQuery query, Class<T> resultType) {
		Assert.hasText(query.getSqlMapId());
		Map<String, Object> parameter = query.queryToMap();
		PageStartInfo p = new PageStartInfo();
		p.setPageNo(Integer.valueOf(String.valueOf(parameter.get("pageNo"))));
		p.setPageSize(Integer.valueOf(String.valueOf(parameter.get("pageSize"))));
		parameter.put(SQL_MAP_PAGE_NO, p.getStart());
		parameter.put(SQL_MAP_PAGE_SIZE, p.getPageSize());
		return sqlSessionTemplateASS.selectList(query.getSqlMapId(), parameter);
	}
	
	public <K, V> List<Map<K, V>> findForPageMapList(BaseQuery query) {
		Assert.hasText(query.getSqlMapId());
		Map<String, Object> parameter = query.queryToMap();
		PageStartInfo p = new PageStartInfo();
		p.setPageNo(Integer.valueOf(String.valueOf(parameter.get("pageNo"))));
		p.setPageSize(Integer.valueOf(String.valueOf(parameter.get("pageSize"))));
		parameter.put(SQL_MAP_PAGE_NO, p.getStart());
		parameter.put(SQL_MAP_PAGE_SIZE, p.getPageSize());
		return sqlSessionTemplateASS.selectList(query.getSqlMapId(), parameter);
	}

	/**
	 * 查询数量
	 * @param query
	 * @return
     */
	public int findForInt(BaseQuery query) {
		Assert.hasText(query.getSqlMapId());
		Map<String, Object> parameter = query.queryToMap();
		Object count = sqlSessionTemplateASS.selectOne(query.getSqlMapId(), parameter);
		return null == count ? 0 : (Integer) count;
	}

	/**
	 * 分页查询  FIXME 还需要进一步处理
	 * 会触发statement + _count 查询总数的一个sql
	 * @param query
	 * @return
	 */
	public Pagination findForPage(BaseQuery query) {
		Assert.hasText(query.getSqlMapId());
		Map<String, Object> parameter = query.queryToMap();
		
		Object count = sqlSessionTemplateASS.selectOne(query.getSqlMapId() + SQL_MAP_PAGE_COUNT_SUFFIX, parameter);
		int totalCount = count == null ? 0 : (Integer) count;
		Pagination p = new Pagination(Integer.valueOf(String.valueOf(parameter.get("pageNo")))
				, Integer.valueOf(String.valueOf(parameter.get("pageSize"))), totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		parameter.put(SQL_MAP_PAGE_NO, p.getFirstResult());
		parameter.put(SQL_MAP_PAGE_SIZE, p.getPageSize());
		
		List list =sqlSessionTemplateASS.selectList(query.getSqlMapId(), parameter);
		p.setList(list);
		return p;

	}

	/**
	 * mybatis 更新
	 * @param query
	 * @return
     */
	public int updateByMybatis(BaseQuery query) {
		Map<String, Object> parameter = query.queryToMap();
		return sqlSessionTemplateASS.update(query.getSqlMapId(), parameter);
	}

}
