package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaAuthorizepbc extends BaseModel {
	protected long id;
	protected long pbcId;
	protected long userId;
	protected Date authDate;
	protected long authPerson;
	
	public HrPaAuthorizepbc(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPbcId() {
		return pbcId;
	}

	public void setPbcId(long pbcId) {
		this.pbcId = pbcId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public long getAuthPerson() {
		return authPerson;
	}

	public void setAuthPerson(long authPerson) {
		this.authPerson = authPerson;
	}
}
