package com.digitalchina.itil.service.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 服务目录节点类型，下拉列表显示是常规服务还是个性化需求
 * @Class Name ServiceCataNodeType
 * @Author sa
 * @Create In 2009-2-26
 */
@SuppressWarnings("serial")
public class SCIRelationShipNodeType extends BaseObject {
	public static String TYPE_GENERAL="general";
	public static String TYPE_SPECIAL="specical";
	
	private Long id;
	private String name; //显示名称
	private String keyword; //关键字，程序使用
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
		final SCIRelationShipNodeType other = (SCIRelationShipNodeType) obj;
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
