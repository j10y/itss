package com.digitalchina.itil.service.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.service.entity.SCIColumn;
import com.digitalchina.itil.service.entity.ServiceItemType;
import com.digitalchina.itil.service.service.SCIColumnService;

public class SCIColumnServiceImpl extends BaseDao implements SCIColumnService {

	public List findAllSCIColumn() {
		// TODO Auto-generated method stub
		
		return null;
	}

	public SCIColumn findSCIColumnById(String id) {
		// TODO Auto-generated method stub
		Criteria c=this.createCriteria(SCIColumn.class);
		c.add(Restrictions.eq("id",Long.parseLong(id)));
		
		SCIColumn sCIColumn=(SCIColumn) c.uniqueResult();
		return sCIColumn;
	}

	public void removeSCIColumnById(String id) {
		// TODO Auto-generated method stub
		SCIColumn sCIColumn=this.findSCIColumnById(id);
		this.remove(sCIColumn);
	}

	public void removeSCIColumnByIds(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			this.removeSCIColumnById(id);
		}
	}

	public SCIColumn saveSCIColumn(SCIColumn column) {
		// TODO Auto-generated method stub
		if(column.getOrder()==null){
			int maxOrder=this.findMaxNumberByOrder(column)+1;
			column.setOrder(maxOrder);
		}
		SCIColumn sCIColumn=(SCIColumn) this.save(column);
		return sCIColumn;
	}

	public List findSCIColumnByServiceItemType(ServiceItemType serviceItemType) {
		// TODO Auto-generated method stub
		Criteria c=this.createCriteria(SCIColumn.class);
		c.add(Restrictions.eq("serviceItemType",serviceItemType));
		c.addOrder(Order.asc("order"));
		List siccolumns=c.list();
		return siccolumns;
	}

	public Page findSCIColumnByServiceItemType(ServiceItemType serviceItemType,
			int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Criteria c=this.createCriteria(SCIColumn.class);
		c.add(Restrictions.eq("serviceItemType",serviceItemType));
		Page page=super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	
	private int findMaxNumberByOrder(SCIColumn column){
		Criteria c=this.createCriteria(SCIColumn.class);
		c.add(Restrictions.eq("serviceItemType",column.getServiceItemType()));
		c.setProjection(Projections.projectionList()
				.add(Projections.max("order").as("maxOrder")));
		Object maxOrder = c.uniqueResult();
		int orderMax = 0; 
		if(maxOrder!=null){
			orderMax = ((Integer)maxOrder).intValue();
		}
		return orderMax;
	}

}
