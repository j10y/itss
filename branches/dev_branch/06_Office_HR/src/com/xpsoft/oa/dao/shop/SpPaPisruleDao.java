package com.xpsoft.oa.dao.shop;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.shop.SpPaPisrule;

public interface SpPaPisruleDao extends BaseDao<SpPaPisrule>{
	/*
	 * 通过外键pisId删除定量得分关联的规则
	 * */
	public abstract void removeByPisId(long pisId);
}
