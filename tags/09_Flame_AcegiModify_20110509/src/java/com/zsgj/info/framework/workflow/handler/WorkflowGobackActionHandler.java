package com.zsgj.info.framework.workflow.handler;

import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.WorkFlowGoBackService;
import com.zsgj.info.framework.workflow.base.JbpmContextFactory;
public class WorkflowGobackActionHandler extends BaseAction implements ActionHandler{

	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
	
	public String goBackWorkflow()throws Exception{
		
		Long fromNodeId = null;
		String fromNodeName = "";
		String json = "";
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		String taskId = super.getRequest().getParameter("taskId");
		TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.valueOf(taskId));
		ContextInstance ci = taskInstance.getContextInstance();
		
		String nodeNames = (String)ci.getVariable("goBack");
		String[] goBack = nodeNames.split(";");//当到了当前节点的时候，要回退到得那个节点就是goBack中最后一个节点名称
		if(goBack.length!=0){
			nodeNames = goBack[goBack.length-2];
		}else{
			throw new Exception("您的回退流程中没有指定上一个节点是谁");
		}
		
		//需要为service准备参数，包括流程实例ID，节点ID，任务名称
		ProcessInstance processInstance = taskInstance.getProcessInstance();
		Long processId = processInstance.getId();//当前流程实例ID
		Node fromNode = processInstance.getProcessDefinition().getNode(nodeNames);
		//开始找上一个节点的NODEID和NODENAME
		if(fromNode!=null&&!"".equals(fromNode)){
			fromNodeId = fromNode.getId();
			fromNodeName = fromNode.getName();
		}
		try{
			if(fromNodeId!=null&&!"".equals(fromNodeName)){
				wfBack.saveWorkFlowGoBack(processId, fromNodeId, fromNodeName);
			}
		}catch(Exception e){
			json = "{success:false}";
			return json;
		}
		json = "{success:true}";
		return json;
	}
	
	public void execute(ExecutionContext executionContext) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("=============================");
		this.goBackWorkflow();
		System.out.println("=============================");
	}

}
