package com.zsgj.itil.config.extlist.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class RequirementLevel extends BaseObject{
	private Long id;
	private String name;
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
}