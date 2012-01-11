package net.shopin.ldap.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 角色 对应于LDAP中的shopin-orgnizationalRole
 * 包含roleOccupant
 * @author wchao
 *
 */
public class Role implements Serializable {
	private String cn;//可能没用
	private String dn; //ou=groups,cn=* 英文
	private String displayName; //用户组名称 必填
	private String description; //非必填
	private String [] roleOccupant; //至少一个
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
	
	public String[] getRoleOccupant() {
		return roleOccupant;
	}
	public void setRoleOccupant(String[] roleOccupant) {
		this.roleOccupant = roleOccupant;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
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
		result = prime * result + ((roleOccupant == null) ? 0 : roleOccupant.hashCode());
		result = prime * result + ((dn == null) ? 0 : dn.hashCode());
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
		Role other = (Role) obj;
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
		if (roleOccupant == null) {
			if (other.roleOccupant != null)
				return false;
		} else if (!roleOccupant.equals(other.roleOccupant))
			return false;
		if (dn == null) {
			if (other.dn != null)
				return false;
		} else if (!dn.equals(other.dn))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
}
