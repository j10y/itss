package com.zsgj.itil.event.dao;

import java.util.List;

import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventAssign;
import com.zsgj.itil.event.entity.EventAuditHis;
import com.zsgj.itil.event.entity.EventType;
import com.zsgj.itil.event.entity.EventTypeServiceItem;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.train.entity.Quest;
import com.zsgj.itil.train.entity.Survey;
/**
 * 事件数据访问接口
 * @Class Name EventDao
 * @Author duxh
 * @Create In Oct 27, 2009
 */
public interface EventDao extends Dao<Event> {
	/**
	 * 根据父事件找出所有相同事件和子事件。
	 * @Methods Name selectSameAndChild
	 * @Create In Oct 27, 2009 By duxh
	 * @param event父事件
	 * @throws DaoException
	 * @return List<Event>
	 */
	public List<Event> selectSameAndChild(Event event) throws DaoException;
	
	/**
	 * 查找当前事件的事件分配情况，当前事件、当前处理人确定唯一，并按事件排序
	 * @author awen
	 * @param eventId
	 * @return
	 */
	EventAssign selectLatestEventAssign(Long eventId);
	/**
	 * 修改事件处理人
	 * @author awen
	 * @param eventId
	 * @param dealerId
	 */
	void updateDealerOfEvent(Long eventId, UserInfo userInfo);
	/**
	 * 查询用户满意度调查问卷
	 * @author awen
	 * @return
	 */
	Survey selectEventSurvey();
	/**
	 * 查询用户反馈试题
	 * @return
	 */
	List<Quest> selectQuest(Long surveyId);
	
	boolean selectIsUserFeedbackOrNot(Long userInfoId, Long objId, Long surveyId);
	
	/**
	 * 通过一个当前事件得到这个事件和其他事件的关系
	 * @Methods Name getEventRelByCurrEvent
	 * @Create In Sep 9, 2009 By guoxl
	 * @param event
	 * @return Page
	 */
	Page selectEventRelByCurrEvent(Event event, int pageNo,int pageSize);
	/**
	 * 获取未完成的事件
	 * @Methods Name getUnFinishEvents
	 * @Create In Sep 16, 2009 By lee
	 * @param summary //事件摘要
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page selectUnFinishEvents(String summary, int pageNo, int pageSize);
	
	 /**
	  * 判断是否已经存在关系
	  * @Methods Name isExist
	  * @Create In Sep 16, 2009 By guoxl
	  * @param currEventId
	  * @param parentEventId
	  * @return boolean true存在、false不存在
	  */
	 boolean selectIsExist(Long currEventId, Long parentEventId);
	 /**
	  * 删除关系并同时删除对应关系
	  * @Methods Name removeDoubleRel
	  * @Create In Sep 16, 2009 By guoxl
	  * @param relId void
	  */
	 void deleteDoubleRel(String relId);
	/**
	 * 通过事件类型查询服务项
	 * @Methods Name selectAllServiceItemByEventType
	 * @Create In May 19, 2010 By huzh
	 * @param eventtype
	 * @return 
	 * @Return List<ServiceItem>
	 */
	public List<ServiceItem> selectAllServiceItemByEventType(String eventtype);
	/**
	 * 通过name查找事件类型
	 * @param pageSize 
	 * @param pageNo 
	 * @Methods Name selectAllEventTypeByName
	 * @Create In May 19, 2010 By huzh
	 * @param name
	 * @return 
	 * @Return List<EventType>
	 */
	public Page selectAllEventTypeByName(String typeName, int pageNo, int pageSize);
	/**
	 * 通过事件类型查询事件类型与服务项的所有关联实体
	 * @Methods Name selectEventTypeData
	 * @Create In May 21, 2010 By huzh
	 * @param eventTypeId
	 * @return 
	 * @Return List<EventTypeServiceItem>
	 */
	public List<EventTypeServiceItem> selectEventTypeServiceItem(String eventTypeId);
	/**
	 * 查询所有的服务项
	 * @param official 
	 * @Methods Name selectAllServiceItem
	 * @Create In May 21, 2010 By huzh
	 * @return 
	 * @Return List<ServiceItem>
	 */
	public List<ServiceItem> selectAllServiceItem(String official);
	/**
	 *  查询给定服务项是否已经在事件类型与服务项关联
	 * @Methods Name selectSIinEventTypeServiceItem
	 * @Create In May 22, 2010 By huzh
	 * @param serviceItemsId
	 * @param dataId 
	 * @return 
	 * @Return List<String>
	 */
	public List<String> selectSIinEventTypeServiceItem(Long[] serviceItemsId, Long dataId);
	/**
	 * 根据事件类型删除所有的事件类型服务项关联实体
	 * @Methods Name deleteAllEventTypeServiceItem
	 * @Create In May 22, 2010 By huzh
	 * @param dataId 
	 * @Return void
	 */
	public void deleteAllEventTypeServiceItem(Long dataId);
	/**
	 * 根据所传参数查询服务项事件类型
	 * @Methods Name selectAllServiceItemEventType
	 * @Create In May 24, 2010 By huzh
	 * @param typeName
	 * @param serviceItemId 
	 * @param pageSize 
	 * @param pageNo 
	 * @Return void
	 */
	public Page selectAllServiceItemEventType(
			String typeName, String serviceItemId, int pageNo, int pageSize);
	/**
	 * 根据服务项查询支持组工程师(包括一线和二线)
	 * @Methods Name selectAllEngineersByServiceItem
	 * @Create In May 24, 2010 By huzh
	 * @param name
	 * @param serviceItemId
	 * @param pageSize 
	 * @param pageNo 
	 * @return 
	 * @Return List<UserInfo>
	 */
	public Page selectAllEngineersByServiceItem(String name,String serviceItemId, int pageNo, int pageSize);
	/**
	 * 根据事件类型类型查询服务项
	 * @Methods Name selectServiceItemByEventType
	 * @Create In Jun 4, 2010 By huzh
	 * @param eventTypeId
	 * @param serviceName
	 * @param official 
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	public Page selectServiceItemByEventType(String eventTypeId,String serviceName, String official, int pageNo, int pageSize);
	/**
	 * 根据相应参数查询问题类型
	 * @Methods Name selectproblemTypeByServiceItem
	 * @Create In Jul 1, 2010 By huzh
	 * @param siTypeName
	 * @param serviceItemId
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	public Page selectproblemTypeByServiceItem(String siTypeName,Long serviceItemId, int pageNo, int pageSize);
	/**
	 * 根据组长选择服务项
	 * @Methods Name selectServiceItemByGroupLeader
	 * @Create In Jul 5, 2010 By huzh
	 * @param name
	 * @param official
	 * @param adminFlag 
	 * @param userInfo 
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	public Page selectServiceItemByGroupLeader(String name, Integer official,Long userId, String adminFlag, int pageNo, int pageSize);
	/**
	 * 查询所有事件
	 * @Methods Name selectAllEventByParams
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
	public Page selectAllEventByParams(String summary, String eventCisn,
			String createUser, String dealUser, String submitUser,
			String eventStatusId, int pageNo, String supportGroupId,
			int pageSize);
	/**
	 * 查询问题类别
	 * @Methods Name selectAllProblmeSort
	 * @Create In Jul 23, 2010 By huzh
	 * @param typeName
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	public Page selectAllProblemSort(String id,String typeName, int pageNo, int pageSize);
	/**
	 * 查询taskId
	 * @Methods Name selectTaskId
	 * @Create In Aug 11, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param processId
	 * @return 
	 * @Return Long
	 */
	public Long selectTaskId(String dataId, String nodeId, String processId);
	/**
	 * 查询所有的审批历史
	 * @Methods Name selectAllEventHistory
	 * @Create In Aug 11, 2010 By huzh
	 * @param event
	 * @param processId
	 * @return 
	 * @Return List<EventAuditHis>
	 */
	public List<EventAuditHis> selectAllEventHistory(Event event,
			String processId);
	/**
	 * 根据dataId查询taskId
	 * @Methods Name selectTaskIdByDataId
	 * @Create In Aug 11, 2010 By huzh
	 * @param dataId
	 * @param processId 
	 * @return 
	 * @Return Long
	 */
	public Long selectTaskIdByDataId(String dataId, String processId);
}
