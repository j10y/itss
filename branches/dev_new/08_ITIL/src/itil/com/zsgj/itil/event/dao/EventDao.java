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
 * �¼����ݷ��ʽӿ�
 * @Class Name EventDao
 * @Author duxh
 * @Create In Oct 27, 2009
 */
public interface EventDao extends Dao<Event> {
	/**
	 * ���ݸ��¼��ҳ�������ͬ�¼������¼���
	 * @Methods Name selectSameAndChild
	 * @Create In Oct 27, 2009 By duxh
	 * @param event���¼�
	 * @throws DaoException
	 * @return List<Event>
	 */
	public List<Event> selectSameAndChild(Event event) throws DaoException;
	
	/**
	 * ���ҵ�ǰ�¼����¼������������ǰ�¼�����ǰ������ȷ��Ψһ�������¼�����
	 * @author awen
	 * @param eventId
	 * @return
	 */
	EventAssign selectLatestEventAssign(Long eventId);
	/**
	 * �޸��¼�������
	 * @author awen
	 * @param eventId
	 * @param dealerId
	 */
	void updateDealerOfEvent(Long eventId, UserInfo userInfo);
	/**
	 * ��ѯ�û�����ȵ����ʾ�
	 * @author awen
	 * @return
	 */
	Survey selectEventSurvey();
	/**
	 * ��ѯ�û���������
	 * @return
	 */
	List<Quest> selectQuest(Long surveyId);
	
	boolean selectIsUserFeedbackOrNot(Long userInfoId, Long objId, Long surveyId);
	
	/**
	 * ͨ��һ����ǰ�¼��õ�����¼��������¼��Ĺ�ϵ
	 * @Methods Name getEventRelByCurrEvent
	 * @Create In Sep 9, 2009 By guoxl
	 * @param event
	 * @return Page
	 */
	Page selectEventRelByCurrEvent(Event event, int pageNo,int pageSize);
	/**
	 * ��ȡδ��ɵ��¼�
	 * @Methods Name getUnFinishEvents
	 * @Create In Sep 16, 2009 By lee
	 * @param summary //�¼�ժҪ
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page selectUnFinishEvents(String summary, int pageNo, int pageSize);
	
	 /**
	  * �ж��Ƿ��Ѿ����ڹ�ϵ
	  * @Methods Name isExist
	  * @Create In Sep 16, 2009 By guoxl
	  * @param currEventId
	  * @param parentEventId
	  * @return boolean true���ڡ�false������
	  */
	 boolean selectIsExist(Long currEventId, Long parentEventId);
	 /**
	  * ɾ����ϵ��ͬʱɾ����Ӧ��ϵ
	  * @Methods Name removeDoubleRel
	  * @Create In Sep 16, 2009 By guoxl
	  * @param relId void
	  */
	 void deleteDoubleRel(String relId);
	/**
	 * ͨ���¼����Ͳ�ѯ������
	 * @Methods Name selectAllServiceItemByEventType
	 * @Create In May 19, 2010 By huzh
	 * @param eventtype
	 * @return 
	 * @Return List<ServiceItem>
	 */
	public List<ServiceItem> selectAllServiceItemByEventType(String eventtype);
	/**
	 * ͨ��name�����¼�����
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
	 * ͨ���¼����Ͳ�ѯ�¼����������������й���ʵ��
	 * @Methods Name selectEventTypeData
	 * @Create In May 21, 2010 By huzh
	 * @param eventTypeId
	 * @return 
	 * @Return List<EventTypeServiceItem>
	 */
	public List<EventTypeServiceItem> selectEventTypeServiceItem(String eventTypeId);
	/**
	 * ��ѯ���еķ�����
	 * @param official 
	 * @Methods Name selectAllServiceItem
	 * @Create In May 21, 2010 By huzh
	 * @return 
	 * @Return List<ServiceItem>
	 */
	public List<ServiceItem> selectAllServiceItem(String official);
	/**
	 *  ��ѯ�����������Ƿ��Ѿ����¼���������������
	 * @Methods Name selectSIinEventTypeServiceItem
	 * @Create In May 22, 2010 By huzh
	 * @param serviceItemsId
	 * @param dataId 
	 * @return 
	 * @Return List<String>
	 */
	public List<String> selectSIinEventTypeServiceItem(Long[] serviceItemsId, Long dataId);
	/**
	 * �����¼�����ɾ�����е��¼����ͷ��������ʵ��
	 * @Methods Name deleteAllEventTypeServiceItem
	 * @Create In May 22, 2010 By huzh
	 * @param dataId 
	 * @Return void
	 */
	public void deleteAllEventTypeServiceItem(Long dataId);
	/**
	 * ��������������ѯ�������¼�����
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
	 * ���ݷ������ѯ֧���鹤��ʦ(����һ�ߺͶ���)
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
	 * �����¼��������Ͳ�ѯ������
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
	 * ������Ӧ������ѯ��������
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
	 * �����鳤ѡ�������
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
	 * ��ѯ�����¼�
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
	 * ��ѯ�������
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
	 * ��ѯtaskId
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
	 * ��ѯ���е�������ʷ
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
	 * ����dataId��ѯtaskId
	 * @Methods Name selectTaskIdByDataId
	 * @Create In Aug 11, 2010 By huzh
	 * @param dataId
	 * @param processId 
	 * @return 
	 * @Return Long
	 */
	public Long selectTaskIdByDataId(String dataId, String processId);
}
