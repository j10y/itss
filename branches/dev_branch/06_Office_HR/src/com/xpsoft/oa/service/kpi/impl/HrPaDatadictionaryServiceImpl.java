package com.xpsoft.oa.service.kpi.impl;

import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaDatadictionaryDao;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.service.kpi.HrPaDatadictionaryService;

public class HrPaDatadictionaryServiceImpl extends BaseServiceImpl<HrPaDatadictionary>
	implements HrPaDatadictionaryService{
	private HrPaDatadictionaryDao dao;
	
	public HrPaDatadictionaryServiceImpl(HrPaDatadictionaryDao dao){
		super(dao);
		this.dao = dao;
	}
	
	public Map<Long, String> getAllByParentId(long parentId) {
		return this.dao.getAllByParentId(parentId);
	}
}
