package com.xpsoft.oa.service.shop;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaPisrule;

public interface SpPaPisruleService extends BaseService<SpPaPisrule>{
	/*
	 * 通过外键pisId删除定量得分关联的规则
	 * */
	public abstract void removeByPisId(long pisId);
}
