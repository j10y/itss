package com.xpsoft.oa.action.danpin;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.danpin.SupplyLinker;
import com.xpsoft.oa.service.danpin.SupplyLinkerService;

public class SupplyLinkerAction extends BaseAction{
	private Long sid;
	private SupplyLinker supplyLinker;
	@Resource
	private SupplyLinkerService supplyLinkerService;
	
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public SupplyLinker getSupplyLinker() {
		return supplyLinker;
	}
	public void setSupplyLinker(SupplyLinker supplyLinker) {
		this.supplyLinker = supplyLinker;
	}
	public SupplyLinkerService getSupplyLinkerService() {
		return supplyLinkerService;
	}
	public void setSupplyLinkerService(SupplyLinkerService supplyLinkerService) {
		this.supplyLinkerService = supplyLinkerService;
	}
}
