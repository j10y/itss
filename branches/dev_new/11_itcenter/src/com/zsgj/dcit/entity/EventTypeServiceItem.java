package com.zsgj.dcit.entity;

/**
 * �¼������������֮��Ĺ���ʵ��
 * @Class Name EventTypeServiceItem
 * @Author huzh
 * @Create In May 19, 2010
 */
@SuppressWarnings("serial")
public class EventTypeServiceItem{
	private Long id;
	private EventType eventType;//�¼�����
	private ServiceItem serviceItem;//������
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
