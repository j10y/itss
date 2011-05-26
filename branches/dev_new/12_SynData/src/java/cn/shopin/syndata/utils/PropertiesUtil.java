package cn.shopin.syndata.utils;

import java.text.MessageFormat;

import org.springframework.context.ApplicationContext;

public class PropertiesUtil {

	/**
	 * 获取资源文件信息
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            资源文件Key
	 * @param defaultValue
	 *            默认信息
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getInstance().getApplicationContext();
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
	 * @Create In 2008-10-19 By sa
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
	
	/**
	 * 格式化资源字符串,将其中的类似{0}的变量替换为对应数据
	 * @Methods Name format
	 * @Create In 2009-4-23 By 张鹏
	 * @param source 资源文件中的内容
	 * @param arg 需要替换的参数值，请按照资源文件中实际位置排序
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
