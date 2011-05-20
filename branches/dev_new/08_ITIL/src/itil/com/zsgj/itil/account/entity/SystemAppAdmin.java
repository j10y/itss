package com.zsgj.itil.account.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class SystemAppAdmin extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 6510135712485258374L;
	private Long id;
	private String itCode;
	private String realName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	

}
