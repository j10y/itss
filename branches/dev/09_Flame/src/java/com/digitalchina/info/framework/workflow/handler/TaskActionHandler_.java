package com.digitalchina.info.framework.workflow.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.action.BaseAction;


public class TaskActionHandler_ extends BaseActionHandler implements ActionHandler,WorkflowConstants{
	private static Log log;
	static 
	{
		//log = LogFactory.getLog(com.digitalchina.info.framework.workflow.handler.TaskActionHandler.class);
	}
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -5941772379061882906L;

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext executionContext) {
		log.debug("execute");
		String eventType = executionContext.getEvent().getEventType();
		//key = definitionName+"_"+nodeName+"_"+eventType;	
		ProcessDefinition pd = executionContext.getProcessInstance().getProcessDefinition();
//		List nodes = pd.getNodes();
//		String nodeName = executionContext.getNode().getName();
		
		//防止nodeName中的中文字符影响不同环境下的检索
		String nodeName = executionContext.getNode().getDescription();
		if(nodeName!=null) {
			nodeName = nodeName.trim();
		}		
		else {
			nodeName = "";
		}
		String key = pd.getName()+"_"+nodeName+"_"+eventType;
		BaseAction action = getAction(key);
		if(action!=null) {
			try {
				action.execute(executionContext);
			}
			catch(Exception ex) {
				System.out.println("action ["+action.getClass().getName()+"] exception:");
				ex.printStackTrace();
			}
		}
		System.out.println(eventType+"**"+nodeName+"********TaskActionHandler");
	}
	
	public static void main(String[] argv) {
		//TaskActionHandler nah = new TaskActionHandler();
//		snah.getActions();
	}
}
