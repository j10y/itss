package com.xpsoft.oa.dao.kpi.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiitemDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;

public class HrPaKpiitemDaoImpl extends BaseDaoImpl<HrPaKpiitem>
	implements HrPaKpiitemDao{
	public HrPaKpiitemDaoImpl(){
		super(HrPaKpiitem.class);
	}
	
	public boolean findByPiIdAndPbcId(long piId, String[] pbcIds) {
		String hql = "select count(*) from HrPaKpiitem where pi.id= "
				+piId + " and pbc.id in (";
		hql += pbcIds[0];
		for(int i = 1; i < pbcIds.length; i++) {
			hql += "," + pbcIds[i];
		}
		hql += ")";
		
		final String hql2 = hql;
		
		long flag = (Long)this.getHibernateTemplate().execute(
			new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createQuery(hql2);
					return query.list().get(0);
				}
			}
		);
		return flag > 0 ? true : false;
	}
}
