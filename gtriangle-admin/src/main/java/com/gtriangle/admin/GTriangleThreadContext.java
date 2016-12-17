package com.gtriangle.admin;


import com.gtriangle.admin.bis.emp.entity.Employee;
import javax.servlet.http.HttpServletRequest;

/**
 * CRM线程变量
 */
public class GTriangleThreadContext {
	/**
	 * 当前用户线程变量
	 */
	private static ThreadLocal<Employee> cmsEmpVariable = new ThreadLocal<Employee>();

	private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
	
	/**
	 * 获得当前用户
	 *
	 * @return
	 */
	public static Employee getEmp() {
		return cmsEmpVariable.get();
	}

	/**
	 * 设置当前用户
	 *
	 * @param employee
	 */
	public static void setEmp(Employee employee) {
		cmsEmpVariable.set(employee);
	}

	/**
	 * 移除当前用户
	 */
	public static void removeEmp() {
		cmsEmpVariable.remove();
	}

	public static void setHttpRequest(HttpServletRequest request){
		
		HttpRequestThreadLocalHolder.set(request);
	}
	public static HttpServletRequest getHttpRequest(){
		return  HttpRequestThreadLocalHolder.get();
	}

	public static void removeHttpRequest(){
		HttpRequestThreadLocalHolder.remove();
	}
}
