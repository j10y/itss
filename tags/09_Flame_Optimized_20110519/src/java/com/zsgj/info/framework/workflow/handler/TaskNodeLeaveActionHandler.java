package com.zsgj.info.framework.workflow.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;
public class TaskNodeLeaveActionHandler implements ActionHandler{
	
	private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private static Logger log;
	static 
	{
		log = Logger.getLogger("workflowlog");
	}
	
	public void execute(ExecutionContext ec) throws Exception {
		
		String nodeName = ec.getToken().getNode().getName();
		String vProcessName = (String)ec.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_NAME");
		String vProcessDesc = (String)ec.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
		Map mapParams=(Map)ec.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		String dataId = (String)mapParams.get("dataId");
		log.info(vProcessDesc+"(����)"+"����"+nodeName+"���뿪�¼�");
		WorkflowRecordTaskInfo taskInfo = cs.findWorkflowRecordTaskInfo(dataId,vProcessName);
		if(taskInfo!=null&&!"".equals(taskInfo)){
			try{
				service.remove(taskInfo);				
			}catch(Exception e){
				new RuntimeException("��"+vProcessName+"��"+nodeName+"�ڵ�"+"ɾ������ӵ���Ϣ��ʱ�����쳣");
			}
		}
		log.info(vProcessDesc+"(����)"+"����"+nodeName+"�뿪�¼�֮����ɰѵ�ǰ�ڵ���������ɾ����������");
	}

}
