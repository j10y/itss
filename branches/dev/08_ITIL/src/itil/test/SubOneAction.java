package test;

import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.node.ProcessState;



public class SubOneAction implements ActionHandler{

	public void execute(ExecutionContext executionContext) throws Exception {
		System.out.println("执行子流程，并且把从父流程中取出的参数得到");	
		Long flag = (Long)executionContext.getProcessInstance().getContextInstance().getVariable("flag");
		
		///String test = (String)executionContext.getToken().getParent().getProcessInstance().getContextInstance().getVariable("testone");
		System.out.println("未知流程"+executionContext.getToken().getProcessInstance().getId());
		
		//		
//		JbpmContext jbpmContext = executionContext.getJbpmContext();
//		//得到父流程的变量值
//		ProcessInstance nowInstance = executionContext.getProcessInstance();
//		String type = (String)nowInstance.getContextInstance().getVariable("type");
//		
//		//得到子流程并且得到在子流程的上下文中赋值
//		ProcessInstance subInstance = nowInstance.getRootToken().getSubProcessInstance();
//		ContextInstance subContextInstance = subInstance.getContextInstance();		
//		subInstance.getContextInstance().setVariable("type", 36);
//		
//		//创建子流程实例
//		ProcessDefinition subProcessDefinition = executionContext.getJbpmContext().getGraphSession().findLatestProcessDefinition("");
//		ProcessState processState = (ProcessState)executionContext.getToken().getNode();
//		processState.setSubProcessDefinition(subProcessDefinition);
//		subInstance.signal();
//		
//		jbpmContext.save(subInstance);
	}
	
}
