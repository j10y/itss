package com.xpsoft.framework.cache;

/**
 * 缓存管理类 提供对缓存的操作方法
 * @Class Name CacheManager
 * @Author likang
 * @Create In Aug 10, 2010
 */
public class CacheManager {  
    
    private BaseCache osCache;  
  
	private final static CacheManager instance = new CacheManager();

	public CacheManager() {  
            osCache = new BaseCache();
    }  
    
    /**
     * 获得实例
     * @Methods Name getInstance
     * @Create In Aug 10, 2010 By likang
     * @return CacheManager
     */
    public static CacheManager getInstance(){  
        return instance;  
    }  
    
    /**
     * 缓存对象
     * @Methods Name putOject
     * @Create In Aug 10, 2010 By likang
     * @param key
     * @param object void
     */
    public void putOject(String key,Object object) {  
        osCache.put(key,object);  
    }  
    
    /**
     * 删除被缓存对象
     * @Methods Name removeObject
     * @Create In Aug 10, 2010 By likang
     * @param key void
     */
    public void removeObject(String key) {  
        osCache.remove(key);  
    }  
    
    /**
     * 获得被缓存对象
     * @Methods Name getObject
     * @Create In Aug 10, 2010 By likang
     * @param key
     * @return Object
     */
    public Object getObject(String key) {  
        try {  
            return (Object) osCache.get(key);  
        } catch (Exception e) {
        	e.printStackTrace();
            return null;  
        }  
    }  
    
    /**
     * 删除所有被缓存对象
     * @Methods Name removeAll
     * @Create In Aug 10, 2010 By likang void
     */
    public void removeAll() {  
        osCache.removeAll();  
    }  
  
}  
