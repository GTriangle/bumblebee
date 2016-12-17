package com.gtriangle.admin.db;

import java.io.Serializable;
import java.util.Collection;

public abstract class CreateSqlResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sql;
	
	public CreateSqlResult() {}
	
	public CreateSqlResult(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public abstract Collection<Object> getSqlValues();
}
