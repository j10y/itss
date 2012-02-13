package com.xpsoft.oa.dao.miswap.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.miswap.TmTemplateDao;
import com.xpsoft.oa.model.miswap.TmTemplate;

public class TmTemplateDaoImpl extends BaseDaoImpl<TmTemplate> implements TmTemplateDao{
	public TmTemplateDaoImpl() {
		super(TmTemplate.class);
	}
}
