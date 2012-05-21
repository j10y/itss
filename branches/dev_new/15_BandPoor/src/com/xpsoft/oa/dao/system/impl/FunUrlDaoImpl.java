package com.xpsoft.oa.dao.system.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.system.FunUrlDao;
import com.xpsoft.oa.model.system.FunUrl;

public class FunUrlDaoImpl extends BaseDaoImpl<FunUrl> implements FunUrlDao {
	public FunUrlDaoImpl() {
		super(FunUrl.class);
	}

	public FunUrl getByPathFunId(String path, Long funId) {
		String hql = "from FunUrl fu where fu.urlPath=? and fu.appFunction.functionId=? ";
		return (FunUrl) findUnique(hql, new Object[] { path, funId });
	}

	public Set<String> getAdminDataSource() {
		final Set<String> urlSet = new TreeSet();
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from FunUrl";
				Query query = session.createQuery(hql);
				List<FunUrl> fuList = query.list();

				for (FunUrl fu : fuList) {
					urlSet.add(fu.getUrlPath());
				}
				return null;
			}
		});
		return urlSet;
	}

	public List<FunUrl> getByFunId(Long funId) {
		return null;
	}

}
