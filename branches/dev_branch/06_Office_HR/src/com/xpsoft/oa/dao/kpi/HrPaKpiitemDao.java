package com.xpsoft.oa.dao.kpi;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;

public interface HrPaKpiitemDao extends BaseDao<HrPaKpiitem>{
	public boolean findByPiIdAndPbcId(long piId, String[] pbcIds);
}
