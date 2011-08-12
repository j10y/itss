package com.xpsoft.oa.dao.kpi.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.kpi.HrPaPerformanceindexscoreDao;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;

public class HrPaPerformanceindexscoreDaoImpl extends BaseDaoImpl<HrPaPerformanceindexscore>
	implements HrPaPerformanceindexscoreDao{
	public HrPaPerformanceindexscoreDaoImpl(){
		super(HrPaPerformanceindexscore.class);
	}
	/*
	 * 通过外键piId批量删除考核项目关联的得分
	 * */
	public void removeByPiId(long piId) {
		final String hql = "delete from HrPaPerformanceindexscore where piId=?";
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
