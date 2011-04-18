package com.digitalchina.info.framework.workflow.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class ConfigUnitMailNodeSender extends BaseObject{
	
	private Long id;
	private String template;
	private String subject;
	private String content;
	private String recipient;//这是集团外用户的用户名和email地址，集团外以字符串形式保存到数据库中
	private Set<UserInfo> userInfos = new HashSet<UserInfo>();
	private Long virtualProcessId;
	private Long nodeId;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result
				+ ((recipient == null) ? 0 : recipient.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result
				+ ((template == null) ? 0 : template.hashCode());
		result = prime * result
				+ ((userInfos == null) ? 0 : userInfos.hashCode());
		result = prime
				* result
				+ ((virtualProcessId == null) ? 0 : virtualProcessId.hashCode());
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
		final ConfigUnitMailNodeSender other = (ConfigUnitMailNodeSender) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (recipient == null) {
			if (other.recipient != null)
				return false;
		} else if (!recipient.equals(other.recipient))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		if (userInfos == null) {
			if (other.userInfos != null)
				return false;
		} else if (!userInfos.equals(other.userInfos))
			return false;
		if (virtualProcessId == null) {
			if (other.virtualProcessId != null)
				return false;
		} else if (!virtualProcessId.equals(other.virtualProcessId))
			return false;
		return true;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Set<UserInfo> getUserInfos() {
		return userInfos;
	}
	public void setUserInfos(Set<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	public Long getVirtualProcessId() {
		return virtualProcessId;
	}
	public void setVirtualProcessId(Long virtualProcessId) {
		this.virtualProcessId = virtualProcessId;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
}
