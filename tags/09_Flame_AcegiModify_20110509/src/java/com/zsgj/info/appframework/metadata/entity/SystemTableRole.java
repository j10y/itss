	package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Role;

/**
 * 系统角色可见字段模板
 * @Class Name SystemTableRoleSetting
 * @Author sa
 * @Create In 2008-9-4
 */
public class SystemTableRole extends BaseObject {
	private static final long serialVersionUID = -684308628284321101L;
	
	public static final Integer LIST = new Integer(1);
	public static final Integer INPUT = new Integer(2);
	public static final Integer EXPORT = new Integer(5);
	public static final Integer MUL_QUERY = new Integer(4); //复合查询字段的可见字段类型
	
	private Long id;
	private String roleSettingName;
	private String roleSettingCnName;
	private SystemMainTable systemMainTable;
	private Integer settingType;
	private Role role;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getRoleSettingCnName() {
		return roleSettingCnName;
	}
	public void setRoleSettingCnName(String roleSettingCnName) {
		this.roleSettingCnName = roleSettingCnName;
	}
	public String getRoleSettingName() {
		return roleSettingName;
	}
	public void setRoleSettingName(String roleSettingName) {
		this.roleSettingName = roleSettingName;
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
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((role == null) ? 0 : role.hashCode());
		result = PRIME * result + ((systemMainTable == null) ? 0 : systemMainTable.hashCode());
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
		final SystemTableRole other = (SystemTableRole) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (systemMainTable == null) {
			if (other.systemMainTable != null)
				return false;
		} else if (!systemMainTable.equals(other.systemMainTable))
			return false;
		return true;
	}
	public Integer getSettingType() {
		return settingType;
	}
	public void setSettingType(Integer settingType) {
		this.settingType = settingType;
	}

}
