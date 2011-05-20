package com.zsgj.itil.workflow.rules;

import java.util.Calendar;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventSulotion;
import com.zsgj.itil.knowledge.entity.KnowContract;
import com.zsgj.itil.knowledge.entity.KnowContractAuditHis;
import com.zsgj.itil.knowledge.entity.KnowFile;
import com.zsgj.itil.knowledge.entity.KnowFileAuditHis;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.knowledge.entity.KnowledgeAuditHis;

public class KnowProcessRuleHelper {
	ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	Service service = (Service) ContextHolder.getBean("baseService");

	/**
	 * 解决方案提交
	 * @Methods Name knowledgeStartFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId 
	 * @Return void
	 */
	public void knowledgeStartFlag(String dataId,String nodeId,
			            String nodeName, String processId) {
		Knowledge knowledge = (Knowledge) service.find(Knowledge.class,dataId, true);
		knowledge.setStatus(Knowledge.STATUS_APPROVING);// 审批中
		//2010-09-20 modified by huzh for 事件结束后创建的解决方案不能用当前登录人作为approver begin
//		this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, "","");
		KnowledgeAuditHis knowledgeAuditHis = new KnowledgeAuditHis();
		knowledgeAuditHis.setProcessId(Long.valueOf(processId));
		if(knowledge.getCreateUser()!=null){
			knowledgeAuditHis.setApprover(knowledge.getCreateUser());
		}
		knowledgeAuditHis.setApproverDate(Calendar.getInstance().getTime());
		knowledgeAuditHis.setKnowledge(knowledge);
		knowledgeAuditHis.setNodeName(nodeName);
		knowledgeAuditHis.setNodeId(String.valueOf(nodeId));
		service.save(knowledgeAuditHis);
		//2010-09-20 modified by huzh for 事件结束后创建的解决方案不能用当前登录人作为approver end
		service.save(knowledge);
	}
	
	/**
	 * 审批解决方案准确性
	 * @Methods Name knowledgeApprovalFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return 
	 * @Return String
	 */
	public String knowledgeApprovalFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment) {
		Knowledge knowledge = (Knowledge) service.find(Knowledge.class,dataId, true);
		Knowledge oldKnowledge=knowledge.getOldKnowledge();
		if (result.equals("Y")) {// 同意，审批通过
			knowledge.setStatus(Knowledge.STATUS_FINISHED);//发布时和变更时
			//2010-05-12 add by huzh for 为变更时，原解决方案该为过期状态 begin
			if(oldKnowledge!=null){//变更
				oldKnowledge.setStatus(Knowledge.STATUS_TIMEOUT);//过期状态
				service.save(oldKnowledge);
			}
			//2010-05-12 add by huzh for 为变更时，原解决方案该为过期状态 begin
		} else {// 不同意
			//2010-05-11 modified by huzh for 解决方案被打回时的状态分类 begin
			EventSulotion eventSulotion=(EventSulotion)service.findUnique(EventSulotion.class, "knowledge", knowledge);
			if(eventSulotion!=null){//发布时，事件解决而来
				knowledge.setStatus(Knowledge.STATUS_RENEW);//待补充
			}else if(oldKnowledge!=null){
				knowledge.setStatus(Knowledge.STATUS_CHANGEDRAFT);//变更草稿
			}else{//发布时的事件解决而来和变更时；
				knowledge.setStatus(Knowledge.STATUS_DRAFT);//草稿
			}
			//2010-05-11 modified by huzh for 解决方案被打回时：若为发布，从事件解决而来的变为待补充状态，不是从解决方案来的变为草稿；若为变更，新解决方案变为草稿，原解决方案不变 begin
		}
		this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,result, comment);
		service.save(knowledge);
		return result;
	}
	
	/**
	 * 解决方案被打回
	 * @Methods Name knowledgeGoBackFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return 
	 * @Return String
	 */
	public String knowledgeGoBackFlag(String dataId,String nodeId,
			String nodeName, String processId, String result, String comment) {
		Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
				dataId, true);
		//2010-05-11 modified by huzh for 解决方案被打回时的状态分类 begin
//		knowledge.setStatus(new Integer("0"));
		EventSulotion eventSulotion=(EventSulotion)service.findUnique(EventSulotion.class, "knowledge", knowledge);
		Knowledge oldKnowledge=knowledge.getOldKnowledge();
		if(eventSulotion!=null){
			knowledge.setStatus(Knowledge.STATUS_RENEW);//待补充
		}else if(oldKnowledge!=null){
			knowledge.setStatus(Knowledge.STATUS_CHANGEDRAFT);//变更草稿
		}else{
			knowledge.setStatus(Knowledge.STATUS_DRAFT);//草稿
		}
		//2010-05-11 modified by huzh for 解决方案被打回时的状态分类 begin
		this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
				result, comment);
		service.save(knowledge);
		return result;
	}
	
	/**
	 * 文件提交
	 * @Methods Name knowFileStartFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId 
	 * @Return void
	 */
	public void knowFileStartFlag(String dataId,String nodeId,
			             String nodeName, String processId) {
		KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
				true);
		this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
						"", "");
		knowFile.setStatus(KnowFile.STATUS_APPROVING);// 2为提交审批中
		service.save(knowFile);
	}
	
	/**
	 * 审批文件准确性
	 * @Methods Name knowFileApprovalFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return 
	 * @Return String
	 */
	public String knowFileApprovalFlag(String dataId,String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
				true);
		if (result.equals("Y")) {// 同意，提交审批中
			knowFile.setStatus(KnowFile.STATUS_APPROVING);
		} else {// 不同意
			KnowFile oldKnowFile=knowFile.getOldKnowFile();
			if(oldKnowFile!=null){
				knowFile.setStatus(KnowFile.STATUS_CHANGEDRAFT);//变更草稿
			}else{
				knowFile.setStatus(KnowFile.STATUS_DRAFT);//草稿
			}
		}
		this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
				result, comment);
		service.save(knowFile);
		return result;
	}
	
	/**
	 * IT服务管理部服务管理岗审核
	 * @Methods Name serviceDeptServiceStationFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return 
	 * @Return String
	 */
	public String serviceDeptServiceStationFlag(String dataId,String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
				true);
		KnowFile oldKnowFile=knowFile.getOldKnowFile();
		if (result.equals("Y")) {// 同意，审批通过
			knowFile.setStatus(KnowFile.STATUS_FINISHED);//正式：发布和变更
			//2010-05-12 add by huzh for 变更时，原文件要变为过期状态 begin
			if(oldKnowFile!=null){
				oldKnowFile.setStatus(KnowFile.STATUS_TIMEOUT);//过期
			}
			//2010-05-12 add by huzh for 变更时，原文件要变为过期状态 end
		} else {// 不同意
			if(oldKnowFile!=null){
				knowFile.setStatus(KnowFile.STATUS_CHANGEDRAFT);//变更草稿
			}else{
				knowFile.setStatus(KnowFile.STATUS_DRAFT);//草稿
			}
		}
		this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
				result, comment);
		service.save(knowFile);
		return result;
	}
	
	/**
	 * 文件被打回
	 * @Methods Name knowFileGoBackFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return 
	 * @Return String
	 */
	public String knowFileGoBackFlag(String dataId,String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
				true);
		this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
				result, comment);
		KnowFile oldKnowFile=knowFile.getOldKnowFile();
		if(oldKnowFile!=null){
			knowFile.setStatus(KnowFile.STATUS_CHANGEDRAFT);//变更草稿
		}else{
			knowFile.setStatus(KnowFile.STATUS_DRAFT);//草稿
		}
		service.save(knowFile);
		return result;
	}
	
	/**
	 * 合同提交
	 * @Methods Name knowContractStartFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId 
	 * @Return void
	 */
	public void knowContractStartFlag(String dataId,String nodeId,
			            String nodeName, String processId) {
		KnowContract knowContract = (KnowContract) service.find(
				KnowContract.class, dataId, true);
		this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
				"", "");
		knowContract.setStatus(KnowContract.STATUS_APPROVING);// 2为提交审批中
		service.save(knowContract);
	}
	
	/**
	 * 审批合同准确性
	 * @Methods Name knowContractApprovalFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return 
	 * @Return String
	 */
	public String knowContractApprovalFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowContract knowContract = (KnowContract) service.find(
				KnowContract.class, dataId, true);
		if (result.equals("Y")) {// 同意，提交审批中
			knowContract.setStatus(KnowContract.STATUS_APPROVING);
		} else {// 不同意
			KnowContract oldKnowContract=knowContract.getOldKnowContract();
			if(oldKnowContract!=null){
				knowContract.setStatus(KnowContract.STATUS_CHANGEDRAFT);//变更草稿
			}else{
				knowContract.setStatus(KnowContract.STATUS_DRAFT);//草稿
			}
		}
		this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
				result, comment);
		service.save(knowContract);
		return result;
	}
	
	/**
	 * IT服务管理部三岗会审
	 * @Methods Name serviceDeptThreeStationFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return 
	 * @Return String
	 */
	public String serviceDeptThreeStationFlag(String dataId,String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId, true);
		KnowContract oldKnowContract=knowContract.getOldKnowContract();
		if (result.equals("Y")) {// 同意，审批通过
			knowContract.setStatus(KnowContract.STATUS_FINISHED);//发布和变更
			//2010-05-12 add by huzh for 变更时，原合同要变为过期状态 begin
			if(oldKnowContract!=null){
				oldKnowContract.setStatus(KnowContract.STATUS_TIMEOUT);
			}
			//2010-05-12 add by huzh for 变更时，原合同要变为过期状态 end
		} else {// 不同意
			if(oldKnowContract!=null){
				knowContract.setStatus(KnowContract.STATUS_CHANGEDRAFT);//变更草稿
			}else{
				knowContract.setStatus(KnowContract.STATUS_DRAFT);//草稿
			}
		}
		this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
				result, comment);
		service.save(knowContract);
		return result;
	}
	
	/**
	 * 合同被打回
	 * @Methods Name knowContractGoBackFlag
	 * @Create In Mar 29, 2010 By huzh
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return 
	 * @Return String
	 */
	public String knowContractGoBackFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowContract knowContract = (KnowContract) service.find(
				KnowContract.class, dataId, true);
		KnowContract oldKnowContract=knowContract.getOldKnowContract();
		if(oldKnowContract!=null){
			knowContract.setStatus(KnowContract.STATUS_CHANGEDRAFT);//变更草稿
		}else{
			knowContract.setStatus(KnowContract.STATUS_DRAFT);//草稿
		}
		this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
				result, comment);
		service.save(knowContract);
		return result;
	}
	
	/**
	 * 保存知识审批历史
	 * @Methods Name saveKnowledgeHis
	 * @Create In Mar 29, 2010 By huzh
	 * @param baseObject
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment 
	 * @Return void
	 */
	public void saveKnowledgeHis(BaseObject baseObject, String nodeId,
			String nodeName, String processId, String result, String comment) {
		if (baseObject instanceof KnowFile) {// 文件类型
			KnowFileAuditHis fileAuditHis = new KnowFileAuditHis();
			fileAuditHis.setResultFlag(result);
			fileAuditHis.setProcessId(Long.valueOf(processId));
			fileAuditHis.setApprover(UserContext.getUserInfo());
			fileAuditHis.setApproverDate(Calendar.getInstance().getTime());
			fileAuditHis.setKnowFile((KnowFile) baseObject);
			fileAuditHis.setNodeName(nodeName);
			fileAuditHis.setComment(comment);
			fileAuditHis.setNodeId(String.valueOf(nodeId));
			service.save(fileAuditHis);
		} else if (baseObject instanceof Knowledge) {// 解决方案类型
			KnowledgeAuditHis knowledgeAuditHis = new KnowledgeAuditHis();
			knowledgeAuditHis.setResultFlag(result);
			knowledgeAuditHis.setProcessId(Long.valueOf(processId));
			knowledgeAuditHis.setApprover(UserContext.getUserInfo());
			knowledgeAuditHis.setApproverDate(Calendar.getInstance().getTime());
			knowledgeAuditHis.setKnowledge((Knowledge) baseObject);
			knowledgeAuditHis.setNodeName(nodeName);
			knowledgeAuditHis.setComment(comment);
			knowledgeAuditHis.setNodeId(String.valueOf(nodeId));
			service.save(knowledgeAuditHis);
		} else {// 合同类型
			KnowContractAuditHis contractAuditHis = new KnowContractAuditHis();
			contractAuditHis.setResultFlag(result);
			contractAuditHis.setProcessId(Long.valueOf(processId));
			contractAuditHis.setApprover(UserContext.getUserInfo());
			contractAuditHis.setApproverDate(Calendar.getInstance().getTime());
			contractAuditHis.setKnowContract((KnowContract) baseObject);
			contractAuditHis.setNodeName(nodeName);
			contractAuditHis.setComment(comment);
			service.save(contractAuditHis);
		}
	}
	
}
