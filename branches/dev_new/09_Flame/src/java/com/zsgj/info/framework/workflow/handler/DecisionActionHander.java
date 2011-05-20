package com.zsgj.info.framework.workflow.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.DecisionHandler;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.workflow.WorkFlowGoBackService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.entity.RuleConfigUnit;
import com.zsgj.info.framework.workflow.rules.ProcessRuleHelper;

/** 
 * @author 杨涛 E-mail: yangtao@info.com
 * @version 创建时间：Apr 16, 2009 10:32:32 AM 
 * 类说明 
 */

@SuppressWarnings("serial")
public class DecisionActionHander implements DecisionHandler{
	
	private Service service = (Service) ContextHolder.getBean("baseService");
	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
	
	public String decide(ExecutionContext executionContext) throws Exception {
		// TODO Auto-generated method stub
		//保存当前节点的nodeName，以便于流程回退；
		ContextInstance ci = executionContext.getContextInstance();
		String nodeName = executionContext.getToken().getNode().getName();//当前节点名称
		String nodeDesc = executionContext.getToken().getNode().getName();//当前节点描述
		Token token = executionContext.getToken();
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
		
        //根据这个节点的规则，走不同的transition
		String transitionName="";
		String rulePath=(String)executionContext.getVariable("rulePath");
		Map mapParams=(Map)executionContext.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		Long processId=(Long)executionContext.getVariable("VIRTUALDEFINITIONINFO_ID");
		Long nodeId=executionContext.getToken().getNode().getId();
		//String nodeName=executionContext.getToken().getNode().getName();
		List<RuleConfigUnit> list=service.find(RuleConfigUnit.class, "processId",processId);
		
		/*****************************************节点参数保存*****************************************************************/
		Long processInstanceId = executionContext.getProcessInstance().getId();
		wfBack.saveWorkflowRegressionParams(processId, processInstanceId, nodeId,nodeName, nodeDesc ,mapParams);
		/*****************************************节点参数保存*****************************************************************/
		
		String ruleName=null;
		for(RuleConfigUnit rc : list){
			if(nodeId.equals(rc.getNodeId())){
				ruleName=rc.getRuleName();
			}
		}
		if(ruleName!=null){
			mapParams.put("ruleName", ruleName);
			mapParams.put("nodeId",String.valueOf(nodeId));
			mapParams.put("nodeName",nodeName);
			if(rulePath!=null){//调规则文件中的规则
				transitionName=ProcessRuleHelper.executeRule(rulePath, mapParams);
			} 
		}else{//如果没有规则的话，就取默认的一条路径
			List<Transition> transitionsList=executionContext.getToken().getNode().getLeavingTransitions();
			transitionName=transitionsList.get(0).getName();
		}
		if(transitionName==null){//如果执行规则，没有返回的话，就取默认的一条路径
			List<Transition> transitionsList=executionContext.getToken().getNode().getLeavingTransitions();
			transitionName=transitionsList.get(0).getName();
		}
		return transitionName;
	}

}
