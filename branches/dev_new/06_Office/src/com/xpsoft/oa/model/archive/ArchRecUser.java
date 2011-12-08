package com.xpsoft.oa.model.archive;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.Department;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchRecUser extends BaseModel {
	protected Long archRecId;
	protected Long userId;//部门收文负责人
	protected String fullname;//部门收文负责人名字
	protected Long depId;
	protected String depName;
	protected Department department;
	protected Long leaderUserId;//分管领导ID
	protected String leaderFullname;//分管领导名字
	protected Long deptUserId;//部门负责人
	protected String deptFullname;//部门负责人名字
	
	//因为分局的发文增加的逻辑
	protected Long officeHeaderUserId;//办公室主任
	protected String officeHeaderFullname;//编公式主任
	protected Long numberUserId;//编号录入人员
	protected String numberFullname;//编号录入人员名字
	protected Long stampUserId;//盖章人员
	protected String stampFullname;//盖章人员名字

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

	public Long getDeptUserId() {
		return deptUserId;
	}

	public void setDeptUserId(Long deptUserId) {
		this.deptUserId = deptUserId;
	}

	/**
	 * @return the deptFullname
	 */
	public String getDeptFullname() {
		return deptFullname;
	}

	/**
	 * @param deptFullname the deptFullname to set
	 */
	public void setDeptFullname(String deptFullname) {
		this.deptFullname = deptFullname;
	}
	
	public Long getOfficeHeaderUserId() {
		return officeHeaderUserId;
	}

	public void setOfficeHeaderUserId(Long officeHeaderUserId) {
		this.officeHeaderUserId = officeHeaderUserId;
	}

	public String getOfficeHeaderFullname() {
		return officeHeaderFullname;
	}

	public void setOfficeHeaderFullname(String officeHeaderFullname) {
		this.officeHeaderFullname = officeHeaderFullname;
	}

	public Long getNumberUserId() {
		return numberUserId;
	}

	public void setNumberUserId(Long numberUserId) {
		this.numberUserId = numberUserId;
	}

	public String getNumberFullname() {
		return numberFullname;
	}

	public void setNumberFullname(String numberFullname) {
		this.numberFullname = numberFullname;
	}

	public Long getStampUserId() {
		return stampUserId;
	}

	public void setStampUserId(Long stampUserId) {
		this.stampUserId = stampUserId;
	}

	public String getStampFullname() {
		return stampFullname;
	}

	public void setStampFullname(String stampFullname) {
		this.stampFullname = stampFullname;
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
		/* 147 */.append(this.leaderUserId, rhs.leaderUserId)
		/* 149 */.append(this.leaderFullname, rhs.leaderFullname)
		/* 149 */.append(this.numberUserId, rhs.numberUserId)
		/* 149 */.append(this.numberFullname, rhs.numberFullname)
		/* 147 */.append(this.deptUserId, rhs.deptUserId)
		/* 149 */.append(this.deptFullname, rhs.deptFullname)
		/* 149 */.append(this.officeHeaderUserId, rhs.officeHeaderUserId)
		/* 149 */.append(this.officeHeaderFullname, rhs.officeHeaderFullname)
		/* 149 */.append(this.stampUserId, rhs.stampUserId)
		/* 149 */.append(this.stampFullname, rhs.stampFullname)
		/* 150 */.isEquals();
	}

	public int hashCode() {
		/* 157 */return new HashCodeBuilder(-82280557, -700257973)
		/* 158 */.append(this.archRecId)
		/* 159 */.append(this.userId)
		/* 160 */.append(this.fullname)
		/* 162 */.append(this.depName)
		/* 162 */.append(this.leaderUserId)
		/* 162 */.append(this.leaderFullname)
		/* 162 */.append(this.deptUserId)
		/* 162 */.append(this.deptFullname)
		/* 162 */.append(this.officeHeaderUserId)
		/* 162 */.append(this.officeHeaderFullname)
		/* 162 */.append(this.numberUserId)
		/* 162 */.append(this.numberFullname)
		/* 162 */.append(this.stampUserId)
		/* 162 */.append(this.stampFullname)
		/* 163 */.toHashCode();
	}

	public String toString() {
		/* 170 */return new ToStringBuilder(this)
		/* 171 */.append("archRecId", this.archRecId)
		/* 172 */.append("userId", this.userId)
		/* 173 */.append("fullname", this.fullname)
		/* 175 */.append("depName", this.depName)
		/* 175 */.append("leaderUserId", this.leaderUserId)
		/* 175 */.append("leaderFullname", this.leaderFullname)
		/* 175 */.append("deptUserId", this.deptUserId)
		/* 175 */.append("deptFullname", this.deptFullname)
		/* 175 */.append("officeHeaderUserId", this.officeHeaderUserId)
		/* 175 */.append("officeHeaderFullname", this.officeHeaderFullname)
		/* 175 */.append("numberUserId", this.numberUserId)
		/* 175 */.append("numberFullname", this.numberFullname)
		/* 175 */.append("stampUserId", this.stampUserId)
		/* 175 */.append("stampFullname", this.stampFullname)
		/* 176 */.toString();
	}
}
