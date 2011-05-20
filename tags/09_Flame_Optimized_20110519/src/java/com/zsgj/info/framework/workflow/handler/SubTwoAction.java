package com.zsgj.info.framework.workflow.handler;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class SubTwoAction implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		System.out.println(executionContext.getProcessInstance().getId());
		Long id = (Long)executionContext.getProcessInstance().getContextInstance().getVariable("flag");
		System.out.println(id);
	}

}
