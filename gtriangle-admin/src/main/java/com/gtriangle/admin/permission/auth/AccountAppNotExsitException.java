package com.gtriangle.admin.permission.auth;

import org.apache.shiro.authc.AccountException;

/**
 * 账号处于有效状态，但对应子系统没有开通，无法正常登陆
 * i.e. 没有开通连锁总店
* @author Brian   
* @date 2016年9月20日 下午1:26:14
 */
public class AccountAppNotExsitException extends AccountException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5163412072841305054L;

	/**
     * Creates a new UnActivedException.
     */
    public AccountAppNotExsitException() {
        super();
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param message the reason for the exception
     */
    public AccountAppNotExsitException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public AccountAppNotExsitException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UnActivedException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public AccountAppNotExsitException(String message, Throwable cause) {
        super(message, cause);
    }
}
