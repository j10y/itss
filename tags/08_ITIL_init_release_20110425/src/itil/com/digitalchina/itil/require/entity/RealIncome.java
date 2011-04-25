package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.config.extlist.entity.SRIncomePlan;

/**
 * 实际收款纪录
 * @Class Name RealIncome
 * @Author lee
 * @Create In Aug 24, 2009
 */
public class RealIncome extends BaseObject{
	
	private Long id;						//自动编号
	private SRIncomePlan incomePlan;		//关联收款计划
	private UpDatePlan upDatePlan;			//关联更新计划
	private String costCenter;				//成本中心
	private Double realMoney;				//实际收款钱数
	private Date  realDate;					//实际收款时间
	private BusinessAccount businessAccount; //商务结算申请
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BusinessAccount getBusinessAccount() {
		return businessAccount;
	}
	public void setBusinessAccount(BusinessAccount businessAccount) {
		this.businessAccount = businessAccount;
	}
	public SRIncomePlan getIncomePlan() {
		return incomePlan;
	}
	public void setIncomePlan(SRIncomePlan incomePlan) {
		this.incomePlan = incomePlan;
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

}
