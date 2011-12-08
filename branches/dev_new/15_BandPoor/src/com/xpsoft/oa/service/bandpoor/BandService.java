package com.xpsoft.oa.service.bandpoor;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.Band;

public abstract interface BandService extends BaseService<Band>{
	public boolean validateUnique(Map params);
}
