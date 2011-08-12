package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaAssessmenttasksassignedDao;
import com.xpsoft.oa.model.kpi.HrPaAssessmenttasksassigned;
import com.xpsoft.oa.service.kpi.HrPaAssessmenttasksassignedService;

public class HrPaAssessmenttasksassignedServiceImpl extends BaseServiceImpl<HrPaAssessmenttasksassigned>
	implements HrPaAssessmenttasksassignedService{
	private HrPaAssessmenttasksassignedDao dao;
	
	public HrPaAssessmenttasksassignedServiceImpl(HrPaAssessmenttasksassignedDao dao){
		super(dao);
		this.dao = dao;
	}
}
