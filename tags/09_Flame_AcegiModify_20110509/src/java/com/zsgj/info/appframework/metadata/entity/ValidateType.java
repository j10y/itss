package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class ValidateType extends BaseObject {
	
	private static final Long serialVersionUID = -6931856090748197866L;
	private Long id;
	private String validateTypeName;
	private String validateTypeCnName;
	private String regular;
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((validateTypeName == null) ? 0 : validateTypeName.hashCode());
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
		final ValidateType other = (ValidateType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (validateTypeName == null) {
			if (other.validateTypeName != null)
				return false;
		} else if (!validateTypeName.equals(other.validateTypeName))
			return false;
		return true;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegular() {
		return regular;
	}
	public void setRegular(String regular) {
		this.regular = regular;
	}
	public String getValidateTypeCnName() {
		return validateTypeCnName;
	}
	public void setValidateTypeCnName(String validateTypeCnName) {
		this.validateTypeCnName = validateTypeCnName;
	}
	public String getValidateTypeName() {
		return validateTypeName;
	}
	public void setValidateTypeName(String validateTypeName) {
		this.validateTypeName = validateTypeName;
	}

}
