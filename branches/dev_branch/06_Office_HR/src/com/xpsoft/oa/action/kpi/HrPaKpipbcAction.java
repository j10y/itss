package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpipbc;
import com.xpsoft.oa.service.kpi.HrPaKpipbcService;

public class HrPaKpipbcAction extends BaseAction{
	@Resource
	private HrPaKpipbcService hrPaKpipbcService;
	private HrPaKpipbc hrPaKpipbc;
	private long id;
	
	public HrPaKpipbcService getHrPaKpipbcService() {
		return hrPaKpipbcService;
	}
	public void setHrPaKpipbcService(HrPaKpipbcService hrPaKpipbcService) {
		this.hrPaKpipbcService = hrPaKpipbcService;
	}
	public HrPaKpipbc getHrPaKpipbc() {
		return hrPaKpipbc;
	}
	public void setHrPaKpipbc(HrPaKpipbc hrPaKpipbc) {
		this.hrPaKpipbc = hrPaKpipbc;
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
		this.hrPaKpipbcService.save(this.hrPaKpipbc);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
