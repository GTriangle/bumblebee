package com.gtriangle.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 通用的上下文配置信息
 * @author gaoyan
 * @version 1.0
 * @created 2016年1月4日 下午4:37:33
 */
public class GTriangleConfigUtil {
	private static final Logger log = LoggerFactory.getLogger(GTriangleConfigUtil.class);
	
	public static HashMap<String, String> getErrorCodeMessage(String propertiesPath) {
		HashMap<String, String> errorCodeMessage = new HashMap<String, String>();
		try {
			Resource resource = new ClassPathResource(propertiesPath);
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			for(Entry<Object, Object> entry:props.entrySet()){
				errorCodeMessage.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}

		} catch (IOException e) {
			log.error("error occurs when parsing properties in errormessage", e);
		}
		return errorCodeMessage;
	}

}
