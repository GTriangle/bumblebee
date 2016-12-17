package com.gtriangle.admin.util;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by SHC on 2016/4/17.
 */
public class ReflectUtil {
    private static Logger log4j = Logger.getLogger(ReflectUtil.class);

    public static Object setValues(Object object, Map map) {
        if (map==null || map.size()==0 || object == null) return null;
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        //写数据
        for (Field f : fields) {
            PropertyDescriptor pd = null;
            String fieldName = f.getName();
            try {
                pd = new PropertyDescriptor(fieldName, clazz);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            if (map.keySet().contains(fieldName)) {
                if (pd == null) continue;
                Method method = pd.getWriteMethod();//获得写方法
                try {
                    if (f.getType() == int.class) {
                        method.invoke(object, (Integer)map.get(fieldName));
                    } else if (f.getType() == String.class) {
                        method.invoke(object, map.get(fieldName)+"");
                    } else if (f.getType() == double.class) {
                        method.invoke(object, (Double)map.get(fieldName));
                    } else if (f.getType() == long.class) {
                        method.invoke(object, (Long)map.get(fieldName));
                    } else if (f.getType() == boolean.class) {
                        method.invoke(object, (Boolean)map.get(fieldName));
                    } else {
                        method.invoke(object, map.get(fieldName));
                    }
                }  catch (Exception e) {
                    log4j.info("reflect json setValue error===" + fieldName);
                    System.out.println(e.getMessage());
                }
            }
        }
        return object;
    }

    public static JSONObject createJsonByFiled(Object object){
        if (object == null) return null;
        Field[] fieLds = object.getClass().getDeclaredFields();
        JSONObject jsonObject = new JSONObject();
        for (Field field : fieLds) {
            field.setAccessible(true);
            try {
                Object o = field.get(object);
                if (o != null) {
                    if (field.getType() == Date.class) {
                        jsonObject.put(field.getName(), ((Date)o).getTime());
                    } else {
                        jsonObject.put(field.getName(), o);
                    }
                } else {
                    jsonObject.put(field.getName(), "");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static Map createMapByFiled(Object object, Map map){
        if (object == null) return null;
        Field[] fieLds = object.getClass().getDeclaredFields();
        for (Field field : fieLds) {
            field.setAccessible(true);
            try {
                Object o = field.get(object);
                if (o != null) {
                    if (field.getType() == Date.class) {
                        map.put(field.getName(), ((Date)o).getTime());
                        System.out.println();
                    } else {
                        map.put(field.getName(), o);
                    }
                } else {
                    map.put(field.getName(), "");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static Map<String,String> createMapByJson(String object){
        if (object == null) return null;
        JSONObject json= JSONObject.fromObject(object);
        Iterator keys=json.keys();
        Map<String,String> map = new HashMap<String,String>();
        while(keys.hasNext()){
            String key=(String) keys.next();
            String value=json.get(key).toString();
            if(value.startsWith("{")&&value.endsWith("}")) {
                map.put(key, (createMapByJson(value)).toString());
            }
            else {
                map.put(key, value);
            }
        }
        return map;
    }

}
