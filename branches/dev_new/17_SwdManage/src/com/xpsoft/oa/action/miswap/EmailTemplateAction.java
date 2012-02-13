package com.xpsoft.oa.action.miswap;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.miswap.EmailTemplate;
import com.xpsoft.oa.service.miswap.EmailTemplateService;

public class EmailTemplateAction extends BaseAction{
	private Long id;
	private EmailTemplate emailTemplate;
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
}
