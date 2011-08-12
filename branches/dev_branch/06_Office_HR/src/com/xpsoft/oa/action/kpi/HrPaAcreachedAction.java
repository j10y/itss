package com.xpsoft.oa.action.kpi;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaAcreached;
import com.xpsoft.oa.service.kpi.HrPaAcreachedService;

import flexjson.JSONSerializer;

public class HrPaAcreachedAction extends BaseAction{
	@Resource
	private HrPaAcreachedService hrPaAcreachedService;
	private HrPaAcreached hrPaAcreached;
	private long id;
	
	public HrPaAcreachedService getHrPaAcreachedService() {
		return hrPaAcreachedService;
	}
	public void setHrPaAcreachedService(HrPaAcreachedService hrPaAcreachedService) {
		this.hrPaAcreachedService = hrPaAcreachedService;
	}
	public HrPaAcreached getHrPaAcreached() {
		return hrPaAcreached;
	}
	public void setHrPaAcreached(HrPaAcreached hrPaAcreached) {
		this.hrPaAcreached = hrPaAcreached;
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
		this.hrPaAcreachedService.save(this.hrPaAcreached);
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
}
