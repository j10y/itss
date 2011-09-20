package com.xpsoft.oa.action.shop;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaAuthpbccitem;
import com.xpsoft.oa.service.shop.SpPaAuthpbccitemService;

public class SpPaAuthpbccitemAction extends BaseAction{
	@Resource
	private SpPaAuthpbccitemService spPaAuthpbccitemService;
	private SpPaAuthpbccitem spPaAuthpbccitem;
	private long id;
	
	public SpPaAuthpbccitemService getSpPaAuthpbccitemService() {
		return spPaAuthpbccitemService;
	}
	public void setSpPaAuthpbccitemService(
			SpPaAuthpbccitemService spPaAuthpbccitemService) {
		this.spPaAuthpbccitemService = spPaAuthpbccitemService;
	}
	public SpPaAuthpbccitem getSpPaAuthpbccitem() {
		return spPaAuthpbccitem;
	}
	public void setSpPaAuthpbccitem(SpPaAuthpbccitem spPaAuthpbccitem) {
		this.spPaAuthpbccitem = spPaAuthpbccitem;
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
		this.spPaAuthpbccitemService.save(this.spPaAuthpbccitem);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
