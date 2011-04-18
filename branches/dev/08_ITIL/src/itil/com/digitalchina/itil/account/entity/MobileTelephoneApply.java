package com.digitalchina.itil.account.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.Platform;
import com.digitalchina.info.framework.security.entity.SameMailDept;
import com.digitalchina.itil.config.extlist.entity.TelephoneCountSign;
import com.digitalchina.itil.config.extlist.entity.TelephoneType;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;

/**
 * 
 * @author gaowen 2009.7.22 个人正式帐号实体
 * 
 */
public class MobileTelephoneApply extends BaseObject {
	private Long id;
	private com.digitalchina.itil.account.entity.AccountType accountType;
	private com.digitalchina.info.framework.security.entity.UserInfo accountowner; // 使用人
	private java.lang.String accountState; // 账号状态
	private java.util.Date   createDate; // 生效时间
	private java.lang.String rightsDesc; // 权限说明
	private java.lang.String remarkDesc; // 备注说明
	private java.lang.String applyReason; //申请原因
    private java.lang.Integer payType ; //是否代缴 
	private java.lang.String telephone; //新手机号码
	private java.lang.String oldTelephone; //原手机号码
	private Platform platForm; // 平台
	private String startDate; // 代缴起始时间
	private String startMonth; //号码变更起始日期
	private Double allowance;//补贴标准
	private Double deptAllowance;//部门补贴标准
	private MobileTelephoneApply oldApply;
	private AccountApplyMainTable applyId;
	private TelephoneCountSign countSign;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public com.digitalchina.itil.account.entity.AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(
			com.digitalchina.itil.account.entity.AccountType accountType) {
		this.accountType = accountType;
	}
	public com.digitalchina.info.framework.security.entity.UserInfo getAccountowner() {
		return accountowner;
	}
	public void setAccountowner(
			com.digitalchina.info.framework.security.entity.UserInfo accountowner) {
		this.accountowner = accountowner;
	}
	public java.lang.String getAccountState() {
		return accountState;
	}
	public void setAccountState(java.lang.String accountState) {
		this.accountState = accountState;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	
	public java.lang.String getRightsDesc() {
		return rightsDesc;
	}
	public void setRightsDesc(java.lang.String rightsDesc) {
		this.rightsDesc = rightsDesc;
	}
	public java.lang.String getRemarkDesc() {
		return remarkDesc;
	}
	public void setRemarkDesc(java.lang.String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}
	public java.lang.String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(java.lang.String applyReason) {
		this.applyReason = applyReason;
	}
	public java.lang.Integer getPayType() {
		return payType;
	}
	public void setPayType(java.lang.Integer payType) {
		this.payType = payType;
	}
	public java.lang.String getTelephone() {
		return telephone;
	}
	public void setTelephone(java.lang.String telephone) {
		this.telephone = telephone;
	}
	public java.lang.String getOldTelephone() {
		return oldTelephone;
	}
	public void setOldTelephone(java.lang.String oldTelephone) {
		this.oldTelephone = oldTelephone;
	}
	public Platform getPlatForm() {
		return platForm;
	}
	public void setPlatForm(Platform platForm) {
		this.platForm = platForm;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Double getAllowance() {
		return allowance;
	}
	public void setAllowance(Double allowance) {
		this.allowance = allowance;
	}
	public Double getDeptAllowance() {
		return deptAllowance;
	}
	public void setDeptAllowance(Double deptAllowance) {
		this.deptAllowance = deptAllowance;
	}
	
	
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public MobileTelephoneApply getOldApply() {
		return oldApply;
	}
	public void setOldApply(MobileTelephoneApply oldApply) {
		this.oldApply = oldApply;
	}
	public AccountApplyMainTable getApplyId() {
		return applyId;
	}
	public void setApplyId(AccountApplyMainTable applyId) {
		this.applyId = applyId;
	}
	
	
	public TelephoneCountSign getCountSign() {
		return countSign;
	}
	public void setCountSign(TelephoneCountSign countSign) {
		this.countSign = countSign;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((telephone == null) ? 0 : telephone.hashCode());
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
		final MobileTelephoneApply other = (MobileTelephoneApply) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		return true;
	}
	 
	
	

}
