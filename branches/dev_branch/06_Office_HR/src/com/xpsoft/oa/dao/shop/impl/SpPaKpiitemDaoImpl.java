package com.xpsoft.oa.dao.shop.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.shop.SpPaKpiitemDao;
import com.xpsoft.oa.model.shop.SpPaKpiitem;

public class SpPaKpiitemDaoImpl extends BaseDaoImpl<SpPaKpiitem>
	implements SpPaKpiitemDao{
	public SpPaKpiitemDaoImpl(){
		super(SpPaKpiitem.class);
	}
	
	public boolean findByPiIdAndPbcId(long piId, String pbcIds) {
		String hql = "select count(*) from SpPaKpiitem where pi.id= "
				+piId + " and pbc.id in (" + pbcIds + ") and pbc.publishStatus = 3";
		
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
