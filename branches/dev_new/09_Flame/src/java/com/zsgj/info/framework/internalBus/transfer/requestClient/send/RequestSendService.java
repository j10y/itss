package com.zsgj.info.framework.internalBus.transfer.requestClient.send;

/**
 * 请求端消息发送服务
 * @Class Name RequestSendService
 * @Author 张鹏
 * @Create In Mar 16, 2008
 */
public interface RequestSendService {
	
	/**
	 * 发送同步消息，发送后发送端必须等待返回后才能完成下面的操作
	 * @Methods Name sendSynRequest
	 * @Create In Mar 16, 2008 By 张鹏
	 * @param RequestMessage 请求消息，按照类型传入
	 * @return 返回具体返回消息类型
	 */
	public Object sendSynRequest(Object RequestMessage);
	
	/**
	 * 发送异步消息，发送后发送端不需要等待返回即可完成下面的操作
	 * @Methods Name sendASyncRequest
	 * @Create In Mar 16, 2008 By 张鹏
	 * @param RequestMessage 请求消息，按照类型传入
	 */
	public void sendASyncRequest(Object RequestMessage);
}
