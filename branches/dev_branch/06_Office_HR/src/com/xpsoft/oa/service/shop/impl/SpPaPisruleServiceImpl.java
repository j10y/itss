package com.xpsoft.oa.service.shop.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaPisruleDao;
import com.xpsoft.oa.model.shop.SpPaPisrule;
import com.xpsoft.oa.service.shop.SpPaPisruleService;

public class SpPaPisruleServiceImpl extends BaseServiceImpl<SpPaPisrule>
	implements SpPaPisruleService{
	private SpPaPisruleDao dao;
	
	public SpPaPisruleServiceImpl(SpPaPisruleDao dao){
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
