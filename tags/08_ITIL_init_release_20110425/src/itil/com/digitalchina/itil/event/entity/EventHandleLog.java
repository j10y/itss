package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 未完成事件处理日志
 * @Class Name EventHandleLog
 * @Author sa
 * @Create In 2009-3-4
 */
public class EventHandleLog extends BaseObject{
	private Long id;
	private Event event;
	private UserInfo handleUser;//处理人
	private String handleLog;//失败原因
	private Date handleDate;//时间
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Date getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}
	public String getHandleLog() {
		return handleLog;
	}
	public void setHandleLog(String handleLog) {
		this.handleLog = handleLog;
	}
	public UserInfo getHandleUser() {
		return handleUser;
	}
	public void setHandleUser(UserInfo handleUser) {
		this.handleUser = handleUser;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
