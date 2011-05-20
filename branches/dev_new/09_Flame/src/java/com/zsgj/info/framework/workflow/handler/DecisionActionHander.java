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
 * @author ���� E-mail: yangtao@info.com
 * @version ����ʱ�䣺Apr 16, 2009 10:32:32 AM 
 * ��˵�� 
 */

@SuppressWarnings("serial")
public class DecisionActionHander implements DecisionHandler{
	
	private Service service = (Service) ContextHolder.getBean("baseService");
	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
	
	public String decide(ExecutionContext executionContext) throws Exception {
		// TODO Auto-generated method stub
		//���浱ǰ�ڵ��nodeName���Ա������̻��ˣ�
		ContextInstance ci = executionContext.getContextInstance();
		String nodeName = executionContext.getToken().getNode().getName();//��ǰ�ڵ�����
		String nodeDesc = executionContext.getToken().getNode().getName();//��ǰ�ڵ�����
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
		
        //��������ڵ�Ĺ����߲�ͬ��transition
		String transitionName="";
		String rulePath=(String)executionContext.getVariable("rulePath");
		Map mapParams=(Map)executionContext.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		Long processId=(Long)executionContext.getVariable("VIRTUALDEFINITIONINFO_ID");
		Long nodeId=executionContext.getToken().getNode().getId();
		//String nodeName=executionContext.getToken().getNode().getName();
		List<RuleConfigUnit> list=service.find(RuleConfigUnit.class, "processId",processId);
		
		/*****************************************�ڵ��������*****************************************************************/
		Long processInstanceId = executionContext.getProcessInstance().getId();
		wfBack.saveWorkflowRegressionParams(processId, processInstanceId, nodeId,nodeName, nodeDesc ,mapParams);
		/*****************************************�ڵ��������*****************************************************************/
		
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
			if(rulePath!=null){//�������ļ��еĹ���
				transitionName=ProcessRuleHelper.executeRule(rulePath, mapParams);
			} 
		}else{//���û�й���Ļ�����ȡĬ�ϵ�һ��·��
			List<Transition> transitionsList=executionContext.getToken().getNode().getLeavingTransitions();
			transitionName=transitionsList.get(0).getName();
		}
		if(transitionName==null){//���ִ�й���û�з��صĻ�����ȡĬ�ϵ�һ��·��
			List<Transition> transitionsList=executionContext.getToken().getNode().getLeavingTransitions();
			transitionName=transitionsList.get(0).getName();
		}
		return transitionName;
	}

}
