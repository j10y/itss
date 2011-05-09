package com.zsgj.info.framework.internalBus.transfer.requestClient.send;

/**
 * �������Ϣ���ͷ���
 * @Class Name RequestSendService
 * @Author ����
 * @Create In Mar 16, 2008
 */
public interface RequestSendService {
	
	/**
	 * ����ͬ����Ϣ�����ͺ��Ͷ˱���ȴ����غ�����������Ĳ���
	 * @Methods Name sendSynRequest
	 * @Create In Mar 16, 2008 By ����
	 * @param RequestMessage ������Ϣ���������ʹ���
	 * @return ���ؾ��巵����Ϣ����
	 */
	public Object sendSynRequest(Object RequestMessage);
	
	/**
	 * �����첽��Ϣ�����ͺ��Ͷ˲���Ҫ�ȴ����ؼ����������Ĳ���
	 * @Methods Name sendASyncRequest
	 * @Create In Mar 16, 2008 By ����
	 * @param RequestMessage ������Ϣ���������ʹ���
	 */
	public void sendASyncRequest(Object RequestMessage);
}
