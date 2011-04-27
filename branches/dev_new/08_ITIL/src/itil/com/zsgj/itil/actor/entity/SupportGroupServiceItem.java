package com.zsgj.itil.actor.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.service.entity.ServiceItem;

/**
 * 支持组默认支持的服务项数据
 * @Class Name SupportGroupServiceItem
 * @Author sa
 * @Create In 2009-3-16
 */
public class SupportGroupServiceItem extends BaseObject {
	private Long id;
	private SupportGroup supportGroup;
	private ServiceItem serviceItem;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	public SupportGroup getSupportGroup() {
		return supportGroup;
	}
	public void setSupportGroup(SupportGroup supportGroup) {
		this.supportGroup = supportGroup;
	} 
}
