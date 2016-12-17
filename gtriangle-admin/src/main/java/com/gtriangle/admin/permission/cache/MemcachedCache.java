package com.gtriangle.admin.permission.cache;

//public class MemcachedCache implements org.springframework.cache.Cache {


//	private com.google.code.ssm.Cache cache;  
//    
//    public com.google.code.ssm.Cache getCache() {  
//        return cache;  
//    }  
//  
//    public void setCache(com.google.code.ssm.Cache cache) {  
//        this.cache = cache;  
//    }  
//  
//    @Override  
//    public String getName() {  
//        return this.cache.getName();  
//    }  
//  
//    @Override  
//    public Object getNativeCache() {  
//        return this.cache;  
//    }  
//  
//    @Override  
//    public ValueWrapper get(Object key) {  
//        Object object = null;  
//        try {  
//            object = this.cache.get((String)key, SerializationType.JAVA);  
//        } catch (TimeoutException e) {  
//            e.printStackTrace();  
//        } catch (CacheException e) {  
//            e.printStackTrace();  
//        }  
//        return (object != null ? new SimpleValueWrapper(object) : null);  
//    }  
//  
//    @Override  
//    public void put(Object key, Object value) {  
//        try {  
//            this.cache.set((String)key, 86400, value, SerializationType.JAVA);  
//        } catch (TimeoutException e) {  
//            e.printStackTrace();  
//        } catch (CacheException e) {  
//            e.printStackTrace();  
//        }  
//    }  
//  
//    @Override  
//    public void evict(Object key) {  
//        try {  
//            this.cache.delete((String)key);  
//        } catch (TimeoutException e) {  
//            e.printStackTrace();  
//        } catch (CacheException e) {  
//            e.printStackTrace();  
//        }  
//    }  
//  
//    @Override  
//    public void clear() {  
//        try {  
//            this.cache.flush();
//        } catch (TimeoutException e) {  
//            e.printStackTrace();  
//        } catch (CacheException e) {  
//            e.printStackTrace();  
//        }  
//    }
//
//	@Override
//	public <T> T get(Object key, Class<T> type) {
//		try {
//			return this.cache.get((String)key, SerializationType.JAVA);
//		} catch (TimeoutException e) {
//			e.printStackTrace();
//		} catch (CacheException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
//		// TODO Auto-generated method stub
//		return null;
//	}  
	

	
	
//}
