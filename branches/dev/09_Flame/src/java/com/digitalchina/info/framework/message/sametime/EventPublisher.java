package com.digitalchina.info.framework.message.sametime;

/**
 * SameTime��Ϣ������
 * @Class Name EventPublisher
 * @Author zhangpeng
 * @Create In Feb 2, 2008
 */
public interface EventPublisher {
	
	/**
	 * ������Ϣ
	 * @Methods Name publishEvent
	 * @Create In Feb 2, 2008 By Iceman
	 * @param eventType ��Ϣ����,������SameTimeSessionFactory
	 * @param title ��Ϣ����
	 * @param body ��Ϣ����
	 * @param recipients �������ַ�������##�ָ�
	 */
	public void publishEvent(int eventType, String title, String body, String recipients);
}
