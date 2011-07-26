package com.xpsoft.oa.model.archive;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.Department;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchRecType extends BaseModel {

	@Expose
	protected Long recTypeId;

	@Expose
	protected String typeName;

	@Expose
	protected Department department;
	/* 27 */protected Set archivesDeps = new HashSet();

	/* 29 */protected Set archives = new HashSet();
	
	@Expose
	protected Long processDefId;
	
	@Expose
	protected String processDefName;

	public ArchRecType() {
	}

	public ArchRecType(Long in_typeId) {
		/* 43 */setRecTypeId(in_typeId);
	}

	public Set getArchives() {
		/* 49 */return this.archives;
	}

	public void setArchives(Set archives) {
		/* 53 */this.archives = archives;
	}

	public Department getDepartment() {
		/* 57 */return this.department;
	}

	public void setDepartment(Department in_department) {
		/* 61 */this.department = in_department;
	}

	public Set getArchivesDeps() {
		/* 65 */return this.archivesDeps;
	}

	public void setArchivesDeps(Set in_archivesDeps) {
		/* 69 */this.archivesDeps = in_archivesDeps;
	}

	public Long getRecTypeId() {
		/* 78 */return this.recTypeId;
	}

	public void setRecTypeId(Long aValue) {
		/* 85 */this.recTypeId = aValue;
	}

	public String getTypeName() {
		/* 93 */return this.typeName;
	}

	public void setTypeName(String aValue) {
		/* 101 */this.typeName = aValue;
	}

	public Long getDepId() {
		/* 108 */return getDepartment() == null ? null : getDepartment()
				.getDepId();
	}

	public void setDepId(Long aValue) {
		/* 115 */if (aValue == null) {
			/* 116 */this.department = null;
			/* 117 */} else if (this.department == null) {
			/* 118 */this.department = new Department(aValue);
			/* 119 */this.department.setVersion(new Integer(0));
		} else {
			/* 121 */this.department.setDepId(aValue);
		}
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
		/* 129 */if (!(object instanceof ArchRecType)) {
			/* 130 */return false;
		}
		/* 132 */ArchRecType rhs = (ArchRecType) object;
		/* 133 */return new EqualsBuilder()
		/* 134 */.append(this.recTypeId, rhs.recTypeId)
		/* 135 */.append(this.typeName, rhs.typeName)		
		/* 113 */.append(this.processDefId, rhs.processDefId)
				 .append(this.processDefName, rhs.processDefName)
		/* 136 */.isEquals();
	}

	public int hashCode() {
		/* 143 */return new HashCodeBuilder(-82280557, -700257973)
		/* 144 */.append(this.recTypeId)
		/* 145 */.append(this.typeName)
				 .append(this.processDefId)
				 .append(this.processDefName)
		/* 146 */.toHashCode();
	}

	public String toString() {
		/* 153 */return new ToStringBuilder(this)
		/* 154 */.append("typeId", this.recTypeId)
		/* 155 */.append("typeName", this.typeName)
		/* 135 */.append("processDefId", this.processDefId)
		/* 135 */.append("processDefName", this.processDefName)
		/* 156 */.toString();
	}
}