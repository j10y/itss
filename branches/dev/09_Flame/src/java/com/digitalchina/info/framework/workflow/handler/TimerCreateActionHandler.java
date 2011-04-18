//package com.digitalchina.info.framework.workflow.handler;
//
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.log4j.Logger;
//import org.jbpm.JbpmContext;
//import org.jbpm.context.exe.ContextInstance;
//import org.jbpm.graph.def.Action;
//import org.jbpm.graph.def.ActionHandler;
//import org.jbpm.graph.def.Node;
//import org.jbpm.graph.exe.ExecutionContext;
//import org.jbpm.graph.exe.Token;
//import org.jbpm.job.Timer;
//import org.jbpm.scheduler.SchedulerService;
//import org.jbpm.scheduler.def.CreateTimerAction;
//import org.jbpm.svc.Services;
//import org.jbpm.taskmgmt.def.Task;
//import org.jbpm.taskmgmt.exe.TaskInstance;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//
//import com.digitalchina.info.framework.context.ContextHolder;
//import com.digitalchina.info.framework.message.mail.service.MailSenderService;
//import com.digitalchina.info.framework.security.entity.Role;
//import com.digitalchina.info.framework.security.entity.UserInfo;
//import com.digitalchina.info.framework.service.Service;
//import com.digitalchina.info.framework.util.DateTool;
//import com.digitalchina.info.framework.util.PropertiesUtil;
//import com.digitalchina.info.framework.workflow.ConfigUnitService;
//import com.digitalchina.info.framework.workflow.TaskAssignService;
//import com.digitalchina.info.framework.workflow.WorkFlowGoBackService;
//import com.digitalchina.info.framework.workflow.WorkflowConstants;
//import com.digitalchina.info.framework.workflow.base.JbpmContextFactory;
//import com.digitalchina.info.framework.workflow.entity.ConfigUnitMail;
//import com.digitalchina.info.framework.workflow.entity.ConfigUnitMailCC;
//import com.digitalchina.info.framework.workflow.entity.ConfigUnitRole;
//import com.digitalchina.info.framework.workflow.entity.ConfigUnitRoleTable;
//import com.digitalchina.info.framework.workflow.entity.ConfigUnitTimer;
//import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
//import com.digitalchina.info.framework.workflow.entity.WorkflowRecordTaskInfo;
//import com.digitalchina.info.framework.workflow.entity.WorkflowRegressionParameters;
//import com.digitalchina.info.framework.workflow.info.NodeInfo;
//
//public class TimerCreateActionHandler extends BaseActionHandler implements ActionHandler,WorkflowConstants{
//
//	private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
//	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
//	private MailSenderService ms = (MailSenderService)ContextHolder.getBean("mailSenderService");
//	private Service service = (Service) ContextHolder.getBean("baseService");
//	private TaskAssignService si = (TaskAssignService) ContextHolder.getBean("taskAssignService");
//	private static Logger log;
//	static 
//	{
//		log = Logger.getLogger("workflowlog");
//	}
//	@Override
//	public void execute(ExecutionContext ec) throws Exception {
//		
////		//���浱ǰ�ڵ��nodeName���Ա������̻��ˣ�
////		String paramId = "";
////		ContextInstance ci = ec.getContextInstance();
////		Long processInstanceId = ec.getProcessInstance().getId();
////		Node node = ec.getNode();
////		Long nodeId=node.getId();
////		String nodeName = ec.getToken().getNode().getName();//��ǰ�ڵ�����
////		String nodeDesc = ec.getToken().getNode().getDescription();//��ǰ�ڵ�����
////		String nodeType = ec.getToken().getNode().toString();//��ǰ�ڵ�����
////		Token token = ec.getToken();
////		/*************************�����ж��Ƿ��һ�ν��뵱ǰ�ڵ�******************************************/
////		TaskInstance ti = ec.getTaskInstance();
////		Long taskId = ti.getId();
////		Long processId=(Long)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
////		String processName = (String)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_NAME");
////		String vProcessDesc = (String)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
////		String creator = (String)ec.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
////		
////		Map mapParams=(Map)ec.getProcessInstance().getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
////		String dataId = (String)mapParams.get("dataId");
////		String reqClass = (String)mapParams.get("reqClass");
////		String goStartState = (String)mapParams.get("goStartState");
////		String applyType = (String)mapParams.get("applyType");
//		
//		
//		
//	}
//	
//	/**
//	 * �ж���һ���ڵ��ǲ��Ƿ������ڵ�
//	 * �����л���ʱ����һ���ڵ㲻��ʹ�������ڵ㣻��Ϊ����������Ļ������̻������ѭ��
//	 * @param ti
//	 * @param nodeName
//	 * @return
//	 */
//	public String isExamineNode(TaskInstance ti,String nodeName){
//		
//		Node fromNode = ti.getProcessInstance().getProcessDefinition().getNode(nodeName);
//		String fromNodeType = fromNode.toString();
//		if(fromNodeType.indexOf("Node")==0||fromNodeType.indexOf("Decision")==0||fromNodeType.indexOf("MailNode")==0){
//			return "N";
//		}else if(fromNodeType.indexOf("StartState")==0){
//			return "S";
//		}
//		return "Y";
//	}
//		
//	/**
//	 * Task�ڵ��ڷ����쳣ʱ���������
//	 * ��Ϊ��Task�ڵ㣬��ʱ��û�н����ַ���goBack��ƴ�ӣ�rule������enter�¼���action;����goback�����һ�������ϸ��ڵ����Ϣ
//	 * ���������쳣��Ҳ����ɾ����ǰ�ڵ�������Ϣ����Ϊ��û�б���
//	 * @param ci
//	 * @param token
//	 */
//	public void nodeTypeSaveException(ContextInstance ci,Token token,TaskInstance ti,Long vProcessId,Long processId){
//		
//		String fromNodeName = "";
//		String fromParamId = "";
//		//�Ѽ�¼ÿһ���ڵ�Ĳ���ɾ����
//		List allNodeMessage = (List)ci.getVariable("goBack");//List��ÿһ���������һ��String����ʽΪparamId+nodeName��
//		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
//		String[] mutipleMessage = fromNodeMessage.split("\\+");
//		fromParamId = mutipleMessage[0];//�ϸ��ڵ����Id
//		fromNodeName = mutipleMessage[1];//�ڵ�����Ϊ���ģ������޷��õ�nodeDesc��api���ƣ�
//		allNodeMessage.remove(allNodeMessage.size()-1);
//		String flag = this.isExamineNode(ti, fromNodeName);
//		while("N".equals(flag)){
//			fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
//			mutipleMessage = fromNodeMessage.split("\\+");
//			fromParamId = mutipleMessage[0];//�ϸ��ڵ����Id
//			fromNodeName = mutipleMessage[1];//�ڵ�����Ϊ���ģ������޷��õ�nodeDesc��api���ƣ�
//			allNodeMessage.remove(allNodeMessage.size()-1);
//			flag = this.isExamineNode(ti, fromNodeName);
//		}
//		if("S".equals(flag)){//���˵���ʼ�ڵ���
//			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
//			Node fromNode = ci.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
//			String goStartState = String.valueOf(fromNode.getId())+","+vProcessId+","+processId;
//			Map bizParam = (Map)ti.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//			String creator= (String)ti.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
//			bizParam.put("goStartState", goStartState);//����һ������ı���
//			ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
//			//��Ȼ���˵���ʼ�ڵ�Ļ����൱�ڴ���ˣ���Ҫ�û������ύ������ȡ����ǰ����
//			String nodeName = ti.getToken().getNode().getName();
//			Task task = ti.getTask().getTaskNode().getTask(nodeName);
//			task.setName("�������̴�ؽڵ�(��ʼ�ڵ�)");
//			ti.setActorId(creator);
//			ti.setTask(task);
//			jbpmContext.save(ti);
//			Token oldeToken = jbpmContext.getToken(Long.valueOf(token.getId()));
//			oldeToken.setNode(fromNode);
//			JbpmContextFactory.closeJbpmContext();
//		}else{
//			try{
//				JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
//				ti.setSignalling(false);//signalling����˼���ǲ��ýڵ�ת��
//				ti.end();
//				Node fromNode = ci.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
//				token.setNode(fromNode);
//				ExecutionContext ec = new ExecutionContext(token);
//				fromNode.enter(ec);
//			}catch(Exception e){
//				System.out.println("wqeqwewqeq");
//				log.error("���˵Ĺ��̷����쳣");
//				e.printStackTrace();
//			}finally{
//				log.info("^^^^^^^^^^^^^^^^^^^^^!!!���˽���!!!^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//				JbpmContextFactory.closeJbpmContext();
//			}
//		}
//		//��ʼ������(���¿�ʼ�ڵ�)
//	}
//	
//	
//	
//	/**
//	 * �����쳣����(�˽ڵ�û�аѱ��ڵ���Ϣ���ӵ�goBack��)
//	 * @param nodeType
//	 * @param ci
//	 * @param token
//	 * @param vProcessName
//	 * @param nodeName
//	 * @param e
//	 */
//	public void handlerSaveExceptionMethod(String nodeType,ContextInstance ci,Token token,String vProcessName , String nodeName,Exception e,TaskInstance ti,Long vProcessId,Long ProcessId){
//		
//		log.error(vProcessName+"(����)�ύ֮��"+"��"+nodeName+"(�ڵ�)�����쳣");
//		log.debug(e.getMessage());
//		this.nodeTypeSaveException(ci, token,ti,vProcessId,ProcessId);
//		//Ȼ�����ʼ�,֪ͨ����Ա
//		this.sendSimpleEmail(vProcessName, nodeName);
//		
//	}
//	
//	
//	
//	
//}
