package com.xpsoft.oa.model.hrm;

import com.xpsoft.core.model.BaseModel;

public class ExportSalary extends BaseModel{
	private Long id;
	private String name;
	private String descr;
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
