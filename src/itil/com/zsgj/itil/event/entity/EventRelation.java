package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * �¼����ӹ�ϵ
 * @Class Name EventRelationship
 * @Author sa
 * @Create In 2009-3-9
 */
public class EventRelation extends BaseObject {
	private Long id;
	//���¼�
	private Event parentEvent;
	//��ǰ�¼�
	private Event event;
	//�¼��������ͣ����ӹ�ϵ����ͨ������ͬһ������
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
