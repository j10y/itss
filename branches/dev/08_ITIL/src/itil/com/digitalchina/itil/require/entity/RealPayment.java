package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.config.extlist.entity.SRExpendPlan;

public class RealPayment extends BaseObject{
	
	private Long id;						//自动编号
	private SRExpendPlan expendPlan;		//关联付款计划
	private UpDatePlan upDatePlan;			//关联更新计划
	private String costCenter;				//成本中心
	private Double realMoney;				//真实付款金额
	private Date realDate;					//真是付款日期
	private BusinessAccount businessAccount;//申请
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getRealMoney() {
		return realMoney;
	}
	public void setRealMoney(Double realMoney) {
		this.realMoney = realMoney;
	}
	public Date getRealDate() {
		return realDate;
	}
	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}
	public BusinessAccount getBusinessAccount() {
		return businessAccount;
	}
	public void setBusinessAccount(BusinessAccount businessAccount) {
		this.businessAccount = businessAccount;
	}
	public SRExpendPlan getExpendPlan() {
		return expendPlan;
	}
	public void setExpendPlan(SRExpendPlan expendPlan) {
		this.expendPlan = expendPlan;
	}
	public UpDatePlan getUpDatePlan() {
		return upDatePlan;
	}
	public void setUpDatePlan(UpDatePlan upDatePlan) {
		this.upDatePlan = upDatePlan;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
}
