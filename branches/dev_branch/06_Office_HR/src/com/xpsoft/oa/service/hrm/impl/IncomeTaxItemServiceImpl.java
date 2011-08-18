package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.IncomeTaxItemDao;
import com.xpsoft.oa.model.hrm.IncomeTaxItem;
import com.xpsoft.oa.service.hrm.IncomeTaxItemService;

public class IncomeTaxItemServiceImpl extends BaseServiceImpl<IncomeTaxItem> implements
	IncomeTaxItemService {
	private IncomeTaxItemDao dao;

	public IncomeTaxItemServiceImpl(IncomeTaxItemDao dao) {
		/* 15 */super(dao);
		/* 16 */this.dao = dao;
	}
	
}