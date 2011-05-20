package com.zsgj.itil.actor.service.impl;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.itil.actor.dao.CustomerDao;
import com.zsgj.itil.actor.service.ActorUtilService;

public class ActorUtilServiceImpl extends BaseService implements ActorUtilService{
	
	private CustomerDao customerDao;
	public Page getAllUser(String userName, int pageNo, int pageSize) {
		return customerDao.getAllUser(userName, pageNo, pageSize);
	}
	public CustomerDao getCustomerDao() {
		return customerDao;
	}
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

}
