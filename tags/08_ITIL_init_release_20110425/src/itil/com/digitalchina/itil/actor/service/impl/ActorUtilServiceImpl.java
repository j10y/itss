package com.digitalchina.itil.actor.service.impl;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.itil.actor.dao.CustomerDao;
import com.digitalchina.itil.actor.service.ActorUtilService;

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
