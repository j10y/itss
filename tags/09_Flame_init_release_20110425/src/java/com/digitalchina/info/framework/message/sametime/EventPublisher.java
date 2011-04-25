package com.digitalchina.info.framework.message.sametime;

/**
 * SameTime消息发布者
 * @Class Name EventPublisher
 * @Author zhangpeng
 * @Create In Feb 2, 2008
 */
public interface EventPublisher {
	
	/**
	 * 发布消息
	 * @Methods Name publishEvent
	 * @Create In Feb 2, 2008 By Iceman
	 * @param eventType 消息类型,定义于SameTimeSessionFactory
	 * @param title 消息标题
	 * @param body 消息主体
	 * @param recipients 接收人字符串，用##分割
	 */
	public void publishEvent(int eventType, String title, String body, String recipients);
}
