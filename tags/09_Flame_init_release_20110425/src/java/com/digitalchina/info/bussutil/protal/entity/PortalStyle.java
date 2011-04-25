package com.digitalchina.info.bussutil.protal.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

@SuppressWarnings("serial")
public class PortalStyle extends BaseObject {
	private Long id;

	private String name;

	private Set portalContainers = new HashSet(0);

	private Date createTime = new Date();

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set getPortalContainers() {
		return portalContainers;
	}

	public void setPortalContainers(Set portalContainers) {
		this.portalContainers = portalContainers;
	}

	public String json() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"id\":");
		sb.append("\"");
		sb.append(this.getId());
		sb.append("\"");
		sb.append(",");
		sb.append("\"name\":");
		sb.append("\"");
		sb.append(this.getName());
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PortalStyle other = (PortalStyle) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
