package com.xpsoft.oa.service.kpi;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaPisrule;

public interface HrPaPisruleService extends BaseService<HrPaPisrule>{
	public abstract void removeByPisId(long pisId);
}
