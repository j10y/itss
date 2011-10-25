package com.xpsoft.oa.dao.hrm.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.EmpProfileHistDao;
import com.xpsoft.oa.model.hrm.EmpProfileHist;

public class EmpProfileHistDaoImpl extends BaseDaoImpl<EmpProfileHist> implements
		EmpProfileHistDao {
	public EmpProfileHistDaoImpl() {
		super(EmpProfileHist.class);
	}

}
