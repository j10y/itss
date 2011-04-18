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
	 * 获得所有的服务项数据
	 * @param official 
	 * 
	 * @Methods Name findServiceItemData
	 * @Create In Mar 17, 2009 By sujs
	 * @return List<ServiceItem>
	 */
	List<ServiceItem> selectServiceItemData(String official);

	/**
	 * 获得服务商类型
	 * 
	 * @Methods Name findServiceType
	 * @Create In Mar 18, 2009 By sujs
	 * @return List<ServiceProviderType>
	 */
//	List<ServiceProviderType> selectServiceProviderType(String serviceProviderTypeId);

	/**
	 * 获取内部服务商
	 * 
	 * @Methods Name findServiceProviderIn
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceProviderIn>
	 */
//	List<ServiceProviderIn> selectServiceProviderIn();

	/**
	 * 获取外部服务商
	 * 
	 * @Methods Name findServiceProviderOut
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceProviderOut>
	 */
//	List<ServiceProviderOut> selectServiceProviderOut();

	/**
	 * 获得内部服务商工程师
	 * 
	 * @Methods Name findServiceEngineerIn
	 * @Create In Mar 18, 2009 By sujs
	 * @return List<ServiceEngineerIn>
	 */
//	List<ServiceEngineerIn> selectServiceEngineerIn(String serviceId);

	/**
	 * 获得外部服务商的服务工程师
	 * 
	 * @Methods Name findServiceEngineerOut
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceEngineerOut>
	 */
//	List<ServiceEngineerOut> selectServiceEngineerOut(String serviceId);

	/**
	 * 通过服务组长id查询工程师中是否含有服务组长
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
	 * 通过ids删除支持组中的记录 删除是逻辑删除 即将标志位置为删除状态。 TODO Apr 3, 2009 By yukw
	 * 
	 * @param ids
	 * @return TODO
	 */
	Boolean deleteSupportGroupByIds(String[] ids);
	/**
	 * 查询服务工程师。
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
	 * 根据服务项查找所有的支持组。
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
	 * 根据支持组查找所有支持组工程师。
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
	 * 根据支持组、支持组工程师的itcode分页查询支持组工程师。
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
	 * 根据支持组查找所有支持组工程师。
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
	 * 查找当前服务组组长
	 * 
	 * @Methods Name selectCurrentGroupLeader
	 * @Create In Oct 30, 2009 By duxh
	 * @return UserInfo
	 */
	UserInfo selectCurrentGroupLeader(Long userId, Long eventId);

	/**
	 * 根据服务项和支持组组长,查找所有支持组。
	 * 
	 * @Methods Name selectSupportGroupList
	 * @Create In Nov 3, 2009 By duxh
	 * @return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupList(ServiceItem serviceItem, UserInfo leader);
	/**
	 * 根据服务项id数组，查询哪些服务项有一级支持组。
	 * @Methods Name selectFirstRankGroup
	 * @Create In Nov 6, 2009 By duxh
	 * @param serviceItemsIdArray 服务项id数组。
	 * @param supportGroupId 如果request中的参数supportGroupId不为"",表示不验证次支持组所支持的服务项。
	 * @return List<String> 有一级支持组的服务项的名称，没有返回空。
	 * @throws DaoException
	 */
	public List<String> selectFirstRankGroup(Long[] serviceItemsIdArray,Long supportGroupId) throws DaoException;
	/**
	 * 根据支持组查找所有支持组工程师。
	 * @Methods Name findEngineersBySupportGroupId
	 * @Create In Nov 12, 2009 By duxh
	 * @param supportgroupId
	 * @return
	 */
	public List<SupportGroupEngineer> selectEngineersBySupportGroupId(Long supportgroupId) throws DaoException;
	/**
	 * 删除支持组所有工程师。
	 * @Methods Name deleteAllEngineers
	 * @Create In Nov 13, 2009 By duxh
	 * @param supportGroup
	 * @throws DaoException
	 */
	public void deleteAllEngineers(SupportGroup supportGroup) throws DaoException;
	/**
	 * 删除支持组所有支持的服务项。
	 * @Methods Name deleteAllSupportGroupServiceItem
	 * @Create In Nov 13, 2009 By duxh
	 * @param supportGroup
	 * @throws DaoException
	 */
	public void deleteAllSupportGroupServiceItem(SupportGroup supportGroup) throws DaoException;
	/**
	 * 通过服务项集合查询所有的支持组
	 * @Methods Name selectEngineersByServiceItems
	 * @Create In May 20, 2010 By huzh
	 * @param siList
	 * @return 
	 * @Return List<SupportGroupEngineer>
	 */
	public List<SupportGroup> selectSupportGroupsByServiceItems(List<ServiceItem> siList);
	/**
	 * 获得工程师所在组的组长
	 * @Methods Name selectCurrentGroupLeaders
	 * @Create In Jun 17, 2010 By huzh
	 * @param userId
	 * @return 
	 * @Return List<UserInfo>
	 */
	List<UserInfo> selectCurrentGroupLeaders(Long userId);
	/**
	 * 根据条件查找所有的支持组工程师
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
	 * 根据组长找所有支持组
	 * @Methods Name selectSupportGroupsByLeader
	 * @Create In Jun 22, 2010 By huzh
	 * @param leader
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupsByLeader(UserInfo leader);
	/**
	 * 查询支持组所选服务项
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
	 * 通过支持组Id以及支持组组长Id查找支持组
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
	 * 根据事件类型查询出所有的支持组
	 * @Methods Name selectSupportGroupByEventType
	 * @Create In Jul 20, 2010 By huzh
	 * @param eventtype
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupByEventType(Long eventtype);
	/**
	 * 根据工程师来查询支持组（先查一级支持组，没有一级支持组的再查二级支持组）
	 * @Methods Name selectSupportGroupByEngineer
	 * @Create In Jul 21, 2010 By huzh
	 * @param engineer
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupByEngineer(UserInfo engineer);
	/**
	 * 根据服务项来查询支持组（先查一级支持组，没有一级支持组的再查二级支持组）
	 * @Methods Name selectSupportGroupByServiceItem
	 * @Create In Jul 21, 2010 By huzh
	 * @param sitem
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> selectSupportGroupByServiceItem(ServiceItem sitem);
	/**
	 * 根据工程师及服务项来找支持组
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
