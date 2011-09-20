package com.xpsoft.oa.dao.shop;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.shop.SpPaKpiitem;

public interface SpPaKpiitemDao extends BaseDao<SpPaKpiitem>{
	public boolean findByPiIdAndPbcId(long piId, String pbcIds);
}
