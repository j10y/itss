package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class ExtOptionData extends BaseObject{
	private static final Long serialVersionUID = -1021390386911002253L;
	private Long id;
	private Integer extColumnId;//扩展字段id编号
	private String extOptionValue;//存放下拉列表或多选的数据
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getExtColumnId() {
		return extColumnId;
	}
	public void setExtColumnId(Integer extColumnId) {
		this.extColumnId = extColumnId;
	}
	public String getExtOptionValue() {
		return extOptionValue;
	}
	public void setExtOptionValue(String extOptionValue) {
		this.extOptionValue = extOptionValue;
	}
	public static Long getSerialVersionUID() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((extColumnId == null) ? 0 : extColumnId.hashCode());
		result = prime * result
				+ ((extOptionValue == null) ? 0 : extOptionValue.hashCode());
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
		final ExtOptionData other = (ExtOptionData) obj;
		if (extColumnId == null) {
			if (other.extColumnId != null)
				return false;
		} else if (!extColumnId.equals(other.extColumnId))
			return false;
		if (extOptionValue == null) {
			if (other.extOptionValue != null)
				return false;
		} else if (!extOptionValue.equals(other.extOptionValue))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
