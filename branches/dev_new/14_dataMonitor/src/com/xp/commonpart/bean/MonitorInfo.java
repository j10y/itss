package com.xp.commonpart.bean;

import java.util.Date;

public class MonitorInfo {
	private Long id;
	private Long databaseId;
	private Date createDate;
	private Integer status;
	private String descrpition;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDatabaseId() {
		return databaseId;
	}
	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
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
