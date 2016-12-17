package com.gtriangle.admin.db.query.expression;

import com.gtriangle.admin.db.StringUtil;
import com.gtriangle.admin.db.query.PropertyValue;

public class NotNullExpression implements Criterion {

	private static final PropertyValue NO_VALUES = new PropertyValue(null,null);
	
	private final String propertyName;

	protected NotNullExpression(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String toSqlString()  {
		return StringUtil.suffix( "`" + propertyName + "`"  , " is not null" );
	}

	@Override
	public String toString() {
		return propertyName + " is not null";
	}
	
	@Override
	public PropertyValue getPropertyValues() {
		return new PropertyValue(propertyName, NO_VALUES);
	}
}
