package com.xpsoft.padoa.test.entity;

import java.util.Date;

import com.xpsoft.framework.dao.BaseObject;


/**
 * 用于记录
 * 交易用户向flash行情交易系统发送信息的日志
 * @Class Name BusinessLoginSendInfoLog
 * @Author likang
 * @Create In Aug 14, 2010
 */
public class LoginSendLog extends BaseObject{
	//主键
	private Long id;
	//发送json信息（不包含密码）
	private String sendInfo;
	//接收json信息
	private String receiveInfo;
	//资金账号
	private String account;
	//发送时间
	private Date sendTime;
	//发送时间
	private Date receiveTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSendInfo() {
		return sendInfo;
	}
	public void setSendInfo(String sendInfo) {
		this.sendInfo = sendInfo;
	}
	public String getReceiveInfo() {
		return receiveInfo;
	}
	public void setReceiveInfo(String receiveInfo) {
		this.receiveInfo = receiveInfo;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	

}
