package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.extlist.entity.SRExpendPlan;
import com.digitalchina.itil.config.extlist.entity.SRIncomePlan;

/**
 * ��֧�ƻ�������ʷ
 * @Class Name UpDatePlanEvent
 * @Author lee
 * @Create In Aug 22, 2009
 */
public class UpDatePlanEvent extends BaseObject{
	private Long id;	//�Զ����
	private Double money;	//���
	private Date startDate; //��ʼʱ��
	private Date endDate;	//����ʱ��
	private String descn;	//����ԭ������
	private SRIncomePlan incomePlan;	//�����տ�ƻ�
	private SRExpendPlan expendPlan;	//��������ƻ�
	private Date createDate;		//����ʱ��
	private UserInfo createUser;	//�����û�
	
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public UserInfo getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
}
