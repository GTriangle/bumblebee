package com.gtriangle.admin.jcaptcha;

import java.util.Locale;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * <p>Employee: Zhang Kaitao
 * <p>Date: 13-3-22 下午3:38
 * <p>Version: 1.0
 */
public class MyManageableImageCaptchaService extends DefaultManageableImageCaptchaService {

	public MyManageableImageCaptchaService(com.octo.captcha.service.captchastore.CaptchaStore captchaStore
			, com.octo.captcha.engine.CaptchaEngine captchaEngine
			, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
		super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
	}

	protected Captcha generateAndStoreCaptcha(Locale locale, String ID)
	/*     */   {
		/* 156 */     Captcha captcha = this.engine.getNextCaptcha(locale);
		captcha.getChallenge();
		/* 157 */     this.store.storeCaptcha(ID, captcha, locale);
		/* 158 */     return captcha;
	/*     */   }


	public boolean hasCapcha(String id, String userCaptchaResponse) {  
		 
		return store.getCaptcha(id) != null && 
				store.getCaptcha(id).validateResponse(userCaptchaResponse);  
	} 






}
