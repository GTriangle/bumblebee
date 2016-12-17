package com.gtriangle.admin.db.query.expression;


import com.gtriangle.admin.db.query.PropertyValue;

public class InExpression implements Criterion {

	private final String propertyName;
	private final Object[] values;
	
	/**
	 * Constructs an InExpression
	 *
	 * @param propertyName The property name to check
	 * @param values The values to check against
	 *
	 * @see Restrictions#in(String, java.util.Collection)
	 * @see Restrictions#in(String, Object...)
	 */
	protected InExpression(String propertyName, Object[] values) {
		this.propertyName = propertyName;
		this.values = values;
	}
	
	@Override
	public String toSqlString() {
		String singleValueParam = "?";
		final String params = values.length > 0
				? repeat( singleValueParam + ", ", values.length - 1 ) + singleValueParam
				: "";
		return propertyName + " in (" + params + ')';
	}

	@Override
	public PropertyValue getPropertyValues() {
		return  new PropertyValue(propertyName, values);
	}
	
	@Override
	public String toString() {
		return propertyName + " in (" + values + ')';
	}
	
	public String repeat(String string, int times) {
		StringBuilder buf = new StringBuilder( string.length() * times );
		for ( int i = 0; i < times; i++ ) {
			buf.append( string );
		}
		return buf.toString();
	}
	
	public static void main(String[] args) {
		Integer[] arr = new Integer[0];
		
		Criterion c = new InExpression("username", arr);
		System.out.println(c.toSqlString());
	}
}
