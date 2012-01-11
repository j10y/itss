package net.shopin.ldap.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * System
 * @author wchao
 *
 */
public class System implements Serializable  {

	private String cn;//可能没用
	private String dn; 
	private String displayName; //系统名称 必填
	private String description; //非必填
	private String o;
	private Integer status;
	private String[] systemOccupant;//系统内的角色
	public static final Integer SATAL_NORMAL = Integer.valueOf(0);
	public static final Integer SATAL_NOT_NORMAL = Integer.valueOf(1);
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
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
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o = o;
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
		result = prime * result + ((dn == null) ? 0 : dn.hashCode());
		result = prime * result + ((o == null) ? 0 : o.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + Arrays.hashCode(systemOccupant);
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
		System other = (System) obj;
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
		if (dn == null) {
			if (other.dn != null)
				return false;
		} else if (!dn.equals(other.dn))
			return false;
		if (o == null) {
			if (other.o != null)
				return false;
		} else if (!o.equals(other.o))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (!Arrays.equals(systemOccupant, other.systemOccupant))
			return false;
		return true;
	}
	
	public void setSystemOccupant(String[] systemOccupant) {
		this.systemOccupant = systemOccupant;
	}
	
	public String [] getSystemOccupant() {
		return this.systemOccupant;
	}
	
}
