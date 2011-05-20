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
		
		//保存当前节点的nodeName，以便于流程回退；
		ContextInstance ci = ec.getContextInstance();
		String nodeName = ec.getToken().getNode().getName();//当前节点名称
		String nodeDesc = ec.getToken().getNode().getName();//当前节点描述
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
		
		System.out.println("创建子流程");
		ProcessState processState = (ProcessState)ec.getToken().getNode();
		//得到父流程
		//思路就是通过父虚拟记录ID和节点ID，唯一确定一个后台配置子流程名称
		Long virtualDefintionId=(Long)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		Map mapParams=(Map)ec.getProcessInstance().getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		/*****************************************节点参数保存*****************************************************************/
		Long processInstanceId = ec.getProcessInstance().getId();
		wfBack.saveWorkflowRegressionParams(virtualDefintionId, processInstanceId, nodeId,nodeName, nodeDesc ,mapParams);
		/*****************************************节点参数保存*****************************************************************/
		//根据配置得到这个ProcessState节点上的子流程
		SubProcessConfigUnit subProcessConfigUnit =configUnitService.findSubProcessConfigUnit(virtualDefintionId, processState.getId());
		Long  subProcessId=subProcessConfigUnit.getSubProcessId();
		String applyType=subProcessConfigUnit.getApplyType();
		String param=subProcessConfigUnit.getParam();
		
		VirtualDefinitionInfo vd=(VirtualDefinitionInfo)service.findUnique(VirtualDefinitionInfo.class, "id", subProcessId);
		//把子流程的id和规则文件的路径放在父流程的上下文中，在子流程实例创建后，从父流程实例上下文中取出放进子流程的实例上下文中
		ec.getProcessInstance().getContextInstance().setVariable("subProcessId", vd.getId());
		ec.getProcessInstance().getContextInstance().setVariable("subProcessName", vd.getVirtualDefinitionName());
		ec.getProcessInstance().getContextInstance().setVariable("subProcessDesc", vd.getVirtualDefinitionDesc());
		ec.getProcessInstance().getContextInstance().setVariable("subProcessRulePath", vd.getRuleFileName());
		ec.getProcessInstance().getContextInstance().setVariable("subProcessParam", "applyType="+applyType+","+param);
		ProcessDefinition subProcessDefinition = ec.getJbpmContext().getGraphSession().loadProcessDefinition(vd.getProcessDefinitionId());
		System.out.println(subProcessDefinition.getDescription());
		/*挂接子流程*/
		ps.formatDefinition(subProcessDefinition);
		ps.addActions(subProcessDefinition);
		processState.setSubProcessDefinition(subProcessDefinition);
	}

}
