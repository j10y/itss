/**
 * @Probject Name: 10_InfoFramework_1
 * @Path: com.digitalchina.info.framework.security.entityUserActionLog.java
 * @Create By 张鹏
 * @Create In 2009-7-27 下午02:46:10
 * TODO
 */
package com.zsgj.info.framework.security.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * @Class Name UserActionLog
 * @Author 张鹏
 * @Create In 2009-7-27
 */
public class UserActionLog extends BaseObject {

	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * 操作用户名称
	 */
	private String userName; 
	
	/**
	 * 操作目标地址
	 */
	private String targetURL;
	
	/**
	 * 访问客户机地址
	 */
	private String clientIP;
	
	/**
	 * @Return the String clientIP
	 */
	public String getClientIP() {
		return clientIP;
	}

	/**
	 * @Param String clientIP to set
	 */
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	/**
	 * 操作时间
	 */
	private Date actionDate;
	
	/**
	 * 备注信息
	 */
	private String remark;
	

	/**
	 * @Return the String remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @Param String remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @Return the String userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @Param String userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @Return the String targetURL
	 */
	public String getTargetURL() {
		return targetURL;
	}

	/**
	 * @Param String targetURL to set
	 */
	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	/**
	 * @Return the Date actionDate
	 */
	public Date getActionDate() {
		return actionDate;
	}

	/**
	 * @Param Date actionDate to set
	 */
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	
}
