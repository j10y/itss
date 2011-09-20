package com.xpsoft.oa.service.shop.impl;

import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaDatadictionaryDao;
import com.xpsoft.oa.model.shop.SpPaDatadictionary;
import com.xpsoft.oa.service.shop.SpPaDatadictionaryService;

public class SpPaDatadictionaryServiceImpl extends BaseServiceImpl<SpPaDatadictionary>
	implements SpPaDatadictionaryService{
	private SpPaDatadictionaryDao dao;
	
	public SpPaDatadictionaryServiceImpl(SpPaDatadictionaryDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 分类取得指标考核类型、指标考核频度、指标考核方式关键字
	 * */
	public Map<Long, String> getAllByParentId(long parentId) {
		return this.dao.getAllByParentId(parentId);
	}
}
