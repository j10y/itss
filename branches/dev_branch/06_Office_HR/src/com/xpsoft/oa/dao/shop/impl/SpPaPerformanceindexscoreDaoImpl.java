package com.xpsoft.oa.dao.shop.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.shop.SpPaPerformanceindexscoreDao;
import com.xpsoft.oa.model.shop.SpPaPerformanceindexscore;

public class SpPaPerformanceindexscoreDaoImpl extends BaseDaoImpl<SpPaPerformanceindexscore>
	implements SpPaPerformanceindexscoreDao{
	public SpPaPerformanceindexscoreDaoImpl(){
		super(SpPaPerformanceindexscore.class);
	}
	/*
	 * 通过外键piId批量删除考核项目关联的得分
	 * */
	public void removeByPiId(long piId) {
		final String hql = "delete from SpPaPerformanceindexscore where piId=?";
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
