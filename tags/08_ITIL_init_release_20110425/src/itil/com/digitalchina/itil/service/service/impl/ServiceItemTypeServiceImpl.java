package com.digitalchina.itil.service.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.service.entity.SCIColumn;
import com.digitalchina.itil.service.entity.ServiceItemType;
import com.digitalchina.itil.service.service.ServiceItemTypeService;

public class ServiceItemTypeServiceImpl extends BaseDao implements ServiceItemTypeService {

	public List findAllServiceItemType() {
		// TODO Auto-generated method stub
		Criteria c=this.createCriteria(ServiceItemType.class);
		List scitList=c.list();
		return scitList;
	}

	public ServiceItemType findServiceItemTypeById(String id) {
		// TODO Auto-generated method stub
		Criteria c=this.createCriteria(ServiceItemType.class);
		c.add(Restrictions.eq("id", Long.parseLong(id)));
		ServiceItemType scit=(ServiceItemType) c.uniqueResult();
		return scit;
	}

	public ServiceItemType saveServiceItemType(ServiceItemType serviceItemType) {
		// TODO Auto-generated method stub
		ServiceItemType obj=(ServiceItemType) this.save(serviceItemType);
		return obj;
	}

	public Page findAllServiceItemType(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Criteria c=this.createCriteria(ServiceItemType.class);
		Page page=super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public Page findServiceItemTypeByName(String name, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Criteria c=this.createCriteria(ServiceItemType.class);
		if(name!=null&&name.length()>0){
			c.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		Page page=super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	
	public void removeServiceItemTypeById(Long id){
		ServiceItemType serviceItemType=this.findServiceItemTypeById(id.toString());
		Criteria c=this.createCriteria(SCIColumn.class);
		c.add(Restrictions.eq("serviceItemType",serviceItemType));
		List<SCIColumn> sCIColumns=c.list();
		for(SCIColumn sCIColumn:sCIColumns){
			this.remove(sCIColumn);
		}
		this.removeObject(ServiceItemType.class, id);
	}
	
	public void removeServiceItemTypeByIds(String[] ids){
		for(String id :ids){
			//this.removeObject(ServiceItemType.class, Long.parseLong(id));
			this.removeServiceItemTypeById(Long.parseLong(id));
		}
	}
}
