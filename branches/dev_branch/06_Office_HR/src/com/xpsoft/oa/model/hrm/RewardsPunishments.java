package com.xpsoft.oa.model.hrm;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Dictionary;

/**
 * 人员奖惩
 * @author wchao
 *
 */
public class RewardsPunishments extends BaseModel {
	
	protected Long rpId;
	protected AppUser appUser;
	protected BigDecimal amount;
	protected String remark;
	protected EmpProfile empProfile;
	//奖惩类型
	protected String rpTypeStr;
	protected Dictionary rpType;
	protected Date createDate;	
	protected AppUser createPerson;

	public Long getRpId() {
		return rpId;
	}
	public void setRpId(Long rpId) {
		this.rpId = rpId;
	}
	public AppUser getAppUser() {
		return appUser;
	}
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public EmpProfile getEmpProfile() {
		return empProfile;
	}
	public void setEmpProfile(EmpProfile empProfile) {
		this.empProfile = empProfile;
	}
	public String getRpTypeStr() {
		return rpTypeStr;
	}
	public void setRpTypeStr(String rpTypeStr) {
		this.rpTypeStr = rpTypeStr;
	}
	public Dictionary getRpType() {
		return rpType;
	}
	public void setRpType(Dictionary rpType) {
		this.rpType = rpType;
	}	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public AppUser getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(AppUser createPerson) {
		this.createPerson = createPerson;
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
			.append(this.amount)
			.append(this.appUser)
			.append(this.empProfile)
			.append(this.rpId)
			.append(this.rpType)	
			.append(this.remark)	
			.append(this.rpTypeStr)
			.append(this.createDate)
			.append(this.createPerson)
			.toHashCode();
	}
	@Override
	public boolean equals(Object obj) {
		RewardsPunishments rhs = (RewardsPunishments)obj;
		return new EqualsBuilder()
			.append(this.amount, rhs.amount)
			.append(this.appUser, rhs.appUser)
			.append(this.empProfile, rhs.empProfile)
			.append(this.rpId, rhs.rpId)
			.append(this.rpType, rhs.rpType)
			.append(this.remark, rhs.remark)
			.append(this.createDate, rhs.createDate)
			.append(this.createPerson, rhs.createPerson)
			.append(this.rpTypeStr, rhs.rpTypeStr)
			.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("amount", this.amount)
			.append("appUser", this.appUser.getFullname())
			.append("empProfile", this.empProfile)
			.append("rpId", this.rpId)
			.append("rpType", this.rpType)
			.append("remark", this.remark)
			.append("rpTypeStr", this.rpTypeStr)
			.append("createDate", this.createDate)
			.append("createPerson", this.createPerson)
			.toString();
	}
	
	
	
}
