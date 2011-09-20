package com.xpsoft.oa.service.shop;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaKpiitem;

public interface SpPaKpiitemService extends BaseService<SpPaKpiitem>{
	public boolean findByPiIdAndPbcId(long piId, String pbcIds);
}
