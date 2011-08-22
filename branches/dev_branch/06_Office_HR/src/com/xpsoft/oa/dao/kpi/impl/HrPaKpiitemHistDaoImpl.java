package com.xpsoft.oa.dao.kpi.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiitemHistDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitemHist;

public class HrPaKpiitemHistDaoImpl extends BaseDaoImpl<HrPaKpiitemHist>
	implements HrPaKpiitemHistDao{
	public HrPaKpiitemHistDaoImpl(){
		super(HrPaKpiitemHist.class);
	}
}
