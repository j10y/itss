package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 问题严重性分类
 * @Class Name EventPonderance
 * @Author sa
 * @Create In 2009-3-4
 */
public class EventPonderance extends BaseObject {
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
