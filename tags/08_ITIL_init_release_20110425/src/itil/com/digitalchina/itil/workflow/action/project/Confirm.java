package com.digitalchina.itil.workflow.action.project;

import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.action.TaskAction;
import com.digitalchina.itil.workflow.action.Constants;

public class Confirm extends TaskAction {

	@Override
	public void execute(ExecutionContext ec) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDefinitionName() {
		return Constants.DEF_PROJECT;
	}

	@Override
	public String getEventType() {
		return EVENTTYPE_NODE_LEAVE;
	}

	@Override
	public String getNodeName() {
		return Constants.NODE_CONFIRM;
	}

}
