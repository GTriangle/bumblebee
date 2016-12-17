package com.gtriangle.admin.db.query;

import java.io.Serializable;

public final class PropertyValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String propertyName;
	
	private final Object value;
	
	
	public PropertyValue(final String propertyName, final Object value) {
		this.propertyName = propertyName;
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String toString() {
		return value==null ? propertyName + "=null" :  propertyName + "=" + value.toString();
	}

	
	@Override
	public boolean equals(Object other) {
		if ( this == other ) {
			return true;
		}
		if ( other == null || getClass() != other.getClass() ) {
			return false;
		}
		final PropertyValue that = (PropertyValue) other;
		
		if (       null == this.getPropertyName() 
				&& null == that.getPropertyName()
				&& null == this.getValue() 
				&& null == that.getValue())
			return true;
		else 
			return super.equals(other);
//		if (null == this.getPropertyName() || null == that.getPropertyName()) return false;
//		else if (null == this.getValue() || null == that.getValue()) return false;
//		else 
//			return (this.propertyName.equals(that.getPropertyName()) && this.getValue().equals(that.getValue()));
		

	}
}
