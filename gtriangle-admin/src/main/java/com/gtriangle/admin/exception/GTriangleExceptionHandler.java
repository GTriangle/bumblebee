package com.gtriangle.admin.exception;

import com.gtriangle.admin.util.ErrorCodeMessageUtil;
import com.gtriangle.admin.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class GTriangleExceptionHandler implements HandlerExceptionResolver {

	private static final Logger log = LoggerFactory.getLogger(GTriangleExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		response.setCharacterEncoding("UTF-8");
		
		String errorMessage = "系统错误";
		//读取错误码信息
		if(ex instanceof BizCoreRuntimeException){
			BizCoreRuntimeException bizCoreRuntimeException = (BizCoreRuntimeException) ex;
			if(bizCoreRuntimeException.getErrorCode() != null){
				errorMessage = ErrorCodeMessageUtil.getErrorCodeMessage(bizCoreRuntimeException.getErrorCode(),
						bizCoreRuntimeException.getErrorContents());
			}else if(bizCoreRuntimeException.getMessage() != null){
				errorMessage = bizCoreRuntimeException.getMessage();
			}
			
		}else{
			log.error("###############unknown exception ########",ex);
		}
		
		//如果是ajax请求
		if(RequestUtils.isAjaxRequest(request)){
			//如果错误消息非空 直接返还，否则返回默认系统错误
			renderJsonDataFail(response, errorMessage);
		}else{
			 return new ModelAndView("/error","errorMessage",errorMessage);
		}
		return null;
	}

	private void renderJsonDataFail(HttpServletResponse response, String msg) {
		render(response, "application/json;charset=UTF-8", "{\"result\":0,\"msg\":\""+msg+"\"}",HttpServletResponse.SC_OK);
	}
	
	/**
	 * 发送内容。使用UTF-8编码。
	 * @param response
	 * @param contentType
	 * @param text
	 */
	private void render(HttpServletResponse response, String contentType,
			String text,int httpStatus) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setStatus(httpStatus);
		try {
			response.setContentLength(text.getBytes("utf-8").length);
		} catch (UnsupportedEncodingException e1) {
			log.error("render exception：",e1);
		}
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}finally{
			try {
				response.getWriter().flush();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
	}
}
