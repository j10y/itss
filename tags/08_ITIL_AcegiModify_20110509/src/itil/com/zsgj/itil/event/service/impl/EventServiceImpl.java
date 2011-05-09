package com.zsgj.itil.event.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.entity.UserRole;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.actor.entity.SupportGroupServiceItem;
import com.zsgj.itil.event.dao.EventDao;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventAssign;
import com.zsgj.itil.event.entity.EventAuditHis;
import com.zsgj.itil.event.entity.EventRelation;
import com.zsgj.itil.event.entity.EventRelationType;
import com.zsgj.itil.event.entity.EventStatus;
import com.zsgj.itil.event.entity.EventSulotion;
import com.zsgj.itil.event.entity.EventType;
import com.zsgj.itil.event.entity.EventTypeServiceItem;
import com.zsgj.itil.event.entity.Problem;
import com.zsgj.itil.event.service.EventService;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.train.entity.Quest;
import com.zsgj.itil.train.entity.Survey;

public class EventServiceImpl extends BaseService implements EventService {
	private EventDao eventDao;

	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}

	public void removeDoubleRel(String relId) {
		try {
			eventDao.deleteDoubleRel(relId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void modifyEventRelation(String relId, Long currEventId, Long parentEventId,
			Long eventRelationTypeId) {

		try {
			eventDao.deleteDoubleRel(relId);

			Event currentEvent = new Event();
			currentEvent.setId(currEventId);

			Event parentEvent = new Event();
			parentEvent.setId(parentEventId);

			EventRelationType eventRelationType = (EventRelationType) eventDao.getObject(
					EventRelationType.class, eventRelationTypeId, true);
			EventRelation eventRelation = new EventRelation();
			eventRelation.setEvent(currentEvent);
			eventRelation.setParentEvent(parentEvent);
			eventRelation.setEventRelationType(eventRelationType);
			eventDao.save(eventRelation);

			EventRelation relRelation = new EventRelation();
			relRelation.setEvent(parentEvent);
			relRelation.setParentEvent(currentEvent);
			if (eventRelationType.getTypeFlag().equals(EventRelationType.SAME)
					|| eventRelationType.getTypeFlag().equals(EventRelationType.RELATION)) {
				relRelation.setEventRelationType(eventRelationType);
			} else if (eventRelationType.getTypeFlag().equals(EventRelationType.PARENT)) {
				relRelation.setEventRelationType((EventRelationType) findUnique(
						EventRelationType.class, "typeFlag", EventRelationType.CHILD));
			} else if (eventRelationType.getTypeFlag().equals(EventRelationType.CHILD)) {
				relRelation.setEventRelationType((EventRelationType) findUnique(
						EventRelationType.class, "typeFlag", EventRelationType.PARENT));
			}
			eventDao.save(relRelation);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void createEventRelation(Long currentEventId, Long parentEventId,
			Long eventRelationTypeId) {

		try {
			Event currentEvent = new Event();
			currentEvent.setId(currentEventId);

			Event parentEvent = new Event();
			parentEvent.setId(parentEventId);

			EventRelationType eventRelationType = (EventRelationType) eventDao.getObject(
					EventRelationType.class, eventRelationTypeId, true);
			EventRelation eventRelation = new EventRelation();
			eventRelation.setEvent(currentEvent);
			eventRelation.setParentEvent(parentEvent);
			eventRelation.setEventRelationType(eventRelationType);
			eventDao.save(eventRelation);

			// 保存对应关系
			EventRelation relRelation = new EventRelation();// 新建
			relRelation.setEvent(parentEvent);
			relRelation.setParentEvent(currentEvent);
			if (eventRelationType.getTypeFlag().equals(EventRelationType.SAME)
					|| eventRelationType.getTypeFlag().equals(EventRelationType.RELATION)) {
				relRelation.setEventRelationType(eventRelationType);
			} else if (eventRelationType.getTypeFlag().equals(EventRelationType.PARENT)) {
				relRelation.setEventRelationType((EventRelationType) findUnique(EventRelationType.class, "typeFlag", EventRelationType.CHILD));
			} else if (eventRelationType.getTypeFlag().equals(EventRelationType.CHILD)) {
				relRelation.setEventRelationType((EventRelationType) findUnique(EventRelationType.class, "typeFlag", EventRelationType.PARENT));
			}
			eventDao.save(relRelation);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public EventAssign findLatestEventAssign(Long eventId) {
		try {
			return eventDao.selectLatestEventAssign(eventId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	// 修改事件处理人
	public void modifyDealerOfEvent(Long eventId, UserInfo userInfo) {
		try {
			eventDao.updateDealerOfEvent(eventId, userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	public String findCurrentNodeNameByTaskId(Long taskId) {
		try {
			TaskInstance taskInstance = (TaskInstance) eventDao.getObject(TaskInstance.class,
					taskId);
			String currentNodeName = taskInstance.getTask().getTaskNode().getDescription();
			return currentNodeName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Survey findEventSurvey() {
		try {
			return eventDao.selectEventSurvey();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Quest> findQuest(Long surveyId) {
		try {
			return eventDao.selectQuest(surveyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public boolean findIsUserFeedbackOrNot(Long userInfoId, Long objId, Long surveyId) {
		try {
			return eventDao.selectIsUserFeedbackOrNot(userInfoId, objId, surveyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}


	public List<Event> findSameAndChild(Event event) throws ServiceException {
		List<Event> events = new ArrayList<Event>();
		try {
			//modified by huzh in 2010/02/04 --- start，递归找出所有同一事件和子事件
			//events = eventDao.selectSameAndChild(event);
			events = findAllSameAndChildEvents(null,event);
			//modified by huzh in 2010/02/04 ---end
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return events;
	}
	//add by huzh in 2010/02/04 --- start ,用于递归查找所有的同一事件和子事件
	private List<Event> findAllSameAndChildEvents(Event sameObj,Event event){
		List<Event> eventList = new ArrayList<Event>();
		List<Event> eventList1 = new ArrayList<Event>();
		eventList = eventDao.selectSameAndChild(event);
        if(eventList != null && eventList.size() > 0){
        		for(int i=0;i<eventList.size();i++){
        			Event pro=eventList.get(i);
				if(pro == sameObj){
					eventList.remove(i);  
				}
			}
        		eventList1.addAll(eventList);
        	for(int i =0;i<eventList.size();i++){
        		Event problemObj=eventList.get(i);
				List<Event> subList = findAllSameAndChildEvents(event,problemObj);
				eventList1.addAll(subList);
				}
			}
        return eventList1;
	}
	//add by huzh in 2010/02/04 --- end
	public Page getEventRelByCurrEvent(Event event, int pageNo, int pageSize) {
		try {
			return eventDao.selectEventRelByCurrEvent(event, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Page getUnFinishEvents(String summary, int pageNo, int pageSize) {
		try {
			return eventDao.selectUnFinishEvents(summary, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public boolean isExist(Long currEventId, Long parentEventId) {
		try {
			return eventDao.selectIsExist(currEventId, parentEventId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void endEventWithValidKnowledge(Event event, Knowledge knowledge)
			throws ServiceException {
		try {
			EventSulotion eventSulotion = (EventSulotion) findUnique(EventSulotion.class, "event",
					event);
			if (eventSulotion != null) {
				eventDao.remove(eventSulotion);
				if (Knowledge.STATUS_DRAFT.equals(eventSulotion.getKnowledge().getStatus()))//2010-05-18 modified by huzh
					eventDao.remove(eventSulotion.getKnowledge());
			}
			eventSulotion = new EventSulotion();
			eventSulotion.setEvent(event);
			eventSulotion.setKnowledge(knowledge);
			eventSulotion.setCreatName(UserContext.getUserInfo());
			eventSulotion.setCreateDate(new Date());
			eventDao.save(eventSulotion);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Page findAllEventTypeByName(String typeName,int pageNo, int pageSize) {
		
		try {
			return eventDao.selectAllEventTypeByName(typeName,pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<EventTypeServiceItem> findEventTypeServiceItem(String eventTypeId) {
		return eventDao.selectEventTypeServiceItem(eventTypeId);
	}

	public List<ServiceItem> findAllServiceItem(String official) {
		return eventDao.selectAllServiceItem(official);
	}

	
	public List<String> findSIinEventTypeServiceItem(Long[] serviceItemsId,Long dataId ) {
		return eventDao.selectSIinEventTypeServiceItem(serviceItemsId,dataId);
	}

	public void dropAllEventTypeServiceItem(Long dataId) {
		eventDao.deleteAllEventTypeServiceItem(dataId);
	}

	public Page findAllServiceItemEventType(
			String typeName, String serviceItemId,int pageNo, int pageSize) {
		return eventDao.selectAllServiceItemEventType(typeName,serviceItemId,pageNo, pageSize);
	}

	public Page findAllEngineersByServiceItem(String name,
			String serviceItemId,int pageNo, int pageSize) {
		return eventDao.selectAllEngineersByServiceItem(name,serviceItemId,pageNo,pageSize);
	}

	public Page findServiceItemByEventType(String eventTypeId,
			String serviceName,String official, int pageNo, int pageSize) {
		return eventDao.selectServiceItemByEventType(eventTypeId,serviceName,official,pageNo,pageSize);
	}

	public Page findproblemTypeByServiceItem(String siTypeName,
			Long serviceItemId, int pageNo, int pageSize) {
		return eventDao.selectproblemTypeByServiceItem(siTypeName,serviceItemId,pageNo,pageSize);
	}

	public Page findServiceItemByGroupLeader(String name, Integer official,Long userId,String adminFlag,
			int pageNo, int pageSize) {
		try {
			return eventDao.selectServiceItemByGroupLeader(name,official,userId,adminFlag,pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Page findAllEventByParams(String summary, String eventCisn,
					String createUser, String dealUser, String submitUser,
					String eventStatusId, int pageNo, String supportGroupId,
					int pageSize) {
		try {
			return eventDao.selectAllEventByParams(summary, eventCisn,createUser, dealUser, submitUser,
															eventStatusId, pageNo, supportGroupId,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Page findAllProblemSort(String id,String typeName, int pageNo, int pageSize) {
		try {
			return eventDao.selectAllProblemSort(id,typeName,pageNo,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	public Long findTaskId(String dataId, String nodeId, String processId) {
		try {
			return eventDao.selectTaskId(dataId,nodeId,processId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<EventAuditHis> findAllEventHistory(Event event, String processId) {
		try {
			return eventDao.selectAllEventHistory(event, processId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Long findTaskIdByDataId(String dataId,String processId) {
		try {
			return eventDao.selectTaskIdByDataId(dataId,processId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	

}
