package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * ���⴦����̹�ϵ����ʱ��ʹ�ã�����Ҫ����
 * @Class Name ProblemHandleLogRel
 * @Author sa
 * @Create In 2009-3-4
 * @deprecated
 */
public class ProblemHandleLogRel extends BaseObject {
	private Long id;
	private Problem problem;//����
	private ProblemHandleLog problemHandleLog;//���������־
	private String remark;//��ע
	
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
