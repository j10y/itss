package com.zsgj.info.framework.workflow.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.action.BaseAction;
import com.zsgj.info.framework.workflow.info.NodeInfo;


public class NodeActionHandler extends BaseActionHandler implements ActionHandler,WorkflowConstants{
	private static Log log;
	static 
	{
		log = LogFactory.getLog(com.zsgj.info.framework.workflow.handler.NodeActionHandler.class);
	}
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -5941772379061882906L;

	/**
	 * NodeAction的执行接口
	 * 在此需要根据流程名，节点名和节点类型找到相应的配置模型
	 * 根据配置模型找到所有的配置单元
	 * 最后执行配置单元的执行函数
	 */
	public void execute(ExecutionContext executionContext){
//		NodeInfo nodeInfo = new NodeInfo(executionContext.getNode());
		//List
		
		
		log.debug("execute");
		//key = definitionName+"_"+nodeName+"_node";			
		String definitionName = executionContext.getProcessInstance().getProcessDefinition().getName();
//		String nodeName = executionContext.getNode().getName();	
		//防止nodeName中的中文字符影响不同环境下的检索
		String nodeName = executionContext.getNode().getDescription();
		if(nodeName!=null) {
			nodeName = nodeName.trim();
		}
		else {
			nodeName = "";
		}
		String eventType = executionContext.getEvent().getEventType();
		//String key = definitionName+"_"+nodeName+"_node";	
		String key = definitionName+"_"+nodeName+"_"+eventType;
		BaseAction action = getAction(key);
		System.out.println("node"+":"+nodeName+"********NodeActionHandler");
		if(action!=null) {
			try {
				action.execute(executionContext);
				//executionContext.getProcessDefinition().getVersion();
			}
			catch(Exception ex) {
				System.out.println("action ["+action.getClass().getName()+"] exception:");
				ex.printStackTrace();
			}
		}
	}
	

}
