package com.xpsoft.oa.dao.kpi;

import java.util.Map;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;

public interface HrPaAssessmentcriteriaDao extends BaseDao<HrPaAssessmentcriteria>{
	/*
	 * 取得所有考核标准关键字和名称
	 * */
	public abstract Map<String, String> getKeyAndName();
}
