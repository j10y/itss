package com.digitalchina.info.framework.workflow.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;

public class ParamFormSuperToSubHandler implements ActionHandler {
	private Service service = (Service) ContextHolder.getBean("baseService");
	//这一步主要目的就是传参
	public void execute(ExecutionContext ec) throws Exception {
//		System.out.println("在子流程创建之后为子流程传参");
		//主要任务是转换参数,父子流程主要区分用token 
		Token token = ec.getToken();	
//		System.out.println(token.getNode().getName());
		
		//Token parentToken = token.getParent();
		
		//先把父流程的取出来
		ProcessInstance parentProcessInstance = token.getProcessInstance();//得到父的流程实例
		System.out.println("父流程"+parentProcessInstance.getId());
		
		/***************************公用的上下文中的东西写死，而特殊的上下文变量重写一遍**********************************************************************/
		
		String creator = UserContext.getUserInfo().getUserName();
		//从父流程实例上下文中取出子流程id,规则文件的路径,和启动这个子流程所需的业务参数
		Long subProcessId = (Long)parentProcessInstance.getContextInstance().getVariable("subProcessId");
		String vname = (String)parentProcessInstance.getContextInstance().getVariable("subProcessName");
		String vPrcessDesc = (String)parentProcessInstance.getContextInstance().getVariable("subProcessDesc");
		String rulePath=(String)parentProcessInstance.getContextInstance().getVariable("subProcessRulePath");
		String subProcessParam=(String)parentProcessInstance.getContextInstance().getVariable("subProcessParam");
		//Map bizParam = (Map)parentProcessInstance.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		//把子流程中需要的东西，一部分写固定的，一部分从父流程实例上下文中取出
		ContextInstance context = token.getSubProcessInstance().getContextInstance();
		context.setVariable("VIRTUALDEFINITIONINFO_ID", subProcessId);
		context.setVariable("VIRTUALDEFINITIONINFO_NAME", vname);
		context.setVariable("VIRTUALDEFINITIONINFO_DESC", vPrcessDesc);
		context.setVariable(WorkflowConstants.RESULT_FLAG, "");
		context.setVariable(WorkflowConstants.COMMENT_FLAG, "");	
		context.setVariable(WorkflowConstants.PROCESS_CREATOR_FLAG, creator);
		context.setVariable("rulePath", rulePath);
		
		//从父流程中的bizParam中根据子流程名字，得到子流程的一些业务参数
		VirtualDefinitionInfo subProcessDefinition=(VirtualDefinitionInfo)service.findUnique(VirtualDefinitionInfo.class, "id", subProcessId);
		String subName= subProcessDefinition.getVirtualDefinitionName();
		Map map = new HashMap();//子流程的实例上下文中的bizParam
		if(subProcessParam!=null&&!"".equals(subProcessParam)){
			String[] variables=subProcessParam.split(",");
			for(String v : variables){
				String key=v.substring(0,v.indexOf("="));
				String value=v.substring(v.indexOf("=")+1);
				map.put(key, value);
			}
		}

		map.put("processId", String.valueOf(token.getSubProcessInstance().getId()));
		context.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, map);
		System.out.println("子流程"+token.getSubProcessInstance().getId());
		/*************************************************************************************************/
	}

}
