package com.gtriangle.admin.db.query.expression;

import com.gtriangle.admin.db.query.PropertyValue;

public interface Criterion {
	
	public static final PropertyValue NO_VALUES = new PropertyValue(null,null);
	
	/**
	 * 将条件封装成sql
	 * @return
	 */
	public String toSqlString();
	/**
	 * 返回条件中属性名（字段名）和对应的值
	 * @return
	 */
	public PropertyValue getPropertyValues();
}
