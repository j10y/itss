package com.digitalchina.itil.event.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.workflow.entity.WorkflowRecordTaskInfo;
import com.digitalchina.itil.actor.entity.SupportGroup;
import com.digitalchina.itil.actor.entity.SupportGroupEngineer;
import com.digitalchina.itil.actor.entity.SupportGroupRank;
import com.digitalchina.itil.actor.entity.SupportGroupServiceItem;
import com.digitalchina.itil.event.dao.EventDao;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventAssign;
import com.digitalchina.itil.event.entity.EventAuditHis;
import com.digitalchina.itil.event.entity.EventRelation;
import com.digitalchina.itil.event.entity.EventRelationType;
import com.digitalchina.itil.event.entity.EventStatus;
import com.digitalchina.itil.event.entity.EventSulotion;
import com.digitalchina.itil.event.entity.EventType;
import com.digitalchina.itil.event.entity.EventTypeServiceItem;
import com.digitalchina.itil.event.entity.ProblemSort;
import com.digitalchina.itil.knowledge.entity.KnowProblemType;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.train.entity.Quest;
import com.digitalchina.itil.train.entity.QuestResult;
import com.digitalchina.itil.train.entity.Survey;
import com.digitalchina.itil.train.entity.SurveyType;

public class EventDaoImpl extends BaseDao<Event> implements EventDao {

	@SuppressWarnings("unchecked")
	public List<Event> selectSameAndChild(Event event) throws DaoException {
		List<Event> events = new ArrayList<Event>();
		try {
			Criteria c = createCriteria(EventRelation.class);
			c.add(Restrictions.eq("event", event))
			.setFetchMode("eventRelationType", FetchMode.JOIN)
			.setFetchMode("parentEvent", FetchMode.JOIN)
			.createAlias("eventRelationType", "type")
			.add(Restrictions.in("type.typeFlag",new Object[] {EventRelationType.SAME,EventRelationType.PARENT}))
			.setProjection(Projections.property("parentEvent"));
			events = c.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
		return events;
	}

	public void deleteDoubleRel(String relId) {
		try {
			Criteria c = createCriteria(EventRelation.class);
			c.setFetchMode("event", FetchMode.JOIN);
			c.setFetchMode("parentEvent", FetchMode.JOIN);
			c.add(Restrictions.eq("id", Long.valueOf(relId)));
			EventRelation curTemp = (EventRelation) c.uniqueResult();
			Criteria c2 = createCriteria(EventRelation.class);
			c2.add(Restrictions.eq("event.id", curTemp.getParentEvent().getId()));
			c2.add(Restrictions.eq("parentEvent.id", curTemp.getEvent().getId()));
			EventRelation curTemp2 = (EventRelation) c2.uniqueResult();
			remove(curTemp);
			remove(curTemp2);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}

	}

	public Page selectEventRelByCurrEvent(Event event, int pageNo, int pageSize) {
		try {
			Criteria c = super.createCriteria(EventRelation.class);
			c.add(Restrictions.eq("event", event));
			Page page =super.pagedQuery(c, pageNo, pageSize);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Survey selectEventSurvey() {
		try {
			SurveyType surveyType = (SurveyType) super.createCriteria(SurveyType.class)
					.setFetchMode("systemMainTable", FetchMode.JOIN).createAlias(
							"this.systemMainTable", "systemMainTable").add(
							Restrictions.eq("systemMainTable.id", new Long("430"))).uniqueResult();
			Criteria criteria = super.createCriteria(Survey.class).setFetchMode("surveyType",
					FetchMode.JOIN).add(Restrictions.eq("this.surveyType.id", surveyType.getId()))
					.add(Restrictions.eq("deployFlag", new Integer("1")));
			Survey survey = (Survey) criteria.list().get(0);
			return survey;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public boolean selectIsExist(Long currEventId, Long parentEventId) {
		try {
			Criteria c = super.createCriteria(EventRelation.class);
			c.add(Restrictions.eq("event.id", currEventId));
			c.add(Restrictions.eq("parentEvent.id", parentEventId));
			List list = c.list();
			return !list.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public boolean selectIsUserFeedbackOrNot(Long userInfoId, Long objId, Long surveyId) {
		try {
			List<QuestResult> questResultList = createCriteria(QuestResult.class)
													.setFetchMode("userId", FetchMode.JOIN)
													.setFetchMode("survey", FetchMode.JOIN)
													.createAlias("this.userId", "userInfo")
													.add(Restrictions.eq("userInfo.id", userInfoId))
													.createAlias("this.survey", "survey")
													.add(Restrictions.eq("survey.id", surveyId))
													.add(Restrictions.eq("objId", objId)).list();
			if (questResultList != null && questResultList.size() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public EventAssign selectLatestEventAssign(Long eventId) {
		try {
			List<EventAssign> eventAssigns = createCriteria(EventAssign.class)
												.add(Restrictions.eq("event.id", eventId))
												.addOrder(Order.desc("id")).list();
			EventAssign eventAssign = eventAssigns.get(0);
			return eventAssign;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<Quest> selectQuest(Long surveyId) {
		try {
			List<Quest> questList = null;
			Criteria c = super.getCriteria(Quest.class);
			c.setFetchMode("survey", FetchMode.JOIN);
			c.createAlias("survey", "survey");
			c.add(Restrictions.eq("survey.id", surveyId));
			c.addOrder(Property.forName("questType").asc());
			questList = c.list();
			return questList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}


	public Page selectUnFinishEvents(String summary, int pageNo, int pageSize) {
		try {
			Criteria c = super.createCriteria(Event.class);
			if(summary!=null&&!summary.equals("")){
				c.add(Restrictions.ilike("summary", summary, MatchMode.ANYWHERE));
			}
			c.createAlias("this.eventStatus", "eventStatus").setFetchMode("eventStatus",FetchMode.JOIN);
			c.add(Restrictions.ne("eventStatus.keyword", EventStatus.FINISH));
			c.add(Restrictions.isNotNull("dealuser"));
			Page page =super.pagedQuery(c, pageNo, pageSize);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public void updateDealerOfEvent(Long eventId, UserInfo userInfo) {
		try {
			String hql = "update Event set dealuser = ? where id = ?";
			getHibernateTemplate().bulkUpdate(hql, new Object[] { userInfo, eventId });
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<ServiceItem> selectAllServiceItemByEventType(String eventtype) {
		try {
			List<ServiceItem> siList=new ArrayList<ServiceItem>();
			if(eventtype!=null){
				DetachedCriteria etdCriteria = DetachedCriteria.forClass(EventTypeServiceItem.class)
								.add(Restrictions.eq("eventType.id", Long.parseLong(eventtype)))
								.setProjection(Property.forName("serviceItem.id"));
				Criteria  criteria=super.createCriteria(ServiceItem.class);
				Disjunction dis = Restrictions.disjunction();
				dis.add(Subqueries.propertyIn("id", etdCriteria));
				criteria.add(dis);
				siList=criteria.list();
			}
			return siList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page selectAllEventTypeByName(String name,int pageNo, int pageSize) {
		try {
			Criteria c = super.createCriteria(EventType.class);
			c.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
			Page page =super.pagedQuery(c, pageNo, pageSize);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	public List<EventTypeServiceItem> selectEventTypeServiceItem(String eventTypeId) {
		Criteria c = createCriteria(EventTypeServiceItem.class);
		EventType eventType = new EventType();
		eventType.setId(Long.parseLong(eventTypeId));
		c.add(Restrictions.eq("eventType", eventType));
		return c.list();
	}

	public List<ServiceItem> selectAllServiceItem(String official) {
		Criteria c = createCriteria(ServiceItem.class);
		if(official!=null&&"".equals(official)==false){
			c.add(Restrictions.eq("official", Integer.valueOf(official)));
		}
		return c.list();
	}

	public List<String> selectSIinEventTypeServiceItem(Long[] serviceItemsId,Long dataId) {
		try {
			Criteria c=createCriteria(EventTypeServiceItem.class)
					.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN)
					.add(Restrictions.in("serviceItem.id", serviceItemsId))
					.setProjection(Projections.property("serviceItem.name"))
					.setProjection(Projections.distinct(Projections.property("serviceItem.name")));
			if(dataId!=null){
				c.add(Restrictions.ne("eventType.id", dataId));
			}
					return c.list();
	} catch (Exception e) {
		e.printStackTrace();
		throw new DaoException(e.getMessage());
	}
	}

	public void deleteAllEventTypeServiceItem(Long dataId) {
		try {
			String hql="delete from EventTypeServiceItem where eventType.id =?";
			getHibernateTemplate().bulkUpdate(hql,dataId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
		
	}

	public Page selectAllServiceItemEventType(
			String typeName, String serviceItemId,int pageNo, int pageSize) {
		try {
			Criteria c=createCriteria(EventTypeServiceItem.class)
					.createAlias("this.eventType", "eventType").setFetchMode("eventType",FetchMode.JOIN)
					.add(Restrictions.ilike("eventType.name", typeName, MatchMode.ANYWHERE))
			        .addOrder(Property.forName("serviceItem").asc());
			if(serviceItemId!=null&&"".equals(serviceItemId)==false){
				c.add(Restrictions.eq("serviceItem.id", Long.parseLong(serviceItemId)));
			}
			Page page =super.pagedQuery(c, pageNo, pageSize);
					return page;
	} catch (Exception e) {
		e.printStackTrace();
		throw new DaoException(e.getMessage());
	}
	}

	public Page selectAllEngineersByServiceItem(String name,
			String serviceItemId,int pageNo, int pageSize) {
		try {
			DetachedCriteria etdCriteria = DetachedCriteria.forClass(SupportGroupServiceItem.class)
							.add(Restrictions.eq("serviceItem.id", Long.parseLong(serviceItemId)))
							.setProjection(Property.forName("supportGroup.id"));
			Criteria  criteria=super.createCriteria(SupportGroupEngineer.class)
							.createAlias("this.userInfo", "userInfo").setFetchMode("userInfo",FetchMode.JOIN)
							.add(Restrictions.or(Restrictions.ilike("userInfo.realName", name, MatchMode.ANYWHERE),
									Restrictions.ilike("userInfo.userName", name, MatchMode.ANYWHERE)));
			Disjunction dis = Restrictions.disjunction();
			dis.add(Subqueries.propertyIn("supportGroup.id", etdCriteria));
			criteria.add(dis)
					.setProjection(Property.forName("userInfo"));
			Page page =super.pagedQuery(criteria, pageNo, pageSize);
			return page;
	} catch (Exception e) {
		e.printStackTrace();
		throw new DaoException(e.getMessage());
	}
	}

	public Page selectServiceItemByEventType(String eventTypeId,
			String serviceName,String official, int pageNo, int pageSize) {
		try {
			Criteria c=createCriteria(EventTypeServiceItem.class)
					.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN)
					.add(Restrictions.ilike("serviceItem.name", serviceName, MatchMode.ANYWHERE))
			        .setProjection(Property.forName("serviceItem"));
			if(eventTypeId!=null&&"".equals(eventTypeId)==false){
				c.add(Restrictions.eq("eventType.id", Long.parseLong(eventTypeId)));
			}
			if(official!=null&&"".equals(official)==false){
				c.add(Restrictions.eq("serviceItem.official", Integer.parseInt(official)));
			}
			Page page =super.pagedQuery(c, pageNo, pageSize);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page selectproblemTypeByServiceItem(String siTypeName,
			Long serviceItemId, int pageNo, int pageSize) {
		try {
			Criteria c=createCriteria(KnowProblemType.class)
					.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN)
					.add(Restrictions.ilike("name", siTypeName, MatchMode.ANYWHERE));
			if(serviceItemId!=null){
				c.add(Restrictions.eq("serviceItem.id", serviceItemId));
			}
			Page page =super.pagedQuery(c, pageNo, pageSize);
			return page;
	} catch (Exception e) {
		e.printStackTrace();
		throw new DaoException(e.getMessage());
	}
	}

	public Page selectServiceItemByGroupLeader(String name, Integer official,Long userId,String adminFlag,
			int pageNo, int pageSize) {
		try {
			if("yes".equals(adminFlag)){//为管理员，返回所有可用服务项
				Criteria  criteria=super.createCriteria(ServiceItem.class)
							.add(Restrictions.eq("official", official))
							.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
				Page page =super.pagedQuery(criteria, pageNo, pageSize);
				return page;
			}else{
				Criteria  c=super.createCriteria(SupportGroup.class)
									.add(Restrictions.eq("groupLeader.id", userId))
									.add(Restrictions.eq("deleteFlag", Integer.valueOf(0)));
				List<SupportGroup> slist=c.list();
				if(slist==null||slist.size()==0){//不是支持组组长
					return new Page();
				}
				Criteria  criteria=super.createCriteria(SupportGroupServiceItem.class)
							.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN)
							.add(Restrictions.eq("serviceItem.official", official))
							.add(Restrictions.ilike("serviceItem.name", name, MatchMode.ANYWHERE));
				criteria.add(Restrictions.in("supportGroup", slist));
				criteria.setProjection(Property.forName("serviceItem"));
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				Page page =super.pagedQuery(criteria, pageNo, pageSize);
				return page;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page selectAllEventByParams(String summary, String eventCisn,
			String createUser, String dealUser, String submitUser,
			String eventStatusId, int pageNo, String supportGroupId,
			int pageSize) {
		Criteria  criteria=super.createCriteria(Event.class).addOrder(Order.desc("id"));
		if(eventStatusId!=null&&"".equals(eventStatusId.trim())==false){
			criteria.add(Restrictions.eq("eventStatus.id", Long.valueOf(eventStatusId)));
		}
		if(supportGroupId!=null&&"".equals(supportGroupId.trim())==false){
			criteria.add(Restrictions.eq("supportGroup.id", Long.valueOf(supportGroupId)));
		}
		if(summary!=null&&"".equals(summary.trim())==false){
			criteria.add(Restrictions.ilike("summary", summary, MatchMode.ANYWHERE));
		}
		if(eventCisn!=null&&"".equals(eventCisn.trim())==false){
			criteria.add(Restrictions.ilike("eventCisn", eventCisn, MatchMode.ANYWHERE));
		}
		if(createUser!=null&&"".equals(createUser.trim())==false){
			criteria.createAlias("this.createUser", "createUser").setFetchMode("createUser",FetchMode.JOIN);
			criteria.add(Restrictions.or(Restrictions.ilike("createUser.realName", createUser, MatchMode.ANYWHERE),
					Restrictions.ilike("createUser.userName", createUser, MatchMode.ANYWHERE)));
		}
		if(dealUser!=null&&"".equals(dealUser.trim())==false){
			criteria.createAlias("this.dealuser", "dealuser").setFetchMode("dealuser",FetchMode.JOIN);
			criteria.add(Restrictions.or(Restrictions.ilike("dealuser.realName", dealUser, MatchMode.ANYWHERE),
					Restrictions.ilike("dealuser.userName", dealUser, MatchMode.ANYWHERE)));
		}
		if(submitUser!=null&&"".equals(submitUser.trim())==false){
			criteria.createAlias("this.submitUser", "submitUser").setFetchMode("submitUser",FetchMode.JOIN);
			criteria.add(Restrictions.or(Restrictions.ilike("submitUser.realName", submitUser, MatchMode.ANYWHERE),
					Restrictions.ilike("submitUser.userName", submitUser, MatchMode.ANYWHERE)));
		}
		Page page =super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}

	public Page selectAllProblemSort(String id,String typeName, int pageNo, int pageSize) {
		Criteria criteria=super.createCriteria(ProblemSort.class)
				.add(Restrictions.ilike("name", typeName, MatchMode.ANYWHERE));
		if(id!=null&&"".equals(id.trim())==false){
			criteria.add(Restrictions.eq("id", Long.valueOf(id)));
		}
		Page page =super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}
	
	public Long selectTaskId(String dataId, String nodeId, String processId) {
		Criteria criteria=super.createCriteria(WorkflowRecordTaskInfo.class)
		.add(Restrictions.eq("dataId", Long.valueOf(dataId)))
		.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)))
		.add(Restrictions.eq("processInstanceId", Long.valueOf(processId)))
		.setProjection(Projections.property("taskId"));
		return (Long) criteria.uniqueResult();
	}

	public List<EventAuditHis> selectAllEventHistory(Event event,
			String processId) {
		Criteria criteria = super.getCriteria(EventAuditHis.class)
		.add(Restrictions.eq("processId", Long.valueOf(processId)))
		.add(Restrictions.eq("event", event))
		.addOrder(Order.asc("approverDate"));
		return criteria.list();
	}

	public Long selectTaskIdByDataId(String dataId,String processId) {
		Criteria c=super.createCriteria(WorkflowRecordTaskInfo.class)
		.add(Restrictions.eq("dataId", Long.valueOf(dataId)))
		.add(Restrictions.eq("nodeId", Long.valueOf("888")))
		.add(Restrictions.eq("processInstanceId", Long.valueOf(processId)))
		.setProjection(Projections.property("taskId"));
		return (Long) c.uniqueResult();
	}


}
