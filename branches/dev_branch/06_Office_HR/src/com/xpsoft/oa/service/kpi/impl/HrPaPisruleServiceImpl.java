package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaPisruleDao;
import com.xpsoft.oa.model.kpi.HrPaPisrule;
import com.xpsoft.oa.service.kpi.HrPaPisruleService;

public class HrPaPisruleServiceImpl extends BaseServiceImpl<HrPaPisrule>
	implements HrPaPisruleService{
	private HrPaPisruleDao dao;
	
	public HrPaPisruleServiceImpl(HrPaPisruleDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 通过外键pisId删除定量得分关联的规则
	 * */
	public void removeByPisId(long pisId) {
		this.dao.removeByPisId(pisId);
	}
}
