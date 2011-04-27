package com.zsgj.info.framework.workflow.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.ProcessState;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.WorkFlowGoBackService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.entity.SubProcessConfigUnit;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;

public class CreateSubProcessActionHandler implements ActionHandler {
	
	private ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private ConfigUnitService configUnitService = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
	
	public void execute(ExecutionContext ec) throws Exception {
		
		//���浱ǰ�ڵ��nodeName���Ա������̻��ˣ�
		ContextInstance ci = ec.getContextInstance();
		String nodeName = ec.getToken().getNode().getName();//��ǰ�ڵ�����
		String nodeDesc = ec.getToken().getNode().getName();//��ǰ�ڵ�����
		Long nodeId=ec.getToken().getNode().getId();
		Token token = ec.getToken();
		String tokenId = String.valueOf(token.getId());
		
		String nowNodeMessage = tokenId+"+"+nodeName;
		List goBack = (List)ci.getVariable("goBack");
		if(goBack!=null&&!"".equals(goBack)){
			goBack.add(nowNodeMessage);
			ci.setVariable("goBack", goBack);
		}else{
			goBack = new ArrayList();
			goBack.add(nowNodeMessage);
			ci.setVariable("goBack", goBack);
		}		
		
		System.out.println("����������");
		ProcessState processState = (ProcessState)ec.getToken().getNode();
		//�õ�������
		//˼·����ͨ���������¼ID�ͽڵ�ID��Ψһȷ��һ����̨��������������
		Long virtualDefintionId=(Long)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		Map mapParams=(Map)ec.getProcessInstance().getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		/*****************************************�ڵ��������*****************************************************************/
		Long processInstanceId = ec.getProcessInstance().getId();
		wfBack.saveWorkflowRegressionParams(virtualDefintionId, processInstanceId, nodeId,nodeName, nodeDesc ,mapParams);
		/*****************************************�ڵ��������*****************************************************************/
		//�������õõ����ProcessState�ڵ��ϵ�������
		SubProcessConfigUnit subProcessConfigUnit =configUnitService.findSubProcessConfigUnit(virtualDefintionId, processState.getId());
		Long  subProcessId=subProcessConfigUnit.getSubProcessId();
		String applyType=subProcessConfigUnit.getApplyType();
		String param=subProcessConfigUnit.getParam();
		
		VirtualDefinitionInfo vd=(VirtualDefinitionInfo)service.findUnique(VirtualDefinitionInfo.class, "id", subProcessId);
		//�������̵�id�͹����ļ���·�����ڸ����̵��������У���������ʵ�������󣬴Ӹ�����ʵ����������ȡ���Ž������̵�ʵ����������
		ec.getProcessInstance().getContextInstance().setVariable("subProcessId", vd.getId());
		ec.getProcessInstance().getContextInstance().setVariable("subProcessName", vd.getVirtualDefinitionName());
		ec.getProcessInstance().getContextInstance().setVariable("subProcessDesc", vd.getVirtualDefinitionDesc());
		ec.getProcessInstance().getContextInstance().setVariable("subProcessRulePath", vd.getRuleFileName());
		ec.getProcessInstance().getContextInstance().setVariable("subProcessParam", "applyType="+applyType+","+param);
		ProcessDefinition subProcessDefinition = ec.getJbpmContext().getGraphSession().loadProcessDefinition(vd.getProcessDefinitionId());
		System.out.println(subProcessDefinition.getDescription());
		/*�ҽ�������*/
		ps.formatDefinition(subProcessDefinition);
		ps.addActions(subProcessDefinition);
		processState.setSubProcessDefinition(subProcessDefinition);
	}

}
