package com.digitalchina.itil.service.service;

import java.util.List;
import java.util.Set;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.SCIRelationShipType;
import com.digitalchina.itil.service.entity.ServiceCatalogue;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceType;

/**
 * dwr��ʽ�������Ŀ¼��������ϵ
 * @Class Name SCIRelationShipManager
 * @author lee
 * @Create In 2008-1-14 
 * TODO
 */
public class SCIRelationShipManager {
	private SCIRelationShipService scirss = (SCIRelationShipService) getBean("sciRelationShipService");
	private SCIRelationShipTypeService scirits = (SCIRelationShipTypeService) getBean("sciRelationShipTypeService");
	private ServiceItemService sis = (ServiceItemService) getBean("serviceItemService");
	
	/**
	 * �����Ŀ¼��ϵ����������������ӷ���Ŀ¼��ϵ
	 * @Methods Name ajaxAddByCI
	 * @Create In 2009-1-14 By lee
	 * @param CIId��������ID
	 * @param parentId��������Ŀ¼��ϵID
	 * @param index����Ե�ǰ�ڵ�����λ��
	 * @return String
	 */
	public String ajaxAddByCI(String CIId,String parentId,String index){
		SCIRelationShip parentRelationShip = scirss.findSCIRelationShipWithParentById(parentId);
		ServiceItem serviceItem = sis.findServiceItemById(CIId);
		String type = parentRelationShip.getTypeFlag();
		if(SCIRelationShip.SCI_TYPE_ITEM.equals(type)){
			return "ERROR_ITEM";
		}
		List<SCIRelationShip> parents = scirss.findRelationShipsInLineToRoot(parentRelationShip);
		for(SCIRelationShip scirs : parents){
			//Long parentsId = scirs.getId();
			//scirs = scirss.findSCIRelationShipWithServiceItemAndServiceCatalogueById(parentsId.toString());
			ServiceItem parentServiceItem = scirs.getServiceItem();
			if(parentServiceItem!=null&&parentServiceItem.equals(serviceItem))
				return "ERROR_RING";
		}
		List<SCIRelationShip> childs = scirss.findChildRelationShipByParent(parentRelationShip);
		for(SCIRelationShip child : childs){
			//Long parentsId = child.getId();
			//child = scirss.findSCIRelationShipWithServiceItemAndServiceCatalogueById(parentsId.toString());
			ServiceItem parentServiceItem = child.getServiceItem();
			if(parentServiceItem!=null&&parentServiceItem.equals(serviceItem))
				return "ERROR_DOUBLE";
		}
		SCIRelationShip	newRelationShip = new SCIRelationShip();
		newRelationShip.setRootServiceCatalogue(parentRelationShip.getRootServiceCatalogue());
		newRelationShip.setParentRelationShip(parentRelationShip);
		newRelationShip.setServiceItem(serviceItem);
		newRelationShip.setTypeFlag(SCIRelationShip.SCI_TYPE_ITEM);
		newRelationShip.setOrder(Integer.valueOf(index)+1);
		SCIRelationShip cuRelationShip = scirss.save(newRelationShip);
		//�������������Ի�����###################################
		ServiceType serviceType = serviceItem.getServiceType();
		SCIRelationShipType sciRelationShipType= new SCIRelationShipType();
		sciRelationShipType.setSciRelationShip(cuRelationShip);
		sciRelationShipType.setServiceType(serviceType);
		scirits.saveJoin(sciRelationShipType);
		//#######################################################
		return "SUCCESS";
	}
	/**
	 * ɾ������Ŀ¼��ϵ��������ɾ�����ӹ�ϵ������ɾ���󸸽ڵ�������
	 * @Methods Name ajaxRemove
	 * @Create In 2009-1-14 By lee
	 * @param id������Ŀ¼��ϵID
	 * @return void
	 */
	public void ajaxRemove(String id){
		SCIRelationShip	scirs = scirss.findSCIRelationShipById(id);
		///////////������������//////////////////
		SCIRelationShip	parentscirs = scirss.findSCIRelationShipById(scirs.getParentRelationShip().getId().toString());
		//#####################�����ڵ����ͣ�����/����###################
		List<SCIRelationShipType> shipTypes = scirits.findTypesByRelationShip(scirs);
		for(SCIRelationShipType shipType : shipTypes){
			scirits.removeJoin(shipType);
		}
		//############################################################
		List<SCIRelationShip> childs = scirss.findChildRelationShipByParent(parentscirs);
		Integer scirsOrder = scirs.getOrder();
		for(SCIRelationShip child : childs){
			Integer childOrder = child.getOrder();
			if(childOrder>scirsOrder){
				child.setOrder(childOrder-1);
				scirss.save(child);
			}
		}
		//////////////////////////////////////
		scirss.remove(scirs);
	}
	/**
	 * �ж��ڲ��϶��Ƿ����
	 * @Methods Name ajaxTest
	 * @Create In 2009-1-14 By lee
	 * @param id�����϶�����Ŀ¼��ϵID
	 * @param oldParentId�����϶�����Ŀ¼��ϵԭ������Ŀ¼��ϵID
	 * @param newParentId�����϶�����Ŀ¼��ϵ�¸�����Ŀ¼��ϵID
	 * @param nodeIndex����Ե�ǰ�ڵ�����λ��
	 * @return String
	 */
	public String ajaxTestMove(String id, String oldParentId, String newParentId, String nodeIndex){
		SCIRelationShip oldParent = scirss.findSCIRelationShipById(oldParentId);
		SCIRelationShip newParent = scirss.findSCIRelationShipById(newParentId);
		SCIRelationShip curRelationShip = scirss.findSCIRelationShipById(id);
		String curType = curRelationShip.getTypeFlag();
		String newParentType = newParent.getTypeFlag();
		if(curType.equals(SCIRelationShip.SCI_TYPE_CATALOGUE)&&newParentType.equals(SCIRelationShip.SCI_TYPE_ITEM)){
			//System.out.print("ERROR\n");
			return "ERROR_MOVE";
		}else{
			return "SUCCESS";
		}
	}
	/**
	 * �жϴ����ӷ���Ŀ¼�Ƿ����
	 * @Methods Name ajaxTestAdd
	 * @Create In 2009-1-14 By lee
	 * @param id�������������Ŀ¼��ϵID
	 * @return String
	 */
	public String ajaxTestAdd(String id){
		SCIRelationShip curRelationShip = scirss.findSCIRelationShipById(id);
		String curType = curRelationShip.getTypeFlag();
		if(curType.equals(SCIRelationShip.SCI_TYPE_ITEM)){
			return "ERROR_ADD";
		}
		return "SUCCESS";
	}
	/**
	 * �жϽڵ�Ϊ��������Ŀ¼���Ƿ�����
	 * @Methods Name ajaxGetKernel
	 * @Create In 2009-1-14 By lee
	 * @param id�������������Ŀ¼��ϵID
	 * @return String
	 */
	public String ajaxGetKernel(String id){
		SCIRelationShip curRelationShip = scirss.findSCIRelationShipById(id);
		String curType = curRelationShip.getTypeFlag();
		if(curType.equals(SCIRelationShip.SCI_TYPE_ITEM)){
			return "KERNEL_ITEM";
		}
		return "KERNEL_CATA";
	}
	/**
	 * ����Ŀ¼��ϵ�����϶������ı��϶���ϵ����ϵ���������ϸ���ϵ����
	 * @Methods Name ajaxMoveRelationShip
	 * @Create In 2009-1-14 By lee
	 * @param id�����϶�����Ŀ¼��ϵID
	 * @param oldParentId�����϶�����Ŀ¼��ϵԭ������Ŀ¼��ϵID
	 * @param newParentId�����϶�����Ŀ¼��ϵ�¸�����Ŀ¼��ϵID
	 * @param nodeIndex����Ե�ǰ�ڵ�����λ��
	 * @return String
	 */
	public String ajaxMoveRelationShip(String id, String oldParentId, String newParentId, String nodeIndex){
		SCIRelationShip oldParent = scirss.findSCIRelationShipById(oldParentId);
		SCIRelationShip newParent = scirss.findSCIRelationShipById(newParentId);
		SCIRelationShip curRelationShip = scirss.findSCIRelationShipById(id);
		String curType = curRelationShip.getTypeFlag();
		String newParentType = newParent.getTypeFlag();
		if(SCIRelationShip.SCI_TYPE_ITEM.equals(curType)&&SCIRelationShip.SCI_TYPE_ITEM.equals(newParentType)){
			return "ERROR_ITEM";
		}else if(curType.equals(SCIRelationShip.SCI_TYPE_CATALOGUE)&&newParentType.equals(SCIRelationShip.SCI_TYPE_ITEM)){
			return "ERROR_MOVE";
		}else if(scirss.isRingRelation(newParent, curRelationShip)){
			return "ERROR_RING";
		}else if((!newParent.equals(oldParent))&&(scirss.isDoubleSameChilds(newParent, curRelationShip))){
			return "ERROR_DOUBLE";
		}else{
		//###################�����ڵ����ͣ�����/����##############################
			List<SCIRelationShipType> shipTypes = scirits.findTypesByRelationShip(curRelationShip);
			for(SCIRelationShipType shipType : shipTypes){
				scirits.removeJoin(shipType);
			}
		//######################################################################
		curRelationShip.setParentRelationShip(newParent);
		///////////������������////////////////////
		Integer oldOrder = curRelationShip.getOrder();
		List<SCIRelationShip> oldchilds = scirss.findChildRelationShipByParent(oldParent);
		for(SCIRelationShip child : oldchilds){
			Integer childOrder = child.getOrder();
			if(childOrder>oldOrder){
				child.setOrder(childOrder-1);
				scirss.save(child);
			}
		}
		Integer newOrder = Integer.valueOf(nodeIndex)+1;
		List<SCIRelationShip> newchilds = scirss.findChildRelationShipByParent(newParent);
		for(SCIRelationShip child : newchilds){
			Integer childOrder = child.getOrder();
			if(childOrder>=newOrder){
				child.setOrder(childOrder+1);
				scirss.save(child);
			}
		}
		curRelationShip.setOrder(Integer.valueOf(nodeIndex)+1);
		//////////////////////////////////////////////////////
		scirss.save(curRelationShip);
		//##############################################
		for(SCIRelationShipType shipType : shipTypes){
			scirits.saveJoin(shipType);
		}
		//##############################################
		return "SUCCESS";
		}
	}
	/**
	 * ����spring����ķ���service
	 * 
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
