package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BpShopinPorClass;
import com.xpsoft.oa.service.bandpoor.BpShopinPorClassService;

import flexjson.JSONSerializer;

public class BpShopinPorClassAction extends BaseAction{
	@Resource
	BpShopinPorClassService bpShopinPorClassService;
	BpShopinPorClass bpShopinPorClass;
	Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<BpShopinPorClass> list = this.bpShopinPorClassService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.bpShopinPorClass = this.bpShopinPorClassService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.bpShopinPorClass));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		this.bpShopinPorClassService.save(this.bpShopinPorClass);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					this.bpShopinPorClassService.remove(Long.parseLong(id));
				}
			}
			this.jsonString = "{success:true}";
		} catch(Exception e) {
			this.jsonString = "{success:false}";
			e.printStackTrace();
		}
		
		return "success";
	}
	
	public BpShopinPorClassService getBpShopinPorClassService() {
		return bpShopinPorClassService;
	}
	public void setBpShopinPorClassService(
			BpShopinPorClassService bpShopinPorClassService) {
		this.bpShopinPorClassService = bpShopinPorClassService;
	}
	public BpShopinPorClass getBpShopinPorClass() {
		return bpShopinPorClass;
	}
	public void setBpShopinPorClass(BpShopinPorClass bpShopinPorClass) {
		this.bpShopinPorClass = bpShopinPorClass;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
