package com.digitalchina.itil.event.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 事件处理类型
 * @Class Name EventDealType
 * @Author huzh
 * @Create In Jun 25, 2010
 */
public class EventDealType extends BaseObject {
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
