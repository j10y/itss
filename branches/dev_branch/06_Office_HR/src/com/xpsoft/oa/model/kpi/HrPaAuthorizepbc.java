package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class HrPaAuthorizepbc extends BaseModel {
	protected Long id;
	protected HrPaKpiPBC2User userPbc;
	protected AppUser authorTo;
	protected Date authDate;
	protected AppUser authPerson;
	
	public HrPaAuthorizepbc(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HrPaKpiPBC2User getUserPbc() {
		return userPbc;
	}

	public void setUserPbc(HrPaKpiPBC2User userPbc) {
		this.userPbc = userPbc;
	}

	public AppUser getAuthorTo() {
		return authorTo;
	}

	public void setAuthorTo(AppUser authorTo) {
		this.authorTo = authorTo;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public AppUser getAuthPerson() {
		return authPerson;
	}

	public void setAuthPerson(AppUser authPerson) {
		this.authPerson = authPerson;
	}

}
