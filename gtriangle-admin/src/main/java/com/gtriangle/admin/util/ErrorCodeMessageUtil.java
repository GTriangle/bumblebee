package com.gtriangle.admin.util;

import java.text.MessageFormat;
import java.util.HashMap;

/**
 * 错误消息的读取工具类
 * 读取
 * @author gaoyan
 * @version 1.0
 * @created 2016年1月4日 下午4:37:33
 */
public class ErrorCodeMessageUtil {

	public static HashMap<String, String> errorCodeMessage = new HashMap<String, String>();

	private static void initErrorCodeMessage() {
		if (errorCodeMessage.isEmpty()) {
			HashMap<String, String> coreErrorCodeMap = GTriangleConfigUtil.getErrorCodeMessage("error_message.properties");
			if(!coreErrorCodeMap.isEmpty()){
				errorCodeMessage.putAll(coreErrorCodeMap);
			}
			
		}
	}
	
	public static String getErrorCodeMessage(String key,Object ... arguments){
		if(errorCodeMessage.isEmpty()){
			initErrorCodeMessage();
		}
		String errorMessageTemplate = errorCodeMessage.get(key);
		if((arguments == null) || (arguments.length<=0)){
			return errorMessageTemplate;
		}else{
			return MessageFormat.format(errorMessageTemplate, arguments);
		}
		
	}
}
