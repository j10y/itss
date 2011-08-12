package com.xpsoft.oa.service.kpi;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaPisrule;

public interface HrPaPisruleService extends BaseService<HrPaPisrule>{
	/*
	 * 通过外键pisId删除定量得分关联的规则
	 * */
	public abstract void removeByPisId(long pisId);
}
