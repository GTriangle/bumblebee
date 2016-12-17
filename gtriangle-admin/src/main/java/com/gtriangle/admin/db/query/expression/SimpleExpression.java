package com.gtriangle.admin.db.query.expression;

import com.gtriangle.admin.db.query.PropertyValue;

public class SimpleExpression implements Criterion {
	
	
	private final String propertyName;
	private final Object value;
	private boolean ignoreCase;
	private final String op;

	protected SimpleExpression(String propertyName, Object value, String op) {
		this.propertyName = propertyName;
		this.value = value;
		this.op = op;
	}

	protected SimpleExpression(String propertyName, Object value, String op, boolean ignoreCase) {
		this.propertyName = propertyName;
		this.value = value;
		this.ignoreCase = ignoreCase;
		this.op = op;
	}

	protected final String getOp() {
		return op;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getValue() {
		return value;
	}

	/**
	 * Make case insensitive.  No effect for non-String values
	 *
	 * @return {@code this}, for method chaining
	 */
	public SimpleExpression ignoreCase() {
		ignoreCase = true;
		return this;
	}

	@Override
	public String toSqlString() {
		final StringBuilder fragment = new StringBuilder();
		final boolean lower = ignoreCase && value instanceof String;
		if ( lower ) {
			fragment.append( "lower" ).append( '(' );
		}
		fragment.append("`").append( propertyName ).append("`");
		if ( lower ) {
			fragment.append( ')' );
		}
		fragment.append( getOp() ).append( "?" );
		
		return fragment.toString();
	}
	
	@Override
	public String toString() {
		return getPropertyName() + getOp() + getValue();
	}

	@Override
	public PropertyValue getPropertyValues() {
		return  new PropertyValue(propertyName, value);
	}

}
