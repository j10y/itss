package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 问题处理过程关系表，暂时不使用，不需要这样
 * @Class Name ProblemHandleLogRel
 * @Author sa
 * @Create In 2009-3-4
 * @deprecated
 */
public class ProblemHandleLogRel extends BaseObject {
	private Long id;
	private Problem problem;//问题
	private ProblemHandleLog problemHandleLog;//处理过程日志
	private String remark;//备注
	
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
	public ProblemHandleLog getProblemHandleLog() {
		return problemHandleLog;
	}
	public void setProblemHandleLog(ProblemHandleLog problemHandleLog) {
		this.problemHandleLog = problemHandleLog;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
