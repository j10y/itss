package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * �������
 * @Class Name ProblemRelation
 * @Author sa
 * @Create In 2009-3-9
 */
public class ProblemRelation extends BaseObject {
	
	private Long id;
	//���¼�
	private Event parentEvent;
	//������
	private Problem parentProblem;
	//��ǰ�¼�
	private Event event;
	//��ǰ����
	private Problem problem;
	//�¼��������ͣ����ӹ�ϵ����ͨ������ͬһ������
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
