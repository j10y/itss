package com.xpsoft.oa.action.danpin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.danpin.SupplyInfo;
import com.xpsoft.oa.service.danpin.SupplyInfoService;

import flexjson.JSONSerializer;

public class SupplyInfoAction extends BaseAction{
	private Long sid;
	private SupplyInfo supplyInfo;
	@Resource
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
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<SupplyInfo> list = this.supplyInfoService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
}
