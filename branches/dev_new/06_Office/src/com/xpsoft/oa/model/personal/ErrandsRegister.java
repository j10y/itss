package com.xpsoft.oa.model.personal;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class ErrandsRegister extends BaseModel {
	protected Long dateId;
	protected Long approvalId;
	protected String descp;
	protected Date startTime;
	protected Date endTime;
	protected Short status;
	protected String approvalOption;
	protected String approvalName;
	protected Short flag;
	protected AppUser appUser;
	
	//请假类型
	protected Long leaveTypeId;
	protected String leaveTypeName;
	
	public static final Short STATUS_UNCHECKED = Short
			.valueOf((short) 0);

	public static final Short STATUS_APPROVAL = Short
			.valueOf((short) 1);

	public static final Short STATUS_UNAPPROVAL = Short
			.valueOf((short) 2);

	public static final Short FLAG_OVERTIME = Short.valueOf((short) 0);

	public static final Short FLAG_LEAVE = Short.valueOf((short) 1);

	public static final Short FLAG_OUT = Short.valueOf((short) 2);

	public ErrandsRegister() {
	}

	public ErrandsRegister(Long in_dateId) {
		setDateId(in_dateId);
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser in_appUser) {
		this.appUser = in_appUser;
	}

	public Long getDateId() {
		return this.dateId;
	}

	public void setDateId(Long aValue) {
		this.dateId = aValue;
	}

	public Long getUserId() {
		return getAppUser() == null ? null : getAppUser().getUserId();
	}

	public void setUserId(Long aValue) {
		if (aValue == null) {
			this.appUser = null;
		} else if (this.appUser == null) {
			this.appUser = new AppUser(aValue);
			this.appUser.setVersion(new Integer(0));
		} else {
			this.appUser.setUserId(aValue);
		}
	}

	public String getDescp() {
		return this.descp;
	}

	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date aValue) {
		this.startTime = aValue;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date aValue) {
		this.endTime = aValue;
	}

	public Long getApprovalId() {
		return this.approvalId;
	}

	public void setApprovalId(Long aValue) {
		this.approvalId = aValue;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short aValue) {
		this.status = aValue;
	}

	public String getApprovalOption() {
		return this.approvalOption;
	}

	public void setApprovalOption(String aValue) {
		this.approvalOption = aValue;
	}

	public String getApprovalName() {
		return this.approvalName;
	}

	public void setApprovalName(String aValue) {
		this.approvalName = aValue;
	}

	public Short getFlag() {
		return this.flag;
	}

	public void setFlag(Short aValue) {
		this.flag = aValue;
	}

	public Long getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(Long leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public boolean equals(Object object) {
		if (!(object instanceof ErrandsRegister)) {
			return false;
		}
		ErrandsRegister rhs = (ErrandsRegister) object;
		return new EqualsBuilder()
			.append(this.dateId, rhs.dateId)
			.append(this.approvalId, rhs.approvalId)
			.append(this.descp, rhs.descp)
			.append(this.startTime, rhs.startTime)
			.append(this.endTime, rhs.endTime)
			.append(this.status, rhs.status)
			.append(this.approvalOption, rhs.approvalOption)
			.append(this.approvalName, rhs.approvalName)
			.append(this.flag, rhs.flag)
			.append(this.leaveTypeId, this.leaveTypeId)
			.append(this.leaveTypeName, this.leaveTypeName)
			.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
			.append(this.dateId)
			.append(this.approvalId)
			.append(this.descp)
			.append(this.startTime)
			.append(this.endTime)
			.append(this.status)
			.append(this.approvalOption)
			.append(this.approvalName)
			.append(this.flag)
			.append(this.leaveTypeId)
			.append(this.leaveTypeName)
			.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("dateId", this.dateId)
			.append("userId", this.approvalId)
			.append("descp", this.descp)
			.append("startTime", this.startTime)
			.append("endTime", this.endTime)
			.append("status", this.status)
			.append("approvalOption", this.approvalOption)
			.append("approvalName", this.approvalName)
			.append("flag", this.flag)
			.append("leaveTypeId", this.leaveTypeId)
			.append("leaveTypeName", this.leaveTypeName)
			.toString();
	}

}
