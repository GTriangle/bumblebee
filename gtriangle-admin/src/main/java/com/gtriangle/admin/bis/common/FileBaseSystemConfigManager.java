package com.gtriangle.admin.bis.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class FileBaseSystemConfigManager implements IFileBaseSystemConfigManager {
	
	private static final String ROP_APP_SECRET_PROPERTIES = "systemConfig.properties";

	private String systemConfigFile = ROP_APP_SECRET_PROPERTIES;

	private final Logger logger = LoggerFactory.getLogger(FileBaseSystemConfigManager.class);

	private Properties properties;
	
	public String getConfig(String appKey) {
		String config = properties.getProperty(appKey);
		if (config == null) {
			logger.error("不存在应用键为{0}的配置,请检查系统的配置文件。", appKey);
		}
		return config;
	}
	
	@Override
	public void setConfig(String key, String value) {
		properties.put(key, value);
	}
	
	public Enumeration<?> getNames() {
		return properties.propertyNames();
	}

	public void setSystemConfigFile(String systemConfigFile) {
		this.systemConfigFile = systemConfigFile;
	}

	public void init() {
		if (properties == null) {
			try {
				properties = new Properties();
				
				InputStream is = this.getClass().getClassLoader().getResourceAsStream(systemConfigFile);
				properties.load(is);
			} catch (IOException e) {
				throw new RuntimeException("在类路径下找不到systemConfig.properties的系统配置的属性文件", e);
			}
		}
	}

	



}
