package com.xpsoft.oa.dao.hrm.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.RewardsPunishmentsDao;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.RewardsPunishments;

@SuppressWarnings("unchecked")
public class RewardsPunishmentsDaoImpl extends BaseDaoImpl<RewardsPunishments> implements
RewardsPunishmentsDao {
	public RewardsPunishmentsDaoImpl() {
		super(RewardsPunishments.class);
	}
}
