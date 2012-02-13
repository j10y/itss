package com.xpsoft.oa.model.miswap;

import com.xpsoft.core.model.BaseModel;

public class SupplyConfig extends BaseModel{
	private Long id;
	private Long supplyInfoSid;
	private Integer receiveTm;
	private Long tmId;
	private Integer receiveEmail;
	private Long emailId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSupplyInfoSid() {
		return supplyInfoSid;
	}
	public void setSupplyInfoSid(Long supplyInfoSid) {
		this.supplyInfoSid = supplyInfoSid;
	}
	public Integer getReceiveTm() {
		return receiveTm;
	}
	public void setReceiveTm(Integer receiveTm) {
		this.receiveTm = receiveTm;
	}
	public Long getTmId() {
		return tmId;
	}
	public void setTmId(Long tmId) {
		this.tmId = tmId;
	}
	public Integer getReceiveEmail() {
		return receiveEmail;
	}
	public void setReceiveEmail(Integer receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	public Long getEmailId() {
		return emailId;
	}
	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}
}
