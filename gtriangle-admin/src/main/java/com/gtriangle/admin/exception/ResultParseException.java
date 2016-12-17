/**
 * FileName:     ResultParseException.java
 * All rights Reserved, Designed By Brian
 * Copyright:    Copyright(C) 2013
 * Company       HYOUSOFT LTD.
 * @author:      Brian
 * @version      V1.0 
 * Createdate:   Apr 19, 2013 4:56:11 PM
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * Apr 19, 2013      Brian          1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
package com.gtriangle.admin.exception;

/**
 * @Description: 解析异常
 * @author Brian
 * @date Apr 19, 2013 4:56:11 PM
 * @version
 */
public class ResultParseException extends Exception {
	
	
	private static final long serialVersionUID = -6267981352685920029L;

	public ResultParseException()
	{
	}

	public ResultParseException(String message)
	{
		super(message);
	}

	public ResultParseException(Throwable cause)
	{
		super(cause);
	}

	public ResultParseException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
