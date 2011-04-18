package com.digitalchina.info.framework.message.sametime.impl;

import javax.servlet.http.HttpSession;

import com.digitalchina.info.framework.message.sametime.SameTime;
import com.digitalchina.info.framework.message.sametime.SameTimeAgent;
import com.digitalchina.info.framework.message.sametime.SameTimeSessionFactory;
import com.digitalchina.info.framework.service.BaseService;
import com.lotus.sametime.core.comparch.STSession;
import com.lotus.sametime.post.Post;
import com.lotus.sametime.post.PostEvent;
import com.lotus.sametime.post.PostService;
import com.lotus.sametime.post.PostServiceListener;

public class SameTimePublisherReceiver extends BaseService implements PostServiceListener {
	
	private PostService postService;
	
	private HttpSession reqSession;
	
	private String jsMethodName;
	
	public SameTimePublisherReceiver(STSession session, HttpSession reqSession,String methodName){
		this.postService = (PostService)session.getCompApi(PostService.COMP_NAME);
		postService.registerPostType(SameTimeSessionFactory.SYSTEM_ALERT_EVENT_TYPE);
		postService.registerPostType(SameTimeSessionFactory.SYSTEM_REQUEST_EVENT_TYPE);
		this.reqSession = reqSession;
		this.jsMethodName = methodName;
		postService.addPostServiceListener(this);
	}
	
	public void posted(PostEvent arg0) {
		// TODO Auto-generated method stub
		SameTime sameTime = (SameTime)reqSession.getAttribute("UserToken");
		sameTime.setReceiverMsg(arg0.getResponseMessage());
		reqSession.setAttribute("UserToken", sameTime);
		
		if(reqSession.getAttribute("applet") != null){
			Post message = arg0.getPost();
			String messageText = message.getMessage();
			String from  = message.getSenderDetails().getDisplayName();
			String subject = message.getTitle();
			String messageType = new Integer(message.getTitle()).toString();
			
			message.respond(1, "OK");
			
			String arguments[] = {messageText, from, subject, messageType};
			
			SameTimeAgent.callJS(jsMethodName, arguments);
		}
	}

}
