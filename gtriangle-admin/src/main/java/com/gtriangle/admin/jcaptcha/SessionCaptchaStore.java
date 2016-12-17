package com.gtriangle.admin.jcaptcha;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaAndLocale;
import com.octo.captcha.service.captchastore.CaptchaStore;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Collection;
import java.util.Locale;

public class SessionCaptchaStore implements CaptchaStore {
	

	@Override
	public Captcha getCaptcha(String id) throws CaptchaServiceException {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		CaptchaAndLocale captchaAndLocale = (CaptchaAndLocale)session.getAttribute(id);
		return captchaAndLocale != null ? (captchaAndLocale.getCaptcha()) : null;
	}

	

	@Override
	public Locale getLocale(String id) throws CaptchaServiceException {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		CaptchaAndLocale captchaAndLocale = (CaptchaAndLocale)session.getAttribute(id);
		return captchaAndLocale != null ? (captchaAndLocale.getLocale()) : null;
	}

	

	@Override
	public boolean hasCaptcha(String id) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		CaptchaAndLocale captcha = (CaptchaAndLocale)session.getAttribute(id);
        return captcha != null;
		//return false;
	}

	@Override
	public boolean removeCaptcha(String id) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.removeAttribute(id);
		return true;
	}

	@Override
	public void storeCaptcha(String id, Captcha captcha) throws CaptchaServiceException {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute(id, new CaptchaAndLocale(captcha));
	}

	@Override
	public void storeCaptcha(String id, Captcha captcha, Locale arg2) throws CaptchaServiceException {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute(id,  new CaptchaAndLocale(captcha, arg2));
	}
	
	@Override
	public void cleanAndShutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void empty() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void initAndStart() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Collection getKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
