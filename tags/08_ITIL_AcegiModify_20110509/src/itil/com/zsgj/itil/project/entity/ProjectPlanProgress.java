package com.zsgj.itil.project.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class ProjectPlanProgress extends BaseObject {
	private Long id;
	private String name;
	
	@Override
	public String getUniquePropName() {
		return "name";
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
}
