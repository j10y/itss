package com.zsgj.info.framework.security.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 权限实体
 * @Class Name Right
 * @Author peixf
 * @Create In 2008-3-11
 */
public class Right extends BaseObject{
	private static final Long serialVersionUID = -7059543983604108297L;
	
	private Long id;
	private String name;
	private String keyName;
	private String descn;
	private Set authorizations = new HashSet(0);
	
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
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((descn == null) ? 0 : descn.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((keyName == null) ? 0 : keyName.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Right other = (Right) obj;
		if (descn == null) {
			if (other.descn != null)
				return false;
		} else if (!descn.equals(other.descn))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keyName == null) {
			if (other.keyName != null)
				return false;
		} else if (!keyName.equals(other.keyName))
			return false;
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set getAuthorizations() {
		return authorizations;
	}
	public void setAuthorizations(Set authorizations) {
		this.authorizations = authorizations;
	}

	
}
