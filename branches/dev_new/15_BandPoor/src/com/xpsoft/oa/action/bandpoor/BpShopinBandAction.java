package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BpShopinBand;
import com.xpsoft.oa.service.bandpoor.BpShopinBandService;

import flexjson.JSONSerializer;

public class BpShopinBandAction extends BaseAction{
	@Resource
	BpShopinBandService bpShopinBandService;
	BpShopinBand bpShopinBand;
	Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<BpShopinBand> list = this.bpShopinBandService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.bpShopinBand = this.bpShopinBandService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.bpShopinBand));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		this.bpShopinBandService.save(this.bpShopinBand);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					this.bpShopinBandService.remove(Long.parseLong(id));
				}
			}
			this.jsonString = "{success:true}";
		} catch(Exception e) {
			this.jsonString = "{success:false}";
			e.printStackTrace();
		}
		
		return "success";
	}
	
	public BpShopinBandService getBpShopinBandService() {
		return bpShopinBandService;
	}
	public void setBpShopinBandService(BpShopinBandService bpShopinBandService) {
		this.bpShopinBandService = bpShopinBandService;
	}
	public BpShopinBand getBpShopinBand() {
		return bpShopinBand;
	}
	public void setBpShopinBand(BpShopinBand bpShopinBand) {
		this.bpShopinBand = bpShopinBand;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
