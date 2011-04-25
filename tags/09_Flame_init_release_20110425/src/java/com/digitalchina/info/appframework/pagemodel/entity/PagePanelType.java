package com.digitalchina.info.appframework.pagemodel.entity;

import com.digitalchina.info.framework.dao.BaseObject;

public class PagePanelType extends BaseObject{
	private Long id;
	private String name; //panel,tabpanel,treepanel
	private String cnName;
	private Integer groupFlag;
	
	
	public Integer getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
}
