package com.xpsoft.oa.service.dg.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.dg.CopyFoxBillDao;
import com.xpsoft.oa.model.dg.CopyFoxBill;
import com.xpsoft.oa.service.dg.CopyFoxBillService;

public class CopyFoxBillServiceImpl extends BaseServiceImpl<CopyFoxBill> implements CopyFoxBillService {
	private CopyFoxBillDao cfbdao;
	 
	public CopyFoxBillServiceImpl(CopyFoxBillDao dao)
	   {
		 super(dao);
	     this.cfbdao = dao;
	   }

}
