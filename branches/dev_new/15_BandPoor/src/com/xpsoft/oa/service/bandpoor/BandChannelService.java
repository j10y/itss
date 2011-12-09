package com.xpsoft.oa.service.bandpoor;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.BandChannel;

public abstract interface BandChannelService extends BaseService<BandChannel>{
	public boolean validateUnique(Map params);
	public boolean multiSave(List<BandChannel> list);
}
