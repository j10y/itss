package com.zsgj.itil.account.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * Ա����Ҫ���޸ļ�¼
 * @Class Name AccountModifyRecord
 * @Author lee
 * @Create In Jan 15, 2010
 */
public class AccountModifyRecord extends BaseObject{
	private Long id;
	private String comment;		//�޸�����
	private String itCode;		//�ʺ�������ITCODE
	public Integer accountFlag;	//�ʺ����ͱ��
	private Date modifyDate;	//�޸�����
	private String accountManger;	//�ʺŹ���ԱITCODE
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
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
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
