package com.xpsoft.oa.model.shop;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class SpPaAuthorizepbc extends BaseModel {
	protected Long id;
	protected SpPaKpiPBC2User userPbc;
	protected AppUser authorTo;
	protected Date authDate;
	protected AppUser authPerson;
	
	public SpPaAuthorizepbc(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpPaKpiPBC2User getUserPbc() {
		return userPbc;
	}

	public void setUserPbc(SpPaKpiPBC2User userPbc) {
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
