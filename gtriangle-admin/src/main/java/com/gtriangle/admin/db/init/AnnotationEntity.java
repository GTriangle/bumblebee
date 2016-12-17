package com.gtriangle.admin.db.init;

import com.gtriangle.admin.db.ISQLGenerator;

public class AnnotationEntity {
	
	/**
	 * 主键信息
	 */
	private String objectPrimaryKeyName;

	/**
	 * sql生成器实例
	 */
	private ISQLGenerator sqlGenerator;
	
	public AnnotationEntity(String objectPrimaryKeyName,ISQLGenerator sqlGenerator) {
		this.objectPrimaryKeyName = objectPrimaryKeyName;
		this.sqlGenerator = sqlGenerator;
	}

	public String getObjectPrimaryKeyName() {
		return objectPrimaryKeyName;
	}

	public void setObjectPrimaryKeyName(String objectPrimaryKeyName) {
		this.objectPrimaryKeyName = objectPrimaryKeyName;
	}

	public ISQLGenerator getSqlGenerator() {
		return sqlGenerator;
	}

	public void setSqlGenerator(ISQLGenerator sqlGenerator) {
		this.sqlGenerator = sqlGenerator;
	}
}
