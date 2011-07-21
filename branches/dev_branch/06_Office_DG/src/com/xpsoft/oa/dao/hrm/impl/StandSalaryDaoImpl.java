package com.xpsoft.oa.dao.hrm.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.Constants;
import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.StandSalaryDao;
import com.xpsoft.oa.model.hrm.StandSalary;

public class StandSalaryDaoImpl extends BaseDaoImpl<StandSalary> implements
		StandSalaryDao {
	public StandSalaryDaoImpl() {
		/* 22 */super(StandSalary.class);
	}

	public boolean checkStandNo(String standardNo) {
		final String hql = "select count(*) from StandSalary ss where ss.standardNo = ?";
		final String sdNo = standardNo;
		Long count = (Long) getHibernateTemplate().execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						/* 32 */Query query = session
								.createQuery(hql);
						/* 33 */query.setString(0, sdNo);
						/* 34 */return query.uniqueResult();
					}
				});
		/* 37 */return count.longValue() == 0L;
	}

	public List<StandSalary> findByPassCheck() {
		/* 45 */String hql = "from StandSalary vo where vo.status=?";
		/* 46 */Object[] objs = { Constants.FLAG_PASS };
		/* 47 */return findByHql(hql, objs);
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.oa.dao.hrm.impl.StandSalaryDaoImpl JD-Core Version: 0.6.0
 */