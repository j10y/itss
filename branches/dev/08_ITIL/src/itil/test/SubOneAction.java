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
		System.out.println("ִ�������̣����ҰѴӸ�������ȡ���Ĳ����õ�");	
		Long flag = (Long)executionContext.getProcessInstance().getContextInstance().getVariable("flag");
		
		///String test = (String)executionContext.getToken().getParent().getProcessInstance().getContextInstance().getVariable("testone");
		System.out.println("δ֪����"+executionContext.getToken().getProcessInstance().getId());
		
		//		
//		JbpmContext jbpmContext = executionContext.getJbpmContext();
//		//�õ������̵ı���ֵ
//		ProcessInstance nowInstance = executionContext.getProcessInstance();
//		String type = (String)nowInstance.getContextInstance().getVariable("type");
//		
//		//�õ������̲��ҵõ��������̵��������и�ֵ
//		ProcessInstance subInstance = nowInstance.getRootToken().getSubProcessInstance();
//		ContextInstance subContextInstance = subInstance.getContextInstance();		
//		subInstance.getContextInstance().setVariable("type", 36);
//		
//		//����������ʵ��
//		ProcessDefinition subProcessDefinition = executionContext.getJbpmContext().getGraphSession().findLatestProcessDefinition("");
//		ProcessState processState = (ProcessState)executionContext.getToken().getNode();
//		processState.setSubProcessDefinition(subProcessDefinition);
//		subInstance.signal();
//		
//		jbpmContext.save(subInstance);
	}
	
}
