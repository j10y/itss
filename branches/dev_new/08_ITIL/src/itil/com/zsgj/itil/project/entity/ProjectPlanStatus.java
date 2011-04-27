package com.zsgj.itil.project.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * ÏîÄ¿×´Ì¬
 * @Class Name PlanStatus
 * @Author sa
 * @Create In 2009-1-4
 */
public class ProjectPlanStatus extends BaseObject {
	private Long id;
	private String name;
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
