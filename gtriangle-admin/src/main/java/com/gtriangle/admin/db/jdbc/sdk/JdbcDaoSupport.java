package com.gtriangle.admin.db.jdbc.sdk;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gtriangle.admin.db.ReflectionUtil;
import com.gtriangle.admin.db.StringUtil;
import com.gtriangle.admin.db.DBRuntimeException;
import com.gtriangle.admin.db.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.util.Assert;

/**
 * jdbc数据库操作支撑
 * 
 * @author kingapex 2010-1-6下午01:54:18
 * @param <T>
 */
public class JdbcDaoSupport<T> implements IDaoSupport<T> {

	private JdbcTemplate jdbcTemplate;
	//private SimpleJdbcTemplate simpleJdbcTemplate;
	protected final Logger logger = LoggerFactory.getLogger(JdbcDaoSupport.class);
	public void execute(String sql, Object... args) {
		try {
			//this.simpleJdbcTemplate.update(sql, args);
			this.jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}

	public int getLastId(String table) {
		return this.jdbcTemplate
				.queryForInt("SELECT last_insert_id() as id");
	}

	public void insert(String table, Map fields) {
		String sql = "";

		try {
		 
			Assert.hasText(table, "表名不能为空");
			Assert.notEmpty(fields, "字段不能为空");
			table = quoteCol(table);

			Object[] cols = fields.keySet().toArray();
			Object[] values = new Object[cols.length];
			for (int i = 0; i < cols.length; i++) {
				if (fields.get(cols[i]) == null) {
					values[i] = null;
				} else {
					values[i] = fields.get(cols[i]).toString();
				}
				cols[i] = quoteCol(cols[i].toString());
			}

			sql = "INSERT INTO " + table + " ("
					+ StringUtil.implode(", ", cols);

			sql = sql + ") VALUES (" + StringUtil.implodeValue(", ", values);

			sql = sql + ")";
			if(table.equals("es_settings")){
				System.out.println(sql);	
			}
//			
			jdbcTemplate.update(sql, values);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new DBRuntimeException(e, sql);
		}
	}

	public void insert(String table, Object po) {
		insert(table, ReflectionUtil.po2Map(po));
	}

	public int queryForInt(String sql, Object... args) {
		try {
			return this.jdbcTemplate.queryForInt(sql, args);
		} catch (RuntimeException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		}
	}


	
	public String queryForString(String sql) {
		String s = "";
		try {
			s = (String) this.jdbcTemplate.queryForObject(sql, String.class);
		} catch (RuntimeException e) {
			e.printStackTrace();
			
		}
		return s;
	}

	@SuppressWarnings("unchecked")
	public List queryForList(String sql, Object... args) {
		return this.jdbcTemplate.queryForList(sql, args);
	}

	public List<T> queryForList(String sql, RowMapper mapper, Object... args) {
		try {
			return this.jdbcTemplate.query(sql, args, mapper);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	public List<T> queryForList(String sql, Class clazz, Object... args) {
		return this.jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);

	}

	public List queryForListPage(String sql, int pageNo, int pageSize,
			Object... args) {

		try {
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = this.buildPageSql(sql, pageNo, pageSize);
			return queryForList(listSql, args);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}

	}

	
	public List<T> queryForList(String sql, int pageNo, int pageSize,
			RowMapper mapper) {

		try {
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = this.buildPageSql(sql, pageNo, pageSize);
			return this.queryForList(listSql, mapper);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}

	}

	
	public long queryForLong(String sql, Object... args) {
		return this.jdbcTemplate.queryForLong(sql, args);
	}

	public Map queryForMap(String sql, Object... args) {
		// Map map = this.simpleJdbcTemplate.queryForMap(sql, args);
		try {
			Map map = this.jdbcTemplate.queryForMap(sql, args);
			return map;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ObjectNotFoundException(ex, sql);
		}
	}

	public T queryForObject(String sql, Class clazz, Object... args) {
		try {
			return (T) jdbcTemplate
					.queryForObject(sql, ParameterizedBeanPropertyRowMapper
							.newInstance(clazz), args);
			// return (T) this.jdbcTemplate.queryForObject(sql, args, clazz);
		} catch (Exception ex) {
			// ex.printStackTrace();
			// throw new ObjectNotFoundException(ex, sql);
			this.logger.error("查询出错", ex);
			return null;
		}
	}

 

	
	public Page queryForPage(String sql, int pageNo, int pageSize,
			Object... args) {
		try {
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) "
					+ removeSelect(removeOrders(sql));
			List list = queryForList(listSql, args);
			int totalCount = queryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	
	public Page queryForPage(String sql, int pageNo, int pageSize,
			RowMapper rowMapper, Object... args) {
		try {
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) "
					+ removeSelect(removeOrders(sql));
			List<T> list = this.queryForList(listSql, rowMapper, args);
			int totalCount = queryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	public Page queryForPage(String sql, int pageNo, int pageSize,
			Class<T> clazz, Object... args) {
	 
		try {
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = buildPageSql(sql, pageNo, pageSize);
			String countSql = "SELECT COUNT(*) "
					+ removeSelect(removeOrders(sql));
			List<T> list = this.queryForList(listSql, clazz, args);
			int totalCount = queryForInt(countSql, args);
			return new Page(0, totalCount, pageSize, list);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}
	}

	
	
	public void update(String table, Map fields, Map where) {
		String whereSql = "";

		if (where != null) {
			Object[] wherecols = where.keySet().toArray();
			for (int i = 0; i < wherecols.length; i++) {
				wherecols[i] = quoteCol(wherecols[i].toString()) + "="
						+ quoteValue(where.get(wherecols[i]).toString());
			}
			whereSql += StringUtil.implode(" AND ", wherecols);
		}
		update(table, fields, whereSql);
	}

	
	public void update(String table, T po, Map where) {
		String whereSql = "";
		// where值
		if (where != null) {
			Object[] wherecols = where.keySet().toArray();
			for (int i = 0; i < wherecols.length; i++) {
				wherecols[i] = quoteCol(wherecols[i].toString()) + "="
						+ quoteValue(where.get(wherecols[i]).toString());
			}
			whereSql += StringUtil.implode(" AND ", wherecols);
		}
		update(table, ReflectionUtil.po2Map(po), whereSql);

	}

	public void update(String table, T po, String where) {
		this.update(table, ReflectionUtil.po2Map(po), where);

	}

	public void update(String table, Map fields, String where) {
		String sql = "";
		try {
			Assert.hasText(table, "表名不能为空");
			Assert.notEmpty(fields, "字段不能为空");
			Assert.hasText(where, "where条件不能为空");
			table = quoteCol(table);

			String key = null;		//	SQLServer 专用
			Object value = null;	//	SQLServer 专用
			
			// 字段值
			Object[] cols = fields.keySet().toArray();

			Object[] values = new Object[cols.length];
			for (int i = 0; i < cols.length; i++) {
				if (fields.get(cols[i]) == null) {
					values[i] = null;
				} else {
					values[i] = fields.get(cols[i]).toString();
				}
				cols[i] = quoteCol(cols[i].toString()) + "=?";

			}

			sql = "UPDATE " + table + " SET " + StringUtil.implode(", ", cols)
					+ " WHERE " + where;

			jdbcTemplate.update(sql, values);
			if(value!=null)	//	SQLServer时可能不为空
				fields.put(key, value);
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}
	

	public String buildPageSql(String sql, int page, int pageSize) {
		String sql_str = sql + " LIMIT " + (page - 1) * pageSize + "," + pageSize;
		return sql_str;

	}

	
	/**
	 * 格式化列名 只适用于Mysql
	 * 
	 * @param col
	 * @return
	 */
	private String quoteCol(String col) {
		if (col == null || col.equals("")) {
			return "";
		} else {
//			if("2".equals(EopSetting.DBTYPE))//Oracle
//				return "\"" + col + "\"";
//			else if("1".equals(EopSetting.DBTYPE))//mysql
//				return "`" + col + "`";
//			else								//mssql
//				return "[" + col + "]";
			return col;
		}
	}

	/**
	 * 格式化值 只适用于Mysql
	 * 
	 * @param value
	 * @return
	 */
	private String quoteValue(String value) {
		if (value == null || value.equals("")) {
			return "''";
		} else {
			return "'" + value.replaceAll("'", "''") + "'";
		}
	}

/*	private String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().lastIndexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");

		return hql.substring(beginPos);
	}*/
	private String getStr(int num, String str) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	/**
	 * 去除sql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private String removeSelect(String sql){
		sql=sql.toLowerCase();
		Pattern p = Pattern.compile("\\(.*\\)",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			int c = m.end()-m.start();
			m.appendReplacement(sb, getStr(c,"~"));
		}
		m.appendTail(sb);
		
		String replacedSql = sb.toString();
		
		return sql.substring(replacedSql.indexOf("from"));
	}
	
	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

//	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
//		this.simpleJdbcTemplate = simpleJdbcTemplate;
//	}

	public T queryForObject(String sql, ParameterizedRowMapper mapper,
			Object... args) {
		try {
			T t = (T) this.jdbcTemplate.queryForObject(sql, mapper, args);
			return t;
		} catch (RuntimeException e) {
			return null;
		}
	}


	@Override
	public List<T> queryForList(String sql, IRowMapperColumnFilter filter,
			Object... args) {
		FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
		return this.queryForList(sql, filterColumnMapRowMapper, args);
	}


	@Override
	public List<T> queryForList(String sql, int pageNo, int pageSize,
			IRowMapperColumnFilter filter) {
		FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
		return this.queryForList(sql, pageNo, pageSize, filterColumnMapRowMapper);
	}


	@Override
	public Page queryForPage(String sql, int pageNo, int pageSize,
			IRowMapperColumnFilter filter, Object... args) {
		FilterColumnMapRowMapper filterColumnMapRowMapper = new FilterColumnMapRowMapper(filter);
		return this.queryForPage(sql, pageNo, pageSize, filterColumnMapRowMapper, args);
	}

}
