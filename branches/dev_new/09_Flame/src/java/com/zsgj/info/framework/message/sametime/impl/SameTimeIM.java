package com.zsgj.info.framework.message.sametime.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import com.lotus.sametime.core.comparch.STSession;
import com.lotus.sametime.core.constants.EncLevel;
import com.lotus.sametime.core.constants.ImTypes;
import com.lotus.sametime.core.types.STUser;
import com.lotus.sametime.im.Im;
import com.lotus.sametime.im.ImEvent;
import com.lotus.sametime.im.ImListener;
import com.lotus.sametime.im.ImServiceListener;
import com.lotus.sametime.im.InstantMessagingService;

public class SameTimeIM implements ImServiceListener, ImListener{
	
	private InstantMessagingService imService;
	
	private Im im;
	
	private STSession session;
	
	private HttpSession reqSession;
	
	private String sendText;
	
	public SameTimeIM(STSession session, HttpSession reqSession){
		this.session = session;
		this.reqSession = reqSession;
		
		this.imService = (InstantMessagingService)this.session.getCompApi(InstantMessagingService.COMP_NAME);
	}
	
	public void sendMessage(STUser stUser,String sendText){
		this.im = this.imService.createIm(stUser, EncLevel.ENC_LEVEL_NONE, ImTypes.IM_TYPE_CHAT);
		this.im.addImListener(this);
		this.im.open();
	}
	
	public void imReceived(ImEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void dataReceived(ImEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void imClosed(ImEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void imOpened(ImEvent arg0) {
		// TODO Auto-generated method stub
		Im im = arg0.getIm();
		if(im.isOpen()){
			this.sendDataMessage(im, "data", "richtext", new byte[]{(byte) 0xEE});
			im.sendText(true, this.sendText);
		}
	}

	public void openImFailed(ImEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void textReceived(ImEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 通知接收方将发送富信息，例如发送连接等
	 * @Methods Name sendDataMessage
	 * @Create In Feb 1, 2008 By Iceman
	 * @param im SameTime会话对象
	 * @param messageType　消息内容类型　＂ｄａｔａ＂
	 * @param messageSubType　信息内容类型，富内容为＂richtext＂
	 * @param messageBytes void
	 */
	private void sendDataMessage(Im im , String messageType, String messageSubType, byte[] messageBytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(baos);
		try {
			dataStream.writeUTF(messageType);
			dataStream.writeUTF(messageSubType);
			dataStream.write(messageBytes);
		} catch (IOException e) {
			throw new AssertionError("sendDataMessage failed");
		}
		im.sendData(true, 27191, 0, baos.toByteArray());
	}
}
