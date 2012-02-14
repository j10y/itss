package com.xpsoft.oa.dao.miswap.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.miswap.TmSendDao;
import com.xpsoft.oa.model.miswap.TmSend;

public class TmSendDaoImpl extends BaseDaoImpl<TmSend> implements TmSendDao{
	public TmSendDaoImpl() {
		super(TmSend.class);
	}
}
