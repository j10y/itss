package cn.shopin.syndata.utils;

import java.text.MessageFormat;

import org.springframework.context.ApplicationContext;

public class PropertiesUtil {

	/**
	 * ��ȡ��Դ�ļ���Ϣ
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            ��Դ�ļ�Key
	 * @param defaultValue
	 *            Ĭ����Ϣ
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
	 * ��ȡ��Դ�ļ���Ϣ
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
	 * ��ʽ����Դ�ַ���,�����е�����{0}�ı����滻Ϊ��Ӧ����
	 * @Methods Name format
	 * @Create In 2009-4-23 By ����
	 * @param source ��Դ�ļ��е�����
	 * @param arg ��Ҫ�滻�Ĳ���ֵ���밴����Դ�ļ���ʵ��λ������
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
