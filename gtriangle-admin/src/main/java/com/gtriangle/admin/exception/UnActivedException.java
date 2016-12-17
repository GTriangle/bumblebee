package com.gtriangle.admin.exception;

import org.apache.shiro.authc.DisabledAccountException;

/**
 * 账号未激活异常
* @author Brian   
* @date 2016年2月2日 下午4:37:57
 */
public class UnActivedException extends DisabledAccountException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5163412072841305054L;

	/**
     * Creates a new UnActivedException.
     */
    public UnActivedException() {
        super();
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param message the reason for the exception
     */
    public UnActivedException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public UnActivedException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public UnActivedException(String message, Throwable cause) {
        super(message, cause);
    }
}
