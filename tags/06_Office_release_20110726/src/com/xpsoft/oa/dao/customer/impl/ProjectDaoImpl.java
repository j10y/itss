package com.xpsoft.oa.dao.customer.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.customer.ProjectDao;
import com.xpsoft.oa.model.customer.Project;

@SuppressWarnings("unchecked")
public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao {
	public ProjectDaoImpl() {
		super(Project.class);
	}

	public boolean checkProjectNo(String projectNo) {
		final String hql = "select count(*) from Project p where p.projectNo = ?";
		final String prjNo = projectNo;
		Long count = (Long) getHibernateTemplate().execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						/* 30 */Query query = session.createQuery(hql);
						/* 31 */query.setString(0, prjNo);
						/* 32 */return query.uniqueResult();
					}
				});
		return count.longValue() == 0L;
	}
}
