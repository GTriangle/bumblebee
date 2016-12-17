package com.gtriangle.admin.db.query.expression;

import com.gtriangle.admin.db.StringUtil;
import com.gtriangle.admin.db.query.PropertyValue;

public class NullExpression implements Criterion {
	
	private static final PropertyValue NO_VALUES = new PropertyValue(null,null);
	
	
	private final String propertyName;

	/**
	 * Constructs a NullExpression
	 *
	 * @param propertyName The name of the property to check for null
	 *
	 */
	protected NullExpression(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Override
	public String toSqlString() {
		return StringUtil.suffix( "`" + propertyName + "`", " is null" );
	}
	
	@Override
	public PropertyValue getPropertyValues() {
		return new PropertyValue(propertyName, NO_VALUES);
	}
	
	@Override
	public String toString() {
		return propertyName + " is null";
	}
}
