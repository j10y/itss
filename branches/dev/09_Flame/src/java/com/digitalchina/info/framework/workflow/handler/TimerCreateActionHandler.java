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
////		//保存当前节点的nodeName，以便于流程回退；
////		String paramId = "";
////		ContextInstance ci = ec.getContextInstance();
////		Long processInstanceId = ec.getProcessInstance().getId();
////		Node node = ec.getNode();
////		Long nodeId=node.getId();
////		String nodeName = ec.getToken().getNode().getName();//当前节点名称
////		String nodeDesc = ec.getToken().getNode().getDescription();//当前节点描述
////		String nodeType = ec.getToken().getNode().toString();//当前节点类型
////		Token token = ec.getToken();
////		/*************************首先判断是否第一次进入当前节点******************************************/
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
//	 * 判断上一个节点是不是费审批节点
//	 * 流程中回退时，上一个节点不能使非审批节点；因为如果是那样的话则流程会出现死循环
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
//	 * Task节点在发生异常时候回退流程
//	 * 因为是Task节点，此时还没有进行字符串goBack的拼接，rule并不是enter事件的action;所以goback的最后一个就是上个节点的信息
//	 * 另外这种异常，也不用删除当前节点任务信息，因为还没有保存
//	 * @param ci
//	 * @param token
//	 */
//	public void nodeTypeSaveException(ContextInstance ci,Token token,TaskInstance ti,Long vProcessId,Long processId){
//		
//		String fromNodeName = "";
//		String fromParamId = "";
//		//把记录每一个节点的参数删除掉
//		List allNodeMessage = (List)ci.getVariable("goBack");//List中每一个对象就是一个String，格式为paramId+nodeName；
//		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
//		String[] mutipleMessage = fromNodeMessage.split("\\+");
//		fromParamId = mutipleMessage[0];//上个节点参数Id
//		fromNodeName = mutipleMessage[1];//节点名称为中文，这里无法用到nodeDesc（api限制）
//		allNodeMessage.remove(allNodeMessage.size()-1);
//		String flag = this.isExamineNode(ti, fromNodeName);
//		while("N".equals(flag)){
//			fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
//			mutipleMessage = fromNodeMessage.split("\\+");
//			fromParamId = mutipleMessage[0];//上个节点参数Id
//			fromNodeName = mutipleMessage[1];//节点名称为中文，这里无法用到nodeDesc（api限制）
//			allNodeMessage.remove(allNodeMessage.size()-1);
//			flag = this.isExamineNode(ti, fromNodeName);
//		}
//		if("S".equals(flag)){//会退到开始节点了
//			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
//			Node fromNode = ci.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
//			String goStartState = String.valueOf(fromNode.getId())+","+vProcessId+","+processId;
//			Map bizParam = (Map)ti.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//			String creator= (String)ti.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
//			bizParam.put("goStartState", goStartState);//设置一个特殊的变量
//			ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
//			//既然回退到开始节点的话就相当于打回了，需要用户重新提交或者是取消当前流程
//			String nodeName = ti.getToken().getNode().getName();
//			Task task = ti.getTask().getTaskNode().getTask(nodeName);
//			task.setName("申请流程打回节点(开始节点)");
//			ti.setActorId(creator);
//			ti.setTask(task);
//			jbpmContext.save(ti);
//			Token oldeToken = jbpmContext.getToken(Long.valueOf(token.getId()));
//			oldeToken.setNode(fromNode);
//			JbpmContextFactory.closeJbpmContext();
//		}else{
//			try{
//				JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
//				ti.setSignalling(false);//signalling的意思就是不让节点转向
//				ti.end();
//				Node fromNode = ci.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
//				token.setNode(fromNode);
//				ExecutionContext ec = new ExecutionContext(token);
//				fromNode.enter(ec);
//			}catch(Exception e){
//				System.out.println("wqeqwewqeq");
//				log.error("回退的过程发生异常");
//				e.printStackTrace();
//			}finally{
//				log.info("^^^^^^^^^^^^^^^^^^^^^!!!回退结束!!!^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//				JbpmContextFactory.closeJbpmContext();
//			}
//		}
//		//开始回退了(留下开始节点)
//	}
//	
//	
//	
//	/**
//	 * 处理异常方法(此节点没有把本节点信息增加到goBack中)
//	 * @param nodeType
//	 * @param ci
//	 * @param token
//	 * @param vProcessName
//	 * @param nodeName
//	 * @param e
//	 */
//	public void handlerSaveExceptionMethod(String nodeType,ContextInstance ci,Token token,String vProcessName , String nodeName,Exception e,TaskInstance ti,Long vProcessId,Long ProcessId){
//		
//		log.error(vProcessName+"(流程)提交之后"+"在"+nodeName+"(节点)发生异常");
//		log.debug(e.getMessage());
//		this.nodeTypeSaveException(ci, token,ti,vProcessId,ProcessId);
//		//然后发送邮件,通知管理员
//		this.sendSimpleEmail(vProcessName, nodeName);
//		
//	}
//	
//	
//	
//	
//}
