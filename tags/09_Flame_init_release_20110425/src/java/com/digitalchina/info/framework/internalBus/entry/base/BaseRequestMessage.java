package com.digitalchina.info.framework.internalBus.entry.base;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 请求消息基类型
 * @Class Name BaseRequestMessage
 * @Author 张鹏
 * @Create In Mar 16, 2008
 */
public class BaseRequestMessage implements Serializable{
	
	protected long serialNo;
	
	protected int messageParentType;
	
	protected int messageType;
	
	protected Date createDate;
	
	protected String usedClassName;
	
	protected String usedMethodName;
	
	public Map parmateMap;
	
	public Map valueMap;

	/**
	 * @Return the long serialNo
	 */
	protected long getSerialNo() {
		return serialNo;
	}

	/**
	 * @Param long serialNo to set
	 */
	protected void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @Return the int messageParentType
	 */
	protected int getMessageParentType() {
		return messageParentType;
	}

	/**
	 * @Param int messageParentType to set
	 */
	protected void setMessageParentType(int messageParentType) {
		this.messageParentType = messageParentType;
	}

	/**
	 * @Return the int messageType
	 */
	protected int getMessageType() {
		return messageType;
	}

	/**
	 * @Param int messageType to set
	 */
	protected void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * @Return the Date createDate
	 */
	protected Date getCreateDate() {
		return createDate;
	}

	/**
	 * @Param Date createDate to set
	 */
	protected void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @Return the String usedClassName
	 */
	protected String getUsedClassName() {
		return usedClassName;
	}

	/**
	 * @Param String usedClassName to set
	 */
	protected void setUsedClassName(String usedClassName) {
		this.usedClassName = usedClassName;
	}

	/**
	 * @Return the String usedMethodName
	 */
	protected String getUsedMethodName() {
		return usedMethodName;
	}

	/**
	 * @Param String usedMethodName to set
	 */
	protected void setUsedMethodName(String usedMethodName) {
		this.usedMethodName = usedMethodName;
	}

	/**
	 * @Return the Map parmateMap
	 */
	public Map getParmateMap() {
		return parmateMap;
	}

	/**
	 * @Param Map parmateMap to set
	 */
	public void setParmateMap(Map parmateMap) {
		this.parmateMap = parmateMap;
	}

	/**
	 * @Return the Map valueMap
	 */
	public Map getValueMap() {
		return valueMap;
	}

	/**
	 * @Param Map valueMap to set
	 */
	public void setValueMap(Map valueMap) {
		this.valueMap = valueMap;
	}

}
