package com.xpsoft.oa.service.bandpoor;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.BandLevel;

public abstract interface BandLevelService extends BaseService<BandLevel>{
	public boolean validateUnique();
}
