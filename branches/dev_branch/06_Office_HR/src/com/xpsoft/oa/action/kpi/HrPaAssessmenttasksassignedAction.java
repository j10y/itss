package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaAssessmenttasksassigned;
import com.xpsoft.oa.service.kpi.HrPaAssessmenttasksassignedService;

public class HrPaAssessmenttasksassignedAction extends BaseAction{
	@Resource
	private HrPaAssessmenttasksassignedService hrPaAssessmenttasksassignedService;
	private HrPaAssessmenttasksassigned hrPaAssessmenttasksassigned;
	private long id;
	
	public HrPaAssessmenttasksassignedService getHrPaAssessmenttasksassignedService() {
		return hrPaAssessmenttasksassignedService;
	}
	public void setHrPaAssessmenttasksassignedService(
			HrPaAssessmenttasksassignedService hrPaAssessmenttasksassignedService) {
		this.hrPaAssessmenttasksassignedService = hrPaAssessmenttasksassignedService;
	}
	public HrPaAssessmenttasksassigned getHrPaAssessmenttasksassigned() {
		return hrPaAssessmenttasksassigned;
	}
	public void setHrPaAssessmenttasksassigned(
			HrPaAssessmenttasksassigned hrPaAssessmenttasksassigned) {
		this.hrPaAssessmenttasksassigned = hrPaAssessmenttasksassigned;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
		return "success";
	}
	
	public String get(){
		return "success";
	}
	
	public String save(){
		this.hrPaAssessmenttasksassignedService.save(this.hrPaAssessmenttasksassigned);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
