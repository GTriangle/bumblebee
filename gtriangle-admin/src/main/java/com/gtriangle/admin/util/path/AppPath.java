package com.gtriangle.admin.util.path;

import org.springframework.web.context.ContextLoader;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * title : Description : 获取工程路径类 Date : 2010-7-28 Company : 中润无限
 * 
 * @author : jiangqt
 * @version : 1.0
 */
public class AppPath {
	private static String APP_ROOT_PATH;// 应用部署的根路径

	static {
		APP_ROOT_PATH = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
	}

	/**
	 * 获取应用部署的根路径
	 * 
	 * @return
	 */
	public static String getRootPath() {
		return APP_ROOT_PATH;
	}

	/**
	 * 获取应用部署的classes的绝对路径
	 * 
	 * @return
	 */
	public static String getAppClassesPath() {
		return APP_ROOT_PATH + "WEB-INF/classes/";
	}

	public static HttpServletRequest getRequest(HttpServletRequest... isRequest) {
		HttpServletRequest request = null;
		if (isRequest.length > 0) {
			request = isRequest[0];
		}
		return request;
	}

	public static String getProjectName() {
		String proUrl = APP_ROOT_PATH.substring(0, APP_ROOT_PATH.length() - 1);
		return (proUrl.substring(proUrl.lastIndexOf("/") + 1));
	}

	/**
	 * 获取操作协议,如http,https,ftp等
	 * 
	 * @return
	 */
	public static String getScheme(HttpServletRequest... isRequest) {
		return getRequest(isRequest).getScheme();
	}

	/**
	 * 获取IP
	 * 
	 * @return
	 */
	public static String getIp(HttpServletRequest... isRequest) {
		return getRequest(isRequest).getServerName();
	}

	/**
	 * 获取端口
	 * 
	 * @return
	 */
	public static int getPort(HttpServletRequest... isRequest) {
		return getRequest(isRequest).getServerPort();
	}

	/**
	 * 获取根路径别名
	 * 
	 * @return
	 */
	public static String getContextPath(HttpServletRequest... isRequest) {
		return getRequest(isRequest).getContextPath();
	}

	public static String getHttpUrl(HttpServletRequest... isRequest) {
		String port = "";
		Integer portValue = getPort(isRequest);
		if (portValue != 80) {
			port = ":" + portValue.toString();
		}

		StringBuilder str = new StringBuilder();
		str.append(getScheme(isRequest)).append("://").append(getIp(isRequest)).append(port);

		return str.toString();
	}

	public static String getProjectUrl(HttpServletRequest... isRequest) {
		StringBuilder str = new StringBuilder();
		str.append(getHttpUrl(isRequest)).append(getContextPath(isRequest)).append("/");

		return str.toString();
	}

	public static String getProjectPath() {
		StringBuilder str = new StringBuilder();
		str.append(getHttpUrl()).append(getContextPath()).append("/");
		return str.toString();
	}

	public static String getHttpAddr() {
		return "http://" + getIp() + ":" + getPort();
	}
}