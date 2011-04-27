package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 问题关联
 * @Class Name ProblemRelation
 * @Author sa
 * @Create In 2009-3-9
 */
public class ProblemRelation extends BaseObject {
	
	private Long id;
	//父事件
	private Event parentEvent;
	//父问题
	private Problem parentProblem;
	//当前事件
	private Event event;
	//当前问题
	private Problem problem;
	//事件关联类型：父子关系，普通关联，同一个问题
	private ProblemRelationType problemRelationType;
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

	public Problem getParentProblem() {
		return parentProblem;
	}

	public void setParentProblem(Problem parentProblem) {
		this.parentProblem = parentProblem;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public ProblemRelationType getProblemRelationType() {
		return problemRelationType;
	}

	public void setProblemRelationType(ProblemRelationType problemRelationType) {
		this.problemRelationType = problemRelationType;
	}

	
}
