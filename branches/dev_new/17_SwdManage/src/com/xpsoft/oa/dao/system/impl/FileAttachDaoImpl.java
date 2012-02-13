package com.xpsoft.oa.dao.system.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.system.FileAttachDao;
import com.xpsoft.oa.model.system.FileAttach;

public class FileAttachDaoImpl extends BaseDaoImpl<FileAttach> implements
		FileAttachDao {
	public FileAttachDaoImpl() {
		super(FileAttach.class);
	}

	public void removeByPath(String filePath) {
		final String hql = "delete from FileAttach fa where fa.filePath = ?";
		final String fp = filePath;
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString(0, fp);
				return Integer.valueOf(query.executeUpdate());
			}
		});
	}

	public FileAttach getByPath(String filePath) {
		String hql = "from FileAttach fa where fa.filePath = ?";
		return (FileAttach) findUnique(hql, new Object[] { filePath });
	}
}
