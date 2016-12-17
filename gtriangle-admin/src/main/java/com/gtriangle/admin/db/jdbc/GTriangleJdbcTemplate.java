package com.gtriangle.admin.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

/**
 * 保存时支持自增主键获取
* @author Brian   
* @date 2016年1月18日 下午2:08:13
 */
public class GTriangleJdbcTemplate extends JdbcTemplate {
	
	/**
	 * 保存时支持自增主键获取
	 * @param sql
	 * @param generatedKeyHolder
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql, final KeyHolder generatedKeyHolder ,Object... args) throws DataAccessException {
		PreparedStatementSetter pss = newArgPreparedStatementSetter(args);
		return update(new SimplePreparedStatementCreator(sql), pss, generatedKeyHolder);
	}
	
	public int update(final PreparedStatementCreator psc, final PreparedStatementSetter pss, final KeyHolder generatedKeyHolder)
			throws DataAccessException {

		Assert.notNull(generatedKeyHolder, "KeyHolder must not be null");
		logger.debug("Executing SQL update and returning generated keys");

		return execute(psc, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException {
				try {
					//设置值
					if (pss != null) {
						pss.setValues(ps);
					}
					
					int rows = ps.executeUpdate();
					List<Map<String, Object>> generatedKeys = generatedKeyHolder.getKeyList();
					generatedKeys.clear();
					ResultSet keys = ps.getGeneratedKeys();
					if (keys != null) {
						try {
							RowMapperResultSetExtractor<Map<String, Object>> rse =
									new RowMapperResultSetExtractor<Map<String, Object>>(getColumnMapRowMapper(), 1);
							generatedKeys.addAll(rse.extractData(keys));
						}
						finally {
							JdbcUtils.closeResultSet(keys);
						}
					}
					if (logger.isDebugEnabled()) {
						logger.debug("SQL update affected " + rows + " rows and returned " + generatedKeys.size() + " keys");
					}
					return rows;
				}
				finally {
					if (pss instanceof ParameterDisposer) {
						((ParameterDisposer) pss).cleanupParameters();
					}
				}
			}
		});
	}
	/**
	 * Simple adapter for PreparedStatementCreator, allowing to use a plain SQL statement.
	 */
	private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {

		private final String sql;

		public SimplePreparedStatementCreator(String sql) {
			Assert.notNull(sql, "SQL must not be null");
			this.sql = sql;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			return con.prepareStatement(this.sql,PreparedStatement.RETURN_GENERATED_KEYS);
		}

		@Override
		public String getSql() {
			return this.sql;
		}
	}
}
