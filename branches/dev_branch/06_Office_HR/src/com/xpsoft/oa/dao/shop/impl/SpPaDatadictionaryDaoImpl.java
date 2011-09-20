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
import com.xpsoft.oa.dao.shop.SpPaDatadictionaryDao;
import com.xpsoft.oa.model.shop.SpPaDatadictionary;

public class SpPaDatadictionaryDaoImpl extends BaseDaoImpl<SpPaDatadictionary>
	implements SpPaDatadictionaryDao{
	public SpPaDatadictionaryDaoImpl(){
		super(SpPaDatadictionary.class);
	}
	/*
	 * 分类取得项目考核类型、项目考核频度、项目考核方式关键字
	 * */
	public Map<Long, String> getAllByParentId(long parentId){
		final String hql = "select id, name from SpPaDatadictionary where parentId=?";
		final long item = parentId;
		
		return (Map<Long, String>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setLong(0, item);
						List list = query.list();
						Map<Long, String> map = new HashMap<Long, String>();
						Iterator it = list.iterator();
						while(it.hasNext()) {
							Object[] obj = (Object[])it.next();
							map.put((Long)obj[0], (String)obj[1]);
						}
						return map;
					}
				}
		);
	}
}
