package com.digitalchina.itil.service.rules;

import java.util.Calendar;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.itil.service.entity.ServiceCatalogue;
import com.digitalchina.itil.service.entity.ServiceCatalogueAuditHis;
import com.digitalchina.itil.service.service.SCIRelationShipService;

public class ServiceRuleHelper {
	Service service = (Service) ContextHolder.getBean("baseService");
    SCIRelationShipService sciRelationShipService = (SCIRelationShipService)ContextHolder.getBean("sciRelationShipService");
	/**
	 * 服务目录历史通用保存方法
	 * @Methods Name saveServiceCataHis
	 * @Create In Mar 23, 2009 By Administrator
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param configItem void
	 */
	public void saveServiceCataHis(String nodeId,String nodeName,String processId,String result,String comment,ServiceCatalogue serviceCatalogue,String alterFlag){
		
		ServiceCatalogueAuditHis scah =new ServiceCatalogueAuditHis();
		scah.setResultFlag(result);				
		scah.setProcessId(Long.valueOf(processId));
		scah.setApprover(UserContext.getUserInfo());
		scah.setApproverDate(Calendar.getInstance().getTime());
		scah.setServiceCatalogue(serviceCatalogue);
		scah.setNodeName(nodeName);
		scah.setComment(comment);
		scah.setNodeId(String.valueOf(nodeId));
		scah.setAlterFlag(alterFlag);
		try{
			service.save(scah);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 服务目录开始节点改变状态
	 * @Methods Name serviceCataStartFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId void
	 */
	public void serviceCataStartFlag(String dataId,String nodeId,String nodeName,String processId,String alterFlag){	
		
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, dataId);
		serviceCatalogue.setStatus(ServiceCatalogue.STATUS_APPROVING);			
		this.saveServiceCataHis(nodeId, nodeName, processId, "Y", "", serviceCatalogue,alterFlag);
		service.save(serviceCatalogue);
		
		
	}
	/**
	 * 服务目录内审审批节点改变状态
	 * @Methods Name serviceCataAuditFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public String serviceCataAuditFlag(String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){	
		
	
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, dataId);
		serviceCatalogue.setStatus(Integer.valueOf(serviceCatalogue.STATUS_APPROVING));
		this.saveServiceCataHis(nodeId, nodeName, processId, result, comment, serviceCatalogue,alterFlag);
		service.save(serviceCatalogue);
		return result;
		
	}
	
	/**
	 * 服务目录用户确认节点改变状态
	 * @Methods Name serviceCataConfirmFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public String serviceCataConfirmFlag(String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){	
		
	
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, dataId);
		if("Y".equals(result)){
			sciRelationShipService.saveServiceCataEvent(serviceCatalogue);
		}else{
			serviceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_APPROVING));
		}			
		this.saveServiceCataHis(nodeId, nodeName, processId, result, comment, serviceCatalogue,alterFlag);
		service.save(serviceCatalogue);
		return result;
		
	}
	/**
	 * 服务目录打回节点改变状态
	 * @Methods Name serviceCataBackFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public String serviceCataBackFlag(String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){	
		
	
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, dataId);
		if("Y".equals(result)){
			serviceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_APPROVING));
		}else{
			serviceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_DRAFT));
		}			
		this.saveServiceCataHis(nodeId, nodeName, processId, result, comment, serviceCatalogue,alterFlag);
		service.save(serviceCatalogue);
		return result;
		
	}
	//****************************************************************服务目录变更********************************************
	/**
	 * 服务目录变更开始节点改变状态
	 * @Methods Name serviceCataAlterStartFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId void
	 */
	public void serviceCataAlterStartFlag(String dataId,String nodeId,String nodeName,String processId,String alterFlag){//dataId是新的id	
		
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, dataId);
		serviceCatalogue.setStatus(ServiceCatalogue.STATUS_APPROVING);			
		this.saveServiceCataHis(nodeId, nodeName, processId, "Y", "", serviceCatalogue,alterFlag);
		service.save(serviceCatalogue);
		
		
	}
	/**
	 * 服务目录变更内审审批节点改变状态
	 * @Methods Name serviceCataAlterAuditFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public String serviceCataAlterAuditFlag(String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){	
		
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, dataId);
		serviceCatalogue.setStatus(Integer.valueOf(serviceCatalogue.STATUS_APPROVING));
		this.saveServiceCataHis(nodeId, nodeName, processId, result, comment, serviceCatalogue,alterFlag);
		service.save(serviceCatalogue);
		return result;
		
	}
	
	/**
	 * 服务目录变更用户确认节点改变状态
	 * @Methods Name serviceCataAlterConfirmFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public String serviceCataAlterConfirmFlag(String oldDataId,String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){	
		
	
		ServiceCatalogue newServiceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, dataId,true);//得到新的服务目录
		if("Y".equals(result)){
			//newServiceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_DELETE));
			ServiceCatalogue oldServiceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, oldDataId,true);//得到原来的服务目录
			sciRelationShipService.saveServiceCataAlterEvent(newServiceCatalogue, oldServiceCatalogue);
			try{
				this.saveServiceCataHis(nodeId, nodeName, processId, result, comment, newServiceCatalogue,alterFlag);
				service.remove(newServiceCatalogue);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}else{
			newServiceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_APPROVING));
			this.saveServiceCataHis(nodeId, nodeName, processId, result, comment, newServiceCatalogue,alterFlag);
		}			
		
		//service.save(newServiceCatalogue);
		return result;
		
	}
	/**
	 * 服务目录变更打回节点改变状态
	 * @Methods Name serviceCataAlterBackFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public String serviceCataAlterBackFlag(String dataId,String nodeId,String nodeName,String processId,String result,String comment,String alterFlag){	
		
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue)service.find(ServiceCatalogue.class, dataId);
		if("Y".equals(result)){
			serviceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_APPROVING));
		}else{
			//modify by lee for debug serviceCatalog to right status in 20100414 begin
			//serviceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_DRAFT));
			serviceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_ALTER_DRAFT));
			//modify by lee for debug serviceCatalog to right status in 20100414 end
		}			
		this.saveServiceCataHis(nodeId, nodeName, processId, result, comment, serviceCatalogue,alterFlag);
		service.save(serviceCatalogue);
		return result;
		
	}

}
