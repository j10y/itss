/**
 * @Probject Name: 10_InfoFramework_R1
 * @Path: com.digitalchina.info.framework.internalBus.transfer.responseClientResponseMessageManage.java
 * @Create By 张鹏
 * @Create In Mar 16, 2008 2:20:12 PM
 * TODO
 */
package com.zsgj.info.framework.internalBus.transfer.responseClient;

import com.zsgj.info.framework.internalBus.transfer.responseClient.receive.ResponseReceiveService;
import com.zsgj.info.framework.internalBus.transfer.responseClient.send.ResponseSendService;

/**
 * 接收端消息管理
 * 利用门面模式
 * @Class Name ResponseMessageManage
 * @Author 张鹏
 * @Create In Mar 16, 2008
 */
public class ResponseMessageManage {
	
	/**
	 * 接收端消息接收处理服务
	 */
	private ResponseReceiveService responseReceiveService;
	
	/**
	 * 接收端消息发送服务
	 */
	private ResponseSendService responseSendService;

	/**
	 * @Return the ResponseReceiveService responseReceiveService
	 */
	public ResponseReceiveService getResponseReceiveService() {
		return responseReceiveService;
	}

	/**
	 * @Param ResponseReceiveService responseReceiveService to set
	 */
	public void setResponseReceiveService(
			ResponseReceiveService responseReceiveService) {
		this.responseReceiveService = responseReceiveService;
	}

	/**
	 * @Return the ResponseSendService responseSendService
	 */
	public ResponseSendService getResponseSendService() {
		return responseSendService;
	}

	/**
	 * @Param ResponseSendService responseSendService to set
	 */
	public void setResponseSendService(ResponseSendService responseSendService) {
		this.responseSendService = responseSendService;
	}
}
