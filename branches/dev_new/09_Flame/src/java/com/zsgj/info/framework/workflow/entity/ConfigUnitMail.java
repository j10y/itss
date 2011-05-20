package com.zsgj.info.framework.workflow.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class ConfigUnitMail extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 2965659679813095436L;
	private Long id;
	private String subject;
	private String content;
	private Long VirtualProcessId;
	private Long NodeId;
	private Set<UserInfo> userInfos = new HashSet<UserInfo>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getVirtualProcessId() {
		return VirtualProcessId;
	}
	public void setVirtualProcessId(Long virtualProcessId) {
		VirtualProcessId = virtualProcessId;
	}
	public Long getNodeId() {
		return NodeId;
	}
	public void setNodeId(Long nodeId) {
		NodeId = nodeId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((NodeId == null) ? 0 : NodeId.hashCode());
		result = prime
				* result
				+ ((VirtualProcessId == null) ? 0 : VirtualProcessId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ConfigUnitMail other = (ConfigUnitMail) obj;
		if (NodeId == null) {
			if (other.NodeId != null)
				return false;
		} else if (!NodeId.equals(other.NodeId))
			return false;
		if (VirtualProcessId == null) {
			if (other.VirtualProcessId != null)
				return false;
		} else if (!VirtualProcessId.equals(other.VirtualProcessId))
			return false;
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
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	public Set<UserInfo> getUserInfos() {
		return userInfos;
	}
	public void setUserInfos(Set<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	
}
