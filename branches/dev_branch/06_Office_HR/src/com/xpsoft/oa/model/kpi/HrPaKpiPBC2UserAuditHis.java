package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

/**
 * 个人pbc审批历史
 * @author wchao
 *
 */
public class HrPaKpiPBC2UserAuditHis extends BaseModel {
	protected Long id;
	//protected HrPaKpiPBC2User hrPaKpiPBC2User;//关联pbcuser
	protected Long hrPaKpiPBC2UserId;//关联pbcuser或者pbcUserCmp的id，同一个考核结果这两个ID一样
	protected AppUser checkUser;//审批人
	protected Date checkTime;//审批时间
	protected Short checkStatus;//审批结果0拒绝1同意
	protected String checkRemark;//审批意见
	
	public HrPaKpiPBC2UserAuditHis() {}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getHrPaKpiPBC2UserId() {
		return hrPaKpiPBC2UserId;
	}

	public void setHrPaKpiPBC2UserId(Long hrPaKpiPBC2UserId) {
		this.hrPaKpiPBC2UserId = hrPaKpiPBC2UserId;
	}

	public AppUser getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(AppUser checkUser) {
		this.checkUser = checkUser;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Short getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	
}
