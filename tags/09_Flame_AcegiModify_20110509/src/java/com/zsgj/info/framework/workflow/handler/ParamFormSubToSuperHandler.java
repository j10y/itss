package com.zsgj.info.framework.workflow.handler;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class ParamFormSubToSuperHandler implements ActionHandler {

	public void execute(ExecutionContext ec) throws Exception {
		//�����Ŀ�ľ��ǰ��������еĲ������ݵ������̵���(����ecӦ���Ǹ�����)
		Long id = ec.getProcessInstance().getId();
		System.out.println("ϣ���Ǹ�����"+id);
		//String test = (String)ec.getProcessInstance().getContextInstance().getVariable("testone");
		//System.out.println(test);
		
		System.out.println(ec.getToken().getSubProcessInstance().getId());
		//System.out.println(ec.getToken().getSubProcessInstance().getContextInstance().getVariable("flag"));
		//ec.getProcessInstance().getContextInstance().setVariable("testone", "mainProcess1");		
		
	}

}
