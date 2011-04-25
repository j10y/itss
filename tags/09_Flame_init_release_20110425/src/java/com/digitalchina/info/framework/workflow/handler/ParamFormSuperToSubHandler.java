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
	//��һ����ҪĿ�ľ��Ǵ���
	public void execute(ExecutionContext ec) throws Exception {
//		System.out.println("�������̴���֮��Ϊ�����̴���");
		//��Ҫ������ת������,����������Ҫ������token 
		Token token = ec.getToken();	
//		System.out.println(token.getNode().getName());
		
		//Token parentToken = token.getParent();
		
		//�ȰѸ����̵�ȡ����
		ProcessInstance parentProcessInstance = token.getProcessInstance();//�õ���������ʵ��
		System.out.println("������"+parentProcessInstance.getId());
		
		/***************************���õ��������еĶ���д����������������ı�����дһ��**********************************************************************/
		
		String creator = UserContext.getUserInfo().getUserName();
		//�Ӹ�����ʵ����������ȡ��������id,�����ļ���·��,��������������������ҵ�����
		Long subProcessId = (Long)parentProcessInstance.getContextInstance().getVariable("subProcessId");
		String vname = (String)parentProcessInstance.getContextInstance().getVariable("subProcessName");
		String vPrcessDesc = (String)parentProcessInstance.getContextInstance().getVariable("subProcessDesc");
		String rulePath=(String)parentProcessInstance.getContextInstance().getVariable("subProcessRulePath");
		String subProcessParam=(String)parentProcessInstance.getContextInstance().getVariable("subProcessParam");
		//Map bizParam = (Map)parentProcessInstance.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		//������������Ҫ�Ķ�����һ����д�̶��ģ�һ���ִӸ�����ʵ����������ȡ��
		ContextInstance context = token.getSubProcessInstance().getContextInstance();
		context.setVariable("VIRTUALDEFINITIONINFO_ID", subProcessId);
		context.setVariable("VIRTUALDEFINITIONINFO_NAME", vname);
		context.setVariable("VIRTUALDEFINITIONINFO_DESC", vPrcessDesc);
		context.setVariable(WorkflowConstants.RESULT_FLAG, "");
		context.setVariable(WorkflowConstants.COMMENT_FLAG, "");	
		context.setVariable(WorkflowConstants.PROCESS_CREATOR_FLAG, creator);
		context.setVariable("rulePath", rulePath);
		
		//�Ӹ������е�bizParam�и������������֣��õ������̵�һЩҵ�����
		VirtualDefinitionInfo subProcessDefinition=(VirtualDefinitionInfo)service.findUnique(VirtualDefinitionInfo.class, "id", subProcessId);
		String subName= subProcessDefinition.getVirtualDefinitionName();
		Map map = new HashMap();//�����̵�ʵ���������е�bizParam
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
		System.out.println("������"+token.getSubProcessInstance().getId());
		/*************************************************************************************************/
	}

}
