package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 事件问题，通过中间表，防止同样一个问题可以属于不同的事件。
 * 如父子事件问题，子事件的问题是否可以同时设置属于父事件的问题。
 * @Class Name EventProblem
 * @Author sa
 * @Create In 2009-3-5
 */
public class EventProblem extends BaseObject{
	private Long id;
	private Event event;//事件
	private Problem problem;//问题
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
}
