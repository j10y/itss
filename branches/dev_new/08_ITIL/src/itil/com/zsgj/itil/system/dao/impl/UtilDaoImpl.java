package com.zsgj.itil.system.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.system.dao.UtilDao;

public class UtilDaoImpl extends BaseDao implements UtilDao{

	
	public Page searchComboMessage(Class clazz, String propertyName, Object value, boolean isLike, String orderBy, boolean isAsc,
			int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Criteria criteria = super.createCriteria(clazz);
		if(isLike){
			criteria.add(Restrictions.ilike(propertyName, value.toString(), MatchMode.ANYWHERE));
		}else{
			criteria.add(Restrictions.eq(propertyName, value));
		}
		
		if(orderBy!=null && !"".equals(orderBy)){
			if(isAsc){
				criteria.addOrder(Order.asc(orderBy));
			}else{
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		return super.pagedQuery(criteria, pageNo, pageSize);
	}

}
