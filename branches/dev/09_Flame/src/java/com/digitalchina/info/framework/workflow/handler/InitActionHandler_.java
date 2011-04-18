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
 * �����û����������ʵ�����г�ʼ������Ҫ�������ı���������
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
	 * //ͨ���������ͽڵ����õ���Ӧ�Ĵ���action������
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -2578196908394538407L;
	
	//Ԥ��ı�������,���е�ֵ�ǴӶ����ͼ�д�������
	Map variables;
	
	public void execute(ExecutionContext executionContext) throws Exception {
		log.debug("execute");
		//�����û���Ƶ������ı���,ֵΪ��������
		initVariables(executionContext);
		
		//key = definitionName+"_"+nodeName+"_init";			
		String definitionName = executionContext.getProcessInstance().getProcessDefinition().getName();
//		String nodeName = executionContext.getNode().getName();	
		//��ֹnodeName�е������ַ�Ӱ�첻ͬ�����µļ���
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
		//�����û���Ƶ������ı���,ֵΪ��������
		if(variables==null) {
			return;
		}
		Set keys = variables.keySet();
		Iterator it = keys.iterator();
		ContextInstance ci = executionContext.getContextInstance();
		while(it.hasNext()) {
			String key = ((String)it.next()).trim();
			if(key.equalsIgnoreCase("String")) {
				ci.createVariable(key, "");//��ֵ
			}
			else {//��ʱֻ����Stringһ������
				ci.createVariable(key, "");//��ֵ
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
