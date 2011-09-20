package com.xpsoft.oa.dao.shop.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.shop.SpPaPisruleDao;
import com.xpsoft.oa.model.shop.SpPaPisrule;

public class SpPaPisruleDaoImpl extends BaseDaoImpl<SpPaPisrule>
	implements SpPaPisruleDao{
	public SpPaPisruleDaoImpl(){
		super(SpPaPisrule.class);
	}
	/*
	 * 通过外键pisId删除定量得分关联的规则
	 * */
	public void removeByPisId(long pisId) {
		final String hql = "delete from SpPaPisrule where pisId=?";
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
