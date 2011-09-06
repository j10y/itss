package com.xpsoft.oa.action.hrm;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.service.hrm.HrPromAssessmentService;

public class HrPromAssessmentAction extends BaseAction{
	private HrPromAssessment hrPromAssessment;
	private HrPromAssessmentService hrPromAssessmentService;
	private Long id;
	
	public HrPromAssessment getHrPromAssessment() {
		return hrPromAssessment;
	}
	public void setHrPromAssessment(HrPromAssessment hrPromAssessment) {
		this.hrPromAssessment = hrPromAssessment;
	}
	public HrPromAssessmentService getHrPromAssessmentService() {
		return hrPromAssessmentService;
	}
	public void setHrPromAssessmentService(
			HrPromAssessmentService hrPromAssessmentService) {
		this.hrPromAssessmentService = hrPromAssessmentService;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String list() {
		return "success";
	}
	
	public String get() {
		return "success";
	}
	
	public String save() {
		return "success";
	}
}
