package com.xpsoft.oa.model.hrm;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.archive.ArchivesType;

public class RewardsPunishmentsType extends BaseModel {
	@Expose
	protected Long typeId;

	@Expose
	protected String typeName;
	
	@Expose
	protected Integer operation;// 1+/0-

	@Expose
	protected String typeDesc;

	protected Integer deleteFlag;//0未删除/1删除

	public RewardsPunishmentsType() {
		
	}

	public RewardsPunishmentsType(Long in_typeId) {
		setTypeId(in_typeId);
	}

	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Long aValue) {
		this.typeId = aValue;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}

	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String aValue) {
		this.typeDesc = aValue;
	}

	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public boolean equals(Object object) {
		if (!(object instanceof ArchivesType)) {
			return false;
		}
		RewardsPunishmentsType rhs = (RewardsPunishmentsType) object;
		return new EqualsBuilder()
			.append(this.typeId, rhs.typeId)
			.append(this.typeName, rhs.typeName)
			.append(this.typeDesc, rhs.typeDesc)
			.append(this.operation, rhs.operation)
			.append(this.deleteFlag, rhs.deleteFlag)
			.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
			.append(this.typeId)
			.append(this.typeName)
			.append(this.typeDesc)
			.append(this.operation)
			.append(this.deleteFlag)
			.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("typeId", this.typeId)
			.append("typeName", this.typeName)
			.append("typeDesc", this.typeDesc)
			.append("operation", this.operation)
			.append("deleteFlag", this.deleteFlag)
			.toString();
	}
}
