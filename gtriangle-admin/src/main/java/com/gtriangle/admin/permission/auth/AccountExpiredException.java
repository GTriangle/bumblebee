package com.gtriangle.admin.permission.auth;

import org.apache.shiro.authc.DisabledAccountException;

/**
 * 账号没有交费，或者已经过期
* @author Brian   
* @date 2016年2月2日 下午4:37:57
 */
public class AccountExpiredException extends DisabledAccountException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5163412072841305054L;

	/**
     * Creates a new UnActivedException.
     */
    public AccountExpiredException() {
        super();
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param message the reason for the exception
     */
    public AccountExpiredException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public AccountExpiredException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public AccountExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
