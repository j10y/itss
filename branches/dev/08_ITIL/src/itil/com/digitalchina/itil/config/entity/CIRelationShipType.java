package com.digitalchina.itil.config.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 配置项关系类型，基本分为：包含、依赖、使用三种。
 * @Class Name ConfigItemRelationType
 * @Author sa
 * @Create In 2008-10-20
 */
public class CIRelationShipType extends BaseObject{
	private Long id;
	private String name;
	private Integer attachFlag;
	
	public Integer getAttachFlag() {
		return attachFlag;
	}
	public void setAttachFlag(Integer attachFlag) {
		this.attachFlag = attachFlag;
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
		final CIRelationShipType other = (CIRelationShipType) obj;
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
