/**
 * 
 */
package com.gtriangle.admin.json;

import com.gtriangle.admin.exception.ResultParseException;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Description: JsonHandler
 * @author Brian
 * @date 2014年5月16日 下午1:37:42
 * @version
 */
public interface JsonHandler {
	
	public static final DateFormat DF_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
     * 将字符串反序列化为相应的对象
     *
     * @param content
     * @param objectType
     * @param <T>
     * @return
     */
    <T> T unmarshaller(String content, Class<T> objectType) throws ResultParseException;
    /**
     * 将对象转化为json字符串并输出到流
     * @param object
     * @param outputStream
     */
    void marshaller(Object object, OutputStream outputStream) throws ResultParseException ;
    /**
     * 将对象转化为json字符串
     * @param object
     */
    String marshaller(Object object) throws ResultParseException;
     /**
      * 将流反序列化为相应的对象
      * @param content
      * @param objectType
      * @return
      * @throws ResultParseException
      */
    public <T> T unmarshaller(InputStream content, Class<T> objectType) throws ResultParseException;
}
