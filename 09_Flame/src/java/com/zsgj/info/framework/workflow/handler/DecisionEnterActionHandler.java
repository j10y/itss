package com.zsgj.info.framework.workflow.handler;

import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.Decision;

/** 
 * @author ���� E-mail: yangtao@info.com
 * @version ����ʱ�䣺Apr 16, 2009 2:23:16 PM 
 * ��˵�� 
 */

@SuppressWarnings("serial")
public class DecisionEnterActionHandler implements ActionHandler{

	public void execute(ExecutionContext executionContext) throws Exception {
		// TODO Auto-generated method stub
		//��DecisionActionHander�������Decision�ڵ���
		Action decisionAction = DelegationFactory.getAction(DelegationFactory.JPDL_DECISION_ACTION);
	    Decision decision=(Decision)executionContext.getToken().getNode();
	    decision.setAction(decisionAction);
	}

}
