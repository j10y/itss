package com.xpsoft.oa.service.kpi.impl;

import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaAssessmentcriteriaDao;
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;
import com.xpsoft.oa.service.kpi.HrPaAssessmentcriteriaService;

public class HrPaAssessmentcriteriaServiceImpl extends BaseServiceImpl<HrPaAssessmentcriteria>
	implements HrPaAssessmentcriteriaService{
	private HrPaAssessmentcriteriaDao dao;
	
	public HrPaAssessmentcriteriaServiceImpl(HrPaAssessmentcriteriaDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 取得所有考核标准关键字和名称
	 * */
	public Map<String, String> getKeyAndName() {
		return this.dao.getKeyAndName();
	}
}
