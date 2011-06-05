package com.zsgj.itil.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.actor.entity.SupportGroupServiceItem;
import com.zsgj.itil.event.entity.EventSulotion;
import com.zsgj.itil.event.entity.EventTypeServiceItem;
import com.zsgj.itil.knowledge.dao.KnowledgeDao;
import com.zsgj.itil.knowledge.entity.KnowContractAuditHis;
import com.zsgj.itil.knowledge.entity.KnowFileAuditHis;
import com.zsgj.itil.knowledge.entity.KnowProblemType;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.knowledge.entity.KnowledgeAuditHis;
import com.zsgj.itil.service.entity.ServiceItem;

public class KnowledgeDaoImpl extends BaseDao<Knowledge> implements
		KnowledgeDao {
	public void updateSolutionUseTime(Long id) {
		try {
			String hql = "update Knowledge k set k.useTime = k.useTime+1 where k.id = ?";
			getHibernateTemplate().bulkUpdate(hql, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Long selectProcessIdOfLatestProcess(Long kId, Long kType) {
		KnowFileAuditHis knowFileAuditHis = null;
		KnowledgeAuditHis knowledgeAuditHis = null;
		KnowContractAuditHis knowContractAuditHis = null;
		if(kType.equals(new Long("1"))){//文件类型
			knowFileAuditHis = (KnowFileAuditHis) super.createCriteria(KnowFileAuditHis.class)
				.add(Restrictions.eq("this.knowFile.id", kId))
				.addOrder(Order.desc("approverDate"))
				.list().get(0);
			return knowFileAuditHis.getProcessId();
		}else if(kType.equals(new Long("2"))){//解决方案
			knowledgeAuditHis = (KnowledgeAuditHis) super.createCriteria(KnowledgeAuditHis.class)
				.add(Restrictions.eq("this.knowledge.id", kId))
				.addOrder(Order.desc("approverDate"))
				.list().get(0);
			return knowledgeAuditHis.getProcessId();
		}else{//合同
			knowContractAuditHis = (KnowContractAuditHis) super.createCriteria(KnowContractAuditHis.class)
				.add(Restrictions.eq("this.knowContract.id", kId))
				.addOrder(Order.desc("approverDate"))
				.list().get(0);
			return knowContractAuditHis.getProcessId();
		}
	}
	public Knowledge selectKnowledgeByEventId(Long eventId) throws DaoException {
		try {
			return (Knowledge) createCriteria(EventSulotion.class)
			.add(Restrictions.eq("event.id",eventId))
			.setProjection(Projections.property("knowledge"))
			.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
			
		}
	}

	public Page selectKnowledgeBySiId(String serviceItemId,String[] summary,int pageNo, int pageSize) {
		try {
			Criteria criteria=super.createCriteria(Knowledge.class);
			if(serviceItemId!=null&&"".equals(serviceItemId)==false){
				criteria.add(Restrictions.eq("serviceItem.id",Long.parseLong(serviceItemId)));
			}
			// 2010-07-16 modified by huzh for 问题名称过滤（多关键字） begin
			for(int i=0;i<summary.length;i++){
				criteria.add(Restrictions.ilike("summary", summary[i].trim(), MatchMode.ANYWHERE));
			}
			criteria.add(Restrictions.eq("status",Integer.valueOf(1)));
			// 2010-07-16 modified by huzh for 问题名称过滤（多关键字） end
			Page page =super.pagedQuery(criteria, pageNo, pageSize);
			return page;//所有审批通过的
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
			
		}
	}

	public void updateProblmeTypesStatus(Long[] problemTypesId, int delete_false) {
    try {
			StringBuilder hql=new StringBuilder();
			hql.append("update KnowProblemType kpt set kpt.deleteFlag = :del");
			hql.append(" where kpt.id in ( :kpt_Ids )");
			createQuery(hql.toString())
			.setInteger("del", delete_false)
			.setParameterList("kpt_Ids", problemTypesId)
			.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page selectKnowledgeByEventType(String eventtypeId, int pageNo, int pageSize) {
		try {
				DetachedCriteria etCriteria=DetachedCriteria.forClass(EventTypeServiceItem.class)
					.setProjection(Property.forName("serviceItem"));
				if(eventtypeId!=null&&!"".equals(eventtypeId)){
					etCriteria.add(Restrictions.eq("eventType.id", Long.parseLong(eventtypeId)));
				}
				Criteria criteria=super.createCriteria(Knowledge.class)
						  .add(Restrictions.eq("status", Knowledge.STATUS_FINISHED));
				if(eventtypeId!=null&&!"".equals(eventtypeId)){
					Disjunction dis = Restrictions.disjunction();
					dis.add(Subqueries.propertyIn("serviceItem", etCriteria));
					criteria.add(dis);
				}
				Page page =super.pagedQuery(criteria, pageNo, pageSize);
				return page;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<SupportGroupEngineer> selectSupportGroupByEngineer(
			UserInfo userInfo) {
		try {
			Criteria criteria=super.createCriteria(SupportGroupEngineer.class);
			criteria.createAlias("this.supportGroup", "supportGroup").setFetchMode("supportGroup",FetchMode.JOIN);
			criteria.add(Restrictions.eq("userInfo",userInfo));
			criteria.add(Restrictions.eq("supportGroup.deleteFlag",Integer.valueOf(0)));//过滤掉已删除的
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
			
		}
	}

	public List<SupportGroupServiceItem> selectServiceItemInSupportGroup(Long serviceItemId,UserInfo userInfo) {
		try {
			Criteria criteria=super.createCriteria(SupportGroupServiceItem.class);
			criteria.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN);
			criteria.createAlias("this.supportGroup", "supportGroup").setFetchMode("supportGroup",FetchMode.JOIN);
			criteria.add(Restrictions.eq("serviceItem.id",serviceItemId));
			criteria.add(Restrictions.eq("supportGroup.deleteFlag",Integer.valueOf(0)));
			criteria.add(Restrictions.eq("supportGroup.groupLeader",userInfo));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
			
		}
	}

	public Page selectAllProblemType(UserInfo userInfo, String adminFlag,
			String name, String serviceItem, int start, int pageSize) {
		try {
			if("yes".equals(adminFlag)){//为管理员，返回所有问题类型
				Criteria  criteria=super.createCriteria(KnowProblemType.class)
				.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
				if(serviceItem!=null&&"".equals(serviceItem)==false){
					criteria.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN);
					criteria.add(Restrictions.eq("serviceItem.official", Integer.valueOf(1)));
					criteria.add(Restrictions.eq("serviceItem.id", Long.valueOf(serviceItem)));
				}
				Page page =super.pagedQuery(criteria, start, pageSize);
				return page;
			}else{
				Criteria  c=super.createCriteria(SupportGroup.class)
									.add(Restrictions.eq("groupLeader", userInfo))
									.add(Restrictions.eq("deleteFlag", Integer.valueOf(0)));
				List<SupportGroup> slist=c.list();//查出以当前登录人为组长的所有支持组
				if(slist==null||slist.size()==0){//不是支持组组长
					return new Page();
				}
				Criteria  criteria=super.createCriteria(SupportGroupServiceItem.class)
							.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN)
							.add(Restrictions.eq("serviceItem.official", Integer.valueOf(1)));
				criteria.add(Restrictions.in("supportGroup", slist));
				criteria.setProjection(Property.forName("serviceItem"));
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				List<ServiceItem> sIlist=criteria.list();//根据支持组查出所有服务项
				if(sIlist==null||sIlist.size()==0){//没有服务项
					return new Page();
				}
				Criteria  typeCriteria=super.createCriteria(KnowProblemType.class)
					.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE))
					.add(Restrictions.in("supportGroup", slist));
				if(serviceItem!=null&&"".equals(serviceItem)==false){
					typeCriteria.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN);
					typeCriteria.add(Restrictions.eq("serviceItem.id", Long.valueOf(serviceItem)));
				}
				Page page =super.pagedQuery(typeCriteria, start, pageSize);
				return page;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	
}
