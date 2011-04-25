package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.config.extlist.entity.SRExpendPlan;
import com.digitalchina.itil.config.extlist.entity.SRIncomePlan;

/**
 * 收支计划更新历史
 * @Class Name UpDatePlan
 * @Author lee
 * @Create In Aug 22, 2009
 */
public class UpDatePlan extends BaseObject{
	private Long id;	//自动编号
	private Double money;	//金额
	private Date startDate; //开始时间
	private Date endDate;	//结束时间
	private String descn;	//更新原因描述
	private SRIncomePlan incomePlan;	//关联收款计划
	private SRExpendPlan expendPlan;	//关联付款计划
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public SRIncomePlan getIncomePlan() {
		return incomePlan;
	}
	public void setIncomePlan(SRIncomePlan incomePlan) {
		this.incomePlan = incomePlan;
	}
	public SRExpendPlan getExpendPlan() {
		return expendPlan;
	}
	public void setExpendPlan(SRExpendPlan expendPlan) {
		this.expendPlan = expendPlan;
	}
}
