package com.xpsoft.oa.service.miswap.impl;

import com.xpsoft.oa.dao.miswap.TmSendDao;
import com.xpsoft.oa.model.miswap.TmSend;
import com.xpsoft.oa.service.miswap.TmSendService;

import com.xpsoft.core.service.impl.BaseServiceImpl;

public class TmSendServiceImpl extends BaseServiceImpl<TmSend> implements TmSendService{
	private TmSendDao dao;
	
	public TmSendServiceImpl(TmSendDao dao) {
		super(dao);
		this.dao = dao;
	}
}
