package com.digitalchina.info.framework.workflow.handler;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.action.BaseAction;


public class TransActionHandler extends BaseActionHandler implements WorkflowConstants, ActionHandler{
	
	private static Log log;
	static 
	{
		log = LogFactory.getLog(com.digitalchina.info.framework.workflow.handler.TransActionHandler.class);
	}
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -4618870197762199100L; 
	
	/**
	 * 
	 */
	public void execute(ExecutionContext executionContext) {
		log.debug("execute");
		if(executionContext.getTransition()==null) {
			System.out.println("no Transition!");
			return;
		}
		String trans = executionContext.getTransition().getName();
		//key = definitionName+"_"+nodeName+"_"+trans;			
		String definitionName = executionContext.getProcessInstance().getProcessDefinition().getName();
//		String nodeName = executionContext.getTransitionSource().getName();	
		//防止nodeName中的中文字符影响不同环境下的检索
		String nodeName = executionContext.getTransition().getDescription();
		//String nodeName = executionContext.getNode().getDescription();
		if(nodeName!=null) {
			nodeName = nodeName.trim();
		}
		else {
			nodeName = "";
		}
		String key = definitionName+"_"+nodeName+"_"+trans;
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
		System.out.println(trans+"**"+nodeName+"************TransActionHandler");
	}
}
