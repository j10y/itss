package com.xpsoft.oa.action.miswap;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.miswap.TmTemplate;
import com.xpsoft.oa.service.miswap.TmTemplateService;

public class TmTemplateAction extends BaseAction{
	private Long id;
	private TmTemplate tmTemplate;
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
}
