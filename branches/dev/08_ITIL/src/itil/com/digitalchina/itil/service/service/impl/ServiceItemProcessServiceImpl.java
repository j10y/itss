package com.digitalchina.itil.service.service.impl;

import java.util.List;

import org.drools.lang.descr.Restriction;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemProcess;
import com.digitalchina.itil.service.service.ServiceItemProcessService;

public class ServiceItemProcessServiceImpl extends BaseDao implements ServiceItemProcessService{

	public ServiceItemProcess findServiceItemProcessById(String id) {
		ServiceItemProcess result = null;
		if(id!=null&&!"".equals(id)){
			Criteria c = super.getCriteria(ServiceItemProcess.class);
			c.setFetchMode("serviceItem", FetchMode.JOIN);
			c.setFetchMode("pagePanel",FetchMode.JOIN);
			c.add(Restrictions.eq("id",Long.valueOf(id)));
			result = (ServiceItemProcess) c.uniqueResult();
		}
		return result;
	}

	public void remove(ServiceItemProcess serviceItemProcess) {
		super.remove(serviceItemProcess);
	}

	public ServiceItemProcess save(ServiceItemProcess serviceItemProcess) {
		return (ServiceItemProcess) super.save(serviceItemProcess);
	}

	public List<ServiceItemProcess> findProcessesByServiceItem(
			ServiceItem serviceItem) {
		List<ServiceItemProcess> list = null;
		Criteria c = super.createCriteria(ServiceItemProcess.class);
		c.add(Restrictions.eq("serviceItem", serviceItem));
		c.addOrder(Order.asc("id"));
		list = c.list();
		return list;
	}

	public ServiceItemProcess findProcessesByServiceItemAndType(
			ServiceItem serviceItem, Integer processType) {
		ServiceItemProcess result=null;
		if(processType==0||processType==1||processType==2){
		Criteria criteria =super.getCriteria(ServiceItemProcess.class);
		criteria.setFetchMode("pagePanel", FetchMode.JOIN);
		criteria.add(Restrictions.eq("serviceItem", serviceItem));
		criteria.add(Restrictions.eq("sidProcessType", processType));
		result=(ServiceItemProcess) criteria.list().get(0);
		}
		return result;
	}

}
