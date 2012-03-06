package com.xp.commonpart.bean;

import java.util.Date;

public class WebMonitorInfo {
	private Long id;
	private Long webId;
	private Date createDate;
	private Integer status;
	private String descrpition;
	private String webName;

	public String getWebName() {
		return webName;
	}
	public void setWebName(String webName) {
		this.webName = webName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getWebId() {
		return webId;
	}
	public void setWebId(Long webId) {
		this.webId = webId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescrpition() {
		return descrpition;
	}
	public void setDescrpition(String descrpition) {
		this.descrpition = descrpition;
	}
	
}
