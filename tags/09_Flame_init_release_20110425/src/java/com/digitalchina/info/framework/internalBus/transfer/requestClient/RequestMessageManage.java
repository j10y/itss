/**
 * @Probject Name: 10_InfoFramework_R1
 * @Path: com.digitalchina.info.framework.internalBus.transfer.requestClientRequestMessageManage.java
 * @Create By 张鹏
 * @Create In Mar 16, 2008 2:19:17 PM
 * TODO
 */
package com.digitalchina.info.framework.internalBus.transfer.requestClient;

import com.digitalchina.info.framework.internalBus.transfer.requestClient.receive.RequestReceiveService;
import com.digitalchina.info.framework.internalBus.transfer.requestClient.send.RequestSendService;

/**
 * 请求端管理类
 * 利用门面模式
 * @Class Name RequestMessageManage
 * @Author 张鹏
 * @Create In Mar 16, 2008
 */
public class RequestMessageManage {
	
	/**
	 * 请求端接收服务
	 */
	private RequestReceiveService requestReceiveService ;
	
	/**
	 * 请求端发送服务
	 */
	private RequestSendService requestResponseService;

	/**
	 * @Return the RequestReceiveService requestReceiveService
	 */
	public RequestReceiveService getRequestReceiveService() {
		return requestReceiveService;
	}

	/**
	 * @Param RequestReceiveService requestReceiveService to set
	 */
	public void setRequestReceiveService(RequestReceiveService requestReceiveService) {
		this.requestReceiveService = requestReceiveService;
	}

	/**
	 * @Return the RequestSendService requestResponseService
	 */
	public RequestSendService getRequestResponseService() {
		return requestResponseService;
	}

	/**
	 * @Param RequestSendService requestResponseService to set
	 */
	public void setRequestResponseService(RequestSendService requestResponseService) {
		this.requestResponseService = requestResponseService;
	}
}
