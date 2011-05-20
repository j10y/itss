package com.zsgj.itil.workflow.action.project;

import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.workflow.action.InitAction;
import com.zsgj.itil.workflow.action.Constants;

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
