package com.xpsoft.framework.cache;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
/**
 * OSCache缓存
 * @Class Name BaseCache
 * @Author likang
 * @Create In Aug 10, 2010
 */
public class BaseCache extends GeneralCacheAdministrator {
	
	Log log = LogFactory.getLog(BaseCache.class);
	
	// 过期时间(单位为秒);
	private int refreshPeriod;

	// 关键字前缀字符;
	private String keyPrefix;

	private static final long serialVersionUID = -6697192675590868L;
	
	public BaseCache() {
		super();
//		Properties props;
//		props = Resources.getResourceAsProperties("ApplicationResources.properties");
//		refreshPeriod = (String) props.get("oscache.refreshPeriod");
//		keyPrefix = (String) props.get("oscache.keyPrefix");
		//默认缓存一天
		refreshPeriod = 60*60*24*1;
		keyPrefix = "cache";
		this.keyPrefix = keyPrefix;
		this.refreshPeriod = refreshPeriod;
		log.info(" ... initialized OScache successfully!");
	}

	/**
	 * 添加被缓存的对象
	 * @Methods Name put
	 * @Create In Aug 10, 2010 By likang
	 * @param key
	 * @param value void
	 */
	public void put(String key, Object value) {
		this.putInCache(this.keyPrefix + "_" + key, value);
	}

	/**
	 * 删除被缓存的对象
	 * @Methods Name remove
	 * @Create In Aug 10, 2010 By likang
	 * @param key void
	 */
	public void remove(String key) {
		this.flushEntry(this.keyPrefix + "_" + key);
	}

	/**
	 * 删除所有被缓存的对象
	 * @Methods Name removeAll
	 * @Create In Aug 10, 2010 By likang
	 * @param date void
	 */
	public void removeAll(Date date) {
		this.flushAll(date);
	}
	
	/**
	 * 删除所有被缓存的对象
	 * @Methods Name removeAll
	 * @Create In Aug 10, 2010 By likang void
	 */
	public void removeAll() {
		this.flushAll();
	}

	/**
	 * 获取被缓存的对象
	 * @Methods Name get
	 * @Create In Aug 10, 2010 By likang
	 * @param key
	 * @return
	 * @throws Exception Object
	 */
	public Object get(String key){
		try {
			return this.getFromCache(this.keyPrefix + "_" + key,
					this.refreshPeriod);
		} catch (NeedsRefreshException e) {
			this.cancelUpdate(this.keyPrefix + "_" + key);
//			throw e;
			return null;
		}
	}

}
