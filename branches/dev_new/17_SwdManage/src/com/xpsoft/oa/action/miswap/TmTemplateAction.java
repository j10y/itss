package com.xpsoft.oa.action.miswap;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.miswap.TmSend;
import com.xpsoft.oa.model.miswap.TmTemplate;
import com.xpsoft.oa.service.miswap.TmTemplateService;

import flexjson.JSONSerializer;

public class TmTemplateAction extends BaseAction{
	private Long id;
	private TmTemplate tmTemplate;
	@Resource
	private TmTemplateService tmTemplateService;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TmTemplate getTmTemplate() {
		return tmTemplate;
	}
	public void setTmTemplate(TmTemplate tmTemplate) {
		this.tmTemplate = tmTemplate;
	}
	public TmTemplateService getTmTemplateService() {
		return tmTemplateService;
	}
	public void setTmTemplateService(TmTemplateService tmTemplateService) {
		this.tmTemplateService = tmTemplateService;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<TmTemplate> list = this.tmTemplateService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.tmTemplate = this.tmTemplateService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.tmTemplate));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
}
