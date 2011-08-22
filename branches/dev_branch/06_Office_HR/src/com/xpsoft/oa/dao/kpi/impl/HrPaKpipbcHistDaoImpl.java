package com.xpsoft.oa.dao.kpi.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpipbcHistDao;
import com.xpsoft.oa.model.kpi.HrPaKpipbcHist;

public class HrPaKpipbcHistDaoImpl extends BaseDaoImpl<HrPaKpipbcHist>
	implements HrPaKpipbcHistDao{
	public HrPaKpipbcHistDaoImpl(){
		super(HrPaKpipbcHist.class);
	}
}
