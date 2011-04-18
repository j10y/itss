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
 * dwr方式管理服务目录与服务项关系
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
	 * 向服务目录关系树拖入服务项进而添加服务目录关系
	 * @Methods Name ajaxAddByCI
	 * @Create In 2009-1-14 By lee
	 * @param CIId，服务项ID
	 * @param parentId，父服务目录关系ID
	 * @param index，相对当前节点排序位置
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
		//处理常规服务与个性化服务###################################
		ServiceType serviceType = serviceItem.getServiceType();
		SCIRelationShipType sciRelationShipType= new SCIRelationShipType();
		sciRelationShipType.setSciRelationShip(cuRelationShip);
		sciRelationShipType.setServiceType(serviceType);
		scirits.saveJoin(sciRelationShipType);
		//#######################################################
		return "SUCCESS";
	}
	/**
	 * 删除服务目录关系，并级联删除其子关系，处理删除后父节点下排序
	 * @Methods Name ajaxRemove
	 * @Create In 2009-1-14 By lee
	 * @param id，服务目录关系ID
	 * @return void
	 */
	public void ajaxRemove(String id){
		SCIRelationShip	scirs = scirss.findSCIRelationShipById(id);
		///////////处理排序问题//////////////////
		SCIRelationShip	parentscirs = scirss.findSCIRelationShipById(scirs.getParentRelationShip().getId().toString());
		//#####################处理父节点类型：常规/个性###################
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
	 * 判断内部拖动是否合理
	 * @Methods Name ajaxTest
	 * @Create In 2009-1-14 By lee
	 * @param id，被拖动服务目录关系ID
	 * @param oldParentId，被拖动服务目录关系原父服务目录关系ID
	 * @param newParentId，被拖动服务目录关系新父服务目录关系ID
	 * @param nodeIndex，相对当前节点排序位置
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
	 * 判断创建子服务目录是否合理
	 * @Methods Name ajaxTestAdd
	 * @Create In 2009-1-14 By lee
	 * @param id，被点击父服务目录关系ID
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
	 * 判断节点为包含服务目录还是服务项
	 * @Methods Name ajaxGetKernel
	 * @Create In 2009-1-14 By lee
	 * @param id，被点击父服务目录关系ID
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
	 * 服务目录关系树内拖动，更改被拖动关系父关系，处理新老父关系排序
	 * @Methods Name ajaxMoveRelationShip
	 * @Create In 2009-1-14 By lee
	 * @param id，被拖动服务目录关系ID
	 * @param oldParentId，被拖动服务目录关系原父服务目录关系ID
	 * @param newParentId，被拖动服务目录关系新父服务目录关系ID
	 * @param nodeIndex，相对当前节点排序位置
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
		//###################处理父节点类型：常规/个性##############################
			List<SCIRelationShipType> shipTypes = scirits.findTypesByRelationShip(curRelationShip);
			for(SCIRelationShipType shipType : shipTypes){
				scirits.removeJoin(shipType);
			}
		//######################################################################
		curRelationShip.setParentRelationShip(newParent);
		///////////处理排序问题////////////////////
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
	 * 返回spring管理的服务service
	 * 
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
}
