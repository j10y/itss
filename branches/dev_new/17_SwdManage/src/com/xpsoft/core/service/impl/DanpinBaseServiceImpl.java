package com.xpsoft.core.service.impl;

import java.util.List;

import com.xpsoft.core.dao.DanpinGenericDao;
import com.xpsoft.core.service.DanpinBaseService;

public class DanpinBaseServiceImpl<T> extends DanpinGenericServiceImpl<T, Long> implements
		DanpinBaseService<T> {
	public DanpinBaseServiceImpl(DanpinGenericDao dao) {
		super(dao);
	}

	public List findDataList(String sql) {
		// TODO Auto-generated method stub
		return dao.findDataList(sql);
	}
	public boolean removeDatabySql(String sql){
		return dao.removeDatabySql(sql); 
	}
	public boolean updateDatabySql(String sql){
		return dao.updateDatabySql(sql); 
	}
}