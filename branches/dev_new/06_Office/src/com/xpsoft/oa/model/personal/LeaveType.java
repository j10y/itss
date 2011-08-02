package com.xpsoft.oa.model.personal;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.Department;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class LeaveType extends BaseModel {

	@Expose
	protected Long typeId;

	@Expose
	protected String typeName;
	
	@Expose
	protected Long processDefId;
	
	@Expose
	protected String processDefName;

	public LeaveType() {
	}

	public LeaveType(Long in_typeId) {
		/* 43 */setTypeId(in_typeId);
	}



	public Long getTypeId() {
		/* 78 */return this.typeId;
	}

	public void setTypeId(Long aValue) {
		/* 85 */this.typeId = aValue;
	}

	public String getTypeName() {
		/* 93 */return this.typeName;
	}

	public void setTypeName(String aValue) {
		/* 101 */this.typeName = aValue;
	}

	public Long getProcessDefId() {
		return this.processDefId;
	}

	public void setProcessDefId(Long aValue) {
		this.processDefId = aValue;
	}

	public String getProcessDefName() {
		return processDefName;
	}

	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}
	
	public boolean equals(Object object) {
		/* 129 */if (!(object instanceof LeaveType)) {
			/* 130 */return false;
		}
		/* 132 */LeaveType rhs = (LeaveType) object;
		/* 133 */return new EqualsBuilder()
		/* 134 */.append(this.typeId, rhs.typeId)
		/* 135 */.append(this.typeName, rhs.typeName)		
		/* 113 */.append(this.processDefId, rhs.processDefId)
				 .append(this.processDefName, rhs.processDefName)
		/* 136 */.isEquals();
	}

	public int hashCode() {
		/* 143 */return new HashCodeBuilder(-82280557, -700257973)
		/* 144 */.append(this.typeId)
		/* 145 */.append(this.typeName)
				 .append(this.processDefId)
				 .append(this.processDefName)
		/* 146 */.toHashCode();
	}

	public String toString() {
		/* 153 */return new ToStringBuilder(this)
		/* 154 */.append("typeId", this.typeId)
		/* 155 */.append("typeName", this.typeName)
		/* 135 */.append("processDefId", this.processDefId)
		/* 135 */.append("processDefName", this.processDefName)
		/* 156 */.toString();
	}
}