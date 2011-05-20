package com.zsgj.itil.workflow.action.project;

import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.workflow.action.EndAction;
import com.zsgj.itil.workflow.action.Constants;

public class Endup extends EndAction {

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
		return Constants.NODE_END;
	}

}
