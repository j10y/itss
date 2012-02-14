package com.xpsoft.oa.action.miswap;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.miswap.EmailTemplate;
import com.xpsoft.oa.service.miswap.EmailTemplateService;

import flexjson.JSONSerializer;

public class EmailTemplateAction extends BaseAction{
	private Long id;
	private EmailTemplate emailTemplate;
	@Resource
	private EmailTemplateService emailTemplateService;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EmailTemplate getEmailTemplate() {
		return emailTemplate;
	}
	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	public EmailTemplateService getEmailTemplateService() {
		return emailTemplateService;
	}
	public void setEmailTemplateService(EmailTemplateService emailTemplateService) {
		this.emailTemplateService = emailTemplateService;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<EmailTemplate> list = this.emailTemplateService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.emailTemplate = this.emailTemplateService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.emailTemplate));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
}
