package com.xpsoft.oa.service.bandpoor;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.BandStyle;

public abstract interface BandStyleService extends BaseService<BandStyle>{
	public boolean validateUnique(Map params);
}
