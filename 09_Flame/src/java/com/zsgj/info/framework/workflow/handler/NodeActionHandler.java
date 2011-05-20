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
	 * NodeAction��ִ�нӿ�
	 * �ڴ���Ҫ�������������ڵ����ͽڵ������ҵ���Ӧ������ģ��
	 * ��������ģ���ҵ����е����õ�Ԫ
	 * ���ִ�����õ�Ԫ��ִ�к���
	 */
	public void execute(ExecutionContext executionContext){
//		NodeInfo nodeInfo = new NodeInfo(executionContext.getNode());
		//List
		
		
		log.debug("execute");
		//key = definitionName+"_"+nodeName+"_node";			
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
