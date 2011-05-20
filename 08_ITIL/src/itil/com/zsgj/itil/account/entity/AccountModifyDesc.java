package com.zsgj.itil.account.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

public class AccountModifyDesc extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 7079681455286666590L;
	private Long id;
	private String comment;		//�޸�����
	private String accountId;		//�ʺ�ID
	public Integer accountFlag;	//�ʺ����ͱ��
	private Date modifyDate;	//�޸�����
	private String accountManger;	//�޸���ITCODE
	public  static final Integer PERSONACCOUNT=1;	//Ա����ʽ�ʺ�
	public  static final Integer SPECAILACCOUNT=2;	//��ʱ�ʺż����ʺ�
	public  static final Integer HRSACCOUNT=3;		//HRS�ʺ�
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Integer getAccountFlag() {
		return accountFlag;
	}
	public void setAccountFlag(Integer accountFlag) {
		this.accountFlag = accountFlag;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getAccountManger() {
		return accountManger;
	}
	public void setAccountManger(String accountManger) {
		this.accountManger = accountManger;
	}
	
	

}
