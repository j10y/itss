package com.xpsoft.oa.dao.shop;

import java.util.Map;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.shop.SpPaDatadictionary;

public interface SpPaDatadictionaryDao extends BaseDao<SpPaDatadictionary>{
	/*
	 * 分类取得指标考核类型、指标考核频度、指标考核方式关键字
	 * */
	public abstract Map<Long, String> getAllByParentId(long parentId);
}
