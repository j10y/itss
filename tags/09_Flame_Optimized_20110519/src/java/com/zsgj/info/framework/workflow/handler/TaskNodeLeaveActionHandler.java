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
		log.info(vProcessDesc+"(流程)"+"进入"+nodeName+"的离开事件");
		WorkflowRecordTaskInfo taskInfo = cs.findWorkflowRecordTaskInfo(dataId,vProcessName);
		if(taskInfo!=null&&!"".equals(taskInfo)){
			try{
				service.remove(taskInfo);				
			}catch(Exception e){
				new RuntimeException("在"+vProcessName+"的"+nodeName+"节点"+"删除任务接点信息的时候发生异常");
			}
		}
		log.info(vProcessDesc+"(流程)"+"进入"+nodeName+"离开事件之后完成把当前节点的特殊参数删除掉的任务");
	}

}
