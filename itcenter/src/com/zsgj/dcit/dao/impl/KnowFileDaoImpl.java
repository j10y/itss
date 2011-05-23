package com.zsgj.dcit.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zsgj.dcit.dao.KnowFileDao;
import com.zsgj.dcit.entity.KnowFile;
import com.zsgj.dcit.util.Pagination;

public class KnowFileDaoImpl extends HibernateDaoSupport implements KnowFileDao  {
	

	public List<KnowFile> getInfos(final int offset,final int length ,final Long columnType) {
		try {
			final String queryHql = "from KnowFile as knowFile where knowFile.status =1 and knowFile.knowFileType =:knowFileType  order by id  desc";
			List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
				 public Object doInHibernate(Session session)
				 throws HibernateException, SQLException {
					 	Date current= new Date();
						 Query query = session.createQuery(queryHql);
						 query.setLong("knowFileType", columnType);
						 query.setFirstResult(offset);
						 query.setMaxResults(length);
						 List list = query.list();
						 return list;
				 }
				});
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public KnowFile getContentInfos(Long id) {
		try{
			KnowFile knowFile = (KnowFile) this.getHibernateTemplate().get(KnowFile.class, id);
			return knowFile;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public Pagination<KnowFile> getListInfo(final Pagination<KnowFile> pagination  ,final Long columnType) {
		
		try {
			final String queryHql = "from KnowFile as knowFile where knowFile.status =1 and knowFile.knowFileType =:knowFileType  order by id  desc";
			final String countHql = "select count(*) from KnowFile as knowFile where knowFile.status =1 and knowFile.knowFileType =:knowFileType ";
			 this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
				 public Object doInHibernate(Session session)
				 throws HibernateException, SQLException {
					 	Date current= new Date();
						 Query query = session.createQuery(queryHql);
						 query.setLong("knowFileType", columnType);
						 query.setFirstResult(pagination.getFirstResult());
						 query.setMaxResults(pagination.getPageSize());
						 Query countQuery = session.createQuery(countHql);
						 countQuery.setLong("knowFileType", columnType);
						 List<KnowFile> list =  query.list();
						 Long count=(Long) countQuery.uniqueResult();
						 pagination.setData(list);
						 pagination.setPageCount(count.intValue());
						 return list;
				 }
				});
			
			return pagination;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Pagination<KnowFile> getSearchInfo(final Pagination<KnowFile> pagination  ,final Long columnType,final String keyValue) {
		
		try {
			final String queryHql = "from KnowFile as knowFile where knowFile.status =1 " +
												"and knowFile.knowFileType =:knowFileType  " +
												"and knowFile.name like:name  " +
												"order by id  desc";
			final String countHql = "select count(*) from KnowFile as knowFile where knowFile.status =1 " +
												"and knowFile.knowFileType =:knowFileType "+
												"and knowFile.name like:name  " ;
			this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
							Date current= new Date();
							Query query = session.createQuery(queryHql);
							query.setLong("knowFileType", columnType);
							query.setString("name", "%"+keyValue+"%");
							query.setFirstResult(pagination.getFirstResult());
							query.setMaxResults(pagination.getPageSize());
							Query countQuery = session.createQuery(countHql);
							countQuery.setLong("knowFileType", columnType);
							countQuery.setString("name", "%"+keyValue+"%");
							List<KnowFile> list =  query.list();
							Long count=(Long) countQuery.uniqueResult();
							pagination.setData(list);
							pagination.setPageCount(count.intValue());
							return list;
						}
					});
			
			return pagination;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateReadTimes(Long id) {		
		try{
			KnowFile knowFile = (KnowFile) this.getHibernateTemplate().get(KnowFile.class, id);
			Long readTimes =  knowFile.getReadTimes();
			if(readTimes==null){
				readTimes=0L;
			}
			readTimes++;
			knowFile.setReadTimes(readTimes);
			this.getHibernateTemplate().update(knowFile);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	

}
