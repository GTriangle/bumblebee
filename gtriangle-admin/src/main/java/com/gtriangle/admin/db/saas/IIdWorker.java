package com.gtriangle.admin.db.saas;

/**
 * 分布式id生产器
 * @author Brian
 *
 */
public interface IIdWorker {
	
	public long nextId();
}
