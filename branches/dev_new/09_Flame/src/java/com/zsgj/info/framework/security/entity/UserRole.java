package com.zsgj.info.framework.security.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 用户与角色直接的关联实体，注意此实体为多余实体，只在hibernate查询无法完成部分功能
 * 时使用。
 * @Class Name UserRole
 * @Author peixf
 * @Create In 2008-3-12
 * @deprecated
 */
public class UserRole extends BaseObject{
	private static final long serialVersionUID = -6622745246804662400L;
	
	private Long id;
	private UserInfo userInfo;
	private Role role;
	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((role == null) ? 0 : role.hashCode());
		result = PRIME * result + ((userInfo == null) ? 0 : userInfo.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserRole other = (UserRole) obj;
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
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
	}
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
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}
