package com.xpsoft.oa.model.archive;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.Department;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchRecUser extends BaseModel {
	protected Long archRecId;
	protected Long userId;
	protected String fullname;
	protected Long depId;
	protected String depName;
	protected Department department;
	protected Long leaderUserId;
	protected String leaderFullname;

	public Long getLeaderUserId() {
		return leaderUserId;
	}

	public void setLeaderUserId(Long leaderUserId) {
		this.leaderUserId = leaderUserId;
	}

	public String getLeaderFullname() {
		return leaderFullname;
	}

	public void setLeaderFullname(String leaderFullname) {
		this.leaderFullname = leaderFullname;
	}

	public Department getDepartment() {
		/* 32 */return this.department;
	}

	public void setDepartment(Department department) {
		/* 36 */this.department = department;
	}

	public ArchRecUser() {
	}

	public ArchRecUser(Long in_archRecId) {
		/* 52 */setArchRecId(in_archRecId);
	}

	public Long getArchRecId() {
		/* 62 */return this.archRecId;
	}

	public void setArchRecId(Long aValue) {
		/* 69 */this.archRecId = aValue;
	}

	public Long getUserId() {
		/* 77 */return this.userId;
	}

	public void setUserId(Long aValue) {
		/* 85 */this.userId = aValue;
	}

	public String getFullname() {
		/* 93 */return this.fullname;
	}

	public void setFullname(String aValue) {
		/* 101 */this.fullname = aValue;
	}

	public Long getDepId() {
		/* 109 */return this.depId;
	}

	public void setDepId(Long aValue) {
		/* 117 */this.depId = aValue;
	}

	public String getDepName() {
		/* 125 */return this.depName;
	}

	public void setDepName(String aValue) {
		/* 133 */this.depName = aValue;
	}

	public boolean equals(Object object) {
		/* 140 */if (!(object instanceof ArchRecUser)) {
			/* 141 */return false;
		}
		/* 143 */ArchRecUser rhs = (ArchRecUser) object;
		/* 144 */return new EqualsBuilder()
		/* 145 */.append(this.archRecId, rhs.archRecId)
		/* 146 */.append(this.userId, rhs.userId)
		/* 147 */.append(this.fullname, rhs.fullname)
		/* 149 */.append(this.depName, rhs.depName)
		/* 150 */.isEquals();
	}

	public int hashCode() {
		/* 157 */return new HashCodeBuilder(-82280557, -700257973)
		/* 158 */.append(this.archRecId)
		/* 159 */.append(this.userId)
		/* 160 */.append(this.fullname)
		/* 162 */.append(this.depName)
		/* 163 */.toHashCode();
	}

	public String toString() {
		/* 170 */return new ToStringBuilder(this)
		/* 171 */.append("archRecId", this.archRecId)
		/* 172 */.append("userId", this.userId)
		/* 173 */.append("fullname", this.fullname)
		/* 175 */.append("depName", this.depName)
		/* 176 */.toString();
	}
}
