package com.digitalchina.itil.event.service;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.entity.UserRole;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.itil.actor.entity.SupportGroup;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventAssign;
import com.digitalchina.itil.event.entity.EventAuditHis;
import com.digitalchina.itil.event.entity.EventRelationType;
import com.digitalchina.itil.event.entity.EventStatus;
import com.digitalchina.itil.event.entity.EventType;
import com.digitalchina.itil.event.entity.EventTypeServiceItem;
import com.digitalchina.itil.knowledge.entity.Knowledge;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.train.entity.Quest;
import com.digitalchina.itil.train.entity.Survey;

public interface EventService extends Service{

	/**
	 * 指定事件关系
	 * @author awen
	 */
	void createEventRelation(Long currentEventId, Long parentEventId, Long eventRelationTypeId);
	/**
	 * 查找当前事件的事件分配情况，当前事件、当前处理人确定唯一，并按事件排序
	 * @author awen
	 * @param eventId
	 * @return
	 */
	EventAssign findLatestEventAssign(Long eventId);
	/**
	 * 修改事件处理人
	 * @author awen
	 * @param eventId
	 * @param dealerId
	 */
	void modifyDealerOfEvent(Long eventId, UserInfo userInfo);
	/**
	 * 通过当前任务Id查找当前节点名称
	 * @Methods Name findCurrentNodeNameByTaskId
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	String findCurrentNodeNameByTaskId(Long taskId);
	/**
	 * 查询用户满意度调查问卷
	 * @author awen
	 * @return
	 */
	Survey findEventSurvey();
	/**
	 * 查询用户反馈试题
	 * @return
	 */
	List<Quest> findQuest(Long surveyId);
	/**
	 * 查询用户是否填写满意度调查。
	 * @Methods Name findIsUserFeedbackOrNot
	 * @Create In Nov 5, 2009 By duxh
	 * @return boolean
	 */
	boolean findIsUserFeedbackOrNot(Long userInfoId, Long eventId, Long surveyId);
	
	/**
	 * 通过一个当前事件得到这个事件和其他事件的关系
	 * @Methods Name getEventRelByCurrEvent
	 * @Create In Sep 9, 2009 By guoxl
	 * @param event
	 * @return Page
	 */
	Page getEventRelByCurrEvent(Event event, int pageNo,int pageSize);
		/**
	 * 获取未完成的事件
	 * @Methods Name getUnFinishEvents
	 * @Create In Sep 16, 2009 By lee
	 * @param summary //事件摘要
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getUnFinishEvents(String summary, int pageNo, int pageSize);
	/**
	 * 修改事件关系
	 * @Methods Name modifyEventRelation
	 * @Create In Sep 16, 2009 By guoxl
	 * @param relId
	 * @param currEventId
	 * @param parentEventId
	 * @param eventRelationTypeId void
	 */
     void modifyEventRelation(String relId,Long currEventId, Long parentEventId,
			Long eventRelationTypeId);
	 /**
	  * 判断是否已经存在关系
	  * @Methods Name isExist
	  * @Create In Sep 16, 2009 By guoxl
	  * @param currEventId
	  * @param parentEventId
	  * @return boolean true存在、false不存在
	  */
	 boolean isExist(Long currEventId, Long parentEventId);
	 /**
	  * 删除关系并同时删除对应关系
	  * @Methods Name removeDoubleRel
	  * @Create In Sep 16, 2009 By guoxl
	  * @param relId void
	  */
	 void removeDoubleRel(String relId);

	 /**
	  * 使用审核通过的解决方案处理事件。
	  * @Methods Name endEventWithValidKnowledge
	  * @Create In Oct 29, 2009 By duxh
	  * @return void
	  */
	 public void endEventWithValidKnowledge(Event event,Knowledge knowledge) throws ServiceException;
	 
	 /**
		 * 根据父事件找出所有相同事件和子事件。
		 * @Methods Name findSameAndChild
		 * @Create In Oct 27, 2009 By duxh
		 * @param event 父事件
		 * @throws ServiceException
		 * @return List<Event>
		 */
	public List<Event> findSameAndChild(Event event) throws ServiceException;
	/**
	 * 通过name查询事件类型
	 * @param pageSize 
	 * @param pageNo 
	 * @Methods Name findAllEventTypeByName
	 * @Create In May 19, 2010 By huzh
	 * @param name
	 * @return 
	 * @Return List<EventType>
	 */
	Page findAllEventTypeByName(String typeName, int pageNo, int pageSize);
	/**
	 * 获得事件类型与服务项的关联实体的数据
	 */
	public List<EventTypeServiceItem> findEventTypeServiceItem(String eventTypeId);
	/**
	 * 获得所有的服务项
	 * @param official 
	 * @param string 
	 * @Methods Name findAllServiceItem
	 * @Create In May 21, 2010 By huzh
	 * @return 
	 * @Return List<ServiceItem>
	 */
	List<ServiceItem> findAllServiceItem(String official);
	 /**
	  * 查询给定服务项是否已经在事件类型与服务项关联
	  * @Methods Name findSIinEventTypeServiceItem
	  * @Create In May 22, 2010 By huzh
	  * @param serviceItemsId
	 * @param dataId 
	  * @return 
	  * @Return List<String>
	  */
	 List<String> findSIinEventTypeServiceItem(Long[] serviceItemsId, Long dataId);
	 /**
	  * 根据事件类型删除所有的事件类型服务项关联实体
	  * @Methods Name dropAllEventTypeServiceItem
	  * @Create In May 22, 2010 By huzh
	  * @param dataId 
	  * @Return void
	  */
	void dropAllEventTypeServiceItem(Long dataId);
	/**
	 * 根据所传参数查询服务项事件类型
	 * @Methods Name findAllServiceItemEventType
	 * @Create In May 24, 2010 By huzh
	 * @param typeName
	 * @param pageSize 
	 * @param pageNo 
	 * @param siName
	 * @return 
	 * @Return List<EventTypeServiceItem>
	 */
	Page findAllServiceItemEventType(String typeName, String serviceItemId, int pageNo, int pageSize);
	/**
	 * 根据服务项查询所有的支持组工程师(包括一线和二线)
	 * @Methods Name findAllEngineersByServiceItem
	 * @Create In May 24, 2010 By huzh
	 * @param name
	 * @param serviceItemId
	 * @param pageSize 
	 * @param pageNo 
	 * @return 
	 * @Return List<UserInfo>
	 */
	Page findAllEngineersByServiceItem(String name,String serviceItemId, int pageNo, int pageSize);
	/**
	 * 根据事件类型查询服务项
	 * @Methods Name findServiceItemByEventType
	 * @Create In Jun 4, 2010 By huzh
	 * @param eventTypeId
	 * @param serviceName
	 * @param official 
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	Page findServiceItemByEventType(String eventTypeId, String serviceName,String official, int pageNo, int pageSize);
	/**
	 * 根据相应参数查询问题类型
	 * @Methods Name findproblemTypeByServiceItem
	 * @Create In Jul 1, 2010 By huzh
	 * @param siTypeName
	 * @param serviceItemId
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	Page findproblemTypeByServiceItem(String siTypeName, Long serviceItemId,int pageNo, int pageSize);
	/**
	 * 根据组长查询服务项
	 * @Methods Name findServiceItemByGroupLeader
	 * @Create In Jul 5, 2010 By huzh
	 * @param name
	 * @param userId 
	 * @param adminFlag 
	 * @param valueOf
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	Page findServiceItemByGroupLeader(String name, Integer official, Long userId, String adminFlag, int pageNo,int pageSize);
	/**
	 * 查询所有事件
	 * @Methods Name findAllEventByParams
	 * @Create In Jul 21, 2010 By huzh
	 * @param summary
	 * @param eventCisn
	 * @param createUser
	 * @param dealUser
	 * @param submitUser
	 * @param eventStatusId
	 * @param pageNo
	 * @param supportGroupId
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	Page findAllEventByParams(String summary, String eventCisn,
			String createUser, String dealUser, String submitUser,
			String eventStatusId, int pageNo, String supportGroupId,
			int pageSize);
	/**
	 * 查询问题类别
	 * @Methods Name findAllProblmeSort
	 * @Create In Jul 23, 2010 By huzh
	 * @param typeName
	 * @param typeName2 
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	Page findAllProblemSort(String id, String typeName, int pageNo, int pageSize);
	/**
	 * 查询taskId
	 * @Methods Name findTaskId
	 * @Create In Aug 11, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param processId
	 * @return 
	 * @Return Long
	 */
	Long findTaskId(String dataId, String nodeId, String processId);
	/**
	 * 查询事件审批历史
	 * @Methods Name findAllEventHistory
	 * @Create In Aug 11, 2010 By huzh
	 * @param event
	 * @param processId
	 * @return 
	 * @Return List<EventAuditHis>
	 */
	List<EventAuditHis> findAllEventHistory(Event event, String processId);
	/**
	 * 根据dataId来查询taskId
	 * @Methods Name findTaskIdByDataId
	 * @Create In Aug 11, 2010 By huzh
	 * @param dataId
	 * @param processId 
	 * @return 
	 * @Return Long
	 */
	Long findTaskIdByDataId(String dataId, String processId);
}
