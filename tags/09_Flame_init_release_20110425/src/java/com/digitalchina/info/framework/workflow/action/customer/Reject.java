package com.digitalchina.info.framework.workflow.action.customer;

import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.action.EndAction;

public class Reject extends EndAction {

	@Override
	public void execute(ExecutionContext ec) throws Exception {
		System.out.println("Eding.......Reject Action");
	}

	@Override
	public String getDefinitionName() {
		return "customer";
	}

	@Override
	public String getNodeName() {
		return "¾Ü¾ø";
	}

}
