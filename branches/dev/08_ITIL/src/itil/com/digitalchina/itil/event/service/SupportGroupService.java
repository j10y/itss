package com.digitalchina.itil.event.service;

import java.util.HashMap;
import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.SupportGroup;
import com.digitalchina.itil.actor.entity.SupportGroupEngineer;
import com.digitalchina.itil.actor.entity.SupportGroupServiceItem;
import com.digitalchina.itil.config.entity.CIBaseData;
import com.digitalchina.itil.config.entity.CIBaseType;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventRelation;
import com.digitalchina.itil.event.entity.EventRelationType;
import com.digitalchina.itil.event.entity.EventStatus;
import com.digitalchina.itil.service.entity.ServiceItem;

public interface SupportGroupService {

	List<SupportGroupServiceItem> findSupportGroupData(String supportId);
	/**
	 * ������еķ���������
	 * @param official 
	 * @Methods Name findServiceItemData
	 * @Create In Mar 17, 2009 By sujs
	 * @return List<ServiceItem>
	 */
	List<ServiceItem> findServiceItemData(String official);
	/**
	 * ��÷���������
	 * @Methods Name findServiceType
	 * @Create In Mar 18, 2009 By sujs
	 * @return List<ServiceProviderType>
	 */
//	List<ServiceProviderType>findServiceProviderType(String serviceProviderTypeId);
	/**
	 * ��ȡ�ڲ�������
	 * @Methods Name findServiceProviderIn
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceProviderIn>
	 */
//	List<ServiceProviderIn> findServiceProviderIn();
	/**
	 * ��ȡ�ⲿ������
	 * @Methods Name findServiceProviderOut
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceProviderOut>
	 */
//	List<ServiceProviderOut>findServiceProviderOut();
	/**
	 * ����ڲ������̹���ʦ
	 * @Methods Name findServiceEngineerIn
	 * @Create In Mar 18, 2009 By sujs
	 * @return List<ServiceEngineerIn>
	 */
//	List<ServiceEngineerIn>findServiceEngineerIn(String serviceId);
	/**
	 * ����ⲿ�����̵ķ��񹤳�ʦ
	 * @Methods Name findServiceEngineerOut
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceEngineerOut>
	 */
//	List<ServiceEngineerOut>findServiceEngineerOut(String serviceId);
	/**
	 * ͨ�������鳤id��ѯ����ʦ���Ƿ��з����鳤
	 * @Methods Name findServiceLead
	 * @Create In Mar 19, 2009 By Administrator
	 * @param id
	 * @return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer>findServiceLead(String id);


	/**
	 * 
	 * @Methods Name findSupportGroupServiceItem
	 * @Create In Nov 12, 2009 By duxh
	 * @param keyword
	 * @return
	 */
	public List<SupportGroupServiceItem> findSupportGroupServiceItem(ServiceItem keyword);
	/**
	 * ͨ��idsɾ��֧�����еļ�¼
	 * ɾ�����߼�ɾ�� ������־λ��Ϊɾ��״̬��
	 * TODO
	 * Apr 3, 2009 By yukw
	 * @param ids
	 * @return TODO
	 */
	Boolean removeSupportGroupByIds(String[] ids);
	/**
	 * ���ҷ��񹤳�ʦ.
	 * @Methods Name findServiceEngineer
	 * @Create In Nov 7, 2009 By duxh
	 * @param serviceProviderType
	 * @param serviceProvider
	 * @param itcode
	 * @param start
	 * @param pageSize
	 * @return
	 */
//	Page findServiceEngineer(Long serviceProviderType, Long serviceProvider,String itcode,int start,int pageSize);
	
	/**
	 * ���ݷ������������֧���鹤��ʦ�������һ��������һ�������򷵻ض�������
	 * @Methods Name findSupportGroupEngineer
	 * @Create In Oct 27, 2009 By duxh
	 * @param serviceItemId
	 * @return
	 * @throws ServiceException
	 * @return List<SupportGroupEngineer>
	 */
	 public List<SupportGroupEngineer> findSupportGroupEngineer(ServiceItem serviceItem) throws ServiceException;
	 /**
	  * ���ݷ����֧�����鳤��֧���鹤��ʦ��itcode��ҳ��ѯ����ʦ�����������event��ȡ��
	  * @Methods Name findCurrentGroupEngineers
	  * @Create In Nov 4, 2009 By duxh
	  * @return Page
	  */
	 public Page findCurrentGroupEngineers(String eventId, UserInfo leader,String itcode,int start,int pageSize) throws ServiceException;
	 /**
	  * ����֧���顢֧���鹤��ʦ��itcode��ҳ��ѯ����ʦ��
	  * @Methods Name findCurrentGroupEngineers
	  * @Create In Nov 4, 2009 By duxh
	  * @return Page
	  */
	 public Page findGroupEngineers(SupportGroup supportGroup,String itcode,int start,int pageSize) throws ServiceException;
	 /**
	 * ���ҵ�ǰ�������鳤
	 * @author awen
	 * @param userId
	 * @return
	 */
	UserInfo fingCurrentGroupLeader(Long userId, Long eventId);
	/**
	 * ���ݷ�����id���飬��ѯ��Щ��������һ��֧���顣
	 * @Methods Name findFirstRankGroup
	 * @Create In Nov 7, 2009 By duxh
	 * @param serviceItemsIdArray ������id���顣
	 * @param supportGroupId ���request�еĲ���supportGroupId��Ϊ"",��ʾ����֤��֧������֧�ֵķ����
	 * @return List<String> ��һ��֧����ķ���������ƣ�û�з��ؿա�
	 * @throws ServiceException
	 */
	public List<String> findFirstRankGroup(Long[] serviceItemsIdArray,Long supportGroupId) throws ServiceException;
	/**
	 * ����֧�����������֧���鹤��ʦ��
	 * @Methods Name findEngineersBySupportGroupId
	 * @Create In Nov 12, 2009 By duxh
	 * @param supportgroupId
	 * @return
	 * @throws ServiceException
	 */
	public List<SupportGroupEngineer> findEngineersBySupportGroupId(Long supportGroupId) throws ServiceException;
	/**
	 * ����֧���顣
	 * @Methods Name saveSupportGroup
	 * @Create In Nov 12, 2009 By duxh
	 * @param supportGroupMap ֧���������Ϣ��
	 * @param engineersId ֧���������֧���鹤��ʦ��
	 * @param serviceItemsId ֧����֧�ֵķ����
	 * @throws ServiceException
	 */
	public void saveSupportGroup(HashMap supportGroupMap,Long[] engineersId,Long[] serviceItemsId)throws ServiceException;
	
	/**
	 * �޸�֧���顣
	 * @Methods Name modifySupportGroup
	 * @Create In Nov 12, 2009 By duxh
	 * @param supportGroupMap ֧���������Ϣ��
	 * @param engineersId ֧���������֧���鹤��ʦ��
	 * @param serviceItemsId ֧����֧�ֵķ����
	 * @throws ServiceException
	 */
	public void modifySupportGroup(HashMap supportGroupMap,Long[] engineersId,Long[] serviceItemsId)throws ServiceException;
	/**
	 * ��ȡ�����ڲ�֧���鹤��ʦ
	 * @Methods Name findAllEngineerIn
	 * @Create In Apr 16, 2010 By lee
	 * @return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> findAllEngineerIn();
	/**
	 * �����¼����ͻ�������ڲ�֧���鹤��ʦ
	 * @Methods Name findSupportGroupEngineerByEventType
	 * @Create In May 19, 2010 By huzh
	 * @param eventtype
	 * @return 
	 * @Return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> findSupportGroupEngineersByEventType(String eventtype);
	/**
	 * �õ�����ʦ�������֧�����鳤
	 * @Methods Name fingCurrentGroupLeaders
	 * @Create In Jun 17, 2010 By huzh
	 * @param userId
	 * @return 
	 * @Return List<UserInfo>
	 */
	List<UserInfo> fingCurrentGroupLeaders(Long userId);
	/**
	 * ��ѯ֧������ѡ������
	 * @Methods Name findServiceItemsBySupportGroupId
	 * @Create In Jul 5, 2010 By huzh
	 * @param supportGroupId
	 * @param official
	 * @return 
	 * @Return List<ServiceItem>
	 */
	List<ServiceItem> findServiceItemsBySupportGroupId(Long supportGroupId,Integer official);
	/**
	 * ͨ��֧����Id�Լ�֧�����鳤Id����֧����
	 * @Methods Name findSupportGroupByLeaderAndSupportId
	 * @Create In Jul 5, 2010 By huzh
	 * @param parseLong
	 * @param id
	 * @return 
	 * @Return SupportGroup
	 */
	SupportGroup findSupportGroupByLeaderAndSupportId(Long supportId, Long leader);
	/**
	 * �����¼����Ͳ�ѯ�����е�֧����
	 * @Methods Name findSupportGroupByEventType
	 * @Create In Jul 20, 2010 By huzh
	 * @param eventtype
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> findSupportGroupByEventType(Long eventtype);
	/**
	 * ���ݹ���ʦ����ѯ֧���飨�Ȳ�һ��֧���飬û��һ��֧������ٲ����֧���飩
	 * @Methods Name findSupportGroupByEngineer
	 * @Create In Jul 21, 2010 By huzh
	 * @param engineer
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> findSupportGroupByEngineer(UserInfo engineer);
	/**
	 * ���ݷ���������ѯ֧���飨�Ȳ�һ��֧���飬û��һ��֧������ٲ����֧���飩
	 * @Methods Name findSupportGroupByServiceItem
	 * @Create In Jul 21, 2010 By huzh
	 * @param sitem
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> findSupportGroupByServiceItem(ServiceItem sitem);
	/**
	 * ���ݹ���ʦ�Լ�����������֧����
	 * @Methods Name findSupportGroupByEngineer
	 * @Create In Aug 9, 2010 By huzh
	 * @param userInfo
	 * @param scidData
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> findSupportGroupByEngineer(UserInfo userInfo,
			ServiceItem scidData);
}
