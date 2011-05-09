package com.zsgj.info.framework.workflow.handler;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
public class RecordTaskIdActionHandler implements ActionHandler{

	public void execute(ExecutionContext ec) throws Exception {
		
		System.out.println(ec.getTaskInstance().getActorId());
		System.out.println("sdfsdf");
		
		
	}

}
