package com.htsoft.oa.dao.customer.impl;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.oa.dao.customer.CusLinkmanDao;
import com.htsoft.oa.model.customer.CusLinkman;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class CusLinkmanDaoImpl extends BaseDaoImpl<CusLinkman> implements
		CusLinkmanDao {
	public CusLinkmanDaoImpl() {
		/* 20 */super(CusLinkman.class);
	}

	public boolean checkMainCusLinkman(Long customerId, Long linkmanId) {
		final StringBuffer hql = new StringBuffer(
				"select count(*) from CusLinkman  cl where cl.isPrimary = 1 and cl.customer.customerId =? ");
		if (linkmanId != null) {
			hql.append("and cl.linkmanId != ? ");
		}
		
		final Long custId = customerId;
		final Long linkMId =linkmanId;
		
		Long count = (Long) getHibernateTemplate().execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						/* 34 */Query query = session.createQuery(hql.toString());
						/* 35 */query.setLong(0,
								custId.longValue());
						/* 36 */if (linkMId != null) {
							/* 37 */query.setLong(1,linkMId.longValue());
						}
						/* 39 */return query.uniqueResult();
					}
				});
		/* 43 */return count.longValue() == 0L;
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.dao.customer.impl.CusLinkmanDaoImpl JD-Core Version: 0.6.0
 */