package com.xpsoft.oa.service.bandpoor;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.Band;

public abstract interface BandService extends BaseService<Band>{
	public boolean validateUnique(Map params);
	public boolean multiSave(List<Band> list);
}
