package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaAuthpbccitemDao;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;

public class HrPaAuthpbccitemServiceImpl extends BaseServiceImpl<HrPaAuthpbccitem>
	implements HrPaAuthpbccitemService{
	private HrPaAuthpbccitemDao dao;
	
	public HrPaAuthpbccitemServiceImpl(HrPaAuthpbccitemDao dao){
		super(dao);
		this.dao = dao;
	}
}
