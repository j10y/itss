package com.zsgj.itil.account.entity;

import com.zsgj.info.framework.dao.BaseObject;
/**
 * 
 * @author gaowen 2009.7.22
 * 帐号类型实体
 *
 */
public class AccountType extends BaseObject{
	 private Long id;
	 private String accountType;
	 private String name;
	 private com.zsgj.info.framework.security.entity.Role role;
	 public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public com.zsgj.info.framework.security.entity.Role getRole() {
		return role;
	}
	public void setRole(com.zsgj.info.framework.security.entity.Role role) {
		this.role = role;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final AccountType other = (AccountType) obj;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
