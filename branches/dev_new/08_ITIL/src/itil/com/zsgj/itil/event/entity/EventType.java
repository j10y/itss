package com.zsgj.itil.event.entity;

import java.util.Date;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.itil.config.entity.CIBaseData;
import com.zsgj.itil.config.entity.CIBaseType;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;

/**
 * 事件类型
 * @Class Name EventType
 * @Author huzh
 * @Create In May 19, 2010
 */
@SuppressWarnings("serial")
public class EventType extends BaseObject {
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
