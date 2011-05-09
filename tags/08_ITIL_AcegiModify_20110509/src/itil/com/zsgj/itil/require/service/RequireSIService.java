package com.zsgj.itil.require.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.SCIRelationShipType;
import com.zsgj.itil.service.entity.ServiceCatalogue;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;

/**
 * 需求服务目录服务
 * @Class Name RequireSIService
 * @Author sa
 * @Create In 2009-2-11
 */
public interface RequireSIService {

	/**
	 * 获取需求功能中用户应该看到的所有服务目录所属的客户id
	 * @Methods Name findCustIdByUser
	 * @Create In 2009-2-11 By sa
	 * @param userInfo
	 * @return List<Long>
	 */
	List<Long> findCustIdByUser(UserInfo userInfo);
	
	/**
	 * 提供客户id获取所有的服务目录
	 * @Methods Name findServiceCatalogueByCust
	 * @Create In 2009-2-11 By sa
	 * @param custIds
	 * @return Page
	 */
	List<ServiceCatalogue> findServiceCatalogueByCust(List<Long> custIds);
	/**
	 * 通过服务目录获取关联的包含常规服务的服务关系数据
	 * @Methods Name findGeneralSCIRelationShipByCata
	 * @Create In Mar 15, 2009 By lee
	 * @param serviceCatalogues
	 * @return List<SCIRelationShip>
	 */
	//List<SCIRelationShip> findGeneralSCIRelationShipByCata(List<ServiceCatalogue> serviceCatalogues);//remove by lee for scrap function in 20090625
	/**
	 * 通过服务目录获取关联的包含个性化服务的服务关系数据
	 * @Methods Name findSpecialSCIRelationShipByCata
	 * @Create In Mar 15, 2009 By lee
	 * @param serviceCatalogues
	 * @return List<SCIRelationShip>
	 */
	//List<SCIRelationShip> findSpecialSCIRelationShipByCata(List<ServiceCatalogue> serviceCatalogues);//remove by lee for scrap function in 20090625
	/*
	 * 根据服务目录获取服务关系数据
	 */
	List<SCIRelationShip> findSCIRelationShipByService(List<ServiceCatalogue> ServiceCatalogue);
	/**
	 * 通过服务关系id获取包含常规服务的子服务目录关系数据
	 * @Methods Name findGeneralSCIRelationShipById
	 * @Create In Mar 15, 2009 By lee
	 * @param sCIRelationShipId
	 * @param storeData
	 * @return List<SCIRelationShip>
	 */
	//List<SCIRelationShip> findGeneralSCIRelationShipById(String sCIRelationShipId,Map storeData);//remove by lee for scrap function in 20090625 
	/**
	 * 通过服务关系id获取包含个性化服务的子服务目录关系数据
	 * @Methods Name findSpecialSCIRelationShipById
	 * @Create In Mar 15, 2009 By lee
	 * @param sCIRelationShipId
	 * @param storeData
	 * @return List<SCIRelationShip>
	 */
	//List<SCIRelationShip> findSpecialSCIRelationShipById(String sCIRelationShipId,Map storeData);//remove by lee for scrap function in 20090625
	/**
	 * 根据服务关系id获取子服务关系数据
	 */
	List<SCIRelationShip> findSCIRelationShipById(String sCIRelationShipId,Map storeData,String serviceTypeKeyWord);
	/**
	 * 
	 * 通过服务关系id获得服务目录数据
	 * 
	 * */
	SCIRelationShip findServiceCatalogueByRelationId(String sCIRelationShipId);
	/**
	 * 通过动态的clazz来获取其中的数据
	 * @Methods Name findAutoClazz
	 * @Create In Mar 4, 2009 By sujs
	 * @param clazz
	 * @return List
	 */
	List findAutoClazz(String clazz);
	
	/**
	 * 通过服务项获取服务项需求主表关联的面板
	 * @Methods Name getPanelsByServiceItem
	 * @Create In Feb 27, 2009 By lee
	 * @param serviceItem
	 * @return List<PageModelPanel>
	 */
	List<PageModelPanel> getPanelsByServiceItem(ServiceItem serviceItem);
	/**
	 * 通过需求主表名和服务项获取该服务项对应数据
	 * @Methods Name findEntities
	 * @Create In Apr 8, 2009 By lee
	 * @param className
	 * @param serviceItemId
	 * @return List
	 */
	List findEntities(String className, String serviceItemId);
	/**
	 * 通过服务关系获得keyword
	 * @Methods Name findNodeKeyWords
	 * @Create In May 7, 2009 By Administrator
	 * @param sCIRelationShip
	 * @return List
	 */
	List<SCIRelationShipType> findNodeKeyWord(SCIRelationShip sCIRelationShip);
	/**
	 * 获取用户查看可查看数据权限的用户,为过滤当前用户查看提交
	 * @Methods Name findDataScopeByUser
	 * @Create In Aug 18, 2009 By lee
	 * @param userInfo
	 * @return List<UserInfo> 返回null则表示用户拥有顶级部门查看所有数据权限
	 */
	List<UserInfo> findDataScopeByUser(UserInfo userInfo);
	/**
	 * 通过当前用户角色权限获取可查看数据,为过滤当前用户查看提交
	 * @Methods Name findEntities
	 * @Create In Aug 18, 2009 By lee
	 * @param className		类名称
	 * @param id			服务项id
	 * @param users			查看用户范围(如果user为空，则表示可查看所有数据)
	 * @return List
	 */
	List findEntities(String className, String id, List<UserInfo> users);
	
	/**
	 * 删除指定申请的审批历史
	 * @Methods Name removeHisByApply
	 * @Create In 2009-11-26 By zhangzy
	 * @param className	//申请类
	 * @param dataId void	//申请ID
	 */
	void removeHisByApply(String className,String dataId);
	/**
	 * 通过当前用户获取用户审批过的流程数据
	 * @Methods Name findAuditHisEntities
	 * @Create In Aug 29, 2009 By lee
	 * @param className		类名称
	 * @param siId			服务项ID
	 * @param user			用户
	 * @return List
	 */
	List findAuditHisEntities(String className, String siId, UserInfo user);
	
	/**
	 * 通过当前用户获取用户审批过的最后一条流程历史
	 * @Methods Name findLastHis
	 * @Create In Aug 30, 2009 By lee
	 * @param id	
	 * @param siId
	 * @param user
	 * @return ServiceItemApplyAuditHis
	 */
	ServiceItemApplyAuditHis findLastHis(String id, String siId, UserInfo user);
	
	/**
	 * 获取工作流节点对应页面名称 
	 * @Methods Name getPageModelNameForNode
	 * @Create In Aug 30, 2009 By lee
	 * @param processId		//虚拟流程ID
	 * @param nodeId		//节点ID
	 * @return String 		//pageModel名称
	 */
	String getPageModelNameByNode(String processId, String nodeId);
}
