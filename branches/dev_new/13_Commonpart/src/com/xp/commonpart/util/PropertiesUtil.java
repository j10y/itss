package com.xp.commonpart.util;


import org.springframework.context.ApplicationContext;


public class PropertiesUtil {

	/**
	 * 获取资源文件信息
	 * 
	 * @Methods Name getProperties
	 * @param Key
	 *            资源文件Key
	 * @param defaultValue
	 *            默认信息
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, new Object[0], ContextHolder.getInstance().getLocal());

			return (message != null && !message.equals("") ? message
					: defaultValue);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 获取资源文件信息
	 * @Methods Name getProperties
	 * @param Key
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getProperties(String Key) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, new Object[0], ContextHolder.getInstance().getLocal());

			return (message != null && !message.equals("") ? message
					: null);
		} catch (Exception e) {
			return null;
		}
	}
}
