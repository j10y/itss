package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 事件父子关系
 * @Class Name EventRelationship
 * @Author sa
 * @Create In 2009-3-9
 */
public class EventRelation extends BaseObject {
	private Long id;
	//父事件
	private Event parentEvent;
	//当前事件
	private Event event;
	//事件关联类型：父子关系，普通关联，同一个问题
	private EventRelationType eventRelationType;
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public EventRelationType getEventRelationType() {
		return eventRelationType;
	}
	public void setEventRelationType(EventRelationType eventRelationType) {
		this.eventRelationType = eventRelationType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Event getParentEvent() {
		return parentEvent;
	}
	public void setParentEvent(Event parentEvent) {
		this.parentEvent = parentEvent;
	}
}
