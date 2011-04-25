package com.digitalchina.info.framework.exception.base;

import org.springframework.context.ApplicationContext;

import com.digitalchina.info.framework.context.ContextHolder;

/**
 * 系统默认异常处理类
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
	 * 获取错误信息
	 * @Methods Name getFrameWorkMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key 资源文件Key
	 * @return String
	 */
	public static String getFrameWorkMessage(String Key) {
		return appContext.getMessage(Key, new Object[0], ContextHolder
				.getInstance().getLocal());
	}

	/**
	 * 获取错误信息
	 * @Methods Name getFrameWorkMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key 资源文件Key
	 * @param DefaultMessage 默认信息
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
