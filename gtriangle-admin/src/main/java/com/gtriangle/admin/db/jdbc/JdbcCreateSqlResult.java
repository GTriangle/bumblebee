package com.gtriangle.admin.db.jdbc;

import java.io.Serializable;
import java.util.Collection;

import com.gtriangle.admin.db.CreateSqlResult;

public class JdbcCreateSqlResult extends CreateSqlResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Collection<Object> sqlValues;

	public JdbcCreateSqlResult(String sql, Collection<Object> sqlValues) {
		super(sql);
		this.sqlValues = sqlValues;
	}

	@Override
	public Collection<Object> getSqlValues() {
		return sqlValues;
	}

	
	
}
