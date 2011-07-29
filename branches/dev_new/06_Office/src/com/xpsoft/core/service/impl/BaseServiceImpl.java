package com.xpsoft.core.service.impl;

import com.xpsoft.core.dao.GenericDao;
import com.xpsoft.core.service.BaseService;

public class BaseServiceImpl<T> extends GenericServiceImpl<T, Long> implements
		BaseService<T> {
	public BaseServiceImpl(GenericDao dao) {
		super(dao);
	}
}
