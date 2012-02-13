package com.xpsoft.oa.action.danpin;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.danpin.SupplyInfo;
import com.xpsoft.oa.service.danpin.SupplyInfoService;

public class SupplyInfoAction extends BaseAction{
	private Long sid;
	private SupplyInfo supplyInfo;
	private SupplyInfoService supplyInfoService;
	
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public SupplyInfo getSupplyInfo() {
		return supplyInfo;
	}
	public void setSupplyInfo(SupplyInfo supplyInfo) {
		this.supplyInfo = supplyInfo;
	}
	public SupplyInfoService getSupplyInfoService() {
		return supplyInfoService;
	}
	public void setSupplyInfoService(SupplyInfoService supplyInfoService) {
		this.supplyInfoService = supplyInfoService;
	}
}
