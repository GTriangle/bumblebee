package com.gtriangle.admin.util;

import com.gtriangle.admin.db.saas.IdWorker;

/**
 * 获得登录用户信息，租户信息等
 */
public class IdGenerateUtils {

	private static IdWorker idWorker = new IdWorker(10);

	public static Long getNextval(){
		return idWorker.nextId();
	}
}
