package com.digitalchina.info.framework.workflow.action.customer;

import org.jbpm.graph.def.Event;
import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.action.TaskAction;

public class LeaveCheck extends TaskAction{
	
	@Override
	public void execute(ExecutionContext ec) throws Exception {	

		System.out.println("LeaveCheckMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMmm");
	}

	@Override
	public String getDefinitionName() {
		return "customer";
	}

	@Override
	public String getEventType() {
		return Event.EVENTTYPE_NODE_LEAVE;
	}

	@Override
	public String getNodeName() {
		return "¼ì²é";
	}
}
