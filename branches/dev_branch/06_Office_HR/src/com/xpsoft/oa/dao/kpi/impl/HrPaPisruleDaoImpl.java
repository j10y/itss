package com.xpsoft.oa.dao.kpi.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.kpi.HrPaPisruleDao;
import com.xpsoft.oa.model.kpi.HrPaPisrule;

public class HrPaPisruleDaoImpl extends BaseDaoImpl<HrPaPisrule>
	implements HrPaPisruleDao{
	public HrPaPisruleDaoImpl(){
		super(HrPaPisrule.class);
	}
	
	public void removeByPisId(long pisId) {
		final String hql = "delete from HrPaPisrule where pisId=?";
		final long item = pisId;
		
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
