package com.xpsoft.oa.service.bandpoor;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.Floor;

public abstract interface FloorService extends BaseService<Floor>{
	public boolean validateUnique();
}
