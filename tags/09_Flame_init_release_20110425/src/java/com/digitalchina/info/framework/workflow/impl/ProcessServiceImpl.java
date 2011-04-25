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
		//����Ƿ����ô����ߣ���û�����쳣
//		List<ActorInfo> creator = (List<ActorInfo>)preAssignMapping.get(WorkflowConstants.PROCESS_CREATOR_FLAG);
		if(creator==null) {
			throw new RuntimeException("No creator.  usage��'map.put(WorkflowConstants.PROCESS_CREATOR_FLAG,creator)' in preAssignMapping��");
		}
			
		try {	
			log.debug("createProcess");
			ProcessDefinition pd = jbpmContext.getGraphSession().findLatestProcessDefinition(defname);
			//���̶���淶��
			formatDefinition(pd);			
			//��Action��Assign����
			addActions(pd);
					
			//��������	
			ProcessInstance processInstance = pd.createProcessInstance();
			//Ԥָ��;
//			preassign(preAssignMapping, processInstance);	
			Long processDefinitionId=pd.getId();
			DefinitionInfo definitionInfo=(DefinitionInfo)this.findUniqueBy(DefinitionInfo.class, "processDefinitionId", processDefinitionId);
			String rulePath=definitionInfo.getRuleName();
			ContextInstance contextInstance = processInstance.getContextInstance();
			//Ԥ�û�������������������������ʼ��Ϊ�գ�����Ԥ�õĻ�������������init��Action��������
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
		//long workflowNormalBackFlag = 0;//�������ͨ���̻��˵Ļ�Ϊ0
		long processInstanceId = 0;
		
		//����Ƿ����ô����ߣ���û�����쳣
		if(creator!=null&&!"".equals(creator)&&vname!=null&&!"".equals(vname)){
			
			
			VirtualDefinitionInfo virtualDefinitionInfo = this.findUniqueBy(VirtualDefinitionInfo.class, "virtualDefinitionName",vname);
			
			/*���ϰ汾�������������������̶����id��ͬȥ����*/
			if(virtualDefinitionInfo!=null&&!"".equals(virtualDefinitionInfo)){
				
				String vPrcessDesc = virtualDefinitionInfo.getVirtualDefinitionDesc();
				ProcessDefinition pd = jbpmContext.getGraphSession().loadProcessDefinition(virtualDefinitionInfo.getProcessDefinitionId());
				log.info(vPrcessDesc+"(����)������"+"������Ϊ"+creator);
				//���̶���淶��,Ҳ���Ǹ������е�taskNode�涨��һ���ڵ�ֻ��һ������task
				try{
					formatDefinition(pd);	
				}catch(Exception e){
					throw new RuntimeException("���̶���淶��(formatDefinition(pd))ʱ�����쳣");
				}
				//��Action��Assign����
				addActions(pd);					
				//��������	
				ProcessInstance processInstance = pd.createProcessInstance();
				String rulePath=virtualDefinitionInfo.getRuleFileName();
				
				if(processInstance!=null&&!"".equals(processInstance)){
					ContextInstance contextInstance = processInstance.getContextInstance();
					//Ԥ�û�������������������������ʼ��Ϊ�գ�����Ԥ�õĻ�������������init��Action��������(�൱�ڹ涨����Ӧ�Ĵ�����,������ʶ,��������)
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
					//add by guangsa for �����������˱�ʶ in 20090819 begin
					//contextInstance.setVariable("workflowNormalBackFlag", workflowNormalBackFlag);
					//add by guangsa for �����������˱�ʶ in 20090819 end
					processInstanceId = processInstance.getId();	
					//�洢ҵ������	
					if(bizParam!=null) {
						bizParam.put("next","");
						bizParam.put("processId", String.valueOf(processInstanceId));//����id 
						if(bizParam.get("users")!=null&&!"".equals(bizParam.get("users"))){//��ʼ�ڵ�ָ���¸��ڵ����
							setVariableValue(bizParam,"userList",(String)bizParam.get("users"));
						}
						contextInstance.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
					}		
					try{
						jbpmContext.save(processInstance);//ֻ��������ʵ��ID
					}catch(Exception e){
						throw new RuntimeException("��������ʵ��ʧ��,�����Ա�������");
					}
					try{
						processInstance.signal();
					}catch(Exception e){
						log.error("������Ϊ"+creator+"��"+vPrcessDesc+"����,δ����������.���ҹ���Ա�˲�!!!!!!");
						processInstance.end();
						jbpmContext.setRollbackOnly();
						throw new RuntimeException("������Ϊ"+creator+"��"+vPrcessDesc+"����,δ����������.���ҹ���Ա�˲�!!!!!!");
					}finally{
						try{
							JbpmContextFactory.closeJbpmContext();
						}catch(Exception e){
							log.error("�ر�jbpmFactoryʱ�����쳣��~��");
							throw new RuntimeException("������Ϊ"+creator+"��"+vPrcessDesc+"����,�ر�jbpmFactoryʱ�����쳣!!!!!!");
						}
					}
					log.info("������Ϊ"+creator+"��"+vPrcessDesc+"����,�������");
				}
			}
		}else{
			throw new RuntimeException("*********�ύ���������ƺͺ�̨���õĲ�������������Ϊ��,�����Ա�������*********");
		}
		return processInstanceId;
	}
	
	
	@SuppressWarnings("unchecked")
	public void endProcess(long instanceId) {
		try {
			log.debug("endProcess");
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(instanceId);
			processInstance.end();//һ��Ҫ��ֹͣ����֮��ȡ����������
			Collection c = processInstance.getTaskMgmtInstance().getTaskInstances();
			if(c!=null) {		
				Iterator it = c.iterator();
				while(it.hasNext()) {
					TaskInstance ti = (TaskInstance)it.next();
					if(ti.getEnd()==null||ti.isOpen()) {//δ�������
						ti.cancel();//ȡ��
						jbpmContext.save(ti);
					}				
				}
			}
			if(processInstance!=null&&!"".equals(processInstance)){
				//��ɾ��������ӵ���Ϣ
				WorkflowRecordTaskInfo taskInfo = super.findUniqueBy(WorkflowRecordTaskInfo.class, "processInstanceId", processInstance.getId());
				if(taskInfo!=null&&!"".equals(taskInfo)){
					super.remove(taskInfo);
				}
				//Ȼ��ɾ������Ӧ�Ļ������̱��е���Ӧ���̵�����
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
				//ֻ���ٴ���������־���ɻ�ñ������־��Ϣ��������Ϣͨ������������á�
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
						if(creatorInfo.charAt(0)=='['&&creatorInfo.endsWith("]")) {//����������
							hi.setActorId(JSONArray.fromObject(creatorInfo).getJSONObject(0).getString("actorId"));
						}
						else if(creatorInfo.charAt(0)=='{'&&creatorInfo.endsWith("}")) {//����������
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
					hi.setTaskName("��������");
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
	//1.������൱�ڵõ���δ����������ڵ�
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
				if(ti.getEnd()==null) {//δ�������
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
	//2.�൱�ڵõ����̵���Ӧ��Ϣ
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
	
	//��actorInfo�б����Ծ�����ʽ����ַ������ڹ��������������еĴ洢
	private String getShortJson(List<ActorInfo> actorInfos){
		String str = "";
		JSONArray ja = JSONArray.fromObject(actorInfos);
		for(int i=0;i<ja.size();i++) {
			JSONObject jo = ja.getJSONObject(i);
			jo.remove("rightActorId");//ɾ��rightActorId���Ժ���Լ���õ�
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
	
	//�ҽ�Actions�����ϵ
	@SuppressWarnings("unchecked")
	public void addActions(ProcessDefinition pd) {
		List nodes = pd.getNodes();
		log.info("���̴����ڶ���Ϊÿһ���ڵ�ҽ���Ӧ���¼�");
		for(int i=0;i<nodes.size();i++) {
			Node node = (Node)nodes.get(i);
			String nodeName = node.toString();
			try {
				//��ʼ�ڵ��Action��������������ͼ�У��������Է���ذ��û�����ı����ӽ���
				//����û�û��������ͼ�����ã������ڴ˹ҽӣ��ڱ�����Ľڵ�Action�����ӱ���			
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
					/******************************�����¼�Ҫ���з����ʼ�����************************************/
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
					/******************************�����������Ԥָ�ɲ���************************************/
					//Ϊÿ��Task�ҽ�Assign����
//					Set<Task> tasks = ((TaskNode)node).getTasks();
//					for(Task task: tasks) {
//						String handler = DelegationFactory.JPDL_TASK_ASSIGN;
//						Delegation assignmentDelegation = new Delegation(handler);
//						task.setAssignmentDelegation(assignmentDelegation);
//					}					
				}
				if((nodeName.indexOf("Node")==0)) {
					
					/******************************�����¼�Ҫ���з����ʼ�����************************************/
//					Event mailEvent = new Event(Event.EVENTTYPE_NODE_ENTER);
//					Action mailAction = DelegationFactory.getAction(DelegationFactory.JPDL_MAIL_ACTION);
//					mailEvent.addAction(mailAction);
//					node.addEvent(mailEvent);
					/******************************����ڵ�֮����й����ļ�����************************************/
					Action ruleAction = DelegationFactory.getAction(DelegationFactory.JPDL_RULE_ACTION);
					node.setAction(ruleAction);
				}
				if(nodeName.indexOf("ProcessState")==0){
					//����Ǵ���������
					Event subEnter = new Event(Event.EVENTTYPE_NODE_ENTER);
					Action createSubProcess = DelegationFactory.getAction(DelegationFactory.JPDL_CREATESUBPROCESS_ACTION);
					subEnter.addAction(createSubProcess);
					//���ȰѸ����������õĲ���ȡ������Ȼ��Ϊ�����̴���
					Event subParamfromSuperParam = new Event(Event.EVENTTYPE_SUBPROCESS_CREATED);					
					Action subParam = DelegationFactory.getAction(DelegationFactory.JPDL_PARAMFROMSUPERTOSUB_ACTION);
					subParamfromSuperParam.addAction(subParam);
					//�������������õĲ���ȡ����Ȼ��ŵ������̵���
					Event superParamFromSubParam = new Event(Event.EVENTTYPE_SUBPROCESS_END);
					Action superParam = DelegationFactory.getAction(DelegationFactory.JPDL_PARAMFROMSUBTOSUPER_ACTION);
					superParamFromSubParam.addAction(superParam);
					
					node.addEvent(subEnter);
					node.addEvent(subParamfromSuperParam);
					node.addEvent(superParamFromSubParam);
				}
				if(nodeName.indexOf("Decision")==0){
					//����Decision�ڵ�ʱΪ����ڵ�Ӹ�actionHandler
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
		log.info("���̴����ڶ���Ϊÿһ���ڵ�ҽ���Ӧ���¼��������");
	}
	
	@SuppressWarnings("unchecked")
	public ProcessDefinition formatDefinition(ProcessDefinition pd) {
		
		String specailTaskName = PropertiesUtil.getProperties("workflow.specailTaskName", "�˺Ź���Ա����");
		List ln = null;
		try{
			ln = pd.getNodes();
		}catch(Exception e){
			throw new RuntimeException("��ʽ�����̽ڵ�(List ln = pd.getNodes())ʱ�����쳣");
		}
		log.info("���̴�����һ�������ʽ����һ������ӵ�ֻ��һ������");
		for(int i=0;i<ln.size();i++) {
			Node n = (Node)ln.get(i);
			if(n instanceof TaskNode) {
				TaskNode tn = (TaskNode)n;
				Set<Task> tasks = tn.getTasks();
				if(tasks==null||tasks.isEmpty()) {//��������ڵ�ͬ��
					Task task = new Task();
					task.setName(tn.getName());	
					tn.addTask(task);
				}else if(tasks.size()>1) {//һ���ڵ�ֻ��һ������ 
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
		log.info("���̴�����һ�������ʽ�����");
		return pd;
	}
	
	/**
	 * ����������ʱ����ָ�������������������ĵ�map��
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
