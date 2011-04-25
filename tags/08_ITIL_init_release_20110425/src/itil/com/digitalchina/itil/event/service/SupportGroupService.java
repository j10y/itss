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
	 * 获得所有的服务项数据
	 * @param official 
	 * @Methods Name findServiceItemData
	 * @Create In Mar 17, 2009 By sujs
	 * @return List<ServiceItem>
	 */
	List<ServiceItem> findServiceItemData(String official);
	/**
	 * 获得服务商类型
	 * @Methods Name findServiceType
	 * @Create In Mar 18, 2009 By sujs
	 * @return List<ServiceProviderType>
	 */
//	List<ServiceProviderType>findServiceProviderType(String serviceProviderTypeId);
	/**
	 * 获取内部服务商
	 * @Methods Name findServiceProviderIn
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceProviderIn>
	 */
//	List<ServiceProviderIn> findServiceProviderIn();
	/**
	 * 获取外部服务商
	 * @Methods Name findServiceProviderOut
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceProviderOut>
	 */
//	List<ServiceProviderOut>findServiceProviderOut();
	/**
	 * 获得内部服务商工程师
	 * @Methods Name findServiceEngineerIn
	 * @Create In Mar 18, 2009 By sujs
	 * @return List<ServiceEngineerIn>
	 */
//	List<ServiceEngineerIn>findServiceEngineerIn(String serviceId);
	/**
	 * 获得外部服务商的服务工程师
	 * @Methods Name findServiceEngineerOut
	 * @Create In Mar 18, 2009 By Administrator
	 * @return List<ServiceEngineerOut>
	 */
//	List<ServiceEngineerOut>findServiceEngineerOut(String serviceId);
	/**
	 * 通过服务组长id查询工程师中是否含有服务组长
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
	 * 通过ids删除支持组中的记录
	 * 删除是逻辑删除 即将标志位置为删除状态。
	 * TODO
	 * Apr 3, 2009 By yukw
	 * @param ids
	 * @return TODO
	 */
	Boolean removeSupportGroupByIds(String[] ids);
	/**
	 * 查找服务工程师.
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
	 * 根据服务项查找所有支持组工程师（如果有一级，返回一级，否则返回二级）。
	 * @Methods Name findSupportGroupEngineer
	 * @Create In Oct 27, 2009 By duxh
	 * @param serviceItemId
	 * @return
	 * @throws ServiceException
	 * @return List<SupportGroupEngineer>
	 */
	 public List<SupportGroupEngineer> findSupportGroupEngineer(ServiceItem serviceItem) throws ServiceException;
	 /**
	  * 根据服务项、支持组组长、支持组工程师的itcode分页查询工程师。服务项根据event获取。
	  * @Methods Name findCurrentGroupEngineers
	  * @Create In Nov 4, 2009 By duxh
	  * @return Page
	  */
	 public Page findCurrentGroupEngineers(String eventId, UserInfo leader,String itcode,int start,int pageSize) throws ServiceException;
	 /**
	  * 根据支持组、支持组工程师的itcode分页查询工程师。
	  * @Methods Name findCurrentGroupEngineers
	  * @Create In Nov 4, 2009 By duxh
	  * @return Page
	  */
	 public Page findGroupEngineers(SupportGroup supportGroup,String itcode,int start,int pageSize) throws ServiceException;
	 /**
	 * 查找当前服务组组长
	 * @author awen
	 * @param userId
	 * @return
	 */
	UserInfo fingCurrentGroupLeader(Long userId, Long eventId);
	/**
	 * 根据服务项id数组，查询哪些服务项有一级支持组。
	 * @Methods Name findFirstRankGroup
	 * @Create In Nov 7, 2009 By duxh
	 * @param serviceItemsIdArray 服务项id数组。
	 * @param supportGroupId 如果request中的参数supportGroupId不为"",表示不验证次支持组所支持的服务项。
	 * @return List<String> 有一级支持组的服务项的名称，没有返回空。
	 * @throws ServiceException
	 */
	public List<String> findFirstRankGroup(Long[] serviceItemsIdArray,Long supportGroupId) throws ServiceException;
	/**
	 * 根据支持组查找所有支持组工程师。
	 * @Methods Name findEngineersBySupportGroupId
	 * @Create In Nov 12, 2009 By duxh
	 * @param supportgroupId
	 * @return
	 * @throws ServiceException
	 */
	public List<SupportGroupEngineer> findEngineersBySupportGroupId(Long supportGroupId) throws ServiceException;
	/**
	 * 保存支持组。
	 * @Methods Name saveSupportGroup
	 * @Create In Nov 12, 2009 By duxh
	 * @param supportGroupMap 支持组相关信息。
	 * @param engineersId 支持组包含的支持组工程师。
	 * @param serviceItemsId 支持组支持的服务项。
	 * @throws ServiceException
	 */
	public void saveSupportGroup(HashMap supportGroupMap,Long[] engineersId,Long[] serviceItemsId)throws ServiceException;
	
	/**
	 * 修改支持组。
	 * @Methods Name modifySupportGroup
	 * @Create In Nov 12, 2009 By duxh
	 * @param supportGroupMap 支持组相关信息。
	 * @param engineersId 支持组包含的支持组工程师。
	 * @param serviceItemsId 支持组支持的服务项。
	 * @throws ServiceException
	 */
	public void modifySupportGroup(HashMap supportGroupMap,Long[] engineersId,Long[] serviceItemsId)throws ServiceException;
	/**
	 * 获取所有内部支持组工程师
	 * @Methods Name findAllEngineerIn
	 * @Create In Apr 16, 2010 By lee
	 * @return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> findAllEngineerIn();
	/**
	 * 根据事件类型获得所有内部支持组工程师
	 * @Methods Name findSupportGroupEngineerByEventType
	 * @Create In May 19, 2010 By huzh
	 * @param eventtype
	 * @return 
	 * @Return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> findSupportGroupEngineersByEventType(String eventtype);
	/**
	 * 得到工程师所在组的支持组组长
	 * @Methods Name fingCurrentGroupLeaders
	 * @Create In Jun 17, 2010 By huzh
	 * @param userId
	 * @return 
	 * @Return List<UserInfo>
	 */
	List<UserInfo> fingCurrentGroupLeaders(Long userId);
	/**
	 * 查询支持组所选服务项
	 * @Methods Name findServiceItemsBySupportGroupId
	 * @Create In Jul 5, 2010 By huzh
	 * @param supportGroupId
	 * @param official
	 * @return 
	 * @Return List<ServiceItem>
	 */
	List<ServiceItem> findServiceItemsBySupportGroupId(Long supportGroupId,Integer official);
	/**
	 * 通过支持组Id以及支持组组长Id查找支持组
	 * @Methods Name findSupportGroupByLeaderAndSupportId
	 * @Create In Jul 5, 2010 By huzh
	 * @param parseLong
	 * @param id
	 * @return 
	 * @Return SupportGroup
	 */
	SupportGroup findSupportGroupByLeaderAndSupportId(Long supportId, Long leader);
	/**
	 * 根据事件类型查询出所有的支持组
	 * @Methods Name findSupportGroupByEventType
	 * @Create In Jul 20, 2010 By huzh
	 * @param eventtype
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> findSupportGroupByEventType(Long eventtype);
	/**
	 * 根据工程师来查询支持组（先查一级支持组，没有一级支持组的再查二级支持组）
	 * @Methods Name findSupportGroupByEngineer
	 * @Create In Jul 21, 2010 By huzh
	 * @param engineer
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> findSupportGroupByEngineer(UserInfo engineer);
	/**
	 * 根据服务项来查询支持组（先查一级支持组，没有一级支持组的再查二级支持组）
	 * @Methods Name findSupportGroupByServiceItem
	 * @Create In Jul 21, 2010 By huzh
	 * @param sitem
	 * @return 
	 * @Return List<SupportGroup>
	 */
	List<SupportGroup> findSupportGroupByServiceItem(ServiceItem sitem);
	/**
	 * 根据工程师以及服务项来找支持组
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
