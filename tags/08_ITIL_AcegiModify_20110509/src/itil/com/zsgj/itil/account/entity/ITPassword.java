package com.zsgj.itil.account.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.require.entity.AccountApplyMainTable;

public class ITPassword  extends BaseObject{
	private Long id;
	private Integer mailType;
	private String webMail;
	private String dcMail;
	private AccountApplyMainTable applyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getMailType() {
		return mailType;
	}
	public void setMailType(Integer mailType) {
		this.mailType = mailType;
	}
	public String getWebMail() {
		return webMail;
	}
	public void setWebMail(String webMail) {
		this.webMail = webMail;
	}
	public String getDcMail() {
		return dcMail;
	}
	public void setDcMail(String dcMail) {
		this.dcMail = dcMail;
	}
	public AccountApplyMainTable getApplyId() {
		return applyId;
	}
	public void setApplyId(AccountApplyMainTable applyId) {
		this.applyId = applyId;
	}
	
	

}
