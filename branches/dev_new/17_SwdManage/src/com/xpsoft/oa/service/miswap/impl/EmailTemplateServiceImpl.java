package com.xpsoft.oa.service.miswap.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.miswap.EmailTemplateDao;
import com.xpsoft.oa.model.miswap.EmailTemplate;
import com.xpsoft.oa.service.miswap.EmailTemplateService;

public class EmailTemplateServiceImpl extends BaseServiceImpl<EmailTemplate> implements EmailTemplateService{
	private EmailTemplateDao dao;
	
	public EmailTemplateServiceImpl(EmailTemplateDao dao) {
		super(dao);
		this.dao = dao;
	}
}
