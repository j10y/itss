package com.xpsoft.oa.action.hrm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.service.hrm.HrPromAssessmentService;

public class HrPromAssessmentAction extends BaseAction{
	private HrPromAssessment hrPromAssessment;
	@Resource
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
	
	public String preview() {
		if(this.id != 0) {
			this.hrPromAssessment = this.hrPromAssessmentService.get(this.id);
		}
		return "show";
	}
	
	public String get() {
		return "success";
	}
	
	public String save() {
		return "success";
	}
}
