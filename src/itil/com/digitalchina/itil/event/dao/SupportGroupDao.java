package com.digitalchina.itil.event.dao;

import java.util.Collection;
import java.util.List;

import com.digitalchina.info.framework.dao.Dao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.SupportGroup;
import com.digitalchina.itil.actor.entity.SupportGroupEngineer;
import com.digitalchina.itil.actor.entity.SupportGroupServiceItem;
import com.digitalchina.itil.service.entity.ServiceItem;

public interface SupportGroupDao extends Dao<SupportGroup> {
	List<SupportGroupServiceItem> selectSupportGroupData(String supportId);

	/**
	 * ������еķ���������
	 * @param official 
	 * 
	 * @Methods Name findServiceItemData
	 * @Create In Mar 17, 2009 By sujs
	 * @return List<ServiceItem>
	 */
	List<ServiceItem> selectServiceItemData(String official);

	/**
	 * ��÷���������
	 * 
	 * @Methods Name findServiceType
	 * @Create In Mar 18, 2009 By sujs
	 * @return List<ServiceProviderType>
	 */
//	List<ServiceProviderType> selectServiceProviderType(String serviceProviderTypeId);

	/**
	 * ��ȡ�ڲ�������
	 * 
	 * @Methods Name findServiceProviderIn
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceProviderIn>
	 */
//	List<ServiceProviderIn> selectServiceProviderIn();

	/**
	 * ��ȡ�ⲿ������
	 * 
	 * @Methods Name findServiceProviderOut
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceProviderOut>
	 */
//	List<ServiceProviderOut> selectServiceProviderOut();

	/**
	 * ����ڲ������̹���ʦ
	 * 
	 * @Methods Name findServiceEngineerIn
	 * @Create In Mar 18, 2009 By sujs
	 * @return List<ServiceEngineerIn>
	 */
//	List<ServiceEngineerIn> selectServiceEngineerIn(String serviceId);

	/**
	 * ����ⲿ�����̵ķ��񹤳�ʦ
	 * 
	 * @Methods Name findServiceEngineerOut
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceEngineerOut>
	 */
//	List<ServiceEngineerOut> selectServiceEngineerOut(String serviceId);

	/**
	 * ͨ�������鳤id��ѯ����ʦ���Ƿ��з����鳤
	 * 
	 * @Methods Name findServiceLead
	 * @Create In Mar 19, 2009 By Administrator
	 * @param id
	 * @return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> selectServiceLead(String id);
	/**
	 * 
	 * @Methods Name selectSupportGroupServiceItem
	 * @Create In Nov 12, 2009 By duxh
	 * @param keyword
	 * @return
	 */
	public List<SupportGroupServiceItem> selectSupportGroupServiceItem(ServiceItem keyword);

	/**
	 * ͨ��idsɾ��֧�����еļ�¼ ɾ�����߼�ɾ�� ������־λ��Ϊɾ��״̬�� TODO Apr 3, 2009 By yukw
	 * 
	 * @param ids
	 * @return TODO
	 */
	Boolean deleteSupportGroupByIds(String[] ids);
	/**
	 * ��ѯ���񹤳�ʦ��
	 * @Methods Name selectServiceEngineer
	 * @Create In Nov 7, 2009 By duxh
	 * @param serviceProviderType
	 * @param serviceProvider
	 * @param itcode
	 * @param start
	 * @param pageSize
	 * @return
	 */
//	Page selectServiceEngineer(Long serviceProviderType, Long serviceProvider,String itcode,
//			int start, int pageSize);

	/**
	 * ���ݷ�����������е�֧���顣
	 * 
	 * @Methods Name selectAllGroup
	 * @Create In Oct 27, 2009 By duxh
	 * @param serviceItem
	 * @return
	 * @throws DaoException
	 * @return List<SupportGroupEngineer>
	 */
	public List<SupportGroup> selectAllGroup(ServiceItem serviceItem) throws DaoException;
	/**
	 * ����֧�����������֧���鹤��ʦ��
	 * 
	 * @Methods Name selectAllEngineer
	 * @Create In Oct 27, 2009 By duxh
	 * @param supportGroups
	 * @return
	 * @throws DaoException
	 * @return List<SupportGroupEngineer>
	 */
	public List<SupportGroupEngineer> selectAllEngineer(List<SupportGroup> supportGroups)
			throws DaoException;

	/**
	 * ����֧���顢֧���鹤��ʦ��itcode��ҳ��ѯ֧���鹤��ʦ��
	 * 
	 * @Methods Name selectAllEngineer
	 * @Create In Oct 27, 2009 By duxh
	 * @param supportGroups
	 * @return
	 * @throws DaoException
	 * @return List<SupportGroupEngineer>
	 */
	public Page selectAllEngineer(List<SupportGroup> supportGroups,String itcode,int start,int pageSize)
			throws DaoException;

	/**
	 * ����֧�����������֧���鹤��ʦ��
	 * 
	 * @Methods Name selectAllEngineer
	 * @Create In Oct 27, 2009 By duxh
	 * @param supportGroupId
	 * @throws DaoException
	 * @return List<SupportGroupEngineer>
	 */
	public List<SupportGroupEngineer> selectAllEngineer(SupportGroup supportGroupId)
			throws DaoException;
	
	/**
	 * ���ҵ�ǰ�������鳤
	 * 
	 * @Methods Name selectCurrentGroupLeader
	 * @Create In Oct 30, 2009 By duxh
	 * @return UserInfo
	 */
	UserInfo selectCurrentGroupLeader(Long userId, Long eventId);

	/**
	 * ���ݷ������֧�����鳤,��������֧���顣
	 * 
	 * @Methods Name selectSupportGroupList
	 * @Create In Nov 3, 2009 By duxh
	 * @return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupList(ServiceItem serviceItem, UserInfo leader);
	/**
	 * ���ݷ�����id���飬��ѯ��Щ��������һ��֧���顣
	 * @Methods Name selectFirstRankGroup
	 * @Create In Nov 6, 2009 By duxh
	 * @param serviceItemsIdArray ������id���顣
	 * @param supportGroupId ���request�еĲ���supportGroupId��Ϊ"",��ʾ����֤��֧������֧�ֵķ����
	 * @return List<String> ��һ��֧����ķ���������ƣ�û�з��ؿա�
	 * @throws DaoException
	 */
	public List<String> selectFirstRankGroup(Long[] serviceItemsIdArray,Long supportGroupId) throws DaoException;
	/**
	 * ����֧�����������֧���鹤��ʦ��
	 * @Methods Name findEngineersBySupportGroupId
	 * @Create In Nov 12, 2009 By duxh
	 * @param supportgroupId
	 * @return
	 */
	public List<SupportGroupEngineer> selectEngineersBySupportGroupId(Long supportgroupId) throws DaoException;
	/**
	 * ɾ��֧�������й���ʦ��
	 * @Methods Name deleteAllEngineers
	 * @Create In Nov 13, 2009 By duxh
	 * @param supportGroup
	 * @throws DaoException
	 */
	public void deleteAllEngineers(SupportGroup supportGroup) throws DaoException;
	/**
	 * ɾ��֧��������֧�ֵķ����
	 * @Methods Name deleteAllSupportGroupServiceItem
	 * @Create In Nov 13, 2009 By duxh
	 * @param supportGroup
	 * @throws DaoException
	 */
	public void deleteAllSupportGroupServiceItem(SupportGroup supportGroup) throws DaoException;
	/**
	 * ͨ��������ϲ�ѯ���е�֧����
	 * @Methods Name selectEngineersByServiceItems
	 * @Create In May 20, 2010 By huzh
	 * @param siList
	 * @return 
	 * @Return List<SupportGroupEngineer>
	 */
	public List<SupportGroup> selectSupportGroupsByServiceItems(List<ServiceItem> siList);
	/**
	 * ��ù���ʦ��������鳤
	 * @Methods Name selectCurrentGroupLeaders
	 * @Create In Jun 17, 2010 By huzh
	 * @param userId
	 * @return 
	 * @Return List<UserInfo>
	 */
	List<UserInfo> selectCurrentGroupLeaders(Long userId);
	/**
	 * ���������������е�֧���鹤��ʦ
	 * @Methods Name selectAllEngineers
	 * @Create In Jun 17, 2010 By huzh
	 * @param supportGroups
	 * @param itcode
	 * @param start
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	Page selectAllEngineers(List<SupportGroup> supportGroups, String itcode,
			int start, int pageSize);
	/**
	 * �����鳤������֧����
	 * @Methods Name selectSupportGroupsByLeader
	 * @Create In Jun 22, 2010 By huzh
	 * @param leader
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupsByLeader(UserInfo leader);
	/**
	 * ��ѯ֧������ѡ������
	 * @Methods Name selectServiceItemsBySupportGroupId
	 * @Create In Jul 5, 2010 By huzh
	 * @param supportGroupId
	 * @param official
	 * @return 
	 * @Return List<ServiceItem>
	 */
	List<ServiceItem> selectServiceItemsBySupportGroupId(Long supportGroupId,
			Integer official);
	/**
	 * ͨ��֧����Id�Լ�֧�����鳤Id����֧����
	 * @Methods Name selectSupportGroupByLeaderAndSupportId
	 * @Create In Jul 5, 2010 By huzh
	 * @param supportId
	 * @param leader
	 * @return 
	 * @Return SupportGroup
	 */
	SupportGroup selectSupportGroupByLeaderAndSupportId(Long supportId,
			Long leader);
	/**
	 * �����¼����Ͳ�ѯ�����е�֧����
	 * @Methods Name selectSupportGroupByEventType
	 * @Create In Jul 20, 2010 By huzh
	 * @param eventtype
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupByEventType(Long eventtype);
	/**
	 * ���ݹ���ʦ����ѯ֧���飨�Ȳ�һ��֧���飬û��һ��֧������ٲ����֧���飩
	 * @Methods Name selectSupportGroupByEngineer
	 * @Create In Jul 21, 2010 By huzh
	 * @param engineer
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupByEngineer(UserInfo engineer);
	/**
	 * ���ݷ���������ѯ֧���飨�Ȳ�һ��֧���飬û��һ��֧������ٲ����֧���飩
	 * @Methods Name selectSupportGroupByServiceItem
	 * @Create In Jul 21, 2010 By huzh
	 * @param sitem
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupByServiceItem(ServiceItem sitem);
	/**
	 * ���ݹ���ʦ������������֧����
	 * @Methods Name selectSupportGroupByEngineer
	 * @Create In Aug 9, 2010 By huzh
	 * @param userInfo
	 * @param scidData
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupByEngineer(UserInfo userInfo,
			ServiceItem scidData);
}
