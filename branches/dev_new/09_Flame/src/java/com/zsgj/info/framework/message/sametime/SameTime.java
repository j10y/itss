package com.zsgj.info.framework.message.sametime;


public class SameTime {

	private String fullUserName;
	
	private String token;
	
	private String error;
	
	private String receiverMsg;

	/**
	 * @Return the String receiverMsg
	 */
	public String getReceiverMsg() {
		return receiverMsg;
	}

	/**
	 * @Param String receiverMsg to set
	 */
	public void setReceiverMsg(String receiverMsg) {
		this.receiverMsg = receiverMsg;
	}

	/**
	 * @Return the String fullUserName
	 */
	public String getFullUserName() {
		return fullUserName;
	}

	/**
	 * @Param String fullUserName to set
	 */
	public void setFullUserName(String fullUserName) {
		this.fullUserName = fullUserName;
	}

	/**
	 * @Return the String token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @Param String token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @Return the String error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @Param String error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

}
