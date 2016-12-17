package com.gtriangle.admin;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 为解决shiro一个request多次频繁访问session cache的问题
 * @author Brian
 *
 */
public class GTriangleWebSessionManager extends DefaultWebSessionManager {

	private static final Logger log = LoggerFactory.getLogger(GTriangleWebSessionManager.class);
	
	@Override
	protected Session retrieveSessionFromDataSource(Serializable sessionId) throws UnknownSessionException {
		
		Session session = null;
		HttpServletRequest request = GTriangleThreadContext.getHttpRequest();
		
		if(request != null) {
			session = (Session)request.getAttribute(Constants.CURRENT_REQUEST_SESSION);
			if(session != null) {
				return session;
			} else {
				session = super.retrieveSessionFromDataSource(sessionId);
				request.setAttribute(Constants.CURRENT_REQUEST_SESSION, session);
			}
		} else {
			session = super.retrieveSessionFromDataSource(sessionId);
		}
		return session;
	}

}
