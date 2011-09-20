package com.xpsoft.oa.action.shop;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaPisrule;
import com.xpsoft.oa.service.shop.SpPaPisruleService;

public class SpPaPisruleAction extends BaseAction{
	@Resource
	private SpPaPisruleService spPaPisruleService;
	private SpPaPisrule spPaPisrule;
	private long id;
	
	public SpPaPisruleService getSpPaPisruleService() {
		return spPaPisruleService;
	}
	public void setSpPaPisruleService(SpPaPisruleService spPaPisruleService) {
		this.spPaPisruleService = spPaPisruleService;
	}
	public SpPaPisrule getSpPaPisrule() {
		return spPaPisrule;
	}
	public void setSpPaPisrule(SpPaPisrule spPaPisrule) {
		this.spPaPisrule = spPaPisrule;
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
		this.spPaPisruleService.save(this.spPaPisrule);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
