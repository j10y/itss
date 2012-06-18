package com.xpsoft.framework.util;

import java.text.MessageFormat;

import org.springframework.context.ApplicationContext;

import com.xpsoft.framework.context.ContextHolder;

/**
 * 读取属性配置文件.propertries 的工具类
 * @Class Name PropertiesUtil
 * @Author likang
 * @Create In Jul 21, 2010
 */
public class PropertiesUtil {
	/**
	 * 获取资源文件中的属性配置信息
	 * @Methods Name getProperties
	 * @Create In Jul 21, 2010 By likang
	 * @param Key
	 *            资源文件Key
	 * @param defaultValue
	 *            默认信息
	 * @return String
	 */
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
	 * @see getProperties(String Key, String defaultValue) 无默认值
	 * @Methods Name getProperties
	 * @Create In Jul 21, 2010 By likang
	 * @param Key
	 * @return String
	 */
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
	
	/**
	 * 格式化资源字符串,将其中的类似{0}的变量替换为对应数据
	 * @Methods Name format
	 * @Create In Jul 21, 2010 By likang
	 * @param source 资源文件中的内容
	 * @param arg 需要替换的参数值，请按照资源文件中实际位置排序
	 * @return String
	 */
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
