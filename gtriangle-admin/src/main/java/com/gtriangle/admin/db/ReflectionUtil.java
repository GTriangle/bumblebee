package com.gtriangle.admin.db;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 反射工具类
* @author Brian   
* @date 2016年5月13日 上午10:46:00
 */
public class ReflectionUtil {
		
//	public static Object invokeMethod(String className, String methodName,
//			Object[] args) {
//
//		try {
//
//			Class serviceClass = Class.forName(className);
//			Object service = serviceClass.newInstance();
//
//			Class[] argsClass = new Class[args.length];
//			for (int i = 0, j = args.length; i < j; i++) {
//				argsClass[i] = args[i].getClass();
//			}
//
//			Method method = serviceClass.getMethod(methodName, argsClass);
//			return method.invoke(service, args);
//
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object newInstance(String className,Object... _args ){

		try {
			  Class[] argsClass = new Class[_args.length];                                  
			                                                                                 
			   for (int i = 0, j = _args.length; i < j; i++) {   
					   
				   if(_args[i]==null){
					   argsClass[i]=null;
				   }
				   else{
					    
					   argsClass[i] = _args[i].getClass();
				   }
			    }      
			   
			   
			 Class newoneClass  = Class.forName(className);
			 Constructor cons = newoneClass.getConstructor(argsClass);                    
             
			 Object obj= cons.newInstance(_args);
			 return obj;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
	
		 
		 return null;
	 
	}

	/**
	 * 将po对象中有属性和值转换成map
	 * 
	 * @param po
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	public static Map<String,Object> po2Map(Object po) {
		Map<String,Object> poMap = new HashMap<String,Object>();
		Map map = new HashMap();
		try {
			map = PropertyUtils.describe(po);
		} catch (Exception ex) {
		}
		Object[] keyArray = map.keySet().toArray();
		for (int i = 0; i < keyArray.length; i++) {
			String str = keyArray[i].toString();
			if (str != null && !str.equals("class")) {
				if (map.get(str) != null) {
					poMap.put(str, map.get(str));
				}
			}
		}

		List<Field> fieldList = obtainAccessibleFields(po);
		for(Field f:fieldList){
			String fieldName = f.getName();
			if(Modifier.isPrivate(f.getModifiers()) == false)
				poMap.remove(fieldName); 
			
		}
		
		return poMap;
	}
	
	/**
     * 调用Getter方法
     * 
     * @param obj
     *            对象
     * @param propertyName
     *            属性名
     * @return
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, null, null);
    }
    /**
     * 调用Setter方法,不指定参数的类型
     * 
     * @param obj
     * @param propertyName
     * @param value
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
            Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }
    
    
    /**
     * 调用Setter方法,指定参数的类型
     * 
     * @param obj
     * @param propertyName  字段名
     * @param value
     * @param propertyType
     *            为空，则取value的Class
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
            Object value, Class<?> propertyType) {
        value = handleValueType(obj,propertyName,value);
        propertyType = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class<?>[] { propertyType },
                new Object[] { value });
    }
    /**
     * 直接调用对象方法，忽视private/protected修饰符
     * 
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param params
     * @return
     */
    public static Object invokeMethod(final Object obj,
            final String methodName, final Class<?>[] parameterTypes,
            final Object[] args) {
        Method method = obtainAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) { throw new IllegalArgumentException(
                "Devkit: Could not find method [" + methodName
                        + "] on target [" + obj + "]."); }
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 循环向上转型，获取对象的DeclaredMethod,并强制设置为可访问 如向上转型到Object仍无法找到，返回null
     * 
     * 用于方法需要被多次调用的情况，先使用本函数先取得Method,然后调用Method.invoke(Object obj,Object...
     * args)
     * 
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method obtainAccessibleMethod(final Object obj,
            final String methodName, final Class<?>... parameterTypes) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            Method method = null;
            try {
                method = superClass.getDeclaredMethod(methodName,
                        parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义，向上转型
            } catch (SecurityException e) {
                // Method不在当前类定义，向上转型
            }
        }
        return null;
    }
    /**
     * 直接读取对象属性值 忽视private/protected修饰符，不经过getter函数
     * 
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object obtainFieldValue(final Object obj,
            final String fieldName) {
        Field field = obtainAccessibleField(obj, fieldName);
        if (field == null) { throw new IllegalArgumentException(
                "Devkit: could not find field [" + fieldName + "] on target ["
                        + obj + "]"); }
        Object retval = null;
        try {
            retval = field.get(obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return retval;
         
    }
    /**
     * 直接设置对象属性值 忽视private/protected修饰符，不经过setter函数
     * 
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object obj, final String fieldName,
            final Object value) {
        Field field = obtainAccessibleField(obj, fieldName);
        if (field == null) { throw new IllegalArgumentException(
                "Devkit: could not find field [" + fieldName + "] on target ["
                        + obj + "]"); }
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * 循环向上转型，获取对象的DeclaredField,并强制设为可访问 如向上转型Object仍无法找到，返回null
     * 
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field obtainAccessibleField(final Object obj,
            final String fieldName) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义，向上转型
            } catch (SecurityException e) {
                // Field不在当前类定义，向上转型
            }
        }
        return null;
    }
	   /**
     * 循环向上转型，获取对象的DeclaredField
     * 
     * @param obj
     * @return
     */
    public static List<Field> obtainAccessibleFields(final Object obj) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        List<Field> fieldList = new ArrayList<Field>();
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            try {
            	Field[] fs =superClass.getDeclaredFields();
            	for(Field f : fs) {
            		fieldList.add(f);
            	}
            } 
            catch (SecurityException e) {
                // Field不在当前类定义，向上转型
            }
        }
        return fieldList;
    }
    
    
    private static Object handleValueType(Object obj, String propertyName,
            Object value){
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        Class<?> argsType = value.getClass();;
        Class<?> returnType = obtainAccessibleMethod(obj, getterMethodName).getReturnType();
        if(argsType == returnType){
            return value;
        }
         
        if (returnType == Boolean.class) {
            String temp = value.toString();
            value = (StringUtils.isNotBlank(temp) && Long.valueOf(temp) > 0) ? true : false;
        } else if (returnType == Long.class) {
            value = Long.valueOf(value.toString());
        }else if(returnType == Date.class){
//            try {
//                value = SimpleDateFormatFactory.getInstance(DateUtil.FULL_TIME_FORMAT).parse(value.toString());
//            } catch (ParseException e) {
//                logger.error("类型转型Timpestap-->Date时，发生错误! " + e.getMessage() + "("+value.toString()+")");
//            }
        } else if (returnType == Short.class) {
            value = Short.valueOf(value.toString());
        } else if (returnType == BigDecimal.class) {
            value = BigDecimal.valueOf(Long.valueOf(value.toString()));
        } else if (returnType == BigInteger.class) {
            value = BigInteger.valueOf(Long.valueOf(value.toString()));
        } else if(returnType == String.class){
            value = String.valueOf(value);
        }else if(returnType == Integer.class){
            value = Integer.valueOf(value.toString());
        }
        return value;
    }
    
    /**
     * 反射 取值、设值,合并两个对象(Field same only )
     * 
     * @param from
     * @param to
     */
    public static <T> void copyProperties(T fromobj, T toobj,
            String... fieldspec) {
        for (String filename : fieldspec) {
            Object val = invokeGetterMethod(fromobj, filename);
            invokeSetterMethod(toobj, filename, val);
        }
         
    }
    
//	private static String getFieldName(String methodName){
//		 
//		methodName = methodName.substring(3);
//		methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
//		return methodName;
//	}
	
	public static void main(String[] args){
		String methodName = "getWidgetList";
		methodName = methodName.substring(3);
		methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
		//System.out.println(methodName);
	}
}
