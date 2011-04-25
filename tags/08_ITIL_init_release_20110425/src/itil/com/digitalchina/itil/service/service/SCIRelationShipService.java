package com.digitalchina.itil.service.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.ServiceCatalogue;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemType;
import com.digitalchina.itil.service.entity.ServiceType;

/**
 * 服务目录关系服务
 * @Class Name SCIRelationShipService
 * @Author sa
 * @Create In 2009-1-14
 */
public interface SCIRelationShipService {
	
	/**
	 * 提供服务目录id获取此目录下的根服务目录关系
	 * @Methods Name findRootRelationShip
	 * @Create In 2009-1-14 By sa
	 * @param serviceCatalogueId
	 * @return SCIRelationShip
	 */
	SCIRelationShip findRootRelationShip(String serviceCatalogueId);
	
	/**
	 * 通过id获取服务项关系，主动抓取父服务项关系
	 * @Methods Name findSCIRelationShip
	 * @Create In 2009-1-14 By sa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	SCIRelationShip findSCIRelationShipWithParentById(String sciRelationShipId);
	/**
	 * 
	 * @Methods Name findSCIRelationShip
	 * @Create In 2009-1-14 By sa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findSCIRelationShip(Map params, int pageNo, int pageSize);
	/**
	 * 通过ID获取服务目录关系
	 * @Methods Name findSCIRelationShipById
	 * @Create In 2009-1-14 By lee
	 * @param sciRelationShipId
	 * @return SCIRelationShip
	 */
	SCIRelationShip findSCIRelationShipById(String sciRelationShipId);
	/**
	 * 保存服务目录关系
	 * @Methods Name findSCIRelationShipById
	 * @Create In 2009-1-14 By lee
	 * @param sciRelationShip
	 * @return SCIRelationShip
	 */
	SCIRelationShip save(SCIRelationShip sciRelationShip);
	/**
	 * 根据ID删除服务目录关系,级联删除所有下级服务目录关系
	 * @Methods Name removeById
	 * @Create In 2009-1-14 By lee
	 * @param id
	 * @return void
	 */
	void remove(SCIRelationShip sciRelationShip);
	/**
	 * 获取服务目录关系的子关系
	 * @Methods Name findChildRelationShipByParent
	 * @Create In 2009-1-14 By lee
	 * @param parentRelationShip
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findChildRelationShipByParent(SCIRelationShip parentRelationShip);
	/**
	 * 获取服务目录关系的所有子关系
	 * @Methods Name findAllChildRelationShipByParent
	 * @Create In 2009-1-14 By lee
	 * @param parentRelationShip
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findAllChildRelationShipByParent(SCIRelationShip parentRelationShip);
	
	/**
	 * 根据服务组合id获取服务组合下所有服务项
	 * @Methods Name findServiceItemByPage
	 * @Create In Jan 15, 2009 By Administrator
	 * @param serviceItemName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findServiceItemByPage(String serviceItemName ,int pageNo, int pageSize);
	/**
	 * 根据id找服务目录
	 * @param id
	 * @return
	 */
	ServiceCatalogue findServiceItemById(String id);
	/**
	 * 通过根服务目录获取根服务目录关系
	 * @Methods Name findRootRelationShipByRootCata
	 * @Create In 2009-1-14 By lee
	 * @param serviceCatalogue
	 * @return SCIRelationShip
	 */
	SCIRelationShip findRootRelationShipByRootCata(ServiceCatalogue serviceCatalogue);
	/**
	 * 获取从参数服务目录关系到根关系一条关系线上的所有关系实体
	 * @Methods Name findRelationShipsInLineToRoot
	 * @Create In 2009-1-14 By lee
	 * @param sciRelationShip
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findRelationShipsInLineToRoot(SCIRelationShip sciRelationShip);
	/**
	 * 判断如何将子关系添加到父关系后是否形成环状关系
	 * @Methods Name isRingRelation
	 * @Create In 2009-1-14 By lee
	 * @param parentRelationShip
	 * @param childRelationShip
	 * @return boolean
	 */
	boolean isRingRelation(SCIRelationShip parentRelationShip,SCIRelationShip childRelationShip);
	/**
	 * 判断如何将子关系添加到父关系后是否出现两个包含相同实体的关系
	 * @Methods Name isDoubleSameChilds
	 * @Create In 2009-1-14 By lee
	 * @param parentRelationShip
	 * @param childRelationShip
	 * @return boolean
	 */
	boolean isDoubleSameChilds(SCIRelationShip parentRelationShip,SCIRelationShip childRelationShip);
	/**
	 * 用于将服务目录Id保存在服务目录合同表中
	 * @Methods Name isDoubleSameChilds
	 * @Create in sujs
	 */
	void saveServiceCatalogueIdToContract (String ServiceCatalogueId);
	/**
	 * 通过服务目录Id获得服务合同Id
	 * @Methods Name getServiceCatalogueContractId
	 * @Create In Mar 1, 2009 By sujs
	 * @param ServiceCatalogueId
	 * @return String
	 */
	String getServiceCatalogueContractId(String ServiceCatalogueId); 
	/**
	 * 通过服务目录ID取得所有服务项信息并保存到ServiceItemSLA表中
	 * @Methods Name saveServiceItemSLAfromservicelogueId
	 * @Create In Mar 1, 2009 sujs
	 * @param ServiceCatalogueId void
	 */
	void saveServiceItemSLAfromservicelogueId(String ServiceCatalogueId );
	/**
	 * 根据服务项名称和类型获取服务项
	 * @Methods Name findServiceItemByPage
	 * @Create In Mar 22, 2009 By lee
	 * @param searchName
	 * @param searchType
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findServiceItemByPage(String searchName, ServiceItemType serviceItemType,int pageNo, int pageSize);
	/**
	 * 根据旧的服务目录id得到他的所有的子关系
	 * @Methods Name getChildSCIRelationShipsByOldId
	 * @Create In Apr 8, 2009 By Administrator
	 * @param oldId
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> getChildSCIRelationShipsByServiceCata(ServiceCatalogue serviceCatalogue);
	/**
	 * 根据服务目录草稿的主id删除他的所有关系及关联的合同和SLA信息
	 * @Methods Name removeServiceCataByRootId
	 * @Create In Apr 13, 2009 By Administrator
	 * @param rootServiceCataId void
	 */
	void removeServiceCataByRootId(String rootServiceCataId);
	/**
	 * 通过根得到他的所有的子服务目录
	 * @Methods Name findServiceCataShipByRoot
	 * @Create In Apr 20, 2009 By Administrator
	 * @param serviceCatalogue
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findServiceCataShipByRoot(ServiceCatalogue serviceCatalogue);
	/**
	 * 通过根删除他的子服务目录关系，要先子后父
	 * @Methods Name removeAllChildServiceCata
	 * @Create In Apr 22, 2009 By Administrator
	 * @param rootRelationShip void
	 */
	void removeAllChildServiceCataShip(SCIRelationShip rootRelationShip);
	/**
	 * 服务目录审批通过后保存历史
	 * @Methods Name saveServiceCataEvent
	 * @Create In Apr 29, 2009 By Administrator
	 * @param serviceCatalogue void
	 */
	void saveServiceCataEvent(ServiceCatalogue serviceCatalogue);
	/**
	 * 服务目录变更审批通过后拷贝和保存历史
	 * @Methods Name saveServiceCataAlterEvent
	 * @Create In Apr 29, 2009 By Administrator
	 * @param newServiceCatalogue
	 * @param oldServiceCatalogue void
	 */
	void saveServiceCataAlterEvent(ServiceCatalogue newServiceCatalogue,ServiceCatalogue oldServiceCatalogue);
	
	/**
	 * 获取用户服务目录树形显示数据
	 * @Methods Name getUserServiceCataJson
	 * @Create In Nov 18, 2009 By lee
	 * @param userInfo
	 * @return String
	 */
	String getUserServiceCataJson(UserInfo userInfo);
	
	/**
	 * 获取用户可用服务目录中的合并后的包含服务项数据的服务目录关系
	 * @Methods Name findServiceItemsByServiceType
	 * @Create In Nov 20, 2009 By lee
	 * @param serviceType
	 * @param userInfo
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findServiceItemsByServiceType(ServiceType serviceType,UserInfo userInfo);
	
	
	/**
	 * 根据用户信息获取服务项
	 * @Methods Name listServiceItemByUserAction
	 * @Create In 15 7, 2010 By zhangzy
	 * @return String
	 */
	List<SCIRelationShip> listServiceItemByUserService(String serviceItemName, ServiceItemType serviceItemType, ServiceType serviceType, UserInfo userInfo );
}
