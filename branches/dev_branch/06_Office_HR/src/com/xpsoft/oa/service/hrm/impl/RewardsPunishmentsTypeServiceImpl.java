package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.RewardsPunishmentsTypeDao;
import com.xpsoft.oa.model.hrm.RewardsPunishmentsType;
import com.xpsoft.oa.service.hrm.RewardsPunishmentsTypeService;

public class RewardsPunishmentsTypeServiceImpl extends
		BaseServiceImpl<RewardsPunishmentsType> implements
		RewardsPunishmentsTypeService {
	private RewardsPunishmentsTypeDao dao;

	public RewardsPunishmentsTypeServiceImpl(RewardsPunishmentsTypeDao dao) {
		super(dao);
		this.dao = dao;
	}
}
