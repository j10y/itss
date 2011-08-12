package com.xpsoft.oa.dao.kpi.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.kpi.HrPaPerformanceindexDao;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;

public class HrPaPerformanceindexDaoImpl extends BaseDaoImpl<HrPaPerformanceindex> 
	implements HrPaPerformanceindexDao{
	public HrPaPerformanceindexDaoImpl(){
		super(HrPaPerformanceindex.class);
	}
	
	public void saveToPublish(long piId) {
		final String hql = "update HrPaPerformanceindex set publishStatus=3 where id=?";
		final long item = piId;
		
		this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Integer doInHibernate(Session session) {
						Query query = session.createQuery(hql);
						query.setLong(0, item);
						return query.executeUpdate();
					}
				}
		);
	}
}
