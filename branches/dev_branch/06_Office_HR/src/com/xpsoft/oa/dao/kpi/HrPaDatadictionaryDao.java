package com.xpsoft.oa.dao.kpi;

import java.util.Map;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;

public interface HrPaDatadictionaryDao extends BaseDao<HrPaDatadictionary>{
	public abstract Map<Long, String> getAllByParentId(long parentId);
}
