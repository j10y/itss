package com.xpsoft.oa.service.kpi;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;

public interface HrPaDatadictionaryService extends BaseService<HrPaDatadictionary>{
	public abstract Map<Long, String> getAllByParentId(long parentId);
}
