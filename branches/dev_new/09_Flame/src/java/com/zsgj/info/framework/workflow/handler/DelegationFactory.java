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
	
	//���������õĳ�ʼ��Action
	public static final String JPDL_INIT_ACTION = "com.zsgj.info.framework.workflow.handler.InitActionHandler";
	
	//���������õĽ���Action
	public static final String JPDL_END_ACTION = "com.zsgj.info.framework.workflow.handler.EndActionHandler";
	
	//���������õĽڵ�Action
	public static final String JPDL_NODE_ACTION = "com.zsgj.info.framework.workflow.handler.NodeActionHandler";
	
	//���������õ�ת��Action
	public static final String JPDL_TRANS_ACTION = "com.zsgj.info.framework.workflow.handler.TransActionHandler";

	//���������õ�����Action
	public static final String JPDL_TASK_ACTION = "com.zsgj.info.framework.workflow.handler.TaskActionHandler";
	
	//ָ�ɴ���
	public static final String JPDL_TASK_ASSIGN = "com.zsgj.info.framework.workflow.handler.TaskAssignHandler";
	
	//�������ļ�Action
	public static final String JPDL_RULE_ACTION = "com.zsgj.info.framework.workflow.handler.RuleActionHandler";
	
	//�����ʼ�action
	public static final String JPDL_MAIL_ACTION = "com.zsgj.info.framework.workflow.handler.SendMailHandler";
	
	public static final String JPDL_MAILNODE_ACTION = "com.zsgj.info.framework.workflow.handler.SendMailNodeActionHandler";
	//�����̸������̴���action
	public static final String JPDL_PARAMFROMSUPERTOSUB_ACTION = "com.zsgj.info.framework.workflow.handler.ParamFormSuperToSubHandler";
	
	//�����̸������̴���action
	public static final String JPDL_PARAMFROMSUBTOSUPER_ACTION = "com.zsgj.info.framework.workflow.handler.ParamFormSubToSuperHandler";
	
	//����������action
	public static final String JPDL_CREATESUBPROCESS_ACTION = "com.zsgj.info.framework.workflow.handler.CreateSubProcessActionHandler";
	
	//timer-createָ�ɴ���
	public static final String JPDL_TIMER_CREATE_ASSIGN = "com.zsgj.info.framework.workflow.handler.TimerCreateActionHandler";
	
	//��¼TaskIdָ�ɴ���
	public static final String JPDL_RECORD_TASKID_ASSIGN = "com.zsgj.info.framework.workflow.handler.RecordTaskIdActionHandler";
	
	public static final String JPDL_EXCEPTION_HANDLER_ACTION = "com.zsgj.info.framework.workflow.handler.ExceptionHandlerActionHandler";
	//��¼TaskIdָ�ɴ���
	public static final String JPDL_TASKLEAVE_ACTION = "com.zsgj.info.framework.workflow.handler.TaskNodeLeaveActionHandler";
	
	//timer-executeָ�ɴ���
	public static final String JPDL_TIMER_EXECUTE_ASSIGN = "com.zsgj.info.framework.workflow.handler.TimerExecuteActionHandler";
	//timer-cancelָ�ɴ���
	public static final String JPDL_TIMER_CANCEL_ASSIGN = "com.zsgj.info.framework.workflow.handler.TimerCancelActionHandler";
	
	//ESBService action
	public static final String JPDL_ESBService_ACTION = "com.zsgj.info.framework.workflow.handler.ESBServiceActionHandler";
	
	//ΪDecision����action
	public static final String JPDL_SetActionForDecision_ACTION = "com.zsgj.info.framework.workflow.handler.DecisionEnterActionHandler";

	//Decision actionָ�ɴ���
	public static final String JPDL_DECISION_ACTION = "com.zsgj.info.framework.workflow.handler.DecisionActionHander";
	
	//���̻��� ָ�ɽڵ�
	public static final String JPDL_AssignNode_ACTION = "com.zsgj.info.framework.workflow.handler.WorkflowGobackActionHandler";//test.Test

	//Decision action
	public static final String JPDL_MailSender_ACTION ="fastSign.mail.Mail";
	
	public static final String JPDL_testCounterSign_ACTION = "com.zsgj.info.framework.workflow.handler.CounterSignHandler";
	//action����
	public static Action getAction(String delegationName) {	
		log.debug("getAction");
		Delegation delegation = new Delegation(delegationName);
		return new Action(delegation);		
	}
}
