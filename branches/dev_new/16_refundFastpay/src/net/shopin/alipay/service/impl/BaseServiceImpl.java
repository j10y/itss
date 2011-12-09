package net.shopin.alipay.service.impl;



import java.util.List;

import net.shopin.alipay.dao.GenericDao;
import net.shopin.alipay.service.BaseService;

public class BaseServiceImpl<T> extends GenericServiceImpl<T, Long> implements
		BaseService<T> {
	public BaseServiceImpl(GenericDao dao) {
		super(dao);
	}

	public List findDataList(String sql) {
		// TODO Auto-generated method stub
		return dao.findDataList(sql);
	}
	public boolean removeDatabySql(String sql){
		return dao.removeDatabySql(sql); 
	}
}