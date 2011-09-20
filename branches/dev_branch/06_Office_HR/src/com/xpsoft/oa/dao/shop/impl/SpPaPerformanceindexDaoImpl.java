package com.xpsoft.oa.dao.shop.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.shop.SpPaPerformanceindexDao;
import com.xpsoft.oa.model.shop.SpPaPerformanceindex;

public class SpPaPerformanceindexDaoImpl extends BaseDaoImpl<SpPaPerformanceindex> 
	implements SpPaPerformanceindexDao{
	public SpPaPerformanceindexDaoImpl(){
		super(SpPaPerformanceindex.class);
	}
	/*
	 * 发布考核项目
	 * */
	public void saveToPublish(long piId) {
		final String hql = "update SpPaPerformanceindex set publishStatus=1 where id=?";
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
