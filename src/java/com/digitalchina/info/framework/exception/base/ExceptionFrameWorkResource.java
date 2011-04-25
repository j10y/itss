package com.digitalchina.info.framework.exception.base;

import org.springframework.context.ApplicationContext;

import com.digitalchina.info.framework.context.ContextHolder;

/**
 * ϵͳĬ���쳣������
 * @Class Name ExceptionFrameWorkResource
 * @Author zhangpeng
 * @Create In Mar 6, 2008
 */
public class ExceptionFrameWorkResource {
	
	private static ApplicationContext appContext;

	public ExceptionFrameWorkResource(){
		appContext = ContextHolder.getInstance().getApplicationContext();
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @Methods Name getFrameWorkMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key ��Դ�ļ�Key
	 * @return String
	 */
	public static String getFrameWorkMessage(String Key) {
		return appContext.getMessage(Key, new Object[0], ContextHolder
				.getInstance().getLocal());
	}

	/**
	 * ��ȡ������Ϣ
	 * @Methods Name getFrameWorkMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key ��Դ�ļ�Key
	 * @param DefaultMessage Ĭ����Ϣ
	 * @return String
	 */
	public static String getFrameWorkMessage(String Key, String DefaultMessage) {
		String rescMessage = getFrameWorkMessage(Key);
		if (rescMessage != null) {
			return rescMessage;
		} else {
			return DefaultMessage;
		}
	}

}
