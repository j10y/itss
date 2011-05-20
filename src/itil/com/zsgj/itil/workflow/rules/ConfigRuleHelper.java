package com.zsgj.itil.workflow.rules;

import java.util.Date;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.service.Service;
import com.zsgj.itil.config.entity.CIBatchModify;
import com.zsgj.itil.config.entity.CIBatchModifyAuditHis;
import com.zsgj.itil.config.service.ConfigItemService;

public class ConfigRuleHelper {
	Service service = (Service) ContextHolder.getBean("baseService");
	ConfigItemService cis=(ConfigItemService)ContextHolder.getBean("configItemService");

	/**
	 * ����������ʷ
	 * @Methods Name saveCIBatchModifyHis
	 * @Create In Mar 30, 2010 By duxh
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param ciBatchModify 
	 * @Return void
	 */
	public void saveCIBatchModifyHis(String nodeId,String nodeName,String processId,String result,String comment,CIBatchModify ciBatchModify){
		CIBatchModifyAuditHis ciModifyHis =new CIBatchModifyAuditHis();
		ciModifyHis.setResultFlag(result);				
		ciModifyHis.setProcessId(Long.valueOf(processId));
		ciModifyHis.setApprover(UserContext.getUserInfo());
		ciModifyHis.setApproverDate(new Date());
		ciModifyHis.setModify(ciBatchModify);
		ciModifyHis.setNodeName(nodeName);
		ciModifyHis.setComment(comment);
		ciModifyHis.setNodeId(nodeId);
		service.save(ciModifyHis);
	}
	
	
	/**
	 * �ύ���
	 * @Methods Name modifyStart
	 * @Create In Mar 30, 2010 By duxh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId 
	 * @Return void
	 */
	public void modifyStart(String dataId,String nodeId,String nodeName,String processId){
		CIBatchModify ciBatchModify = (CIBatchModify)service.find(CIBatchModify.class, dataId);
		ciBatchModify.setStatus(CIBatchModify.STATUS_PROCESSING);
		service.save(ciBatchModify);
		this.saveCIBatchModifyHis(nodeId, nodeName, processId, "", "", ciBatchModify);
	}
	/**
	 * �����̻�֧���鸺��������
	 * @Methods Name modifyConfirmByManagerAudit
	 * @Create In Mar 30, 2010 By duxh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @Return String
	 */
	public String modifyConfirmByManagerAudit(String dataId,String nodeId,String nodeName,String processId,String result,String comment){
		CIBatchModify ciBatchModify =new CIBatchModify();
		ciBatchModify.setId(Long.valueOf(dataId));
		this.saveCIBatchModifyHis(nodeId, nodeName, processId, result, comment, ciBatchModify);
		return result;
	}
	/**
	 * IT�������Ա����
	 * @Methods Name modifyConfirmByITAudit
	 * @Create In Mar 30, 2010 By duxh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @Return String
	 */
	public String modifyConfirmByITAudit(String dataId,String nodeId,String nodeName,String processId,String result,String comment){
		CIBatchModify ciBatchModify =new CIBatchModify();
		ciBatchModify.setId(Long.valueOf(dataId));
		this.saveCIBatchModifyHis(nodeId, nodeName, processId, result, comment, ciBatchModify);
		return result;
	}
	/**
	 * �ύ��ȷ��
	 * @Methods Name modifyConfirmUser
	 * @Create In Mar 30, 2010 By duxh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @Return String
	 */
	public String modifyConfirmUser(String dataId,String nodeId,String nodeName,String processId,String result,String comment){
		CIBatchModify ciBatchModify=new CIBatchModify();
		ciBatchModify.setId(Long.valueOf(dataId));
		if(result.equalsIgnoreCase("Y")){
			cis.saveSuccessModify(Long.valueOf(dataId));
		}
		this.saveCIBatchModifyHis(nodeId, nodeName, processId, result, comment, ciBatchModify);
		return result;
	}
	/**
	 * ���
	 * @Methods Name modifyBack
	 * @Create In Mar 30, 2010 By duxh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @Return String
	 */
	public String modifyBack(String dataId,String nodeId,String nodeName,String processId,String result,String comment){
		CIBatchModify ciBatchModify =(CIBatchModify) service.find(CIBatchModify.class, dataId);
		if(result.equalsIgnoreCase("N")){
			cis.saveUnSuccessModify(ciBatchModify);
		}
		this.saveCIBatchModifyHis(nodeId, nodeName, processId, result, comment, ciBatchModify);
		return result;
	}
}

