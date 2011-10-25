package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.EmpProfileHistDao;
import com.xpsoft.oa.model.hrm.EmpProfileHist;
import com.xpsoft.oa.service.hrm.EmpProfileHistService;

public class EmpProfileHistServiceImpl extends BaseServiceImpl<EmpProfileHist>
		implements EmpProfileHistService {
	private EmpProfileHistDao dao;

	public EmpProfileHistServiceImpl(EmpProfileHistDao dao) {
		super(dao);
		this.dao = dao;
	}

}
