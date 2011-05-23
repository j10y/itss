package com.zsgj.dcit.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zsgj.dcit.dao.KnowledgeDao;
import com.zsgj.dcit.entity.Knowledge;
import com.zsgj.dcit.util.Pagination;

public class KnowledgeDaoImpl extends HibernateDaoSupport implements KnowledgeDao {

	public List getInfos(final int offset,final int length ,final Long columnType) {
		try {			
			List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
				 public Object doInHibernate(Session session)
				 throws HibernateException, SQLException {
						 try {
							String queryHql = " from Knowledge as knowledge "
								+" join knowledge.serviceItem si , "
								+" EventTypeServiceItem eventTypeServiceItem ,"
								+" EventType eventType"
								+" where eventTypeServiceItem.serviceItem.id=si.id "
								+" and eventTypeServiceItem.eventType=:eventType "
								+" and eventType.id=eventTypeServiceItem.eventType  "
								+" and knowledge.status=1"
								+" order by knowledge.id desc";
							if(columnType==0L){//如果columnType是0，代表要读取所有类型的数据							
								queryHql = " from Knowledge as knowledge "
								+" left  join knowledge.serviceItem si , "
								+" EventTypeServiceItem eventTypeServiceItem ,"
								+" EventType eventType"
								+" where eventTypeServiceItem.serviceItem.id=si.id "
								+" and eventType.id=eventTypeServiceItem.eventType  "
								+" and knowledge.status=1"
								+" order by knowledge.id desc";
							 }	
							 Query query = session.createQuery(queryHql);
							 query.setFirstResult(offset);
							 query.setMaxResults(length);
							 if(columnType!=0L){//如果columnType是0，代表要读取所有类型的数据	
								 query.setLong("eventType", columnType);
							 }
							 List list = query.list();
							 return list;
						} catch (Exception e) {
							e.printStackTrace();
						}
						 return null;
				 }
			
				});
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Knowledge getContentInfos(Long id) {
		try{
			Knowledge knowledge = (Knowledge) this.getHibernateTemplate().get(Knowledge.class, id);
			return knowledge;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public Pagination  getListInfo(final Pagination  pagination ,final Long columnType ) {
		
		try {			
			 this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
				 public Object doInHibernate(Session session)
				 throws HibernateException, SQLException {
					 
					String queryHql = " from Knowledge as knowledge "
							+" join knowledge.serviceItem si , "
							+" EventTypeServiceItem eventTypeServiceItem ,"
							+" EventType eventType"
							+" where eventTypeServiceItem.serviceItem.id=si.id "
							+" and eventTypeServiceItem.eventType=:eventType "
							+" and eventType.id=eventTypeServiceItem.eventType  "
							+" and knowledge.status=1"
							+" order by knowledge.id desc";
					
					String countHql = "select count(*) from Knowledge as knowledge "
							+" join knowledge.serviceItem si , "
							+" EventTypeServiceItem eventTypeServiceItem ,"
							+" EventType eventType"
							+" where eventTypeServiceItem.serviceItem.id=si.id "
							+" and eventTypeServiceItem.eventType=:eventType "
							+" and eventType.id=eventTypeServiceItem.eventType  "
							+" and knowledge.status=1";
					 
					 if(columnType==0L){//如果columnType是0，代表要读取所有类型的数据	
						queryHql =" from Knowledge as knowledge "
							+" left  join knowledge.serviceItem si , "
							+" EventTypeServiceItem eventTypeServiceItem ,"
							+" EventType eventType"
							+" where eventTypeServiceItem.serviceItem.id=si.id "
							+" and eventType.id=eventTypeServiceItem.eventType  "
							+" and knowledge.status=1"
							+" order by knowledge.id desc";
						
						countHql = "select count(*) from Knowledge as knowledge "
							+" left  join knowledge.serviceItem si , "
							+" EventTypeServiceItem eventTypeServiceItem ,"
							+" EventType eventType"
							+" where eventTypeServiceItem.serviceItem.id=si.id "
							+" and eventType.id=eventTypeServiceItem.eventType  "
							+" and knowledge.status=1";
					 }						
						 Query query = session.createQuery(queryHql);
						 query.setFirstResult(pagination.getFirstResult());
						 query.setMaxResults(pagination.getPageSize());
						 if(columnType!=0L){//如果columnType是0，代表要读取所有类型的数据	
							 query.setLong("eventType", columnType);
						 }
						 Query countQuery = session.createQuery(countHql);
						 if(columnType!=0L){//如果columnType是0，代表要读取所有类型的数据	
							 countQuery.setLong("eventType", columnType);
						 }
						 List<Knowledge> list =  query.list();
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
	public Pagination  getSearchInfo(final Pagination  pagination ,final Long columnType,final String keyValue ) {
		
		try {			
			this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
							
							String queryHql = " from Knowledge as knowledge "
								+" join knowledge.serviceItem si , "
								+" EventTypeServiceItem eventTypeServiceItem ,"
								+" EventType eventType"
								+" where eventTypeServiceItem.serviceItem.id=si.id "
								+" and eventTypeServiceItem.eventType=:eventType "
								+" and eventType.id=eventTypeServiceItem.eventType  "
								+" and knowledge.status=1" 
								+" and knowledge.summary like :summary "
								+" order by knowledge.id desc";
							
							String countHql = "select count(*) from Knowledge as knowledge "
								+" join knowledge.serviceItem si , "
								+" EventTypeServiceItem eventTypeServiceItem ,"
								+" EventType eventType"
								+" where eventTypeServiceItem.serviceItem.id=si.id "
								+" and eventTypeServiceItem.eventType=:eventType "
								+" and eventType.id=eventTypeServiceItem.eventType  "
								+" and knowledge.status=1"
								+" and knowledge.summary like :summary ";
							
							if(columnType==0L){//如果columnType是0，代表要读取所有类型的数据	
								queryHql =" from Knowledge as knowledge "
									+" left  join knowledge.serviceItem si , "
									+" EventTypeServiceItem eventTypeServiceItem ,"
									+" EventType eventType"
									+" where eventTypeServiceItem.serviceItem.id=si.id "
									+" and eventType.id=eventTypeServiceItem.eventType  "
									+" and knowledge.status=1"
									+" and knowledge.summary like :summary "
									+" order by knowledge.id desc";
								
								countHql = "select count(*) from Knowledge as knowledge "
									+" left  join knowledge.serviceItem si , "
									+" EventTypeServiceItem eventTypeServiceItem ,"
									+" EventType eventType"
									+" where eventTypeServiceItem.serviceItem.id=si.id "
									+" and eventType.id=eventTypeServiceItem.eventType  "
									+" and knowledge.status=1"
									+" and knowledge.summary like :summary ";
							}						
							Query query = session.createQuery(queryHql);
							query.setFirstResult(pagination.getFirstResult());
							query.setMaxResults(pagination.getPageSize());
							query.setString("summary", "%"+keyValue+"%");
							if(columnType!=0L){//如果columnType是0，代表要读取所有类型的数据	
								query.setLong("eventType", columnType);
							}
							Query countQuery = session.createQuery(countHql);
							if(columnType!=0L){//如果columnType是0，代表要读取所有类型的数据	
								countQuery.setLong("eventType", columnType);
							}
							countQuery.setString("summary", "%"+keyValue+"%");
							List<Knowledge> list =  query.list();
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
			Knowledge knowledge = (Knowledge) this.getHibernateTemplate().get(Knowledge.class, id);
			Long readTimes =  knowledge.getReadTimes();
			if(readTimes==null){
				readTimes=0L;
			}
			readTimes++;
			knowledge.setReadTimes(readTimes);
			this.getHibernateTemplate().update(knowledge);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	

}
