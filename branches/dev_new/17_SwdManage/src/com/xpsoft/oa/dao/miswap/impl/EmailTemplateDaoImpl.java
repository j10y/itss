package com.xpsoft.oa.dao.miswap.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.miswap.EmailTemplateDao;
import com.xpsoft.oa.model.miswap.EmailTemplate;

public class EmailTemplateDaoImpl extends BaseDaoImpl<EmailTemplate> implements EmailTemplateDao{
	public EmailTemplateDaoImpl() {
		super(EmailTemplate.class);
	}
}
