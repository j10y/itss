package com.digitalchina.itil.actor.entity;

import com.digitalchina.info.framework.dao.DiscObject;

/**
 * 客户分类：集团IT，集团内其他部门/公司，集团外
 * @Class Name CustomerType
 * @Author sa
 * @Create In 2008-10-21
 */
@SuppressWarnings("serial")
public class CustomerType extends DiscObject{
	public static final String CUSTOMER_IN="in";
	public static final String CUSTOMER_OUT="out";
	private Long id;
	private String name;//内部客户，外部客户
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
		final CustomerType other = (CustomerType) obj;
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
	
	
}
