package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class ConfigUnitMailNodeSenderTable extends BaseObject{
	
	private Long id;
	private ConfigUnitMailNodeSender mailNodeSender;
	private UserInfo userInfo;
	private String mail;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ConfigUnitMailNodeSender getMailNodeSender() {
		return mailNodeSender;
	}
	public void setMailNodeSender(ConfigUnitMailNodeSender mailNodeSender) {
		this.mailNodeSender = mailNodeSender;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result
				+ ((mailNodeSender == null) ? 0 : mailNodeSender.hashCode());
		result = prime * result
				+ ((userInfo == null) ? 0 : userInfo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ConfigUnitMailNodeSenderTable other = (ConfigUnitMailNodeSenderTable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (mailNodeSender == null) {
			if (other.mailNodeSender != null)
				return false;
		} else if (!mailNodeSender.equals(other.mailNodeSender))
			return false;
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
	}
	
	
}
