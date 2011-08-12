package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;

public class HrPaAuthpbccitemAction extends BaseAction{
	@Resource
	private HrPaAuthpbccitemService hrPaAuthpbccitemService;
	private HrPaAuthpbccitem hrPaAuthpbccitem;
	private long id;
	
	public HrPaAuthpbccitemService getHrPaAuthpbccitemService() {
		return hrPaAuthpbccitemService;
	}
	public void setHrPaAuthpbccitemService(
			HrPaAuthpbccitemService hrPaAuthpbccitemService) {
		this.hrPaAuthpbccitemService = hrPaAuthpbccitemService;
	}
	public HrPaAuthpbccitem getHrPaAuthpbccitem() {
		return hrPaAuthpbccitem;
	}
	public void setHrPaAuthpbccitem(HrPaAuthpbccitem hrPaAuthpbccitem) {
		this.hrPaAuthpbccitem = hrPaAuthpbccitem;
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
		this.hrPaAuthpbccitemService.save(this.hrPaAuthpbccitem);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
