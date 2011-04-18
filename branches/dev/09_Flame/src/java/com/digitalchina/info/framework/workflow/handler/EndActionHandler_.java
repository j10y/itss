package com.digitalchina.info.framework.workflow.handler;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.action.BaseAction;

/**
 * 根据用户定义对流程实例进行初始化，主要是上下文变量的设置
 * @Class Name InitActionHandler
 * @Author yang
 * @Create In 2008-4-14
 */
public class EndActionHandler_ extends BaseActionHandler implements WorkflowConstants,ActionHandler{
	private static Log log;
	static 
	{
		//log = LogFactory.getLog(com.digitalchina.info.framework.workflow.handler.EndActionHandler.class);
	}
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -2578196908394538407L;
	
		
	public void execute(ExecutionContext executionContext) {
		log.debug("execute");
		//key = definitionName+"_"+nodeName+"_end";			
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
		String key = definitionName+"_"+nodeName+"_end";
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
		System.out.println("end***"+nodeName+"********************EndActionHandler");
	}
	
}
