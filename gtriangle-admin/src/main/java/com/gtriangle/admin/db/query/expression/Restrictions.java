package com.gtriangle.admin.db.query.expression;

import java.util.Collection;


public class Restrictions {

	/**
	 * Apply an "equal" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return SimpleExpression
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression eq(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, "=" );
	}

	/**
	 * Apply an "equal" constraint to the named property.  If the value
	 * is null, instead apply "is null".
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see #eq
	 * @see #isNull
	 */
	public static Criterion eqOrIsNull(String propertyName, Object value) {
		return value == null
				? isNull( propertyName )
				: eq( propertyName, value );
	}

	/**
	 * Apply a "not equal" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion

	 * @see SimpleExpression
	 */
	public static SimpleExpression ne(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, "<>" );
	}

	/**
	 * Apply a "not equal" constraint to the named property.  If the value
	 * is null, instead apply "is not null".
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see #ne
	 * @see #isNotNull
	 */
	public static Criterion neOrIsNotNull(String propertyName, Object value) {
		return value == null
				? isNotNull( propertyName )
				: ne( propertyName, value );
	}
	
	/**
	 * Apply a "greater than" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression gt(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, ">" );
	}

	/**
	 * Apply a "less than" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression lt(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, "<" );
	}

	/**
	 * Apply a "less than or equal" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression le(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, "<=" );
	}
	/**
	 * Apply a "greater than or equal" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression ge(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, ">=" );
	}
	
	/**
	 * Apply an "is null" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 *
	 * @return Criterion
	 *
	 * @see NullExpression
	 */
	public static Criterion isNull(String propertyName) {
		return new NullExpression( propertyName );
	}

	/**
	 * Apply an "is not null" constraint to the named property
	 *
	 * @param propertyName The property name
	 *
	 * @return The Criterion
	 *
	 * @see NotNullExpression
	 */
	public static Criterion isNotNull(String propertyName) {
		return new NotNullExpression( propertyName );
	}
	/**
	 * Apply an "in" constraint to the named property.
	 *
	 * @param propertyName The name of the property
	 * @param values The literal values to use in the IN restriction
	 *
	 * @return The Criterion
	 *
	 * @see InExpression
	 */
	public static Criterion in(String propertyName, Object... values) {
		return new InExpression( propertyName, values );
	}

	/**
	 * Apply an "in" constraint to the named property.
	 *
	 * @param propertyName The name of the property
	 * @param values The literal values to use in the IN restriction
	 *
	 * @return The Criterion
	 *
	 * @see InExpression
	 */
	public static Criterion in(String propertyName, @SuppressWarnings("rawtypes") Collection values) {
		return new InExpression( propertyName, values.toArray() );
	}
}
