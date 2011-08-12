package com.xpsoft.oa.dao.kpi;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaPisrule;

public interface HrPaPisruleDao extends BaseDao<HrPaPisrule>{
	public abstract void removeByPisId(long pisId);
}
