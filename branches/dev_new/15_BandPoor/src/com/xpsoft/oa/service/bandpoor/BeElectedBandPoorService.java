package com.xpsoft.oa.service.bandpoor;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.BeElectedBandPoor;

public interface BeElectedBandPoorService extends BaseService<BeElectedBandPoor>{
	public void saveCountScoreValue(List<BeElectedBandPoor> list,String year,String poorVersion);
}
