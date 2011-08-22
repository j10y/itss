package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.oa.model.kpi.HrPaKpiitemHist;
import com.xpsoft.oa.service.kpi.HrPaKpiitemHistService;

public class HrPaKpiitemHistAction {
	@Resource
	private HrPaKpiitemHistService hrPaKpiitemHistService;
	private HrPaKpiitemHist hrPaKpiitemHist;
	private long id;
	
	public HrPaKpiitemHistService getHrPaKpiitemHistService() {
		return hrPaKpiitemHistService;
	}
	public void setHrPaKpiitemHistService(
			HrPaKpiitemHistService hrPaKpiitemHistService) {
		this.hrPaKpiitemHistService = hrPaKpiitemHistService;
	}
	public HrPaKpiitemHist getHrPaKpiitemHist() {
		return hrPaKpiitemHist;
	}
	public void setHrPaKpiitemHist(HrPaKpiitemHist hrPaKpiitemHist) {
		this.hrPaKpiitemHist = hrPaKpiitemHist;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
