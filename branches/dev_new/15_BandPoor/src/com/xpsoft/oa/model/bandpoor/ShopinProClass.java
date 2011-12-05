package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

public class ShopinProClass extends BaseModel{
	private Long id;
	private String proClassName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProClassName() {
		return proClassName;
	}
	public void setProClassName(String proClassName) {
		this.proClassName = proClassName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((proClassName == null) ? 0 : proClassName.hashCode());
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
		final ShopinProClass other = (ShopinProClass) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (proClassName == null) {
			if (other.proClassName != null)
				return false;
		} else if (!proClassName.equals(other.proClassName))
			return false;
		return true;
	}
	
	
}
