package com.xpsoft.oa.service.kpi;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;

public interface HrPaDatadictionaryService extends BaseService<HrPaDatadictionary>{
	/*
	 * 分类取得指标考核类型、指标考核频度、指标考核方式关键字
	 * */
	public abstract Map<Long, String> getAllByParentId(long parentId);
}
