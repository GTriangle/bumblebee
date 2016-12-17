package com.gtriangle.admin.permission.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * 使用 spring 封装 shiro cache
 * @author Brian
 *
 */
public class SpringCacheManagerWrapper implements CacheManager,Destroyable  {

	
	private static final Logger log = LoggerFactory.getLogger(SpringCacheManagerWrapper.class);
	 
	private org.springframework.cache.CacheManager cacheManager;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		org.springframework.cache.Cache springCache = cacheManager.getCache(name);
		return new SpringCacheWrapper(springCache);
	}
	/**
	 * 设置spring cache manager
	 *
	 * @param cacheManager
	 */
	public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	static class SpringCacheWrapper<K,V> implements Cache<K, V> {

		private org.springframework.cache.Cache springCache;

		SpringCacheWrapper(org.springframework.cache.Cache springCache) {
			this.springCache = springCache;
		}

		@Override
		public Object get(Object key) throws CacheException {
			log.debug("#########load cache = {} ###################",key);
			Object value = springCache.get(key);
			if (value instanceof SimpleValueWrapper) {
				return ((SimpleValueWrapper) value).get();
			}
			return value;
		}

		@Override
		public V put(K key, V value) throws CacheException {
			springCache.put(key, value);
			return value;
		}

		@Override
		public V remove(K key) throws CacheException {
			springCache.evict(key);
			return null;
		}

		@Override
		public void clear() throws CacheException {
			springCache.clear();
		}

		@Override
		public int size() {
			throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
		}

		@Override
		public Set keys() {
			throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
		}

		@Override
		public Collection values() {
			throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
		}
	}

	@Override
	public void destroy() throws Exception {
		if(cacheManager != null) {
			Collection<String> cacheNames = cacheManager.getCacheNames();
			for(String name : cacheNames) {
				log.debug("#########destroy cache = {} ###################",name);
				org.springframework.cache.Cache springCache = cacheManager.getCache(name);
				//springCache.clear();
			}
			
		}
		
	}
}
