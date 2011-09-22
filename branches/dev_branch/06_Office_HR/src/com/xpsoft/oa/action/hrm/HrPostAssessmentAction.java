package com.xpsoft.oa.action.hrm;

import javax.annotation.Resource;

import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.service.hrm.HrPostAssessmentService;

public class HrPostAssessmentAction {
	@Resource
	private HrPostAssessmentService hPostAssessmentService;
	private HrPostAssessment hrPostAssessment;
	private Long id;
	public HrPostAssessmentService getHPostAssessmentService() {
		return hPostAssessmentService;
	}
	public void setHPostAssessmentService(
			HrPostAssessmentService postAssessmentService) {
		hPostAssessmentService = postAssessmentService;
	}
	public HrPostAssessment getHrPostAssessment() {
		return hrPostAssessment;
	}
	public void setHrPostAssessment(HrPostAssessment hrPostAssessment) {
		this.hrPostAssessment = hrPostAssessment;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
