package com.zsgj.info.framework.workflow.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.action.Script;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.Fork;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.WorkFlowGoBackService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.web.adapter.struts.Mail;



public class SendMailNodeActionHandler implements ActionHandler {

	private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
	
	public void execute(ExecutionContext ec) throws Exception {
		
		
		//保存当前节点的nodeName，以便于流程回退；
		ContextInstance ci = ec.getContextInstance();
		String nodeName = ec.getToken().getNode().getName();//当前节点名称
		String nodeDesc = ec.getToken().getNode().getName();//当前节点描述
		Long nodeId=ec.getToken().getNode().getId();
		Map mapParams=(Map)ec.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		Long processId=(Long)ec.getVariable("VIRTUALDEFINITIONINFO_ID");
		Token token = ec.getToken();
		String tokenId = String.valueOf(token.getId());
		
		String nowNodeMessage = tokenId+","+nodeName;
		List goBack = (List)ci.getVariable("goBack");
		if(goBack!=null&&!"".equals(goBack)){
			goBack.add(nowNodeMessage);
			ci.setVariable("goBack", goBack);
		}else{
			goBack = new ArrayList();
			goBack.add(nowNodeMessage);
			ci.setVariable("goBack", nowNodeMessage);
		}
		
		/*****************************************节点参数保存*****************************************************************/
		Long processInstanceId = ec.getProcessInstance().getId();
		wfBack.saveWorkflowRegressionParams(processId, processInstanceId, nodeId,nodeName, nodeDesc ,mapParams);
		/*****************************************节点参数保存*****************************************************************/
		
		Mail mail = new Mail();
		try {
			mail.execute(ec);
		    } catch (JbpmException e) {
		      throw e;
		    } catch (Exception e) {
		      throw new JbpmException("couldn't send email", e);
		    }
		   // mail.leave(ec);
	}

}

//Delegation delegation = new Delegation(DelegationFactory.JPDL_MailSender_ACTION);//Mail.class.getName()
//delegation.setProcessDefinition(ec.getProcessDefinition());
//delegation.setConfiguration(config.toString());
//Action action = new Action(delegation);
//Action action = DelegationFactory.getAction(DelegationFactory.JPDL_MailSender_ACTION);

