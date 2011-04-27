package com.zsgj.info.framework.workflow.handler;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class ParamFormSubToSuperHandler implements ActionHandler {

	public void execute(ExecutionContext ec) throws Exception {
		//这个的目的就是把子流程中的参数传递到父流程当中(其中ec应该是父流程)
		Long id = ec.getProcessInstance().getId();
		System.out.println("希望是父流程"+id);
		//String test = (String)ec.getProcessInstance().getContextInstance().getVariable("testone");
		//System.out.println(test);
		
		System.out.println(ec.getToken().getSubProcessInstance().getId());
		//System.out.println(ec.getToken().getSubProcessInstance().getContextInstance().getVariable("flag"));
		//ec.getProcessInstance().getContextInstance().setVariable("testone", "mainProcess1");		
		
	}

}
