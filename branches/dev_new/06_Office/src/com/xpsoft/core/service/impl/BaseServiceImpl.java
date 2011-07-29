package com.xpsoft.core.service.impl;


import java.util.List;

import com.xpsoft.core.dao.GenericDao;
import com.xpsoft.core.service.BaseService;

public class BaseServiceImpl<T> extends GenericServiceImpl<T, Long> implements
		BaseService<T> {
	public BaseServiceImpl(GenericDao dao) {
		super(dao);
	}

	public List findDataList(String sql) {
		// TODO Auto-generated method stub
		return dao.findDataList(sql);
	}

}