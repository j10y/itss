package com.xpsoft.oa.dao.kpi.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiitemDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;

public class HrPaKpiitemDaoImpl extends BaseDaoImpl<HrPaKpiitem>
	implements HrPaKpiitemDao{
	public HrPaKpiitemDaoImpl(){
		super(HrPaKpiitem.class);
	}
}
