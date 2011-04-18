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
	 * ָ���¼���ϵ
	 * @author awen
	 */
	void createEventRelation(Long currentEventId, Long parentEventId, Long eventRelationTypeId);
	/**
	 * ���ҵ�ǰ�¼����¼������������ǰ�¼�����ǰ������ȷ��Ψһ�������¼�����
	 * @author awen
	 * @param eventId
	 * @return
	 */
	EventAssign findLatestEventAssign(Long eventId);
	/**
	 * �޸��¼�������
	 * @author awen
	 * @param eventId
	 * @param dealerId
	 */
	void modifyDealerOfEvent(Long eventId, UserInfo userInfo);
	/**
	 * ͨ����ǰ����Id���ҵ�ǰ�ڵ�����
	 * @Methods Name findCurrentNodeNameByTaskId
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	String findCurrentNodeNameByTaskId(Long taskId);
	/**
	 * ��ѯ�û�����ȵ����ʾ�
	 * @author awen
	 * @return
	 */
	Survey findEventSurvey();
	/**
	 * ��ѯ�û���������
	 * @return
	 */
	List<Quest> findQuest(Long surveyId);
	/**
	 * ��ѯ�û��Ƿ���д����ȵ��顣
	 * @Methods Name findIsUserFeedbackOrNot
	 * @Create In Nov 5, 2009 By duxh
	 * @return boolean
	 */
	boolean findIsUserFeedbackOrNot(Long userInfoId, Long eventId, Long surveyId);
	
	/**
	 * ͨ��һ����ǰ�¼��õ�����¼��������¼��Ĺ�ϵ
	 * @Methods Name getEventRelByCurrEvent
	 * @Create In Sep 9, 2009 By guoxl
	 * @param event
	 * @return Page
	 */
	Page getEventRelByCurrEvent(Event event, int pageNo,int pageSize);
		/**
	 * ��ȡδ��ɵ��¼�
	 * @Methods Name getUnFinishEvents
	 * @Create In Sep 16, 2009 By lee
	 * @param summary //�¼�ժҪ
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getUnFinishEvents(String summary, int pageNo, int pageSize);
	/**
	 * �޸��¼���ϵ
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
	  * �ж��Ƿ��Ѿ����ڹ�ϵ
	  * @Methods Name isExist
	  * @Create In Sep 16, 2009 By guoxl
	  * @param currEventId
	  * @param parentEventId
	  * @return boolean true���ڡ�false������
	  */
	 boolean isExist(Long currEventId, Long parentEventId);
	 /**
	  * ɾ����ϵ��ͬʱɾ����Ӧ��ϵ
	  * @Methods Name removeDoubleRel
	  * @Create In Sep 16, 2009 By guoxl
	  * @param relId void
	  */
	 void removeDoubleRel(String relId);

	 /**
	  * ʹ�����ͨ���Ľ�����������¼���
	  * @Methods Name endEventWithValidKnowledge
	  * @Create In Oct 29, 2009 By duxh
	  * @return void
	  */
	 public void endEventWithValidKnowledge(Event event,Knowledge knowledge) throws ServiceException;
	 
	 /**
		 * ���ݸ��¼��ҳ�������ͬ�¼������¼���
		 * @Methods Name findSameAndChild
		 * @Create In Oct 27, 2009 By duxh
		 * @param event ���¼�
		 * @throws ServiceException
		 * @return List<Event>
		 */
	public List<Event> findSameAndChild(Event event) throws ServiceException;
	/**
	 * ͨ��name��ѯ�¼�����
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
	 * ����¼������������Ĺ���ʵ�������
	 */
	public List<EventTypeServiceItem> findEventTypeServiceItem(String eventTypeId);
	/**
	 * ������еķ�����
	 * @param official 
	 * @param string 
	 * @Methods Name findAllServiceItem
	 * @Create In May 21, 2010 By huzh
	 * @return 
	 * @Return List<ServiceItem>
	 */
	List<ServiceItem> findAllServiceItem(String official);
	 /**
	  * ��ѯ�����������Ƿ��Ѿ����¼���������������
	  * @Methods Name findSIinEventTypeServiceItem
	  * @Create In May 22, 2010 By huzh
	  * @param serviceItemsId
	 * @param dataId 
	  * @return 
	  * @Return List<String>
	  */
	 List<String> findSIinEventTypeServiceItem(Long[] serviceItemsId, Long dataId);
	 /**
	  * �����¼�����ɾ�����е��¼����ͷ��������ʵ��
	  * @Methods Name dropAllEventTypeServiceItem
	  * @Create In May 22, 2010 By huzh
	  * @param dataId 
	  * @Return void
	  */
	void dropAllEventTypeServiceItem(Long dataId);
	/**
	 * ��������������ѯ�������¼�����
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
	 * ���ݷ������ѯ���е�֧���鹤��ʦ(����һ�ߺͶ���)
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
	 * �����¼����Ͳ�ѯ������
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
	 * ������Ӧ������ѯ��������
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
	 * �����鳤��ѯ������
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
	 * ��ѯ�����¼�
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
	 * ��ѯ�������
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
	 * ��ѯtaskId
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
	 * ��ѯ�¼�������ʷ
	 * @Methods Name findAllEventHistory
	 * @Create In Aug 11, 2010 By huzh
	 * @param event
	 * @param processId
	 * @return 
	 * @Return List<EventAuditHis>
	 */
	List<EventAuditHis> findAllEventHistory(Event event, String processId);
	/**
	 * ����dataId����ѯtaskId
	 * @Methods Name findTaskIdByDataId
	 * @Create In Aug 11, 2010 By huzh
	 * @param dataId
	 * @param processId 
	 * @return 
	 * @Return Long
	 */
	Long findTaskIdByDataId(String dataId, String processId);
}
