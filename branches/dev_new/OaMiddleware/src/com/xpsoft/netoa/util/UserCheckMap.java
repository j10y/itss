package com.xpsoft.netoa.util;

import java.util.HashMap;


/**
 * 用户密码map
 * @Class Name UserCheckMap
 * @Author likang
 * @Create In Jul 28, 2011
 */
public class UserCheckMap {
	
	private final static UserCheckMap instance = new UserCheckMap();
	
	private static HashMap<String, String> data = new HashMap<String, String>();
	
	/** 
	 * 单例模式
	 * @Methods Name getInstance
	 * @Create In Sep 1, 2010 By likang
	 * @return InitSys
	 */
    public static UserCheckMap getInstance(){  
        return instance;  
    }  
    
    /**
     * 否通过验证
     * @Methods Name passCheck
     * @Create In Jul 28, 2011 By likang
     * @param key
     * @param value
     * @return boolean
     */
    public static boolean passCheck(String key, String value) {
    	System.out.println(data);
    	boolean pass = false;
    	if (key != null && value != null) {
    		if (data.get(key) != null && data.get(key).equals(value)) {
				pass = true;
			}
		}
    	return pass;
    }
    
    /**
     * 增加
     * @Methods Name put
     * @Create In Jul 28, 2011 By likang
     * @param key
     * @param value void
     */
    public static void put(String key, String value){
    	data.put(key, value);
    }
    
    /**
     * 获取
     * @Methods Name get
     * @Create In Jul 30, 2011 By likang
     * @param key void
     */
    public static String get(String key){
    	return data.get(key);
    }

}
