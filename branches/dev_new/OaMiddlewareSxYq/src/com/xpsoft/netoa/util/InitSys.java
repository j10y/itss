package com.xpsoft.netoa.util;

import java.util.HashMap;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 系统初始化
 * @Class Name InitSys
 * @Author likang
 * @Create In Sep 1, 2010
 */
public class InitSys {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private final static InitSys instance = new InitSys();
	
	/**
	 * 单例模式
	 * @Methods Name getInstance
	 * @Create In Sep 1, 2010 By Administrator
	 * @return InitSys
	 */
    public static InitSys getInstance(){  
        return instance;  
    }  
	
	/**
	 * 初始化用户密码map，用于每次功能操作的安全验证
	 * @Methods Name initUserCheckMap
	 * @Create In Jul 28, 2011 By likang
	 * @param context void
	 */
	public void initUserCheckMap(ServletContext context) {
		context.setAttribute(Constants.USER_CHECK_MAP, UserCheckMap.getInstance());
	}
	
	/**
	 * 获取用户密码map
	 * @Methods Name getUserCheckMap
	 * @Create In Jul 28, 2011 By likang
	 * @param context
	 * @return UserCheckMap
	 */
//	public UserCheckMap getUserCheckMap(ServletContext context) {
//		return (UserCheckMap) context.getAttribute(Constants.USER_CHECK_MAP);
//	}
}
