package com.xpsoft.oa.dao.shop.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.shop.SpPaAssessmentcriteriaDao;
import com.xpsoft.oa.model.shop.SpPaAssessmentcriteria;

public class SpPaAssessmentcriteriaDaoImpl extends BaseDaoImpl<SpPaAssessmentcriteria>
	implements SpPaAssessmentcriteriaDao{
	public SpPaAssessmentcriteriaDaoImpl(){
		super(SpPaAssessmentcriteria.class);
	}
	/*
	 * 取得所有考核标准关键字和名称
	 * */
	public Map<String, String> getKeyAndName() {
		final String hql = "select acKey, acName from SpPaAssessmentcriteria";
		
		return (Map<String, String>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Map<String, String> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						List list = query.list();
						Map<String, String> map = new HashMap<String, String>();
						Iterator it = list.iterator();
						while(it.hasNext()) {
							Object[] obj = (Object[])it.next();
							map.put((String)obj[0], (String)obj[1]);
						}
						return map;
					}
				}
		);
	}
}
