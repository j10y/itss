package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.RewardsPunishmentsDao;
import com.xpsoft.oa.model.hrm.RewardsPunishments;
import com.xpsoft.oa.service.hrm.RewardsPunishmentsService;

public class RewardsPunishmentsServiceImpl extends BaseServiceImpl<RewardsPunishments> implements
	RewardsPunishmentsService {
	private RewardsPunishmentsDao dao;

	public RewardsPunishmentsServiceImpl(RewardsPunishmentsDao dao) {
		super(dao);
		this.dao = dao;
	}
}