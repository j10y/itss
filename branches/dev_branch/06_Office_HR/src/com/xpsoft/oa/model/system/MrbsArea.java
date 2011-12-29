package com.xpsoft.oa.model.system;

import com.xpsoft.core.model.BaseModel;

public class MrbsArea extends BaseModel{
	public static final Integer DELETED  = 0;//
	public static final Integer CREATED  = 0;//
	
	private Long id;
	private String areaName;
	private String linkman;
	private String descn;
	private String shortdescn;
	
	private Integer flag = 1;//1 normal 0 delted
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getShortdescn() {
		return shortdescn;
	}
	public void setShortdescn(String shortdescn) {
		this.shortdescn = shortdescn;
	}
}
