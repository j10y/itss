package com.xpsoft.oa.model.miswap;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class TmSend extends BaseModel{
	private Long id;
	private Date sendDate;
	private Long sendCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Long getSendCount() {
		return sendCount;
	}
	public void setSendCount(Long sendCount) {
		this.sendCount = sendCount;
	}
}
