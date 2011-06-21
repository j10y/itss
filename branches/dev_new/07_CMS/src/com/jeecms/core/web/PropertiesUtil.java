package com.jeecms.core.web;

import java.text.MessageFormat;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

public class PropertiesUtil {

	/**
	 * 获取资源文件内容，有默认值
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 * @param defaultValue
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, null, null);

			return (message != null && !message.equals("") ? message
					: defaultValue);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 获取资源文件内容，有默认值
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 * @param defaultValue
	 * @param local
	 * 			客户请求中的区域
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getProperties(String Key, String defaultValue, Locale local) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, new Object[0], local);

			return (message != null && !message.equals("") ? message
					: defaultValue);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 获取资源文件内容，无默认值
	 * @Methods Name getProperties
	 * @Create In 2008-10-19 By sa
	 * @param Key
	 * @param local
	 * 			客户请求中的区域
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getProperties(String Key, Locale local) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, new Object[0], local);

			return (message != null && !message.equals("") ? message
					: null);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取资源文件内容，无默认值
	 * @Methods Name getProperties
	 * @Create In 2008-10-19 By sa
	 * @param Key
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getProperties(String Key) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key,  null, null);

			return (message != null && !message.equals("") ? message
					: null);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据参数格式化资源文件内容
	 * @Methods Name format
	 * @Create In 2009-4-23 By jack
	 * @param source 为格式化信息
	 * @param arg 格式化的参数
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String format(String source,Object[] arg){
		MessageFormat formatter = new MessageFormat("");
		formatter.setLocale(ContextHolder.getInstance().getLocal());
		String value = "";
		try{
			value = formatter.format(source,arg);
		}catch(Exception e){
			value = source;
		}
		
		return value;
	}
}
