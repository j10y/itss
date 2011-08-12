package com.xpsoft.oa.dao.kpi;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaPisrule;

public interface HrPaPisruleDao extends BaseDao<HrPaPisrule>{
	/*
	 * 通过外键pisId删除定量得分关联的规则
	 * */
	public abstract void removeByPisId(long pisId);
}
