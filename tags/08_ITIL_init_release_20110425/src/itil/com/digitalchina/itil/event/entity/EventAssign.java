package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 事件分配
 * @Class Name EventAssign
 * @Author sa
 * @Create In 2009-3-4
 */
@SuppressWarnings("serial")
public class EventAssign extends BaseObject {
	private Long id;
	private Event event;
	private UserInfo assignUser; //分配人
	private Date assignDate; //分配时间
	private Date finishDate; //处理完成日期
	private String remark; //备注
	private Role roleId;
	
	public Date getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}
	public UserInfo getAssignUser() {
		return assignUser;
	}
	public void setAssignUser(UserInfo assignUser) {
		this.assignUser = assignUser;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Role getRoleId() {
		return roleId;
	}
	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}
	
	
}
