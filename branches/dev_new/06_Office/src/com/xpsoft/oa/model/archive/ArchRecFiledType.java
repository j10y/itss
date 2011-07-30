package com.xpsoft.oa.model.archive;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.Department;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 收文归档类型
 * @author awen
 *
 */
public class ArchRecFiledType extends BaseModel {

	@Expose
	protected Long recFiledTypeId;

	@Expose
	protected String typeName;

	public ArchRecFiledType() {
	}

	public ArchRecFiledType(Long in_typeId) {
		/* 43 */setRecFiledTypeId(in_typeId);
	}

	/**
	 * @return the recFiledTypeId
	 */
	public Long getRecFiledTypeId() {
		return recFiledTypeId;
	}

	/**
	 * @param recFiledTypeId the recFiledTypeId to set
	 */
	public void setRecFiledTypeId(Long recFiledTypeId) {
		this.recFiledTypeId = recFiledTypeId;
	}

	public String getTypeName() {
		/* 93 */return this.typeName;
	}

	public void setTypeName(String aValue) {
		/* 101 */this.typeName = aValue;
	}

	
	
	public boolean equals(Object object) {
		/* 129 */if (!(object instanceof ArchRecFiledType)) {
			/* 130 */return false;
		}
		/* 132 */ArchRecFiledType rhs = (ArchRecFiledType) object;
		/* 133 */return new EqualsBuilder()
		/* 134 */.append(this.recFiledTypeId, rhs.recFiledTypeId)
		/* 135 */.append(this.typeName, rhs.typeName)
		/* 136 */.isEquals();
	}

	public int hashCode() {
		/* 143 */return new HashCodeBuilder(-82280557, -700257973)
		/* 144 */.append(this.recFiledTypeId)
		/* 145 */.append(this.typeName)
		/* 146 */.toHashCode();
	}

	public String toString() {
		/* 153 */return new ToStringBuilder(this)
		/* 154 */.append("recFiledTypeId", this.recFiledTypeId)
		/* 155 */.append("typeName", this.typeName)
		/* 156 */.toString();
	}
}