package com.xpsoft.oa.service.bandpoor;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.BusinessArea;

public abstract interface BusinessAreaService extends BaseService<BusinessArea>{
	public boolean validateUnique();
}
