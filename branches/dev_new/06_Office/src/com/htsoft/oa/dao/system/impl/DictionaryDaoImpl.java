package com.htsoft.oa.dao.system.impl;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.oa.dao.system.DictionaryDao;
import com.htsoft.oa.model.system.Dictionary;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class DictionaryDaoImpl extends BaseDaoImpl<Dictionary> implements
		DictionaryDao {
	public DictionaryDaoImpl() {
		/* 18 */super(Dictionary.class);
	}

	public List<String> getAllItems() {
		/* 23 */String hql = "select itemName from Dictionary group by itemName";
		/* 24 */return (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						/* 28 */Query query = session
								.createQuery("select itemName from Dictionary group by itemName");
						/* 29 */return query.list();
					}
				});
	}

	public List<String> getAllByItemName(String itemName) {
		final String hql = "select itemValue from Dictionary where itemName=?";
		final String item = itemName;
		return (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						/* 41 */Query query = session
								.createQuery(hql);
						/* 42 */query.setString(0, item);
						/* 43 */return query.list();
					}
				});
	}
}
