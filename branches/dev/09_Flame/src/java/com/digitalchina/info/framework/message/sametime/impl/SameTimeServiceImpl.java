package com.digitalchina.info.framework.message.sametime.impl;

import javax.servlet.http.HttpSession;

import com.digitalchina.info.framework.message.sametime.EventPublisher;
import com.digitalchina.info.framework.message.sametime.SameTime;
import com.digitalchina.info.framework.message.sametime.SameTimeService;
import com.digitalchina.info.framework.service.BaseService;
import com.lotus.sametime.community.CommunityService;
import com.lotus.sametime.community.LoginEvent;
import com.lotus.sametime.community.LoginListener;
import com.lotus.sametime.core.comparch.DuplicateObjectException;
import com.lotus.sametime.core.comparch.STSession;
import com.lotus.sametime.token.TokenComp;
import com.lotus.sametime.token.TokenEvent;
import com.lotus.sametime.token.TokenService;
import com.lotus.sametime.token.TokenServiceListener;

public class SameTimeServiceImpl extends BaseService implements SameTimeService, TokenServiceListener, LoginListener{
	
	private String hostName;
	
	private String jsMethod;
	
	private boolean stop;
	
	private boolean success;
	
	private boolean loginScuess;
	
	private boolean hasError;
	
	private int waitSeconds;
	
	private HttpSession reqSession;
	
	private TokenService token;
	
	private STSession session ;
	
	private CommunityService comms;
	
	private SameTimePublisherReceiver receive;

	private EventPublisher publisher;
	
	public SameTimeServiceImpl(){
		this.hostName = getProperties("system.sametime.hostname","BJAS10");
		this.stop = false;
		this.loginScuess = false;
		
		waitSeconds = Integer.valueOf(getProperties("system.sametime.timeout","5")).intValue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.digitalchina.pmcenter.common.base.service.impl.SameTimeService#login(java.lang.String, java.lang.String, javax.servlet.http.HttpSession)
	 */
	public void login(String loginUser,String passWord,HttpSession reqSession,String jsMethod){

		this.reqSession = reqSession;		
		this.jsMethod = jsMethod;
		
		try {
			
			int timeOut = 0;
			
			session = new STSession(loginUser);
			session.loadAllComponents();
			session.start();
			comms = (CommunityService)session.getCompApi(CommunityService.COMP_NAME);
			
			token = new TokenComp(session);
			
			token.addTokenServiceListener(this);
			comms.addLoginListener(this);
			
			comms.loginByPassword(this.hostName, loginUser, passWord);
			
			while(!stop){
				Thread.sleep(50);
				if(timeOut++ * 50 >= waitSeconds * 1000){
					this.stop = true;
					this.success = false;
				}
			}
			
		} catch (DuplicateObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.digitalchina.pmcenter.common.base.service.impl.SameTimeService#logout()
	 */
	public void logout(){
		try{
			comms.logout();
			session.stop();
			session.unloadSession();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
	}
	
	/**
	 * @Return the String hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @Param String hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void generateTokenFailed(TokenEvent arg0) {

		this.loginScuess = true;
		this.hasError = true;
		
	}

	public void serviceAvailable(TokenEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void tokenGenerated(TokenEvent arg0) {
		// TODO Auto-generated method stub
		SameTime sameTime = new SameTime();
		
		sameTime.setFullUserName(arg0.getToken().getLoginName());
		sameTime.setToken(arg0.getToken().getTokenString());
		
		this.success = true;
		this.stop = true;
		this.loginScuess = true;
		
		
		this.reqSession.setAttribute("UserToken", sameTime);
	}

	public void loggedIn(LoginEvent arg0) {
		try{
			this.token.generateToken();
			publisher = new SameTimePublisher(this.session,this.reqSession);
			receive = new SameTimePublisherReceiver(this.session,this.reqSession,this.jsMethod);
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}

	public void loggedOut(LoginEvent arg0) {
		if(arg0.getReason() == 0){
			this.success = true;
		}else{
			this.logger.error("SameTime Login Fault!!");
		}
		this.stop = true;
	}

	/**
	 * @Return the boolean loginScuess
	 */
	public boolean isLoginScuess() {
		return loginScuess;
	}

	/**
	 * @Param boolean loginScuess to set
	 */
	public void setLoginScuess(boolean loginScuess) {
		this.loginScuess = loginScuess;
	}


	/**
	 * @Return the boolean hasError
	 */
	public boolean isHasError() {
		return hasError;
	}


	/**
	 * @Param boolean hasError to set
	 */
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	/**
	 * @Return the EventPublisher publisher
	 */
	public EventPublisher getPublisher() {
		return publisher;
	}

	/**
	 * @Param EventPublisher publisher to set
	 */
	public void setPublisher(EventPublisher publisher) {
		this.publisher = publisher;
	}

}
