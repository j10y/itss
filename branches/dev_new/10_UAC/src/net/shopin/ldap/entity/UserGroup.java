package net.shopin.ldap.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户组 对应于LDAP中的groupOfNames
 * 包含memember
 * @author wchao
 *
 */
public class UserGroup implements Serializable {
	private String cn;//可能没用
	private String rdn; //ou=groups,cn=* 英文
	private String displayName; //用户组名称 必填
	private String description; //非必填
	private String [] members; //至少一个
	private Integer status;
	
	public static final Integer SATAL_NORMAL = Integer.valueOf(0);
	public static final Integer SATAL_NOT_NORMAL = Integer.valueOf(1);
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getMembers() {
		return members;
	}
	public void setMembers(String[] members) {
		this.members = members;
	}
	public String getRdn() {
		return rdn;
	}
	public void setRdn(String rdn) {
		this.rdn = rdn;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cn == null) ? 0 : cn.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result + ((rdn == null) ? 0 : rdn.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		UserGroup other = (UserGroup) obj;
		if (cn == null) {
			if (other.cn != null)
				return false;
		} else if (!cn.equals(other.cn))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (rdn == null) {
			if (other.rdn != null)
				return false;
		} else if (!rdn.equals(other.rdn))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
}
