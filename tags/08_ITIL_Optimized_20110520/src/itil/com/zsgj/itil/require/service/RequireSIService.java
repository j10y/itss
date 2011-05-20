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
 * �������Ŀ¼����
 * @Class Name RequireSIService
 * @Author sa
 * @Create In 2009-2-11
 */
public interface RequireSIService {

	/**
	 * ��ȡ���������û�Ӧ�ÿ��������з���Ŀ¼�����Ŀͻ�id
	 * @Methods Name findCustIdByUser
	 * @Create In 2009-2-11 By sa
	 * @param userInfo
	 * @return List<Long>
	 */
	List<Long> findCustIdByUser(UserInfo userInfo);
	
	/**
	 * �ṩ�ͻ�id��ȡ���еķ���Ŀ¼
	 * @Methods Name findServiceCatalogueByCust
	 * @Create In 2009-2-11 By sa
	 * @param custIds
	 * @return Page
	 */
	List<ServiceCatalogue> findServiceCatalogueByCust(List<Long> custIds);
	/**
	 * ͨ������Ŀ¼��ȡ�����İ����������ķ����ϵ����
	 * @Methods Name findGeneralSCIRelationShipByCata
	 * @Create In Mar 15, 2009 By lee
	 * @param serviceCatalogues
	 * @return List<SCIRelationShip>
	 */
	//List<SCIRelationShip> findGeneralSCIRelationShipByCata(List<ServiceCatalogue> serviceCatalogues);//remove by lee for scrap function in 20090625
	/**
	 * ͨ������Ŀ¼��ȡ�����İ������Ի�����ķ����ϵ����
	 * @Methods Name findSpecialSCIRelationShipByCata
	 * @Create In Mar 15, 2009 By lee
	 * @param serviceCatalogues
	 * @return List<SCIRelationShip>
	 */
	//List<SCIRelationShip> findSpecialSCIRelationShipByCata(List<ServiceCatalogue> serviceCatalogues);//remove by lee for scrap function in 20090625
	/*
	 * ���ݷ���Ŀ¼��ȡ�����ϵ����
	 */
	List<SCIRelationShip> findSCIRelationShipByService(List<ServiceCatalogue> ServiceCatalogue);
	/**
	 * ͨ�������ϵid��ȡ�������������ӷ���Ŀ¼��ϵ����
	 * @Methods Name findGeneralSCIRelationShipById
	 * @Create In Mar 15, 2009 By lee
	 * @param sCIRelationShipId
	 * @param storeData
	 * @return List<SCIRelationShip>
	 */
	//List<SCIRelationShip> findGeneralSCIRelationShipById(String sCIRelationShipId,Map storeData);//remove by lee for scrap function in 20090625 
	/**
	 * ͨ�������ϵid��ȡ�������Ի�������ӷ���Ŀ¼��ϵ����
	 * @Methods Name findSpecialSCIRelationShipById
	 * @Create In Mar 15, 2009 By lee
	 * @param sCIRelationShipId
	 * @param storeData
	 * @return List<SCIRelationShip>
	 */
	//List<SCIRelationShip> findSpecialSCIRelationShipById(String sCIRelationShipId,Map storeData);//remove by lee for scrap function in 20090625
	/**
	 * ���ݷ����ϵid��ȡ�ӷ����ϵ����
	 */
	List<SCIRelationShip> findSCIRelationShipById(String sCIRelationShipId,Map storeData,String serviceTypeKeyWord);
	/**
	 * 
	 * ͨ�������ϵid��÷���Ŀ¼����
	 * 
	 * */
	SCIRelationShip findServiceCatalogueByRelationId(String sCIRelationShipId);
	/**
	 * ͨ����̬��clazz����ȡ���е�����
	 * @Methods Name findAutoClazz
	 * @Create In Mar 4, 2009 By sujs
	 * @param clazz
	 * @return List
	 */
	List findAutoClazz(String clazz);
	
	/**
	 * ͨ���������ȡ����������������������
	 * @Methods Name getPanelsByServiceItem
	 * @Create In Feb 27, 2009 By lee
	 * @param serviceItem
	 * @return List<PageModelPanel>
	 */
	List<PageModelPanel> getPanelsByServiceItem(ServiceItem serviceItem);
	/**
	 * ͨ�������������ͷ������ȡ�÷������Ӧ����
	 * @Methods Name findEntities
	 * @Create In Apr 8, 2009 By lee
	 * @param className
	 * @param serviceItemId
	 * @return List
	 */
	List findEntities(String className, String serviceItemId);
	/**
	 * ͨ�������ϵ���keyword
	 * @Methods Name findNodeKeyWords
	 * @Create In May 7, 2009 By Administrator
	 * @param sCIRelationShip
	 * @return List
	 */
	List<SCIRelationShipType> findNodeKeyWord(SCIRelationShip sCIRelationShip);
	/**
	 * ��ȡ�û��鿴�ɲ鿴����Ȩ�޵��û�,Ϊ���˵�ǰ�û��鿴�ύ
	 * @Methods Name findDataScopeByUser
	 * @Create In Aug 18, 2009 By lee
	 * @param userInfo
	 * @return List<UserInfo> ����null���ʾ�û�ӵ�ж������Ų鿴��������Ȩ��
	 */
	List<UserInfo> findDataScopeByUser(UserInfo userInfo);
	/**
	 * ͨ����ǰ�û���ɫȨ�޻�ȡ�ɲ鿴����,Ϊ���˵�ǰ�û��鿴�ύ
	 * @Methods Name findEntities
	 * @Create In Aug 18, 2009 By lee
	 * @param className		������
	 * @param id			������id
	 * @param users			�鿴�û���Χ(���userΪ�գ����ʾ�ɲ鿴��������)
	 * @return List
	 */
	List findEntities(String className, String id, List<UserInfo> users);
	
	/**
	 * ɾ��ָ�������������ʷ
	 * @Methods Name removeHisByApply
	 * @Create In 2009-11-26 By zhangzy
	 * @param className	//������
	 * @param dataId void	//����ID
	 */
	void removeHisByApply(String className,String dataId);
	/**
	 * ͨ����ǰ�û���ȡ�û�����������������
	 * @Methods Name findAuditHisEntities
	 * @Create In Aug 29, 2009 By lee
	 * @param className		������
	 * @param siId			������ID
	 * @param user			�û�
	 * @return List
	 */
	List findAuditHisEntities(String className, String siId, UserInfo user);
	
	/**
	 * ͨ����ǰ�û���ȡ�û������������һ��������ʷ
	 * @Methods Name findLastHis
	 * @Create In Aug 30, 2009 By lee
	 * @param id	
	 * @param siId
	 * @param user
	 * @return ServiceItemApplyAuditHis
	 */
	ServiceItemApplyAuditHis findLastHis(String id, String siId, UserInfo user);
	
	/**
	 * ��ȡ�������ڵ��Ӧҳ������ 
	 * @Methods Name getPageModelNameForNode
	 * @Create In Aug 30, 2009 By lee
	 * @param processId		//��������ID
	 * @param nodeId		//�ڵ�ID
	 * @return String 		//pageModel����
	 */
	String getPageModelNameByNode(String processId, String nodeId);
}
