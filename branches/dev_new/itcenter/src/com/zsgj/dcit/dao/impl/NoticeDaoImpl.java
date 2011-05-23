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

import com.zsgj.dcit.dao.NoticeDao;
import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.util.Pagination;

public class NoticeDaoImpl extends HibernateDaoSupport implements NoticeDao  {
	

	public List<Notice> getInfos(final int offset,final int length ,final Long columnType) {
		try {
			final String queryHql = "from Notice as notice where " +
					" notice.newNoticeType =:newNoticeType  and " +
					" notice.auditflag = 1  order by notice.id  desc";
			List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
				 public Object doInHibernate(Session session)
				 throws HibernateException, SQLException {
					 	Date current= new Date();
						 Query query = session.createQuery(queryHql);
						 query.setLong("newNoticeType", columnType);
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

	public Notice getContentInfos(Long id) {
		try{
			Notice notice = (Notice) this.getHibernateTemplate().get(Notice.class, id);
			return notice;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public Pagination<Notice> getListInfo(final Pagination<Notice> pagination  ,final Long columnType) {
		
		try {
			final String queryHql = "from Notice as notice where " +
					" notice.newNoticeType =:newNoticeType  and " +
					" notice.auditflag = 1 order by notice.id  desc";
			final String countHql = "select count(*) from Notice as notice where " +
					" notice.newNoticeType =:newNoticeType  and notice.auditflag = 1";
			 this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
				 public Object doInHibernate(Session session)
				 throws HibernateException, SQLException {
					 	Date current= new Date();
						 Query query = session.createQuery(queryHql);
						 query.setLong("newNoticeType", columnType);
						 query.setFirstResult(pagination.getFirstResult());
						 query.setMaxResults(pagination.getPageSize());
						 Query countQuery = session.createQuery(countHql);
						 countQuery.setLong("newNoticeType", columnType);
						 List<Notice> list =  query.list();
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
			Notice notice = (Notice) this.getHibernateTemplate().get(Notice.class, id);
			Long readTimes =  notice.getReadTimes();
			if(readTimes==null){
				readTimes=0L;
			}
			readTimes++;
			notice.setReadTimes(readTimes);
			this.getHibernateTemplate().update(notice);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public Pagination<Notice> getSearchInfo(final Pagination<Notice> pagination,
			final	Long columnType, final String keyValue) {
		try {
			final String queryHql = "from Notice as notice where  " +
												" and notice.newNoticeType =:newNoticeType " +
												" and notice.title like :title  and" +
												" notice.auditflag = 1 order by notice.id  desc";
			final String countHql = "select count(*) from Notice as notice where " +
												" and notice.newNoticeType =:newNoticeType "+
												" and notice.title like :title  and notice.auditflag = 1" ;
			 this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
				 public Object doInHibernate(Session session)
				 throws HibernateException, SQLException {
					 	Date current= new Date();
						 Query query = session.createQuery(queryHql);
						 query.setLong("newNoticeType", columnType);
						 query.setString("title", "%"+keyValue+"%");
						 query.setFirstResult(pagination.getFirstResult());
						 query.setMaxResults(pagination.getPageSize());
						 Query countQuery = session.createQuery(countHql);
						 countQuery.setLong("newNoticeType", columnType);
						 countQuery.setString("title", "%"+keyValue+"%");
						 List<Notice> list =  query.list();
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

}
