package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaCategory;
import com.xpsoft.oa.service.kpi.HrPaCategoryService;

public class HrPaCategoryAction extends BaseAction{
	@Resource
	private HrPaCategoryService hrPaCategoryService;
	private HrPaCategory hrPaCategory;
	private long id;
	
	public HrPaCategoryService getHrPaCategoryService() {
		return hrPaCategoryService;
	}
	public void setHrPaCategoryService(HrPaCategoryService hrPaCategoryService) {
		this.hrPaCategoryService = hrPaCategoryService;
	}
	public HrPaCategory getHrPaCategory() {
		return hrPaCategory;
	}
	public void setHrPaCategory(HrPaCategory hrPaCategory) {
		this.hrPaCategory = hrPaCategory;
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
		this.hrPaCategoryService.save(this.hrPaCategory);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
