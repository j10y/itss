package com.xpsoft.oa.dao.shop;

import java.util.Map;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.shop.SpPaAssessmentcriteria;

public interface SpPaAssessmentcriteriaDao extends BaseDao<SpPaAssessmentcriteria>{
	/*
	 * 取得所有考核标准关键字和名称
	 * */
	public abstract Map<String, String> getKeyAndName();
}
