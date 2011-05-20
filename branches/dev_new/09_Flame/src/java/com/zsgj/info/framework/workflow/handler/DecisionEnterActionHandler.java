package com.zsgj.info.framework.workflow.handler;

import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.Decision;

/** 
 * @author 杨涛 E-mail: yangtao@info.com
 * @version 创建时间：Apr 16, 2009 2:23:16 PM 
 * 类说明 
 */

@SuppressWarnings("serial")
public class DecisionEnterActionHandler implements ActionHandler{

	public void execute(ExecutionContext executionContext) throws Exception {
		// TODO Auto-generated method stub
		//把DecisionActionHander加在这个Decision节点上
		Action decisionAction = DelegationFactory.getAction(DelegationFactory.JPDL_DECISION_ACTION);
	    Decision decision=(Decision)executionContext.getToken().getNode();
	    decision.setAction(decisionAction);
	}

}
