package com.xpsoft.oa.action.shop;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaCategory;
import com.xpsoft.oa.service.shop.SpPaCategoryService;

public class SpPaCategoryAction extends BaseAction{
	@Resource
	private SpPaCategoryService spPaCategoryService;
	private SpPaCategory spPaCategory;
	private long id;
	
	public SpPaCategoryService getSpPaCategoryService() {
		return spPaCategoryService;
	}
	public void setSpPaCategoryService(SpPaCategoryService spPaCategoryService) {
		this.spPaCategoryService = spPaCategoryService;
	}
	public SpPaCategory getSpPaCategory() {
		return spPaCategory;
	}
	public void setSpPaCategory(SpPaCategory spPaCategory) {
		this.spPaCategory = spPaCategory;
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
		this.spPaCategoryService.save(this.spPaCategory);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
