package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * �¼����⣬ͨ���м����ֹͬ��һ������������ڲ�ͬ���¼���
 * �縸���¼����⣬���¼��������Ƿ����ͬʱ�������ڸ��¼������⡣
 * @Class Name EventProblem
 * @Author sa
 * @Create In 2009-3-5
 */
public class EventProblem extends BaseObject{
	private Long id;
	private Event event;//�¼�
	private Problem problem;//����
	
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
