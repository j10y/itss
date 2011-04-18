package com.digitalchina.info.appframework.metadata.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 系统可见字段更新版本实体，配合用户可见字段进行可见字段信息更新
 * @Class Name SystemTableSettingVersion
 * @Author sa
 * @Create In 2009-1-22
 */
public class SystemTableSettingVersion extends BaseObject {
	private Long id;
	private SystemMainTable systemMainTable;
	private Integer settingType;
	private Integer version;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Integer getSettingType() {
		return settingType;
	}
	public void setSettingType(Integer settingType) {
		this.settingType = settingType;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((settingType == null) ? 0 : settingType.hashCode());
		result = PRIME * result + ((systemMainTable == null) ? 0 : systemMainTable.hashCode());
		result = PRIME * result + ((version == null) ? 0 : version.hashCode());
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
		final SystemTableSettingVersion other = (SystemTableSettingVersion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (settingType == null) {
			if (other.settingType != null)
				return false;
		} else if (!settingType.equals(other.settingType))
			return false;
		if (systemMainTable == null) {
			if (other.systemMainTable != null)
				return false;
		} else if (!systemMainTable.equals(other.systemMainTable))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
}
