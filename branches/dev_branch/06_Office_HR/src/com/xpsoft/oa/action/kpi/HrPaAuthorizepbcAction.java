package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaAuthorizepbc;
import com.xpsoft.oa.service.kpi.HrPaAuthorizepbcService;

public class HrPaAuthorizepbcAction extends BaseAction{
	@Resource
	private HrPaAuthorizepbcService hrPaAuthorizepbcService;
	private HrPaAuthorizepbc hrPaAuthorizepbc;
	private long id;
	
	public HrPaAuthorizepbcService getHrPaAuthorizepbcService() {
		return hrPaAuthorizepbcService;
	}
	public void setHrPaAuthorizepbcService(
			HrPaAuthorizepbcService hrPaAuthorizepbcService) {
		this.hrPaAuthorizepbcService = hrPaAuthorizepbcService;
	}
	public HrPaAuthorizepbc getHrPaAuthorizepbc() {
		return hrPaAuthorizepbc;
	}
	public void setHrPaAuthorizepbc(HrPaAuthorizepbc hrPaAuthorizepbc) {
		this.hrPaAuthorizepbc = hrPaAuthorizepbc;
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
		this.hrPaAuthorizepbcService.save(this.hrPaAuthorizepbc);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
