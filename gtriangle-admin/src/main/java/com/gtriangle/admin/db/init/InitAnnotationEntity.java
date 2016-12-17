package com.gtriangle.admin.db.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.gtriangle.admin.db.annotation.NotDbField;
import com.gtriangle.admin.db.annotation.Table;
import com.gtriangle.admin.util.ScanClassUtil;
import com.gtriangle.admin.db.jdbc.JdbcSQLGenerator;
import com.gtriangle.admin.db.annotation.PrimaryKeyField;

public class InitAnnotationEntity {
	public static final Map<String, AnnotationEntity> classesMap = new ConcurrentHashMap<String, AnnotationEntity>();

	public void init() {
		Set<Class<?>> classes = ScanClassUtil.getClasses("com.gtriangle");
		if ((classes != null) && (!classes.isEmpty())) {
			for (Class<?> c : classes) {
				Table table = c.getAnnotation(Table.class);
				if (table != null) {
					classesMap.put(c.getCanonicalName(), AbsBaseDaoSupport(c));
				}
			}
		}
	}
	
	private AnnotationEntity AbsBaseDaoSupport(Class<?> entityClass) {
		Field[] fields = entityClass.getDeclaredFields();
		Set<String> columns = new LinkedHashSet<String>();//使用tree 保证顺序一致
		String fieldName = null;
		String dbPrimaryKeyName = null;
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotDbField.class) || Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			fieldName = field.getName();
			// 实体类主键名称idName;
			if (field.isAnnotationPresent(PrimaryKeyField.class)) {
				dbPrimaryKeyName = fieldName;
			}
			columns.add(fieldName);
		}
		Table table = entityClass.getAnnotation(Table.class);
		if (null == table) {
			throw new RuntimeException("类-" + entityClass + ",未用@Table注解标识!!");
		}
		String tableName = table.tableName();
		return new AnnotationEntity(dbPrimaryKeyName,
				new JdbcSQLGenerator(tableName, dbPrimaryKeyName,columns));
	}
}
