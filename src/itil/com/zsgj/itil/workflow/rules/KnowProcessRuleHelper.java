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
	 * ��������ύ
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
		knowledge.setStatus(Knowledge.STATUS_APPROVING);// ������
		//2010-09-20 modified by huzh for �¼������󴴽��Ľ�����������õ�ǰ��¼����Ϊapprover begin
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
		//2010-09-20 modified by huzh for �¼������󴴽��Ľ�����������õ�ǰ��¼����Ϊapprover end
		service.save(knowledge);
	}
	
	/**
	 * �����������׼ȷ��
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
		if (result.equals("Y")) {// ͬ�⣬����ͨ��
			knowledge.setStatus(Knowledge.STATUS_FINISHED);//����ʱ�ͱ��ʱ
			//2010-05-12 add by huzh for Ϊ���ʱ��ԭ���������Ϊ����״̬ begin
			if(oldKnowledge!=null){//���
				oldKnowledge.setStatus(Knowledge.STATUS_TIMEOUT);//����״̬
				service.save(oldKnowledge);
			}
			//2010-05-12 add by huzh for Ϊ���ʱ��ԭ���������Ϊ����״̬ begin
		} else {// ��ͬ��
			//2010-05-11 modified by huzh for ������������ʱ��״̬���� begin
			EventSulotion eventSulotion=(EventSulotion)service.findUnique(EventSulotion.class, "knowledge", knowledge);
			if(eventSulotion!=null){//����ʱ���¼��������
				knowledge.setStatus(Knowledge.STATUS_RENEW);//������
			}else if(oldKnowledge!=null){
				knowledge.setStatus(Knowledge.STATUS_CHANGEDRAFT);//����ݸ�
			}else{//����ʱ���¼���������ͱ��ʱ��
				knowledge.setStatus(Knowledge.STATUS_DRAFT);//�ݸ�
			}
			//2010-05-11 modified by huzh for ������������ʱ����Ϊ���������¼���������ı�Ϊ������״̬�����Ǵӽ���������ı�Ϊ�ݸ壻��Ϊ������½��������Ϊ�ݸ壬ԭ����������� begin
		}
		this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,result, comment);
		service.save(knowledge);
		return result;
	}
	
	/**
	 * ������������
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
		//2010-05-11 modified by huzh for ������������ʱ��״̬���� begin
//		knowledge.setStatus(new Integer("0"));
		EventSulotion eventSulotion=(EventSulotion)service.findUnique(EventSulotion.class, "knowledge", knowledge);
		Knowledge oldKnowledge=knowledge.getOldKnowledge();
		if(eventSulotion!=null){
			knowledge.setStatus(Knowledge.STATUS_RENEW);//������
		}else if(oldKnowledge!=null){
			knowledge.setStatus(Knowledge.STATUS_CHANGEDRAFT);//����ݸ�
		}else{
			knowledge.setStatus(Knowledge.STATUS_DRAFT);//�ݸ�
		}
		//2010-05-11 modified by huzh for ������������ʱ��״̬���� begin
		this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
				result, comment);
		service.save(knowledge);
		return result;
	}
	
	/**
	 * �ļ��ύ
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
		knowFile.setStatus(KnowFile.STATUS_APPROVING);// 2Ϊ�ύ������
		service.save(knowFile);
	}
	
	/**
	 * �����ļ�׼ȷ��
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
		if (result.equals("Y")) {// ͬ�⣬�ύ������
			knowFile.setStatus(KnowFile.STATUS_APPROVING);
		} else {// ��ͬ��
			KnowFile oldKnowFile=knowFile.getOldKnowFile();
			if(oldKnowFile!=null){
				knowFile.setStatus(KnowFile.STATUS_CHANGEDRAFT);//����ݸ�
			}else{
				knowFile.setStatus(KnowFile.STATUS_DRAFT);//�ݸ�
			}
		}
		this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
				result, comment);
		service.save(knowFile);
		return result;
	}
	
	/**
	 * IT������������������
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
		if (result.equals("Y")) {// ͬ�⣬����ͨ��
			knowFile.setStatus(KnowFile.STATUS_FINISHED);//��ʽ�������ͱ��
			//2010-05-12 add by huzh for ���ʱ��ԭ�ļ�Ҫ��Ϊ����״̬ begin
			if(oldKnowFile!=null){
				oldKnowFile.setStatus(KnowFile.STATUS_TIMEOUT);//����
			}
			//2010-05-12 add by huzh for ���ʱ��ԭ�ļ�Ҫ��Ϊ����״̬ end
		} else {// ��ͬ��
			if(oldKnowFile!=null){
				knowFile.setStatus(KnowFile.STATUS_CHANGEDRAFT);//����ݸ�
			}else{
				knowFile.setStatus(KnowFile.STATUS_DRAFT);//�ݸ�
			}
		}
		this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
				result, comment);
		service.save(knowFile);
		return result;
	}
	
	/**
	 * �ļ������
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
			knowFile.setStatus(KnowFile.STATUS_CHANGEDRAFT);//����ݸ�
		}else{
			knowFile.setStatus(KnowFile.STATUS_DRAFT);//�ݸ�
		}
		service.save(knowFile);
		return result;
	}
	
	/**
	 * ��ͬ�ύ
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
		knowContract.setStatus(KnowContract.STATUS_APPROVING);// 2Ϊ�ύ������
		service.save(knowContract);
	}
	
	/**
	 * ������ͬ׼ȷ��
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
		if (result.equals("Y")) {// ͬ�⣬�ύ������
			knowContract.setStatus(KnowContract.STATUS_APPROVING);
		} else {// ��ͬ��
			KnowContract oldKnowContract=knowContract.getOldKnowContract();
			if(oldKnowContract!=null){
				knowContract.setStatus(KnowContract.STATUS_CHANGEDRAFT);//����ݸ�
			}else{
				knowContract.setStatus(KnowContract.STATUS_DRAFT);//�ݸ�
			}
		}
		this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
				result, comment);
		service.save(knowContract);
		return result;
	}
	
	/**
	 * IT����������ڻ���
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
		if (result.equals("Y")) {// ͬ�⣬����ͨ��
			knowContract.setStatus(KnowContract.STATUS_FINISHED);//�����ͱ��
			//2010-05-12 add by huzh for ���ʱ��ԭ��ͬҪ��Ϊ����״̬ begin
			if(oldKnowContract!=null){
				oldKnowContract.setStatus(KnowContract.STATUS_TIMEOUT);
			}
			//2010-05-12 add by huzh for ���ʱ��ԭ��ͬҪ��Ϊ����״̬ end
		} else {// ��ͬ��
			if(oldKnowContract!=null){
				knowContract.setStatus(KnowContract.STATUS_CHANGEDRAFT);//����ݸ�
			}else{
				knowContract.setStatus(KnowContract.STATUS_DRAFT);//�ݸ�
			}
		}
		this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
				result, comment);
		service.save(knowContract);
		return result;
	}
	
	/**
	 * ��ͬ�����
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
			knowContract.setStatus(KnowContract.STATUS_CHANGEDRAFT);//����ݸ�
		}else{
			knowContract.setStatus(KnowContract.STATUS_DRAFT);//�ݸ�
		}
		this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
				result, comment);
		service.save(knowContract);
		return result;
	}
	
	/**
	 * ����֪ʶ������ʷ
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
		if (baseObject instanceof KnowFile) {// �ļ�����
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
		} else if (baseObject instanceof Knowledge) {// �����������
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
		} else {// ��ͬ����
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
