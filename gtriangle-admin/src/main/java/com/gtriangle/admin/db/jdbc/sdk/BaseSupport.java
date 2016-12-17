package com.gtriangle.admin.db.jdbc.sdk;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSupport<T> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private IDBRouter baseDBRouter;
	protected IDaoSupport<T> baseDaoSupport;
	@Resource(name = "daoSupport")
	protected IDaoSupport<T> daoSupport;

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	protected String getTableName(String moude) {
		return baseDBRouter.getTableName(moude);

	}

	public IDaoSupport<T> getDaoSupport() {
		return daoSupport;
	}


	public IDaoSupport<T> getBaseDaoSupport() {
		return baseDaoSupport;
	}

	public void setBaseDaoSupport(IDaoSupport<T> baseDaoSupport) {
		this.baseDaoSupport = baseDaoSupport;
	}

	public void setDaoSupport(IDaoSupport<T> daoSupport) {
		this.daoSupport = daoSupport;
	}

	public IDBRouter getBaseDBRouter() {
		return baseDBRouter;
	}

	public void setBaseDBRouter(IDBRouter baseDBRouter) {
		this.baseDBRouter = baseDBRouter;
	}
}
