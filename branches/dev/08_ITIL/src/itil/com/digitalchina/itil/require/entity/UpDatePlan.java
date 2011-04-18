package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.config.extlist.entity.SRExpendPlan;
import com.digitalchina.itil.config.extlist.entity.SRIncomePlan;

/**
 * ��֧�ƻ�������ʷ
 * @Class Name UpDatePlan
 * @Author lee
 * @Create In Aug 22, 2009
 */
public class UpDatePlan extends BaseObject{
	private Long id;	//�Զ����
	private Double money;	//���
	private Date startDate; //��ʼʱ��
	private Date endDate;	//����ʱ��
	private String descn;	//����ԭ������
	private SRIncomePlan incomePlan;	//�����տ�ƻ�
	private SRExpendPlan expendPlan;	//��������ƻ�
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
