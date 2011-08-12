package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaPisrule;
import com.xpsoft.oa.service.kpi.HrPaPisruleService;

public class HrPaPisruleAction extends BaseAction{
	@Resource
	private HrPaPisruleService hrPaPisruleService;
	private HrPaPisrule hrPaPisrule;
	private long id;
	
	public HrPaPisruleService getHrPaPisruleService() {
		return hrPaPisruleService;
	}
	public void setHrPaPisruleService(HrPaPisruleService hrPaPisruleService) {
		this.hrPaPisruleService = hrPaPisruleService;
	}
	public HrPaPisrule getHrPaPisrule() {
		return hrPaPisrule;
	}
	public void setHrPaPisrule(HrPaPisrule hrPaPisrule) {
		this.hrPaPisrule = hrPaPisrule;
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
		this.hrPaPisruleService.save(this.hrPaPisrule);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
