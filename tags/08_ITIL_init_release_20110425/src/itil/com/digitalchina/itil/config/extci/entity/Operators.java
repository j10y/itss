package com.digitalchina.itil.config.extci.entity;

import com.digitalchina.info.framework.dao.BaseObject;

public class Operators extends BaseObject {


	private static final long serialVersionUID = 4341386404290711020L;
	
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
