package com.digitalchina.itil.event.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.itil.actor.entity.SupportGroup;
import com.digitalchina.itil.actor.entity.SupportGroupEngineer;
import com.digitalchina.itil.actor.entity.SupportGroupServiceItem;
import com.digitalchina.itil.event.dao.EventDao;
import com.digitalchina.itil.event.dao.SupportGroupDao;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventType;
import com.digitalchina.itil.event.service.SupportGroupService;
import com.digitalchina.itil.service.entity.ServiceItem;

public class SupportGroupServiceImpl extends BaseService implements
		SupportGroupService {
	private MetaDataManager metaDataManager;
	private SupportGroupDao supportGroupDao;
	private EventDao eventDao;
	@SuppressWarnings("unchecked")
	/**
	 * 获得服务项数据
	 */
	public List<ServiceItem> findServiceItemData(String official) {
		return supportGroupDao.selectServiceItemData(official);
	}

	/**
	 * 获得支持组的数据
	 */
	@SuppressWarnings("unchecked")
	public List<SupportGroupServiceItem> findSupportGroupData(String supportId) {
		return supportGroupDao.selectSupportGroupData(supportId);
	}

	/**
	 * 获得服务商类型
	 */

//	@SuppressWarnings("unchecked")
//	public List<ServiceProviderType> findServiceProviderType(
//			String serviceProviderTypeId) {
//		return supportGroupDao.selectServiceProviderType(serviceProviderTypeId);
//	}

	/**
	 * 获取内部服务商
	 */
//	@SuppressWarnings("unchecked")
//	public List<ServiceProviderIn> findServiceProviderIn() {
//		return supportGroupDao.selectServiceProviderIn();
//	}

	/**
	 * 获取外部服务商
	 */
//	@SuppressWarnings("unchecked")
//	public List<ServiceProviderOut> findServiceProviderOut() {
//		return supportGroupDao.selectServiceProviderOut();
//	}

	/**
	 * 获得内部服务商工程师
	 */
//	@SuppressWarnings("unchecked")
//	public List<ServiceEngineerIn> findServiceEngineerIn(String serviceId) {
//		return supportGroupDao.selectServiceEngineerIn(serviceId);
//	}

	/**
	 * 获得外部服务商工程师
	 */
//	@SuppressWarnings("unchecked")
//	public List<ServiceEngineerOut> findServiceEngineerOut(String serviceId) {
//		return supportGroupDao.selectServiceEngineerOut(serviceId);
//	}

	/**
	 * 通过服务组长id查询工程师中是否含有服务组长
	 */
	@SuppressWarnings("unchecked")
	public List<SupportGroupEngineer> findServiceLead(String id) {
		return supportGroupDao.selectServiceLead(id);
	}

	public Boolean removeSupportGroupByIds(String[] ids) {
		return supportGroupDao.deleteSupportGroupByIds(ids);
	}
	public List<SupportGroupServiceItem> findSupportGroupServiceItem(ServiceItem keyword) {
		return supportGroupDao.selectSupportGroupServiceItem(keyword);
	}

//	public Page findServiceEngineer(Long serviceProviderType, Long serviceProvider,String itcode,int start,int pageSize) {
//		return supportGroupDao.selectServiceEngineer(serviceProviderType, serviceProvider,itcode,start, pageSize);
//	}

	public List<SupportGroupEngineer> findSupportGroupEngineer(ServiceItem serviceItem){
		List<SupportGroupEngineer> engineers=new ArrayList<SupportGroupEngineer>();
    	try {
			List<SupportGroup> supportGroups=supportGroupDao.selectAllGroup(serviceItem);
			//2010-06-04 modified by huzh for 优化 begin
			 List<SupportGroup> firstGroups=new ArrayList<SupportGroup>();
			 List<SupportGroup> secondGroups=new ArrayList<SupportGroup>();
			 if(supportGroups!=null&&supportGroups.size()!=0){
  		   for(SupportGroup supportGroup : supportGroups){
  			   if(supportGroup.getGroupRank().getKeyString().equals("first")){
	   					firstGroups.add(supportGroup);
	   				}else if(supportGroup.getGroupRank().getKeyString().equals("second")){
	   					secondGroups.add(supportGroup);
	   				}
  		   }
//			for(SupportGroup supportGroup:supportGroups){
//				if(supportGroup.getGroupRank().getKeyString().equals("first")){
//					engineers=supportGroupDao.selectAllEngineer(supportGroup);
//					break;
//				}
//			}
  		 if(firstGroups!=null&&firstGroups.size()!=0){//先找一级支持组工程师
			   engineers=supportGroupDao.selectAllEngineer(firstGroups);
		   }
	  	if((engineers==null||engineers.size()==0)&&secondGroups.size()!=0){//一级没有，再找二级工程师
	  		   engineers=supportGroupDao.selectAllEngineer(secondGroups);  
	  	   }
//	   if(engineers==null||engineers.size()==0){//一个支持组不是一级就是二级
//			engineers=supportGroupDao.selectAllEngineer(supportGroups);
//	   }
		 }
			//2010-06-04 modified by huzh for 优化 end
		} catch (Exception e) {
			e.printStackTrace();
			throw new  ServiceException();
		}
    	return engineers;
    }
	
	public UserInfo fingCurrentGroupLeader(Long userId, Long eventId) {
		try {
			return supportGroupDao.selectCurrentGroupLeader(userId, eventId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	public Page findCurrentGroupEngineers(String eventId, UserInfo leader,
			String itcode, int start, int pageSize) throws ServiceException {
		try {
//			List<SupportGroup> supportGroups=supportGroupDao.selectSupportGroupList(
//					   ((Event)find(Event.class, eventId)).getScidData(),
//						leader);
			List<SupportGroup> supportGroups=supportGroupDao.selectSupportGroupsByLeader(leader);
			//2010-06-17 modified by huzh for 获得组工程师修改 begin
//			return supportGroupDao.selectAllEngineer(supportGroups, itcode, start, pageSize);
			return supportGroupDao.selectAllEngineers(supportGroups, itcode, start, pageSize);
			//2010-06-17 modified by huzh for 获得组工程师修改 end
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Page findGroupEngineers(SupportGroup supportGroup, String itcode, int start,
			int pageSize) throws ServiceException {
		try {
			ArrayList<SupportGroup> supportGroups=new ArrayList<SupportGroup>();
			if(supportGroup!=null){
				supportGroups.add(supportGroup);
			}
			return supportGroupDao.selectAllEngineer(supportGroups, itcode, start, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<String> findFirstRankGroup(Long[] serviceItemsIdArray,Long supportGroupId) throws ServiceException {
		try {
			return supportGroupDao.selectFirstRankGroup(serviceItemsIdArray,supportGroupId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<SupportGroupEngineer> findEngineersBySupportGroupId(Long supportGroupId) throws ServiceException {
		try {
			return supportGroupDao.selectEngineersBySupportGroupId(supportGroupId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void saveSupportGroup(HashMap supportGroupMap, Long[] engineersId, Long[] serviceItemsId)
			throws ServiceException {
		//保存支持组
		SupportGroup supportGroup=(SupportGroup) metaDataManager.saveEntityData(SupportGroup.class, supportGroupMap);
		Long groupLeader=null;//判断组长在不在组员当中。
		for(int i=0;i<engineersId.length;i++){//保存支持组工程师。
			if(engineersId[i].compareTo(supportGroup.getGroupLeader().getId())==0){
				groupLeader=engineersId[i];
			}
			SupportGroupEngineer engineer =new SupportGroupEngineer();
			UserInfo userInfo=new UserInfo();
			userInfo.setId(engineersId[i]);
			engineer.setSupportGroup(supportGroup);
			engineer.setUserInfo(userInfo);
			save(engineer);
		}
		if(groupLeader==null){//如果组长不在组员中，将其添加进去。
			SupportGroupEngineer engineer =new SupportGroupEngineer();
			engineer.setUserInfo(supportGroup.getGroupLeader());
			engineer.setSupportGroup(supportGroup);
			save(engineer);
		}
		for(int i=0;i<serviceItemsId.length;i++){//保存支持组支持的服务项。
			SupportGroupServiceItem  supportGroupServiceItem  =new SupportGroupServiceItem();
			ServiceItem serviceItem=new ServiceItem();
			serviceItem.setId(serviceItemsId[i]);
			supportGroupServiceItem.setSupportGroup(supportGroup);
			supportGroupServiceItem.setServiceItem(serviceItem);
			save(supportGroupServiceItem);
		}
		
	}

	public void modifySupportGroup(HashMap supportGroupMap, Long[] engineersId, Long[] serviceItemsId)
			throws ServiceException {
		//保存支持组
		SupportGroup supportGroup=(SupportGroup) metaDataManager.saveEntityData(SupportGroup.class, supportGroupMap);
		supportGroupDao.deleteAllEngineers(supportGroup);//删除原有支持组工程师
		Long groupLeader=null;//判断组长在不在组员当中。
		for(int i=0;i<engineersId.length;i++){//保存支持组工程师。
			if(engineersId[i].compareTo(supportGroup.getGroupLeader().getId())==0){
				groupLeader=engineersId[i];
			}
			SupportGroupEngineer engineer =new SupportGroupEngineer();
			UserInfo userInfo=new UserInfo();
			userInfo.setId(engineersId[i]);
			engineer.setSupportGroup(supportGroup);
			engineer.setUserInfo(userInfo);
			save(engineer);
		}
		if(groupLeader==null){//如果组长不在组员中，将其添加进去。
			SupportGroupEngineer engineer =new SupportGroupEngineer();
			engineer.setUserInfo(supportGroup.getGroupLeader());
			engineer.setSupportGroup(supportGroup);
			save(engineer);
		}
		//删除原支持组支持的服务项。
		supportGroupDao.deleteAllSupportGroupServiceItem(supportGroup);
		for(int i=0;i<serviceItemsId.length;i++){//保存支持组支持的服务项。
			SupportGroupServiceItem  supportGroupServiceItem  =new SupportGroupServiceItem();
			ServiceItem serviceItem=new ServiceItem();
			serviceItem.setId(serviceItemsId[i]);
			supportGroupServiceItem.setSupportGroup(supportGroup);
			supportGroupServiceItem.setServiceItem(serviceItem);
			save(supportGroupServiceItem);
		}
		
	}

	public void setMetaDataManager(MetaDataManager metaDataManager) {
		this.metaDataManager = metaDataManager;
	}

	public void setSupportGroupDao(SupportGroupDao supportGroupDao) {
		this.supportGroupDao = supportGroupDao;
	}

	public List<SupportGroupEngineer> findAllEngineerIn() {
		return this.findAll(SupportGroupEngineer.class);
	}

	public List<SupportGroupEngineer> findSupportGroupEngineersByEventType(String eventtype) {
		    List<SupportGroupEngineer> engineers=new ArrayList<SupportGroupEngineer>();
           List<ServiceItem> siList=eventDao.selectAllServiceItemByEventType(eventtype);
           if(siList!=null&&siList.size()!=0){
        	   List<SupportGroup> groups=supportGroupDao.selectSupportGroupsByServiceItems(siList);
        	   if(groups!=null&&groups.size()!=0){
        		   List<SupportGroup> firstGroups=new ArrayList<SupportGroup>();
        		   List<SupportGroup> secondGroups=new ArrayList<SupportGroup>();
        		   for(SupportGroup supportGroup : groups){
        			   if(supportGroup.getGroupRank().getKeyString().equals("first")){
		   					firstGroups.add(supportGroup);
		   				}else if(supportGroup.getGroupRank().getKeyString().equals("second")){
		   					secondGroups.add(supportGroup);
		   				}
        		   }
//        		   for(SupportGroup supportGroup : groups){
//		   				if(supportGroup.getGroupRank().getKeyString().equals("first")){
//		   					engineers.addAll(supportGroupDao.selectAllEngineer(supportGroup));
//		   				}
//	   			   }
        		   if(firstGroups!=null&&firstGroups.size()!=0){//先找一级支持组工程师
        			   engineers=supportGroupDao.selectAllEngineer(firstGroups);
        		   }
	        	   if((engineers==null||engineers.size()==0)&&secondGroups.size()!=0){//一级没有，再找二级工程师
//	        		   for(SupportGroup supportGroup : groups){
//			   				if(supportGroup.getGroupRank().getKeyString().equals("second")){
//			   					engineers.addAll(supportGroupDao.selectAllEngineer(supportGroup));
//			   				}
//		   			   }   
	        		   engineers=supportGroupDao.selectAllEngineer(secondGroups);  
	        	   }
//	        	   if(engineers==null||engineers.size()==0){
//			   			engineers.addAll(supportGroupDao.selectAllEngineer(groups));
//		   		   }   
	          }
        	}
		return engineers;
		
	}


	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}

	public List<UserInfo> fingCurrentGroupLeaders(Long userId) {
		try {
			return supportGroupDao.selectCurrentGroupLeaders(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<ServiceItem> findServiceItemsBySupportGroupId(
			Long supportGroupId, Integer official) {
		try {
			return supportGroupDao.selectServiceItemsBySupportGroupId(supportGroupId,official);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public SupportGroup findSupportGroupByLeaderAndSupportId(Long supportId, Long leader) {
		try {
			return supportGroupDao.selectSupportGroupByLeaderAndSupportId(supportId,leader);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<SupportGroup> findSupportGroupByEventType(Long eventtype) {
		try {
			return supportGroupDao.selectSupportGroupByEventType(eventtype);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<SupportGroup> findSupportGroupByEngineer(UserInfo engineer) {
		try {
			return supportGroupDao.selectSupportGroupByEngineer(engineer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<SupportGroup> findSupportGroupByServiceItem(ServiceItem sitem) {
		try {
			return supportGroupDao.selectSupportGroupByServiceItem(sitem);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<SupportGroup> findSupportGroupByEngineer(UserInfo userInfo,
			ServiceItem scidData) {
		try {
			return supportGroupDao.selectSupportGroupByEngineer(userInfo,
					scidData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
}
