package com.digitalchina.itil.event.entity;

import com.digitalchina.info.framework.dao.BaseObject;
/**
 * 事件查看标识
 * @Class Name EventViewFlag
 * @Author sujs
 * @Create In Mar 9, 2009
 */
public class EventViewFlag extends BaseObject {
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
