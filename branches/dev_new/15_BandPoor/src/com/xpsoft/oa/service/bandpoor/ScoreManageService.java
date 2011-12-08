package com.xpsoft.oa.service.bandpoor;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.InfoPoor;

public abstract interface ScoreManageService extends BaseService<InfoPoor>{
	public boolean validateUnique();
}
