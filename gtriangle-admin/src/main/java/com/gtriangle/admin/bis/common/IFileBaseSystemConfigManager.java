package com.gtriangle.admin.bis.common;

public interface IFileBaseSystemConfigManager {
	
	String getConfig(String appKey);
	
	void setConfig(String key, String value);
	
}
