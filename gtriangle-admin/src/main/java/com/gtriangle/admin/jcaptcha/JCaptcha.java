package com.gtriangle.admin.jcaptcha;


import java.awt.image.BufferedImage;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaStore;

/**
 * 图片验证码服务
* @author Brian   
* @date 2016年2月27日 下午3:27:58
 */
public class JCaptcha {
	

    private MyManageableImageCaptchaService captchaService;
    
    public JCaptcha(CaptchaStore captchaStore) {
    	captchaService = new MyManageableImageCaptchaService(
    			captchaStore
    			, new ImageCaptchaEngineExtend()
    			, 180
    			, 100000
    			, 75000);
    }

    public boolean validateResponse(HttpServletRequest request, String userCaptchaResponse) {
        //if (request.getSession(false) == null) return false;

        boolean validated = false;
        try {
            String id = request.getSession().getId();
            validated = captchaService.validateResponseForID(id, userCaptchaResponse).booleanValue();
        } catch (CaptchaServiceException e) {
            e.printStackTrace();
        }
        return validated;
    }
    
    public boolean hasCaptcha(HttpServletRequest request, String userCaptchaResponse) {  
            if (request.getSession(false) == null) return false;  
            boolean validated = false;  
            try {  
                String id = request.getSession().getId();  
                validated = captchaService.hasCapcha(id, userCaptchaResponse);  
            } catch (CaptchaServiceException e) {  
                e.printStackTrace();  
            }  
            return validated;  
        }  
    
    public BufferedImage getImageChallengeForID(String id, Locale locale) {
    	return captchaService.getImageChallengeForID(id, locale);
    }

//    public boolean hasCaptcha(HttpServletRequest request, String userCaptchaResponse) {
//        if (request.getSession(false) == null) return false;
//        boolean validated = false;
//        try {
//            String id = request.getSession().getId();
//            validated = captchaService.hasCapcha(id, userCaptchaResponse);
//        } catch (CaptchaServiceException e) {
//            e.printStackTrace();
//        }
//        return validated;
//    }

}
