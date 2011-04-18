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
 * ����Ŀ¼��ϵ����
 * @Class Name SCIRelationShipService
 * @Author sa
 * @Create In 2009-1-14
 */
public interface SCIRelationShipService {
	
	/**
	 * �ṩ����Ŀ¼id��ȡ��Ŀ¼�µĸ�����Ŀ¼��ϵ
	 * @Methods Name findRootRelationShip
	 * @Create In 2009-1-14 By sa
	 * @param serviceCatalogueId
	 * @return SCIRelationShip
	 */
	SCIRelationShip findRootRelationShip(String serviceCatalogueId);
	
	/**
	 * ͨ��id��ȡ�������ϵ������ץȡ���������ϵ
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
	 * ͨ��ID��ȡ����Ŀ¼��ϵ
	 * @Methods Name findSCIRelationShipById
	 * @Create In 2009-1-14 By lee
	 * @param sciRelationShipId
	 * @return SCIRelationShip
	 */
	SCIRelationShip findSCIRelationShipById(String sciRelationShipId);
	/**
	 * �������Ŀ¼��ϵ
	 * @Methods Name findSCIRelationShipById
	 * @Create In 2009-1-14 By lee
	 * @param sciRelationShip
	 * @return SCIRelationShip
	 */
	SCIRelationShip save(SCIRelationShip sciRelationShip);
	/**
	 * ����IDɾ������Ŀ¼��ϵ,����ɾ�������¼�����Ŀ¼��ϵ
	 * @Methods Name removeById
	 * @Create In 2009-1-14 By lee
	 * @param id
	 * @return void
	 */
	void remove(SCIRelationShip sciRelationShip);
	/**
	 * ��ȡ����Ŀ¼��ϵ���ӹ�ϵ
	 * @Methods Name findChildRelationShipByParent
	 * @Create In 2009-1-14 By lee
	 * @param parentRelationShip
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findChildRelationShipByParent(SCIRelationShip parentRelationShip);
	/**
	 * ��ȡ����Ŀ¼��ϵ�������ӹ�ϵ
	 * @Methods Name findAllChildRelationShipByParent
	 * @Create In 2009-1-14 By lee
	 * @param parentRelationShip
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findAllChildRelationShipByParent(SCIRelationShip parentRelationShip);
	
	/**
	 * ���ݷ������id��ȡ������������з�����
	 * @Methods Name findServiceItemByPage
	 * @Create In Jan 15, 2009 By Administrator
	 * @param serviceItemName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findServiceItemByPage(String serviceItemName ,int pageNo, int pageSize);
	/**
	 * ����id�ҷ���Ŀ¼
	 * @param id
	 * @return
	 */
	ServiceCatalogue findServiceItemById(String id);
	/**
	 * ͨ��������Ŀ¼��ȡ������Ŀ¼��ϵ
	 * @Methods Name findRootRelationShipByRootCata
	 * @Create In 2009-1-14 By lee
	 * @param serviceCatalogue
	 * @return SCIRelationShip
	 */
	SCIRelationShip findRootRelationShipByRootCata(ServiceCatalogue serviceCatalogue);
	/**
	 * ��ȡ�Ӳ�������Ŀ¼��ϵ������ϵһ����ϵ���ϵ����й�ϵʵ��
	 * @Methods Name findRelationShipsInLineToRoot
	 * @Create In 2009-1-14 By lee
	 * @param sciRelationShip
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findRelationShipsInLineToRoot(SCIRelationShip sciRelationShip);
	/**
	 * �ж���ν��ӹ�ϵ��ӵ�����ϵ���Ƿ��γɻ�״��ϵ
	 * @Methods Name isRingRelation
	 * @Create In 2009-1-14 By lee
	 * @param parentRelationShip
	 * @param childRelationShip
	 * @return boolean
	 */
	boolean isRingRelation(SCIRelationShip parentRelationShip,SCIRelationShip childRelationShip);
	/**
	 * �ж���ν��ӹ�ϵ��ӵ�����ϵ���Ƿ��������������ͬʵ��Ĺ�ϵ
	 * @Methods Name isDoubleSameChilds
	 * @Create In 2009-1-14 By lee
	 * @param parentRelationShip
	 * @param childRelationShip
	 * @return boolean
	 */
	boolean isDoubleSameChilds(SCIRelationShip parentRelationShip,SCIRelationShip childRelationShip);
	/**
	 * ���ڽ�����Ŀ¼Id�����ڷ���Ŀ¼��ͬ����
	 * @Methods Name isDoubleSameChilds
	 * @Create in sujs
	 */
	void saveServiceCatalogueIdToContract (String ServiceCatalogueId);
	/**
	 * ͨ������Ŀ¼Id��÷����ͬId
	 * @Methods Name getServiceCatalogueContractId
	 * @Create In Mar 1, 2009 By sujs
	 * @param ServiceCatalogueId
	 * @return String
	 */
	String getServiceCatalogueContractId(String ServiceCatalogueId); 
	/**
	 * ͨ������Ŀ¼IDȡ�����з�������Ϣ�����浽ServiceItemSLA����
	 * @Methods Name saveServiceItemSLAfromservicelogueId
	 * @Create In Mar 1, 2009 sujs
	 * @param ServiceCatalogueId void
	 */
	void saveServiceItemSLAfromservicelogueId(String ServiceCatalogueId );
	/**
	 * ���ݷ��������ƺ����ͻ�ȡ������
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
	 * ���ݾɵķ���Ŀ¼id�õ��������е��ӹ�ϵ
	 * @Methods Name getChildSCIRelationShipsByOldId
	 * @Create In Apr 8, 2009 By Administrator
	 * @param oldId
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> getChildSCIRelationShipsByServiceCata(ServiceCatalogue serviceCatalogue);
	/**
	 * ���ݷ���Ŀ¼�ݸ����idɾ���������й�ϵ�������ĺ�ͬ��SLA��Ϣ
	 * @Methods Name removeServiceCataByRootId
	 * @Create In Apr 13, 2009 By Administrator
	 * @param rootServiceCataId void
	 */
	void removeServiceCataByRootId(String rootServiceCataId);
	/**
	 * ͨ�����õ��������е��ӷ���Ŀ¼
	 * @Methods Name findServiceCataShipByRoot
	 * @Create In Apr 20, 2009 By Administrator
	 * @param serviceCatalogue
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findServiceCataShipByRoot(ServiceCatalogue serviceCatalogue);
	/**
	 * ͨ����ɾ�������ӷ���Ŀ¼��ϵ��Ҫ���Ӻ�
	 * @Methods Name removeAllChildServiceCata
	 * @Create In Apr 22, 2009 By Administrator
	 * @param rootRelationShip void
	 */
	void removeAllChildServiceCataShip(SCIRelationShip rootRelationShip);
	/**
	 * ����Ŀ¼����ͨ���󱣴���ʷ
	 * @Methods Name saveServiceCataEvent
	 * @Create In Apr 29, 2009 By Administrator
	 * @param serviceCatalogue void
	 */
	void saveServiceCataEvent(ServiceCatalogue serviceCatalogue);
	/**
	 * ����Ŀ¼�������ͨ���󿽱��ͱ�����ʷ
	 * @Methods Name saveServiceCataAlterEvent
	 * @Create In Apr 29, 2009 By Administrator
	 * @param newServiceCatalogue
	 * @param oldServiceCatalogue void
	 */
	void saveServiceCataAlterEvent(ServiceCatalogue newServiceCatalogue,ServiceCatalogue oldServiceCatalogue);
	
	/**
	 * ��ȡ�û�����Ŀ¼������ʾ����
	 * @Methods Name getUserServiceCataJson
	 * @Create In Nov 18, 2009 By lee
	 * @param userInfo
	 * @return String
	 */
	String getUserServiceCataJson(UserInfo userInfo);
	
	/**
	 * ��ȡ�û����÷���Ŀ¼�еĺϲ���İ������������ݵķ���Ŀ¼��ϵ
	 * @Methods Name findServiceItemsByServiceType
	 * @Create In Nov 20, 2009 By lee
	 * @param serviceType
	 * @param userInfo
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> findServiceItemsByServiceType(ServiceType serviceType,UserInfo userInfo);
	
	
	/**
	 * �����û���Ϣ��ȡ������
	 * @Methods Name listServiceItemByUserAction
	 * @Create In 15 7, 2010 By zhangzy
	 * @return String
	 */
	List<SCIRelationShip> listServiceItemByUserService(String serviceItemName, ServiceItemType serviceItemType, ServiceType serviceType, UserInfo userInfo );
}
