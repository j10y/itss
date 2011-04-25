package com.digitalchina.itil.workflow.rules;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.itil.config.extlist.entity.ErpReqScopes;
import com.digitalchina.itil.config.extlist.entity.SRGroupFinanceInfo;
import com.digitalchina.itil.config.extlist.entity.SRTransmisEngineer;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventAuditHis;
import com.digitalchina.itil.event.entity.EventStatus;
import com.digitalchina.itil.finance.entity.BatchType;
import com.digitalchina.itil.finance.entity.RequirementFinanceType;
import com.digitalchina.itil.knowledge.entity.KnowContract;
import com.digitalchina.itil.knowledge.entity.KnowContractAuditHis;
import com.digitalchina.itil.knowledge.entity.KnowFile;
import com.digitalchina.itil.knowledge.entity.KnowFileAuditHis;
import com.digitalchina.itil.knowledge.entity.Knowledge;
import com.digitalchina.itil.knowledge.entity.KnowledgeAuditHis;
import com.digitalchina.itil.knowledge.entity.KnowledgeType;
import com.digitalchina.itil.require.entity.ERP_NormalNeed;
import com.digitalchina.itil.require.entity.ErpEngineerFeedback;
import com.digitalchina.itil.require.service.RequireService;
import com.digitalchina.itil.require.service.SRService;
import com.digitalchina.itil.service.entity.Constants;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis;
import com.digitalchina.itil.service.service.SCIRelationShipService;
/**
 * 
 * @Class Name ProcessRuleHelper
 * @Author YangTao
 * @Create In Mar 4, 2009
 */
public class SRProcessRuleHelper{
	Service service = (Service) ContextHolder.getBean("baseService");
	RequireService requireService = (RequireService) ContextHolder.getBean("requireService"); 
    SCIRelationShipService sciRelationShipService = (SCIRelationShipService)ContextHolder.getBean("sciRelationShipService");
	SRService srService=(SRService)ContextHolder.getBean("srService");
	
/***************************************************************************************************************************************/
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("������" + className + "����ȷ��");
			e.printStackTrace();
		}
		return clazz;
	}
	/**
	 * �����������ʷ����
	 * @Methods Name saveRequireHis
	 * @Create In Mar 23, 2009 By Administrator
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param configItem void
	 */
	public void saveRequireHis(String dataId,String nodeId,String processId,String nodeName,String result,String comment,ServiceItem serviceItem){
		
		ServiceItemApplyAuditHis siaah =new ServiceItemApplyAuditHis();
		siaah.setResultFlag(result);				
		siaah.setProcessId(Long.valueOf(processId));
		siaah.setApprover(UserContext.getUserInfo());
		siaah.setApproverDate(Calendar.getInstance().getTime());
		siaah.setServiceItem(serviceItem);
		siaah.setNodeName(nodeName);
		siaah.setComment(comment);
		siaah.setNodeId(nodeId);
		siaah.setRequirementId(Long.valueOf(dataId));
		
		try{
			service.save(siaah);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ���ɶ���ʵ��
	 * @Methods Name findObjectByClassName
	 * @Create In Mar 23, 2009 By Administrator
	 * @param reqClass
	 * @param dataId
	 * @return
	 * @throws Exception BeanWrapper
	 */
	public Object findObjectByClassName(String reqClass,String dataId)throws Exception{
		
		Class clazzClass = Class.forName(reqClass);
		Object object = service.find(clazzClass, dataId, true);
//		BeanWrapper obj = new BeanWrapperImpl(object);		
		return object;	
		
	}
	/**
	 * �������ʼ�ڵ�״̬�ı�(��Ҫ������״̬Ϊ�ͱ�����ʷ�Ȳ���)
	 * @Methods Name configStartFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId void
	 */
	public void requireStartFlag(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String reqClass)throws Exception{	
		
		Object result = this.findObjectByClassName(reqClass,dataId);			
		BeanWrapper obj = new BeanWrapperImpl(result);		
		obj.setPropertyValue("status", Constants.STATUS_APPROVING);		
		ServiceItem serviceItem=(ServiceItem)service.find(ServiceItem.class, serviceItemId);
		
		this.saveRequireHis(dataId,nodeId,processId,nodeName,"Y","",serviceItem);
		service.save(result);
		
	}
	
	/**
	 * ������������ڵ�ı�״̬
	 * @Methods Name requireAuditFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public void requireAuditFlag(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String result,String comment){	
		
		ServiceItem serviceItem=(ServiceItem)service.find(ServiceItem.class, serviceItemId);		
		this.saveRequireHis(dataId,nodeId,processId,nodeName,result,comment,serviceItem);			
		
	}
	/**
	 * �Ƿ���ҪBASIS����ʦ����
	 * @Methods Name selectBASISEngineer
	 * @Create In Apr 10, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String selectBASISEngineer(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String result,String comment){	
		ERP_NormalNeed need = (ERP_NormalNeed) service.find(ERP_NormalNeed.class, dataId);
		ErpEngineerFeedback erpEngineerFeedback = (ErpEngineerFeedback) service.findUnique(ErpEngineerFeedback.class, "erpNeed", need);
		Integer basisFlag = erpEngineerFeedback.getBasisFlag();
		if(basisFlag==null||ErpEngineerFeedback.NOTNEED.equals(basisFlag)){
			return "N";
		}
		return "Y";
	}
	/**
	 * �Ƿ���ҪEB����ʦ����
	 * @Methods Name selectEBEngineer
	 * @Create In Apr 10, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String selectEBEngineer(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String result,String comment){	
		ERP_NormalNeed need = (ERP_NormalNeed) service.find(ERP_NormalNeed.class, dataId);
		List<ErpReqScopes> erpReqScopes = service.find(ErpReqScopes.class, "erpReq", need);
		for(ErpReqScopes erpReqScope :erpReqScopes){
			if("ȫ����".equals(erpReqScope.getUseScope().getName())||
					"����".equals(erpReqScope.getUseScope().getName())||
					"����".equals(erpReqScope.getUseScope().getName())||
					"��Ӧ��".equals(erpReqScope.getUseScope().getName())){
				return "Y";
			}
		}
		return "N";
	}
	/**
	 * �Ƿ���Ҫ���乤��ʦ����
	 * @Methods Name selectTransmissionEngineer
	 * @Create In Apr 10, 2009 By Administrator
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment void
	 */
	public String selectTransmissionEngineer(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String result,String comment){	
		SRTransmisEngineer engineer = srService.findTransmissionEngineer(dataId);
		Integer needOrNot = engineer.getNeedOrNot();
		if(needOrNot==null||needOrNot.equals(SRTransmisEngineer.NOTNEED)){
			return "N";
		}
		return "Y";
	}
	/**
	 * �������ж�.���Ų���δѡ����������ת�����ͻ�IT������������
	 * ��ѡ�������������������Ϊ���ͻ������ݡ���ת������������ˡ�������Ϊ�����ϡ��򡰹�Ӧ�̡���ת�������������ˡ�
	 * @Methods Name requireBatchFlag
	 * @Create In Apr 15, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String requireBatchFlag(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String result,String comment){
		  SRGroupFinanceInfo financeInfo = srService.findGroupGinanceInfo(dataId);
		  RequirementFinanceType financeType = financeInfo.getFinanceType();
		  BatchType batchType = financeInfo.getBatchType();
		  if(financeType.getId()!=2){
		   result = "N";//�������������ֱ�������ͻ�IT��������
		  }else{
		   if(batchType.getId()==1){
		    result = "C";//ѡ��ͻ������ݣ��������������
		   }else{
		    result = "F";//ѡ�����ϻ�Ӧ�̣���������������
		   }
		  }
		  return result;
		 }
	
	/**
	 * ERP���Ի��������̣�����ʦʵʩ�����������ƣ�
	 * ������Ϊ�������࣬�ύ��ֱ�ӽ�������ͬʱ���û������ʼ���
	 * ������Ϊ���������࣬���������������
	 * @Methods Name engineerApplyToTestOrEnd
	 * @Create In May 17, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String engineerApplyToTestOrEnd(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String result,String comment){
		SRGroupFinanceInfo financeInfo = srService.findGroupGinanceInfo(dataId);
		  RequirementFinanceType financeType = financeInfo.getFinanceType();
		  if(financeType.getId()==2&&result.equals("Y")){
		   result = "toEnd";//�����������ֱ����������
		  }
		  return result;
	}
	/**
	 * �ڲ��ͻ���չ�ж�
	 * @Methods Name requireRemarkFlag
	 * @Create In Apr 15, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String requireRemarkFlag(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String result,String comment){
		  SRGroupFinanceInfo financeInfo = srService.findGroupGinanceInfo(dataId);
		  RequirementFinanceType financeType = financeInfo.getFinanceType();
		  BatchType batchType = financeInfo.getBatchType();
		  if(financeType.getId()==3){
		   return "Y";
		  }else{
		   return "N";
		  }
		 }
	/**
	 * ��������ؽڵ�״̬�ı�(��Ҫ������״̬Ϊ�ͱ�����ʷ�Ȳ���)
	 * @Methods Name configBackFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public void requireBackFlag(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String reqClass,String result,String comment)throws Exception{		
		
		Object obj = this.findObjectByClassName(reqClass,dataId);			
		BeanWrapper bw = new BeanWrapperImpl(obj);		
		bw.setPropertyValue("status", Constants.STATUS_DRAFT);
		
		ServiceItem serviceItem=(ServiceItem)service.find(ServiceItem.class, serviceItemId);		
		this.saveRequireHis(dataId,nodeId,processId,nodeName,result,comment,serviceItem);
		
		service.save(obj);	

	}
	
	/**
	 * �����������ڵ�״̬�ı�(��Ҫ������״̬Ϊ�ͱ�����ʷ�Ȳ���)
	 * @Methods Name configBackFlag
	 * @Create In Mar 23, 2009 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment void
	 */
	public void requireEndFlag(String dataId,String serviceItemId ,String nodeId,String nodeName,String processId,String reqClass)throws Exception{		
		ServiceItem serviceItem=(ServiceItem)service.find(ServiceItem.class, serviceItemId);
		BaseObject obj = (BaseObject) this.findObjectByClassName(reqClass,dataId);			
		BeanWrapper bw = new BeanWrapperImpl(obj);			
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(reqClass, obj);//�õ�ԭʵ��
		if(oldObject!=null){
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			//bw.setPropertyValue("id",oldId);
			BeanUtils.copyProperties(obj, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(obj);
			this.saveRequireHis(oldId.toString(),nodeId,processId,nodeName,"","",serviceItem);
			requireService.saveEntityToEvent(reqClass, oldObject);
		}else{
			service.save(obj);
			this.saveRequireHis(dataId,nodeId,processId,nodeName,"","",serviceItem);
			requireService.saveEntityToEvent(reqClass, obj);
		}
	}
	
	/************************************************************�������¼�����****************************************************************/
	public void saveEventHis(String nodeId,String nodeName,String processId,String result,String comment,Event event){
		
		EventAuditHis eah =new EventAuditHis();
		eah.setResultFlag(result);				
		eah.setProcessId(Long.valueOf(processId));
		eah.setApprover(UserContext.getUserInfo());
		eah.setApproverDate(Calendar.getInstance().getTime());
		eah.setEvent(event);
		eah.setNodeName(nodeName);
		eah.setComment(comment);
		eah.setNodeId(String.valueOf(nodeId));
		try{
			service.save(eah);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void eventStartFlag(String dataId, String nodeId, String nodeName, String processId)throws Exception{		
		EventStatus dealingStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "dealing");
		EventStatus reAssignStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "confirm");
		EventStatus endStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "finish");
		EventStatus userconfirmStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "userconfirm");
		Event event = (Event)service.find(Event.class, dataId,true);
		event.setEventStatus(dealingStatus);
		this.saveEventHis(nodeId, nodeName, processId, "", "", event);
		service.save(event);
	}
	public String  engineerProcessFlag(String dataId, String nodeId, String nodeName, String processId, String result, String comment)throws Exception {
		Event event = (Event)service.find(Event.class, dataId);
		//����Event״̬
		EventStatus dealingStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "dealing");
		EventStatus reAssignStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "confirm");
		EventStatus endStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "finish");
		EventStatus userconfirmStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "userconfirm");
		if(result.equals("cannotresolve")){
			event.setEventStatus(reAssignStatus);
		}else if(result.equals("resolveByuserself")){
			event.setEventStatus(endStatus);
		}else if(result.equals("touserconfirm")){
			event.setEventStatus(userconfirmStatus);
		}else{
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		//result�滻next
		return result;
	}
	public String headerProcessFlag(String dataId, String nodeId, String nodeName, String processId, String result, String comment) throws Exception {
		Event event = (Event)service.find(Event.class, dataId);
		//����Event״̬
		EventStatus dealingStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "dealing");
		if(result.equals("reAssign")){
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		//result�滻next
		return result;
	}
	
	public String otherOrgProcessFlag(String dataId, String nodeId, String nodeName, String processId, String result, String comment) throws Exception {
		Event event = (Event)service.find(Event.class, dataId);
		//����Event״̬
		EventStatus dealingStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "dealing");
		EventStatus reAssignStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "confirm");
		EventStatus endStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "finish");
		EventStatus userconfirmStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "userconfirm");
		if(result.equals("cannotresolve")){
			event.setEventStatus(reAssignStatus);
		}else if(result.equals("resolveByuserself")){
			event.setEventStatus(endStatus);
		}else if(result.equals("touserconfirm")){
			event.setEventStatus(userconfirmStatus);
		}else{
			event.setEventStatus(dealingStatus);
		}
		event.setEventStatus(dealingStatus);
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		//result�滻next
		return result;
	}
	
	public String userConfirmFlag(String dataId, String nodeId, String nodeName, String processId, String result, String comment) throws Exception {
		Event event = (Event)service.find(Event.class, dataId);
		//����Event״̬
		EventStatus dealingStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "dealing");
		EventStatus reAssignStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "confirm");
		EventStatus endStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "finish");
		EventStatus userconfirmStatus = (EventStatus)service.findUnique(EventStatus.class, "keyword", "userconfirm");
		if(result.equals("over")){
			event.setEventStatus(endStatus);
		}else{
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		//result�滻next
		return result;
	}
	
	//****************************************************֪ʶ����*******************************************
	public void saveKnowledgeHis(BaseObject baseObject , String nodeId, String nodeName, String processId, String result, String comment){
		if(baseObject instanceof KnowFile) {//�ļ�����
			KnowFileAuditHis kfah = new KnowFileAuditHis();
			kfah.setResultFlag(result);				
			kfah.setProcessId(Long.valueOf(processId));
			kfah.setApprover(UserContext.getUserInfo());
			kfah.setApproverDate(Calendar.getInstance().getTime());
			kfah.setKnowFile((KnowFile)baseObject);
//			kfah.setObjType(kfah.OBJTYPE_KNOWLEDGEFILE);
			kfah.setNodeName(nodeName);
			kfah.setComment(comment);
			kfah.setNodeId(String.valueOf(nodeId));
			service.save(kfah);
		}else if(baseObject instanceof Knowledge) {//�����������
			KnowledgeAuditHis kah = new KnowledgeAuditHis();
			kah.setResultFlag(result);				
			kah.setProcessId(Long.valueOf(processId));
			kah.setApprover(UserContext.getUserInfo());
			kah.setApproverDate(Calendar.getInstance().getTime());
			kah.setKnowledge((Knowledge)baseObject);
//			kah.setObjType(kah.OBJTYPE_KNOWLEDGE);
			kah.setNodeName(nodeName);
			kah.setComment(comment);
			kah.setNodeId(String.valueOf(nodeId));
			service.save(kah);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			KnowContractAuditHis kcah = new KnowContractAuditHis();
			kcah.setResultFlag(result);				
			kcah.setProcessId(Long.valueOf(processId));
			kcah.setApprover(UserContext.getUserInfo());
			kcah.setApproverDate(Calendar.getInstance().getTime());
			kcah.setKnowContract((KnowContract)baseObject);
//			kcah.setObjType(kcah.OBJTYPE_KNOWLEDGECONTRACT);
			kcah.setNodeName(nodeName);
			kcah.setComment(comment);
			service.save(kcah);
		}
	}
	public void knowledgeStartFlag(String dataId, String dataType,String nodeId,String nodeName, String processId){
		KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.KnowFile")) {//�ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId, true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId, "", "");
			knowFile.setStatus(new Integer("2"));//2Ϊ�ύ������
			service.save(knowFile);
		}else if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.Knowledge")) {//�����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class, dataId, true);
			knowledge.setStatus(new Integer("2"));//2Ϊ�ύ������
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, "", "");
			service.save(knowledge);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId, "", "");
			knowContract.setStatus(new Integer("2"));//2Ϊ�ύ������
			service.save(knowContract);
		}
	}
	public String judgeTypeFlag(String dataId,String dataType, String nodeId, String nodeName, String processId, String result,String comment){
		KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.KnowFile")) {//�ļ�����
			result = "tofa";
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId, true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId, result, comment);
			//service.save(knowFile);
		}else if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.Knowledge")) {//�����������
			result = "tosa";
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class, dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, result, comment);
			//service.save(knowledge);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			result = "toca";
			KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId, result, comment);
			//service.save(knowContract);
		}
		return result;
	}
	public String fileApprovalFlag(String dataId,String dataType, String nodeId, String nodeName, String processId, String result,String comment){
		KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.KnowFile")) {//�ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId, true);
			if(result.equals("Y")){//ͬ��
				knowFile.setStatus(new Integer("2"));
			}else{//��ͬ�⣬�ݸ�
				knowFile.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId, result, comment);
			service.save(knowFile);
		}else if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.Knowledge")) {//�����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class, dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, result, comment);
			//service.save(knowledge);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId, result, comment);
			//service.save(knowContract);
		}
		return result;
	}
	public String solutionApprovalFlag(String dataId,String dataType, String nodeId, String nodeName, String processId, String result,String comment){
		KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.KnowFile")) {//�ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId, true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId, result, comment);
			//service.save(knowFile);
		}else if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.Knowledge")) {//�����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class, dataId, true);
			if(result.equals("Y")){//ͬ��
				knowledge.setStatus(new Integer("1"));
			}else{//��ͬ�⣬�ݸ�
				knowledge.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, result, comment);
			service.save(knowledge);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId, result, comment);
			//service.save(knowContract);
		}
		return result;
	}
	public String contractApprovalFlag(String dataId,String dataType, String nodeId, String nodeName, String processId, String result,String comment){
		KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.KnowFile")) {//�ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId, true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId, result, comment);
			//service.save(knowFile);
		}else if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.Knowledge")) {//�����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class, dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, result, comment);
			//service.save(knowledge);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId, true);
			if(result.equals("Y")){//ͬ��
				knowContract.setStatus(new Integer("2"));
			}else{//��ͬ�⣬�ݸ�
				knowContract.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId, result, comment);
			service.save(knowContract);
		}
		return result;
	}
	public String serviceDeptServiceStationFlag(String dataId,String dataType, String nodeId, String nodeName, String processId, String result,String comment){
		KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.KnowFile")) {//�ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId, true);
			if(result.equals("Y")){//ͬ��
				knowFile.setStatus(new Integer("1"));
			}else{//��ͬ�⣬�ݸ�
				knowFile.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId, result, comment);
			service.save(knowFile);
		}else if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.Knowledge")) {//�����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class, dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, result, comment);
			//service.save(knowledge);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId, result, comment);
			//service.save(knowContract);
		}
		return result;
	}
	public String serviceDeptThreeStationFlag(String dataId,String dataType, String nodeId, String nodeName, String processId, String result,String comment){
		KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.KnowFile")) {//�ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId, true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId, result, comment);
			//service.save(knowFile);
		}else if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.Knowledge")) {//�����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class, dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, result, comment);
			//service.save(knowledge);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId, true);
			if(result.equals("Y")){//ͬ��
				knowContract.setStatus(new Integer("1"));
			}else{//��ͬ�⣬�ݸ�
				knowContract.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId, result, comment);
			service.save(knowContract);
		}
		return result;
	}
	public String gobackFlag(String dataId,String dataType, String nodeId, String nodeName, String processId, String result,String comment){
		KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.KnowFile")) {//�ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId, true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId, result, comment);
			knowFile.setStatus(new Integer("0"));
			service.save(knowFile);
		}else if(classNameString.endsWith("com.digitalchina.itil.knowledge.entity.Knowledge")) {//�����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class, dataId, true);
			knowledge.setStatus(new Integer("0"));
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, result, comment);
			service.save(knowledge);
		}else {//��ͬ����com.digitalchina.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(KnowContract.class, dataId, true);
			knowContract.setStatus(new Integer("0"));
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId, result, comment);
			service.save(knowContract);
		}
		return result;
	}
}
