package com.digitalchina.itil.train.entity;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 调查问卷类型：课程类，事件与问题类，需求类型等等
 * @Class Name SurveyType
 * @Author sa
 * @Create In 2009-2-16
 */
public class SurveyType extends BaseObject {
	private Long id;
	private String name;
	private SystemMainTable systemMainTable;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((systemMainTable == null) ? 0 : systemMainTable.hashCode());
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
		final SurveyType other = (SurveyType) obj;
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
		if (systemMainTable == null) {
			if (other.systemMainTable != null)
				return false;
		} else if (!systemMainTable.equals(other.systemMainTable))
			return false;
		return true;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
}
