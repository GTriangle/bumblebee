package com.gtriangle.admin.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键
 * @author kingapex
 * 2010-1-22下午04:08:58
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.FIELD) 
public @interface PrimaryKeyField {
}
