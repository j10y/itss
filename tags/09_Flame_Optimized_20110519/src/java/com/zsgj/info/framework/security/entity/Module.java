package com.zsgj.info.framework.security.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 模块实体
 * @Class Name Module
 * @Author peixf
 * @Create In 2008-3-5
 */
public class Module extends BaseObject{
	private static final Long serialVersionUID = -4990110851439538814L;

	private Long id;
	
	private String name;
	
	private Module parentModule;
	
	private String serviceKeyName; //服务关键字
	
	private String url;
	
	private String displayPicPath;
	
	private Set resources = new HashSet(0);
	
	private Set authorizations = new HashSet();
	
	private Set childModules = new HashSet(0);
	
	private String descn; //描述
	
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
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

	public Module getParentModule() {
		return parentModule;
	}

	public void setParentModule(Module parentModule) {
		this.parentModule = parentModule;
	}

	public Set getResources() {
		return resources;
	}

	public void setResources(Set resources) {
		this.resources = resources;
	}

	public String getServiceKeyName() {
		return serviceKeyName;
	}

	public void setServiceKeyName(String serviceKeyName) {
		this.serviceKeyName = serviceKeyName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((parentModule == null) ? 0 : parentModule.hashCode());
		result = PRIME * result + ((serviceKeyName == null) ? 0 : serviceKeyName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Module other = (Module) obj;
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
		if (parentModule == null) {
			if (other.parentModule != null)
				return false;
		} else if (!parentModule.equals(other.parentModule))
			return false;
		if (serviceKeyName == null) {
			if (other.serviceKeyName != null)
				return false;
		} else if (!serviceKeyName.equals(other.serviceKeyName))
			return false;
		return true;
	}

	public Set getChildModules() {
		return childModules;
	}

	public void setChildModules(Set childModules) {
		this.childModules = childModules;
	}

	public Set getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set authorizations) {
		this.authorizations = authorizations;
	}

	/**
	 * @Return the String displayPicPath
	 */
	public String getDisplayPicPath() {
		return displayPicPath;
	}

	/**
	 * @Param String displayPicPath to set
	 */
	public void setDisplayPicPath(String displayPicPath) {
		this.displayPicPath = displayPicPath;
	}

	
}
