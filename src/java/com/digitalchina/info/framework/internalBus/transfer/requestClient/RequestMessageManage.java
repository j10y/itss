/**
 * @Probject Name: 10_InfoFramework_R1
 * @Path: com.digitalchina.info.framework.internalBus.transfer.requestClientRequestMessageManage.java
 * @Create By ����
 * @Create In Mar 16, 2008 2:19:17 PM
 * TODO
 */
package com.digitalchina.info.framework.internalBus.transfer.requestClient;

import com.digitalchina.info.framework.internalBus.transfer.requestClient.receive.RequestReceiveService;
import com.digitalchina.info.framework.internalBus.transfer.requestClient.send.RequestSendService;

/**
 * ����˹�����
 * ��������ģʽ
 * @Class Name RequestMessageManage
 * @Author ����
 * @Create In Mar 16, 2008
 */
public class RequestMessageManage {
	
	/**
	 * ����˽��շ���
	 */
	private RequestReceiveService requestReceiveService ;
	
	/**
	 * ����˷��ͷ���
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
