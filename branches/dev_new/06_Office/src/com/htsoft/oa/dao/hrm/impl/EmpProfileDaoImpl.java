package com.htsoft.oa.dao.hrm.impl;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.oa.dao.hrm.EmpProfileDao;
import com.htsoft.oa.model.hrm.EmpProfile;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class EmpProfileDaoImpl extends BaseDaoImpl<EmpProfile> implements
		EmpProfileDao {
	public EmpProfileDaoImpl() {
		super(EmpProfile.class);
	}

	public boolean checkProfileNo(String profileNo) {
		final String hql = "select count(*) from EmpProfile ep where ep.profileNo = ?";
		final String profNo = profileNo;
		Long count = (Long) getHibernateTemplate().execute(new HibernateCallback() {
			
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						/* 30 */Query query = session
								.createQuery(hql);
						/* 31 */query.setString(0, profNo);
						/* 32 */return query.uniqueResult();
					}
				});
		/* 35 */return count.longValue() == 0L;
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.dao.hrm.impl.EmpProfileDaoImpl JD-Core Version: 0.6.0
 */