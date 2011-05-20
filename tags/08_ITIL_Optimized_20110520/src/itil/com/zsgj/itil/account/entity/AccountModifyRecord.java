package com.zsgj.itil.account.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 员工简要表修改记录
 * @Class Name AccountModifyRecord
 * @Author lee
 * @Create In Jan 15, 2010
 */
public class AccountModifyRecord extends BaseObject{
	private Long id;
	private String comment;		//修改描述
	private String itCode;		//帐号所有者ITCODE
	public Integer accountFlag;	//帐号类型标记
	private Date modifyDate;	//修改日期
	private String accountManger;	//帐号管理员ITCODE
	public  static final Integer PERSONACCOUNT=1;	//员工正式帐号
	public  static final Integer SPECAILACCOUNT=2;	//临时帐号及特帐号
	public  static final Integer HRSACCOUNT=3;		//HRS帐号
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
