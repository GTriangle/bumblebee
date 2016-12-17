<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <%
		String 	CDN_VERSION 	=   "?cdnversion=mo-20161206-0-001";

//		String	CDN_TYPE		=	"";//更新CDN静态资源节点
//		String	LIB_CDN_TYPE	=	"";//静态资源类库CDN资源节点
		boolean IS_DEBUG		=	true;
        String domain = "http://" + request.getServerName();
		if(domain.contains("192")|| domain.contains("localhost")){
			domain = domain + ":" + request.getLocalPort();
		}
        String 	systemModel		= "dev";	
        if(domain.contains("192")|| domain.contains("localhost")|| domain.contains("admintest")){
            systemModel = "dev";
        }else{
            systemModel = "online";
        }
		if(!"dev".equals(systemModel)){             //线上部署时的节点设定
			IS_DEBUG		=	false;				//是否打印日志
		}

		/**
		*:统一的资源路径设定,其他位置都将引用这个地方的设定
		*:系统默认传递的是不带"/"的地址
		*/
		String fc_account_domain=  domain + "/";
		String fc_base_domain 	=  domain + "/mgt/";
		String fc_cdn_lib 		=  domain + "/res/lib/";
		String fc_cdn_static 	=  domain + "/res/page/";
		String fc_cdn_image		=  "";
		
	%>