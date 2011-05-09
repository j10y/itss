/**
 * @Probject Name: 10_InfoFramework_R1
 * @Path: com.digitalchina.info.framework.internalBus.transfer.responseClientResponseMessageManage.java
 * @Create By ����
 * @Create In Mar 16, 2008 2:20:12 PM
 * TODO
 */
package com.zsgj.info.framework.internalBus.transfer.responseClient;

import com.zsgj.info.framework.internalBus.transfer.responseClient.receive.ResponseReceiveService;
import com.zsgj.info.framework.internalBus.transfer.responseClient.send.ResponseSendService;

/**
 * ���ն���Ϣ����
 * ��������ģʽ
 * @Class Name ResponseMessageManage
 * @Author ����
 * @Create In Mar 16, 2008
 */
public class ResponseMessageManage {
	
	/**
	 * ���ն���Ϣ���մ������
	 */
	private ResponseReceiveService responseReceiveService;
	
	/**
	 * ���ն���Ϣ���ͷ���
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
