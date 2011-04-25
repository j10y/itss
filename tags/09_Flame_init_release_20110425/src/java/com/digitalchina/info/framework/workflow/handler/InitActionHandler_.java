package com.digitalchina.info.framework.workflow.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
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
public class InitActionHandler_ extends BaseActionHandler implements WorkflowConstants,ActionHandler{
	private static Log log;
	static 
	{
		//log = LogFactory.getLog(com.digitalchina.info.framework.workflow.handler.InitActionHandler.class);
	}
	/**
	 * //通过流程名和节点名得到相应的处理action来处理
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -2578196908394538407L;
	
	//预设的变量容器,其中的值是从定义的图中传过来的
	Map variables;
	
	public void execute(ExecutionContext executionContext) throws Exception {
		log.debug("execute");
		//设置用户设计的上下文变量,值为变量类型
		initVariables(executionContext);
		
		//key = definitionName+"_"+nodeName+"_init";			
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
		String key = definitionName+"_"+nodeName+"_init";
		BaseAction action = getAction(key);
		if(action!=null) {
			try {
				action.execute(executionContext);
			}
			catch(Exception ex) {
				log.error("action ["+action.getClass().getName()+"] exception:");
				System.out.println("action ["+action.getClass().getName()+"] exception:");
				ex.printStackTrace();
			}
		}
		System.out.println("init***"+nodeName+"********************InitActionHandler");
		//executionContext.getNode().
	
	
	}

	private void initVariables(ExecutionContext executionContext) {
		//设置用户设计的上下文变量,值为变量类型
		if(variables==null) {
			return;
		}
		Set keys = variables.keySet();
		Iterator it = keys.iterator();
		ContextInstance ci = executionContext.getContextInstance();
		while(it.hasNext()) {
			String key = ((String)it.next()).trim();
			if(key.equalsIgnoreCase("String")) {
				ci.createVariable(key, "");//空值
			}
			else {//暂时只处理String一种类型
				ci.createVariable(key, "");//空值
			}
		}		
		System.out.println(variables+"***********************InitActionHandler");
	}
	
	public Map getVariables() {
		return variables;
	}

	public void setVariables(Map variables) {
		this.variables = variables;
	}

	public InitActionHandler_() {		
	}
	
	public InitActionHandler_(String configuration){
		System.out.println("==Action1Handler contstructor==");
		System.out.println("==configuration is:"+configuration+"==");
	}

	public void setConfiguration(String configuration){
		System.out.println("==Action1Handler setConfiguration==");
		System.out.println("==configuration is:"+configuration+"==");
	}
	
}
