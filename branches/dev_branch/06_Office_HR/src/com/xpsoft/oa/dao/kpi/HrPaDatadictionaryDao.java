package com.xpsoft.oa.dao.kpi;

import java.util.Map;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;

public interface HrPaDatadictionaryDao extends BaseDao<HrPaDatadictionary>{
	/*
	 * 分类取得项目考核类型、项目考核频度、项目考核方式关键字
	 * */
	public abstract Map<Long, String> getAllByParentId(long parentId);
}
