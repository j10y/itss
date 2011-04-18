package com.digitalchina.info.framework.workflow;

public interface WorkflowConstants {
	//���������ļ�
	public static final String CONFIG_FILE = "/workflowconfig.properties";
	//ָ�ɵ������������ļ�
	public static final String ASSIGN_MAP = "_ASSIGN_MAP";
	//����Form�����ļ�
	public static final String FORM_FILE = "/workflowform.properties";
	
	//���Ƶ�Action���ڰ�,Ĭ��ֵ
	public static final String DEFAULT_ACTION_PACKAGE = "com.digitalchina.info.framework.workflow.action";
	
	//����ͼ���ڰ�,Ĭ��ֵ
	public static final String DEFAULT_JPDL_PACKAGE = "com.digitalchina.info.framework.workflow.jpdl";
					
	//�������д洢�����̴����߱�ʶ
	public static final String PROCESS_CREATOR_FLAG = "_PROCESS_CREATOR_FLAG_";
	
	//�������д洢�����̴�����ռλֵ
	public static final String PROCESS_CREATOR_ID = "_PROCESS_CREATOR_ID";
	
	//�ڵ��ҳ���ʶǰ׺��Ҳ��map��key
	public static final String NODE_KEY = "node_";
	
	//�洢���������е�ҵ�������ֵ��Map��
	public static final String BUSINESS_PARAM_KEY = "_BUSINESS_PARAM_KEY_";
	
	//�洢���������еģ�Map��TaskInfo�ļ�ֵ��ע��ʵ���ϴ��ȥ����taskInstance,ȡ��ʱת��ΪtaskInfo.
	public static final String TASKINFO_KEY = "_TASK_INFO_KEY_";
	
	//�洢���������еģ�Map��TaskId�ļ�ֵ
	public static final String TASK_ID_KEY = "_TASK_ID_KEY_";
	
	//�뿪��־λ
	public static final String LEAVE_FLAG = "YES";
	
	//handler����
	public static final String ACTION_HANDLER = "org.jbpm.graph.def.ActionHandler";
	public static final String ASSIGNMENT_HANDLER = "org.jbpm.taskmgmt.def.AssignmentHandler";
	public static final String CONTROLLER_HANDLER = "org.jbpm.taskmgmt.def.TaskControllerHandler";
	public static final String DECISION_HANDLER = "org.jbpm.graph.node.DecisionHandler";
	
	//�ڵ�����
	public static final String NODE = "NODE";
	public static final String STATE = "STATE";
	public static final String START = "START";
	public static final String END = "END";
	public static final String FORK = "FORK";
	public static final String JOIN = "JOIN";
	public static final String DECISION = "DECISION";
	public static final String TASKNODE = "TASKNODE";
	public static final String MAILNODE = "MAILNODE";
	public static final String TRANSITION = "TRANSITION";	
		
	//�򵥵���˽��	
	public static final String RESULT_FLAG = "result";
	public static final String COMMENT_FLAG = "comment";
	public static final String RESULT_YES = "Y";
	public static final String RESULT_NO = "N";
	public static final String RESULT_RESERVE = "R";
	
	
	//��־����
	public static final long PROCESS_LOG = 1;
	public static final long COMPOSITE_LOG = 11;
	public static final long ACTION_LOG = 111;
	public static final long SIGNAL_LOG = 112;
	public static final long TRANSITION_LOG = 113;
	public static final long MESSAGE_LOG = 12;
	public static final long NODE_LOG = 13;
	public static final long PROCESS_STATE_LOG = 131;
	public static final long PROCESS_INSTANCE_CREATE_LOG = 14;
	public static final long PROCESS_INSTANCE_END_LOG = 15;
	public static final long SWIMLANE_LOG = 16;
	public static final long SWIMLANE_ASSIGN_LOG = 161;
	public static final long SWIMLANE_CREATE_LOG = 162;
	public static final long TASK_LOG = 17;
	public static final long TASK_ASSIGN_LOG = 171;
	public static final long TASK_CREATE_LOG = 172;
	public static final long TASK_END_LOG = 173;
	public static final long TOKEN_CREATE_LOG = 18;
	public static final long TOKEN_END_LOG = 19;
	public static final long VARIABLE_LOG = 10;
	public static final long VARIABLE_CREATE_LOG = 101;
	public static final long VARIABLE_DELETE_LOG = 102;
	public static final long VARIABLE_UPDATE_LOG = 103;
}
