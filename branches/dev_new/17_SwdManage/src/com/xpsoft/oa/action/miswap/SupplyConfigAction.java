package com.xpsoft.oa.action.miswap;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.miswap.SupplyConfig;
import com.xpsoft.oa.service.miswap.SupplyConfigService;

public class SupplyConfigAction extends BaseAction{
	private Long id;
	private SupplyConfig supplyConfig;
	@Resource
	private SupplyConfigService supplyConfigService;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SupplyConfig getSupplyConfig() {
		return supplyConfig;
	}
	public void setSupplyConfig(SupplyConfig supplyConfig) {
		this.supplyConfig = supplyConfig;
	}
	public SupplyConfigService getSupplyConfigService() {
		return supplyConfigService;
	}
	public void setSupplyConfigService(SupplyConfigService supplyConfigService) {
		this.supplyConfigService = supplyConfigService;
	}
	
	public String save() {
		this.supplyConfigService.save(this.supplyConfig);
		this.jsonString = "{success:true}";
		
		return "success";
	}
}
