package com.zsgj.itil.event.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 问题分配
 * @Class Name EventAssign
 * @Author sa
 * @Create In 2009-3-4
 */
public class ProblemAssign extends BaseObject {
	private Long id;
	private Problem problem;
	private Event event;
	private UserInfo assignUser; //分配人
	private Date assignDate; //分配时间
	private Date finishDate; //处理完成日期
	private String descn;//备注
	
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
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
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
	/**
	 * @Return the Event event
	 */
	public Event getEvent() {
		return event;
	}
	/**
	 * @Param Event event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	
	
}
