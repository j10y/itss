package com.digitalchina.itil.event.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 事件出现频率
 * @Class Name EventFrequency
 * @Author sa
 * @Create In 2009-3-4
 */
public class EventFrequency extends BaseObject {
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
