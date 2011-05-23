package com.zsgj.dcit.entity;

/**
 * 事件类型与服务项之间的关联实体
 * @Class Name EventTypeServiceItem
 * @Author huzh
 * @Create In May 19, 2010
 */
@SuppressWarnings("serial")
public class EventTypeServiceItem{
	private Long id;
	private EventType eventType;//事件类型
	private ServiceItem serviceItem;//服务项
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	
	
}
