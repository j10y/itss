package com.zsgj.info.bussutil.protal.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;

@SuppressWarnings("serial")
public class Portal extends BaseObject {
	private Long id;
	private String name;
	private Set portalColumns = new HashSet(0);
	private PortalContainer portalContainer;
	private Date createTime=new Date();
	
	//本标签页使用的格式模板,既分多少列显示.
	private PortalColumnTemplate portalColumnTemplate;

	public PortalColumnTemplate getPortalColumnTemplate() {
		return portalColumnTemplate;
	}

	public void setPortalColumnTemplate(PortalColumnTemplate portalColumnTemplate) {
		this.portalColumnTemplate = portalColumnTemplate;
	}

	public Set getPortalColumns() {
		return portalColumns;
	}

	public void setPortalColumns(Set portalColumns) {
		this.portalColumns = portalColumns;
	}

	public PortalContainer getPortalContainer() {
		return portalContainer;
	}

	public void setPortalContainer(PortalContainer portalContainer) {
		this.portalContainer = portalContainer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String json(){
		StringBuffer sb=new StringBuffer("{");
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

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((portalColumnTemplate == null) ? 0 : portalColumnTemplate.hashCode());
		result = PRIME * result + ((portalContainer == null) ? 0 : portalContainer.hashCode());
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
		final Portal other = (Portal) obj;
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
		if (portalColumnTemplate == null) {
			if (other.portalColumnTemplate != null)
				return false;
		} else if (!portalColumnTemplate.equals(other.portalColumnTemplate))
			return false;
		if (portalContainer == null) {
			if (other.portalContainer != null)
				return false;
		} else if (!portalContainer.equals(other.portalContainer))
			return false;
		return true;
	}
}
