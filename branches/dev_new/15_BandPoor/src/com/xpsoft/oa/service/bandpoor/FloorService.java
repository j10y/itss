package com.xpsoft.oa.service.bandpoor;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.model.bandpoor.Floor;

public abstract interface FloorService extends BaseService<Floor>{
	public boolean validateUnique(Map params);
	public boolean multiSave(List<Floor> list);
}
