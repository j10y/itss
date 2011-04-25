package com.digitalchina.info.framework.workflow.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.Event;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.log.NodeLog;
import org.jbpm.graph.log.ProcessInstanceCreateLog;
import org.jbpm.graph.node.Decision;
import org.jbpm.graph.node.MailNode;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.instantiation.Delegation;
import org.jbpm.logging.log.ProcessLog;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.log.TaskEndLog;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.info.framework.workflow.ProcessService;
import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.base.JbpmContextFactory;
import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.WorkflowRecordTaskInfo;
import com.digitalchina.info.framework.workflow.entity.WorkflowRegressionParameters;
import com.digitalchina.info.framework.workflow.handler.DelegationFactory;
import com.digitalchina.info.framework.workflow.info.ActorInfo;
import com.digitalchina.info.framework.workflow.info.HistoryInfo;
import com.digitalchina.info.framework.workflow.info.ProcessInfo;
import com.digitalchina.info.framework.workflow.info.TaskInfo;
/**
 * 
 * @Class Name ProcessManagerImpl
 * @Author yang
 * @Create In 2008-3-20
 */
public class ProcessServiceImpl extends BaseDao  implements ProcessService,WorkflowConstants {
	private static Logger log;
	static 
	{
		log = Logger.getLogger("workflowlog");
	}
	public long createProcess(String defname, String creator) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		long processInstanceId = 0;
		//检查是否设置创建者，如没有则异常
//		List<ActorInfo> creator = (List<ActorInfo>)preAssignMapping.get(WorkflowConstants.PROCESS_CREATOR_FLAG);
		if(creator==null) {
			throw new RuntimeException("No creator.  usage：'map.put(WorkflowConstants.PROCESS_CREATOR_FLAG,creator)' in preAssignMapping。");
		}
			
		try {	
			log.debug("createProcess");
			ProcessDefinition pd = jbpmContext.getGraphSession().findLatestProcessDefinition(defname);
			//流程定义规范化
			formatDefinition(pd);			
			//挂Action和Assign代理
			addActions(pd);
					
			//启动流程	
			ProcessInstance processInstance = pd.createProcessInstance();
			//预指派;
//			preassign(preAssignMapping, processInstance);	
			Long processDefinitionId=pd.getId();
			DefinitionInfo definitionInfo=(DefinitionInfo)this.findUniqueBy(DefinitionInfo.class, "processDefinitionId", processDefinitionId);
			String rulePath=definitionInfo.getRuleName();
			ContextInstance contextInstance = processInstance.getContextInstance();
			//预置基本的审批结果和批语变量，初始化为空，其他预置的环境变量可以在init的Action里面设置
			contextInstance.setVariable(RESULT_FLAG, "");
			contextInstance.setVariable(COMMENT_FLAG, "");	
			contextInstance.setVariable(WorkflowConstants.PROCESS_CREATOR_FLAG,creator);
			contextInstance.setVariable("rulePath",rulePath);
			processInstanceId = processInstance.getId();
			
			jbpmContext.save(processInstance);
			processInstance.signal();
		
			
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return processInstanceId;
	}
	
	@SuppressWarnings("unchecked")
	public long createProcess(String vname, String creator, Map bizParam) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		//long workflowNormalBackFlag = 0;//如果是普通流程回退的话为0
		long processInstanceId = 0;
		
		//检查是否设置创建者，如没有则异常
		if(creator!=null&&!"".equals(creator)&&vname!=null&&!"".equals(vname)){
			
			
			VirtualDefinitionInfo virtualDefinitionInfo = this.findUniqueBy(VirtualDefinitionInfo.class, "virtualDefinitionName",vname);
			
			/*新老版本都能正常启动，按流程定义的id不同去启动*/
			if(virtualDefinitionInfo!=null&&!"".equals(virtualDefinitionInfo)){
				
				String vPrcessDesc = virtualDefinitionInfo.getVirtualDefinitionDesc();
				ProcessDefinition pd = jbpmContext.getGraphSession().loadProcessDefinition(virtualDefinitionInfo.getProcessDefinitionId());
				log.info(vPrcessDesc+"(流程)被创建"+"创建者为"+creator);
				//流程定义规范化,也就是给流程中的taskNode规定好一个节点只有一个任务task
				try{
					formatDefinition(pd);	
				}catch(Exception e){
					throw new RuntimeException("流程定义规范化(formatDefinition(pd))时候发生异常");
				}
				//挂Action和Assign代理
				addActions(pd);					
				//启动流程	
				ProcessInstance processInstance = pd.createProcessInstance();
				String rulePath=virtualDefinitionInfo.getRuleFileName();
				
				if(processInstance!=null&&!"".equals(processInstance)){
					ContextInstance contextInstance = processInstance.getContextInstance();
					//预置基本的审批结果和批语变量，初始化为空，其他预置的环境变量可以在init的Action里面设置(相当于规定好相应的创建人,审批标识,审批内容)
					//add testParam By guangsa in 20090716 begin
					//contextInstance.setVariable("testProcessId", 2159l);
					//add testParam By guangsa in 20090716 end
					contextInstance.setVariable("VIRTUALDEFINITIONINFO_ID", virtualDefinitionInfo.getId());		
					contextInstance.setVariable("VIRTUALDEFINITIONINFO_NAME", vname);
					contextInstance.setVariable("VIRTUALDEFINITIONINFO_DESC", vPrcessDesc);
					contextInstance.setVariable(RESULT_FLAG, "");
					contextInstance.setVariable(COMMENT_FLAG, "");	
					contextInstance.setVariable(WorkflowConstants.PROCESS_CREATOR_FLAG,creator);	
					contextInstance.setVariable("rulePath",rulePath);
					//add by guangsa for 流程正常回退标识 in 20090819 begin
					//contextInstance.setVariable("workflowNormalBackFlag", workflowNormalBackFlag);
					//add by guangsa for 流程正常回退标识 in 20090819 end
					processInstanceId = processInstance.getId();	
					//存储业务数据	
					if(bizParam!=null) {
						bizParam.put("next","");
						bizParam.put("processId", String.valueOf(processInstanceId));//流程id 
						if(bizParam.get("users")!=null&&!"".equals(bizParam.get("users"))){//开始节点指派下个节点的人
							setVariableValue(bizParam,"userList",(String)bizParam.get("users"));
						}
						contextInstance.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
					}		
					try{
						jbpmContext.save(processInstance);//只保存流程实例ID
					}catch(Exception e){
						throw new RuntimeException("保存流程实例失败,请管理员检查配置");
					}
					try{
						processInstance.signal();
					}catch(Exception e){
						log.error("创建者为"+creator+"的"+vPrcessDesc+"流程,未能正常创建.请找管理员核查!!!!!!");
						processInstance.end();
						jbpmContext.setRollbackOnly();
						throw new RuntimeException("创建者为"+creator+"的"+vPrcessDesc+"流程,未能正常创建.请找管理员核查!!!!!!");
					}finally{
						try{
							JbpmContextFactory.closeJbpmContext();
						}catch(Exception e){
							log.error("关闭jbpmFactory时发生异常！~！");
							throw new RuntimeException("创建者为"+creator+"的"+vPrcessDesc+"流程,关闭jbpmFactory时发生异常!!!!!!");
						}
					}
					log.info("创建者为"+creator+"的"+vPrcessDesc+"流程,创建完毕");
				}
			}
		}else{
			throw new RuntimeException("*********提交的流程名称和后台配置的不符或是申请人为空,请管理员检查配置*********");
		}
		return processInstanceId;
	}
	
	
	@SuppressWarnings("unchecked")
	public void endProcess(long instanceId) {
		try {
			log.debug("endProcess");
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(instanceId);
			processInstance.end();//一定要在停止流程之后取消各项任务
			Collection c = processInstance.getTaskMgmtInstance().getTaskInstances();
			if(c!=null) {		
				Iterator it = c.iterator();
				while(it.hasNext()) {
					TaskInstance ti = (TaskInstance)it.next();
					if(ti.getEnd()==null||ti.isOpen()) {//未完成任务
						ti.cancel();//取消
						jbpmContext.save(ti);
					}				
				}
			}
			if(processInstance!=null&&!"".equals(processInstance)){
				//先删除掉任务接点信息
				WorkflowRecordTaskInfo taskInfo = super.findUniqueBy(WorkflowRecordTaskInfo.class, "processInstanceId", processInstance.getId());
				if(taskInfo!=null&&!"".equals(taskInfo)){
					super.remove(taskInfo);
				}
				//然后删除掉相应的回退流程表中的相应流程的数据
				List<WorkflowRegressionParameters> regParam = super.findBy(WorkflowRegressionParameters.class, "processInstanceId", processInstance.getId());
				if(regParam.size()!=0){
					for(WorkflowRegressionParameters regressionParam : regParam){
						super.remove(regressionParam);
					}
				}
			}
			jbpmContext.save(processInstance);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
	}

	@SuppressWarnings("unchecked")
	public List<HistoryInfo> getHistory(long instanceId) {
		ArrayList listStatus = null;
		ArrayList<HistoryInfo> result = new ArrayList<HistoryInfo>();
		try {
			log.debug("getHistory");
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();			
			Map mapLog = jbpmContext.getLoggingSession().findLogsByProcessInstance(instanceId);
			ProcessInstance processInstance = jbpmContext.getProcessInstance(instanceId);
			ContextInstance contextInstance = processInstance.getContextInstance();
			Map bizParams = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			String definitionName = processInstance.getProcessDefinition().getName();			
			Collection collection = mapLog.values();
			Iterator it = collection.iterator();			
			
			if(it.hasNext()) {
				Object o = it.next(); 
				listStatus = (ArrayList)o;			
			}	
			for(int i=0;i<listStatus.size();i++) {
				HistoryInfo hi = new HistoryInfo();
				Object o = listStatus.get(i);
				//只跟踪创建任务日志，可获得必须的日志信息，其他信息通过环境变量获得。
				if(o instanceof ProcessLog) {
					ProcessLog pl = (ProcessLog)o;
					hi.setActorId(pl.getActorId());
					hi.setDate(pl.getDate().toString());
					hi.setId(pl.getId());
					hi.setNodeName(pl.toString());
					hi.setDefinitionName(definitionName);
					hi.setLogType(PROCESS_LOG);
				}
				if(o instanceof ProcessInstanceCreateLog) {
					ProcessLog pl = (ProcessLog)o;
					String creatorInfo = (String)processInstance.getContextInstance().getVariable(PROCESS_CREATOR_FLAG);
					if(creatorInfo!=null) {
						if(creatorInfo.charAt(0)=='['&&creatorInfo.endsWith("]")) {//兼容老数据
							hi.setActorId(JSONArray.fromObject(creatorInfo).getJSONObject(0).getString("actorId"));
						}
						else if(creatorInfo.charAt(0)=='{'&&creatorInfo.endsWith("}")) {//兼容老数据
							hi.setActorId(JSONObject.fromObject(creatorInfo).getString("actorId"));
						}
						else{
							hi.setActorId(creatorInfo);
						}
					}
					else {
						hi.setActorId("");
					}
					hi.setDate(pl.getDate().toString());
					hi.setId(pl.getId());					
					hi.setDefinitionName(definitionName);
					hi.setLogType(PROCESS_LOG);
					String nodeName = ((Node)processInstance.getProcessDefinition().getNodes().get(0)).getName();
					hi.setNodeName(nodeName);
					hi.setTaskName("发起流程");
					hi.setProcessId(instanceId);
					result.add(hi);
				}
				if(o instanceof NodeLog) {
					NodeLog nl = (NodeLog)o;
					hi.setNodeName(nl.getNode().getName());
					hi.setActorId(nl.getActorId());
					hi.setLogType(NODE_LOG);
				}
				if(o instanceof TaskEndLog) {
					TaskEndLog tel = (TaskEndLog)o;
					TaskInfo ti = TaskInfo.copy(tel.getTaskInstance(),bizParams);
					hi = new HistoryInfo(ti);
					result.add(hi);
				}
				
			}	
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return result;
	}
	
	public DefinitionInfo getDefinitionInfo(long instanceId) {
		DefinitionInfo definitionInfo = null;
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("getDefinitionInfo");
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(instanceId);
			definitionInfo =  new DefinitionInfo(processInstance.getProcessDefinition());
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return definitionInfo;
	}

	public ProcessInstance getProcessById(long instanceId) {
		ProcessInstance processInstance = null;
		try {
			log.debug("getProcessById");
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			processInstance = jbpmContext.loadProcessInstance(instanceId);			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return processInstance;
	}
	//1.这就是相当于得到还未结束的任务节点
	public List<TaskInfo> getActiveTasks(long processId){
		List<TaskInfo> tasks = new ArrayList<TaskInfo>();
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("getActiveTasks");
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(processId);	
			Collection c = processInstance.getTaskMgmtInstance().getTaskInstances();
			ContextInstance contextInstance = processInstance.getContextInstance();
			Map bizParams = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			Iterator it = c.iterator();
			while(it.hasNext()) {
				TaskInstance ti = (TaskInstance)it.next();
				if(ti.getEnd()==null) {//未完成任务
//					ti.setActorId(ti.getTask().getActorIdExpression());
					tasks.add(TaskInfo.copy(ti,bizParams));
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return tasks;
		
	}
	
	
	public List<TaskInfo> getAllTasks(long processId){
		List<TaskInfo> tasks = new ArrayList<TaskInfo>();
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("getActiveTasks");
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(processId);	
			Collection c = processInstance.getTaskMgmtInstance().getTaskInstances();
			ContextInstance contextInstance = processInstance.getContextInstance();
			Map bizParams = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			Iterator it = c.iterator();
			while(it.hasNext()) {
			TaskInstance ti = (TaskInstance)it.next();
			tasks.add(TaskInfo.copy(ti,bizParams));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return tasks;
		
	}
	//2.相当于得到流程的相应信息
	public ProcessInfo getProcessInfo(long processId) {
		ProcessInfo processInfo = null;		
		try {
			log.debug("getProcessInfo");
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			ProcessInstance processInstance = null;
			processInstance = jbpmContext.loadProcessInstance(processId);	
			processInfo = new ProcessInfo(processInstance);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return processInfo;
	}
	
	//从actorInfo列表中以精简形式获得字符串用于工作流在上下文中的存储
	private String getShortJson(List<ActorInfo> actorInfos){
		String str = "";
		JSONArray ja = JSONArray.fromObject(actorInfos);
		for(int i=0;i<ja.size();i++) {
			JSONObject jo = ja.getJSONObject(i);
			jo.remove("rightActorId");//删除rightActorId，以后可以计算得到
			JSONArray jaProxy = jo.getJSONArray("proxies");
			boolean hasProxy = false;
			Iterator it = jaProxy.iterator();
			while(it.hasNext()) {
				JSONObject joProxy = (JSONObject)it.next();
				Object proxyId = joProxy.get("proxyId");
				if(proxyId!=null&&!proxyId.equals("")) {
					hasProxy = true;
					joProxy.remove("rightActorId");
				}
				else {
					it.remove();
				}
			}
			if(!hasProxy) {
				jo.remove("proxies");
			}
		}
		str = ja.toString();
		return str;
	}
	
	//挂接Actions代理关系
	@SuppressWarnings("unchecked")
	public void addActions(ProcessDefinition pd) {
		List nodes = pd.getNodes();
		log.info("流程创建第二步为每一个节点挂接相应的事件");
		for(int i=0;i<nodes.size();i++) {
			Node node = (Node)nodes.get(i);
			String nodeName = node.toString();
			try {
				//开始节点的Action可以配置在流程图中，这样可以方便地把用户定义的变量加进来
				//如果用户没有在流程图中配置，可以在此挂接，在被代理的节点Action中增加变量			
				if(nodeName.indexOf("StartState")==0) {
					Event event2 = new Event(Event.EVENTTYPE_NODE_LEAVE);
					Action ruleAction = DelegationFactory.getAction(DelegationFactory.JPDL_RULE_ACTION);
					event2.addAction(ruleAction);
					node.addEvent(event2);
				}
				if(nodeName.indexOf("EndState")==0) {
					Event event1 = new Event(Event.EVENTTYPE_NODE_ENTER);
					Action ruleAction = DelegationFactory.getAction(DelegationFactory.JPDL_RULE_ACTION);
					event1.addAction(ruleAction);
					node.addEvent(event1);
				}
				if((nodeName.indexOf("TaskNode")==0)) {
					/******************************进入事件要进行发送邮件操作************************************/
					Event mailEvent = new Event(Event.EVENTTYPE_NODE_ENTER);
					//Action mailAction = DelegationFactory.getAction(DelegationFactory.JPDL_MAIL_ACTION);
					Action mailAction = DelegationFactory.getAction(DelegationFactory.JPDL_testCounterSign_ACTION);
					mailEvent.addAction(mailAction);
					node.addEvent(mailEvent);
					
//					Event timerEvent = new Event(Event.EVENTTYPE_TASK_CREATE);
//					Action timer = DelegationFactory.getAction(DelegationFactory.JPDL_TIMER_CREATE_ASSIGN);
//					timerEvent.addAction(timer);
//					node.addEvent(timerEvent); 
					
					Event leaveEvent = new Event(Event.EVENTTYPE_NODE_LEAVE);
					Action leaveAction = DelegationFactory.getAction(DelegationFactory.JPDL_TASKLEAVE_ACTION);
					leaveEvent.addAction(leaveAction);
					node.addEvent(leaveEvent);
					
//					ExceptionHandler testOne = new ExceptionHandler();
//					Action testAction = DelegationFactory.getAction(DelegationFactory.JPDL_EXCEPTION_HANDLER_ACTION);
//					testOne.setExceptionClassName("testOne");
//					testOne.addAction(testAction);
//					node.addExceptionHandler(testOne);
					/******************************任务进行任务预指派操作************************************/
					//为每个Task挂接Assign代理
//					Set<Task> tasks = ((TaskNode)node).getTasks();
//					for(Task task: tasks) {
//						String handler = DelegationFactory.JPDL_TASK_ASSIGN;
//						Delegation assignmentDelegation = new Delegation(handler);
//						task.setAssignmentDelegation(assignmentDelegation);
//					}					
				}
				if((nodeName.indexOf("Node")==0)) {
					
					/******************************进入事件要进行发送邮件操作************************************/
//					Event mailEvent = new Event(Event.EVENTTYPE_NODE_ENTER);
//					Action mailAction = DelegationFactory.getAction(DelegationFactory.JPDL_MAIL_ACTION);
//					mailEvent.addAction(mailAction);
//					node.addEvent(mailEvent);
					/******************************进入节点之后进行规则文件操作************************************/
					Action ruleAction = DelegationFactory.getAction(DelegationFactory.JPDL_RULE_ACTION);
					node.setAction(ruleAction);
				}
				if(nodeName.indexOf("ProcessState")==0){
					//这个是创建子流程
					Event subEnter = new Event(Event.EVENTTYPE_NODE_ENTER);
					Action createSubProcess = DelegationFactory.getAction(DelegationFactory.JPDL_CREATESUBPROCESS_ACTION);
					subEnter.addAction(createSubProcess);
					//首先把父流程中有用的参数取出啊来然后为子流程传参
					Event subParamfromSuperParam = new Event(Event.EVENTTYPE_SUBPROCESS_CREATED);					
					Action subParam = DelegationFactory.getAction(DelegationFactory.JPDL_PARAMFROMSUPERTOSUB_ACTION);
					subParamfromSuperParam.addAction(subParam);
					//把子流程中有用的参数取出来然后放到父流程当中
					Event superParamFromSubParam = new Event(Event.EVENTTYPE_SUBPROCESS_END);
					Action superParam = DelegationFactory.getAction(DelegationFactory.JPDL_PARAMFROMSUBTOSUPER_ACTION);
					superParamFromSubParam.addAction(superParam);
					
					node.addEvent(subEnter);
					node.addEvent(subParamfromSuperParam);
					node.addEvent(superParamFromSubParam);
				}
				if(nodeName.indexOf("Decision")==0){
					//进入Decision节点时为这个节点加个actionHandler
					((Decision)node).setDecisionDelegation(new Delegation(DelegationFactory.JPDL_DECISION_ACTION));
				}
				if(nodeName.indexOf("MailNode")==0){
					
					MailNode mailNode = new MailNode();
					Event event = new Event(Event.EVENTTYPE_NODE_ENTER);
					Action testOne = DelegationFactory.getAction(DelegationFactory.JPDL_MAILNODE_ACTION);
					event.addAction(testOne);
					node.addEvent(event);
				}
			}		
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		log.info("流程创建第二步为每一个节点挂接相应的事件操作完毕");
	}
	
	@SuppressWarnings("unchecked")
	public ProcessDefinition formatDefinition(ProcessDefinition pd) {
		
		String specailTaskName = PropertiesUtil.getProperties("workflow.specailTaskName", "账号管理员处理");
		List ln = null;
		try{
			ln = pd.getNodes();
		}catch(Exception e){
			throw new RuntimeException("格式化流程节点(List ln = pd.getNodes())时候发生异常");
		}
		log.info("流程创建第一步任务格式化，一个任务接点只有一个任务");
		for(int i=0;i<ln.size();i++) {
			Node n = (Node)ln.get(i);
			if(n instanceof TaskNode) {
				TaskNode tn = (TaskNode)n;
				Set<Task> tasks = tn.getTasks();
				if(tasks==null||tasks.isEmpty()) {//此任务与节点同名
					Task task = new Task();
					task.setName(tn.getName());	
					tn.addTask(task);
				}else if(tasks.size()>1) {//一个节点只有一个任务 
					Task task = (Task)tasks.iterator().next();
					tasks.clear();	
					tn.addTask(task);
				}else if(tasks.size()==1){
					Iterator ite = tasks.iterator();
					while(ite.hasNext()){
						Task task = (Task)ite.next();
						task.setName(tn.getName());	
						tn.addTask(task);
					}
				}
			}
		}
		log.info("流程创建第一步任务格式化完毕");
		return pd;
	}
	
	/**
	 * 对流程启动时传的指定人数参数放入上下文的map中
	 * @Methods Name setVariableValue
	 * @Create In Mar 24, 2009 By Administrator
	 * @param mapParams
	 * @param name
	 * @param value void
	 */
	private void setVariableValue(Map mapParams,String name, String value) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Service service = (Service) ContextHolder.getBean("baseService");
		try {
			log.debug("setVariableByTaskId");
			
				String userList = "";
				if (value.contains("$")) {
					String[] nodeUser = value.split("\\$");
					
					for (String str: nodeUser) {
						String nodeName = str.substring(0, str
								.indexOf(":"));
						String str1 = str.substring(str.indexOf(":") + 1);
						String[] users = str1.split(",");
						userList += nodeName + ":";
						for (String id : users) {
							UserInfo user = (UserInfo) this.getObject(
									UserInfo.class, Long.valueOf(id));
							userList += user.getUserName();
							userList += ",";
						}
						if (userList.endsWith(",")) {
							userList = userList.substring(0,
									userList.length() - 1);
						}
						userList += "$";
					}
					if (userList.endsWith("$")) {
						userList = userList.substring(0, userList.length() - 1);
					}
				} else {
					String string=value;
					String nodeName = string.substring(0, string
							.indexOf(":"));
					string = string.substring(string.indexOf(":") + 1);
					String[] users = string.split(",");
					userList += nodeName + ":";
					for (String id : users) {
						UserInfo user = (UserInfo) this.getObject(
								UserInfo.class, Long.valueOf(id));
						userList += user.getUserName();
						userList += ",";
					}
					if (userList.endsWith(",")) {
						userList = userList.substring(0,
								userList.length() - 1);
					}

				}
				mapParams.put(name, userList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
