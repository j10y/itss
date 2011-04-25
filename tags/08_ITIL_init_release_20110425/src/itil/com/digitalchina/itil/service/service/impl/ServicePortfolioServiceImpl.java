package com.digitalchina.itil.service.service.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.itil.service.entity.ServicePortfolio;
import com.digitalchina.itil.service.service.ServicePortfolioService;

public class ServicePortfolioServiceImpl extends BaseDao implements ServicePortfolioService{

	public ServicePortfolio findServicePortfolioById(String id) {
		if(id==null||id.equals("")){
			return null;
		}
		ServicePortfolio result = null;
		Criteria c = super.getCriteria(ServicePortfolio.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		result = (ServicePortfolio) c.uniqueResult();
		return result;
	}

}
