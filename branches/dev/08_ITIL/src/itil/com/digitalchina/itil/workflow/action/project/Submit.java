package com.digitalchina.itil.workflow.action.project;

import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.action.InitAction;
import com.digitalchina.itil.workflow.action.Constants;

public class Submit extends InitAction {

	@Override
	public void execute(ExecutionContext ec) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDefinitionName() {
		return Constants.DEF_PROJECT;
	}

	@Override
	public String getNodeName() {
		return Constants.NODE_SUBMIT;
	}

}
