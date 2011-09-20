package com.xpsoft.oa.action.shop;

import javax.annotation.Resource;

import com.xpsoft.oa.model.shop.SpPaKpiitemHist;
import com.xpsoft.oa.service.shop.SpPaKpiitemHistService;

public class SpPaKpipbcHistAction {
	@Resource
	private SpPaKpiitemHistService spPaKpiitemHistService;
	private SpPaKpiitemHist spPaKpiitemHist;
	private long id;
	
	public SpPaKpiitemHistService getSpPaKpiitemHistService() {
		return spPaKpiitemHistService;
	}
	public void setSpPaKpiitemHistService(
			SpPaKpiitemHistService spPaKpiitemHistService) {
		this.spPaKpiitemHistService = spPaKpiitemHistService;
	}
	public SpPaKpiitemHist getSpPaKpiitemHist() {
		return spPaKpiitemHist;
	}
	public void setSpPaKpiitemHist(SpPaKpiitemHist spPaKpiitemHist) {
		this.spPaKpiitemHist = spPaKpiitemHist;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
