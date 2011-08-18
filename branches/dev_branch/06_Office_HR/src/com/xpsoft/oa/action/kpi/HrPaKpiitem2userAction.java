package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;

public class HrPaKpiitem2userAction extends BaseAction {
	@Resource
	private HrPaKpiitem2userService hrPaKpiitem2userService;
	private HrPaKpiitem2user hrPaKpiitem2user;
	private long id;
	
	public HrPaKpiitem2userService getHrPaKpiitem2userService() {
		return hrPaKpiitem2userService;
	}
	public void setHrPaKpiitem2userService(
			HrPaKpiitem2userService hrPaKpiitem2userService) {
		this.hrPaKpiitem2userService = hrPaKpiitem2userService;
	}
	public HrPaKpiitem2user getHrPaKpiitem2user() {
		return hrPaKpiitem2user;
	}
	public void setHrPaKpiitem2user(HrPaKpiitem2user hrPaKpiitem2user) {
		this.hrPaKpiitem2user = hrPaKpiitem2user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		return "success";
	}
	
	public String get() {
		return "success";
	}
	
	public String multiDel() {
		return "success";
	}
	
	public String save() {
		return "success";
	}
}
