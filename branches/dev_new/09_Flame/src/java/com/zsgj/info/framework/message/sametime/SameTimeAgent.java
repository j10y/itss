package com.zsgj.info.framework.message.sametime;

import java.applet.Applet;

import netscape.javascript.JSObject;

import com.zsgj.info.framework.message.sametime.impl.SameTimeServiceImpl;

public class SameTimeAgent extends Applet {
	
	private SameTimeService sameTimeSession;
	
	private static Applet currentApplet;
	
	public void init(){
		currentApplet = this;
		
	}
	
	public static String callJS(String methodName, String[] args){
		String ret = "";
		try{
			JSObject window = (JSObject)JSObject.getWindow(currentApplet);
			Object retVal = window.call(methodName, args);
			if(retVal == null){
				ret = null;
			}else if(retVal.toString().equals("undefined")){
				ret = null;
			}else{
				ret = retVal.toString();
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @Return the SameTimeServiceImpl sameTimeSession
	 */
	public SameTimeServiceImpl getSameTimeSession(String loginUser) {
		sameTimeSession = (SameTimeServiceImpl)SameTimeSessionFactory.getInstance(loginUser);
		if(!sameTimeSession.isLoginScuess()){
			return null;
		}else if(sameTimeSession.isLoginScuess() && sameTimeSession.isHasError()){
			return null;
		}else{
			return (SameTimeServiceImpl)sameTimeSession;
		}
	}

	/**
	 * @Param SameTimeServiceImpl sameTimeSession to set
	 */
	public void setSameTimeSession(SameTimeServiceImpl sameTimeSession) {
		this.sameTimeSession = sameTimeSession;
	}
}
