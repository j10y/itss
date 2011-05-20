package com.zsgj.info.framework.workflow.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.Action;
import org.jbpm.instantiation.Delegation;

public class DelegationFactory {
	private static Log log;
	static 
	{
		log = LogFactory.getLog(com.zsgj.info.framework.workflow.handler.DelegationFactory.class);
	}
	
	//流程中配置的初始化Action
	public static final String JPDL_INIT_ACTION = "com.zsgj.info.framework.workflow.handler.InitActionHandler";
	
	//流程中配置的结束Action
	public static final String JPDL_END_ACTION = "com.zsgj.info.framework.workflow.handler.EndActionHandler";
	
	//流程中配置的节点Action
	public static final String JPDL_NODE_ACTION = "com.zsgj.info.framework.workflow.handler.NodeActionHandler";
	
	//流程中配置的转移Action
	public static final String JPDL_TRANS_ACTION = "com.zsgj.info.framework.workflow.handler.TransActionHandler";

	//流程中配置的任务Action
	public static final String JPDL_TASK_ACTION = "com.zsgj.info.framework.workflow.handler.TaskActionHandler";
	
	//指派代理
	public static final String JPDL_TASK_ASSIGN = "com.zsgj.info.framework.workflow.handler.TaskAssignHandler";
	
	//读规则文件Action
	public static final String JPDL_RULE_ACTION = "com.zsgj.info.framework.workflow.handler.RuleActionHandler";
	
	//发送邮件action
	public static final String JPDL_MAIL_ACTION = "com.zsgj.info.framework.workflow.handler.SendMailHandler";
	
	public static final String JPDL_MAILNODE_ACTION = "com.zsgj.info.framework.workflow.handler.SendMailNodeActionHandler";
	//父流程给子流程传参action
	public static final String JPDL_PARAMFROMSUPERTOSUB_ACTION = "com.zsgj.info.framework.workflow.handler.ParamFormSuperToSubHandler";
	
	//子流程给父流程传参action
	public static final String JPDL_PARAMFROMSUBTOSUPER_ACTION = "com.zsgj.info.framework.workflow.handler.ParamFormSubToSuperHandler";
	
	//创建子流程action
	public static final String JPDL_CREATESUBPROCESS_ACTION = "com.zsgj.info.framework.workflow.handler.CreateSubProcessActionHandler";
	
	//timer-create指派代理
	public static final String JPDL_TIMER_CREATE_ASSIGN = "com.zsgj.info.framework.workflow.handler.TimerCreateActionHandler";
	
	//记录TaskId指派代理
	public static final String JPDL_RECORD_TASKID_ASSIGN = "com.zsgj.info.framework.workflow.handler.RecordTaskIdActionHandler";
	
	public static final String JPDL_EXCEPTION_HANDLER_ACTION = "com.zsgj.info.framework.workflow.handler.ExceptionHandlerActionHandler";
	//记录TaskId指派代理
	public static final String JPDL_TASKLEAVE_ACTION = "com.zsgj.info.framework.workflow.handler.TaskNodeLeaveActionHandler";
	
	//timer-execute指派代理
	public static final String JPDL_TIMER_EXECUTE_ASSIGN = "com.zsgj.info.framework.workflow.handler.TimerExecuteActionHandler";
	//timer-cancel指派代理
	public static final String JPDL_TIMER_CANCEL_ASSIGN = "com.zsgj.info.framework.workflow.handler.TimerCancelActionHandler";
	
	//ESBService action
	public static final String JPDL_ESBService_ACTION = "com.zsgj.info.framework.workflow.handler.ESBServiceActionHandler";
	
	//为Decision加上action
	public static final String JPDL_SetActionForDecision_ACTION = "com.zsgj.info.framework.workflow.handler.DecisionEnterActionHandler";

	//Decision action指派代理
	public static final String JPDL_DECISION_ACTION = "com.zsgj.info.framework.workflow.handler.DecisionActionHander";
	
	//流程回退 指派节点
	public static final String JPDL_AssignNode_ACTION = "com.zsgj.info.framework.workflow.handler.WorkflowGobackActionHandler";//test.Test

	//Decision action
	public static final String JPDL_MailSender_ACTION ="fastSign.mail.Mail";
	
	public static final String JPDL_testCounterSign_ACTION = "com.zsgj.info.framework.workflow.handler.CounterSignHandler";
	//action代理
	public static Action getAction(String delegationName) {	
		log.debug("getAction");
		Delegation delegation = new Delegation(delegationName);
		return new Action(delegation);		
	}
}
