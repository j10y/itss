package com.digitalchina.info.framework.security.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 用户与角色直接的关联实体
 * @Class Name UserRole
 * @Author peixf
 * @Create In 2008-3-12
 * @deprecated
 */
public class RoleAuthoriz extends BaseObject{
	private static final long serialVersionUID = -6622745246804662400L;
	
	private Long id;
	private Role role;
	private Authorization authorization;
	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((authorization == null) ? 0 : authorization.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RoleAuthoriz other = (RoleAuthoriz) obj;
		if (authorization == null) {
			if (other.authorization != null)
				return false;
		} else if (!authorization.equals(other.authorization))
			return false;
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
		return true;
	}
	
	
	
}
