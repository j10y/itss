package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;
import com.xpsoft.oa.service.kpi.HrPaKpiitemService;

public class HrPaKpiitemAction extends BaseAction{
	@Resource
	private HrPaKpiitemService hrPaKpiitemService;
	private HrPaKpiitem hrPaKpiitem;
	private long id;
	
	public HrPaKpiitemService getHrPaKpiitemService() {
		return hrPaKpiitemService;
	}
	public void setHrPaKpiitemService(HrPaKpiitemService hrPaKpiitemService) {
		this.hrPaKpiitemService = hrPaKpiitemService;
	}
	public HrPaKpiitem getHrPaKpiitem() {
		return hrPaKpiitem;
	}
	public void setHrPaKpiitem(HrPaKpiitem hrPaKpiitem) {
		this.hrPaKpiitem = hrPaKpiitem;
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
		this.hrPaKpiitemService.save(this.hrPaKpiitem);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
