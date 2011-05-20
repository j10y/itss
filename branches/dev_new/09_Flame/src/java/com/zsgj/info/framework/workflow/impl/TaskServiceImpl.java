package com.zsgj.info.framework.workflow.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.exception.RuleFileException;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.base.JbpmContextFactory;
import com.zsgj.info.framework.workflow.entity.RuleConfigUnit;
import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;
import com.zsgj.info.framework.workflow.info.ProcessInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;
import com.zsgj.info.framework.workflow.rules.ProcessRuleHelper;

public class TaskServiceImpl extends BaseDao implements TaskService {   

	private static Log log;
	static 
	{
		log = LogFactory.getLog(com.zsgj.info.framework.workflow.impl.TaskServiceImpl.class);
	}
	/**
	 * ����ӵ�ִ������
	 * @Methods Name continueAudit
	 * @Create In Apr 10, 2009 By Administrator
	 * @param taskId
	 */
	public void execute(long taskId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		try {	
			log.debug("execute");
			Session s = jbpmContext.getSession();
			TaskInstance taskInstance = (TaskInstance) s.get(TaskInstance.class, new Long(taskId));
			//��������
			if(!taskInstance.isOpen()) {
				throw new RuntimeException("��ǰ�������Ѿ���ϵͳ���ʼ����������ˣ�������ϸ�˲飡��������ʼ��������¼ϵͳ���������ϵͳ��������������ˢ�£�");
				//return;
			}
			//ͨ������ʵ���õ�ʵ���еı���
			ContextInstance ci = taskInstance.getContextInstance();
			Map variables = ci.getVariables();
			
			//�����TASKINFO,���������ѭ�����⡣
			if(variables.containsKey(WorkflowConstants.TASKINFO_KEY)){			
				variables.remove(WorkflowConstants.TASKINFO_KEY);
			}
			
			//����������Ϣ���л��󱣴��taskInstance��conmment�������ſ���
			//����ÿ��taskInstance�йص���Ϣ�������������������ı仯��Ӱ��.
			JSONObject jo = JSONObject.fromObject(variables);
			String result = (String)jo.get(WorkflowConstants.RESULT_FLAG);
			String comment = (String)jo.get(WorkflowConstants.COMMENT_FLAG);
			
			
//			Set<String> keySet = variables.keySet();
//			Set<String> nodeKeys = new HashSet();
			//����comment�ڵ�message�������ƣ����ܱ��棻��ʱ�ر��������
			//���Ԥָ����Ϣ
			taskInstance.addComment(jo.toString());
			String rulePath=(String)ci.getVariable("rulePath");
			//�������
			Map mapParams=(Map)ci.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//			List repeateAuditFlag = (List)ci.getVariable("repeateAuditWorkflow");
			Long processId=(Long)ci.getVariable("VIRTUALDEFINITIONINFO_ID");
//			String vProcessName = (String)ci.getVariable("VIRTUALDEFINITIONINFO_NAME");
//			String vProcessDesc = (String)ci.getVariable("VIRTUALDEFINITIONINFO_DESC");
			Long nodeId=taskInstance.getTask().getTaskNode().getId();
			String nodeName=taskInstance.getTask().getTaskNode().getName();
//			Integer version=taskInstance.getProcessInstance().getProcessDefinition().getVersion();
			String ruleName = this.findRuleConfigUnitByParam(nodeId, processId);
			if(ruleName!=null&&!"".equals(ruleName)){
			    mapParams.put("ruleName",ruleName);
				mapParams.put("result",result);
				mapParams.put("nodeId",String.valueOf(nodeId));
				mapParams.put("nodeName",nodeName);
				mapParams.put("comment",comment);
				String transitionName=null;
				if(rulePath!=null){//�������ļ��еĹ���
//					try{
						transitionName=ProcessRuleHelper.executeRule(rulePath, mapParams);
//					}catch(RuleFileException e){
//						throw new RuleFileException(e.getMessage());
//					}catch(Exception e){
//						throw new RuntimeException(vProcessDesc+"(����)��"+nodeName+"(�ڵ�)����audit��ȡ�����ʱ�����쳣");
//					}
				}
				if(transitionName!=null) {//Ĭ�ϵ�ת���־,�뿪��transation����
					//ע�⣺�˴���taskInstance���������ģ��Ա�������Action��ʹ��,
					//���ܴ���TaskInfo,��ΪTaskInfo����Jbpm�ڲ��࣬���Զ�֧�ִ��л�
					//��ȡ��taskInstance��ʱ���ٽ���ת����ΪtaskInfo.
					ci.setVariable(WorkflowConstants.TASKINFO_KEY, taskInstance);
					ci.setVariable("result", result);
					//result�б�����ǡ�ת��|��������˴�ȡ��ת����Ϣ
					taskInstance.end(transitionName);	//��������ж��ת��ڵ㣬��ô�������һ��ת��涨����������ת,���ַ�ʽ��end��ת��ֵ��		
				}else{//ת��Ϊ��ʱ
					taskInstance.end();//������ת��û���κ�Ҫ�󣬼���ʲô�ط�����Ӱ�������������ֻ��һ��ת����end����
				}
			}
			else{//û�й���ʱ������ִ��
				taskInstance.end(result);
			}
			jbpmContext.save(taskInstance);	//������֪ͨjbpmһ������ʵ���Ѿ����
			
		}catch(Exception e) {
			if(e.getCause() instanceof com.zsgj.info.framework.exception.RuleFileException){
				jbpmContext.setRollbackOnly();
				throw new RuleFileException(e.getMessage());
			}else{
				jbpmContext.setRollbackOnly();
				throw new RuntimeException("��һ�ڵ�û�о���������,����ϵ����Ա�˲飡");
			}
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
			
		}
	}

	public TaskInstance getTaskById(long taskId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		TaskInstance taskInstance = null;
		try {
			log.debug("getTaskById");
			Session s = jbpmContext.getSession();
			Criteria c = s.createCriteria(TaskInstance.class);
			c.add(Restrictions.eq("id", new Long(taskId)));
			c.setFetchMode("task", FetchMode.JOIN);
			c.createAlias("task.taskNode", "taskNode").setFetchMode("taskNode", FetchMode.JOIN);
			c.createAlias("taskNode.processDefinition", "processDefinition").setFetchMode("processDefinition", FetchMode.JOIN);
			//c.setFetchMode("taskNode", FetchMode.JOIN);
			taskInstance = (TaskInstance) c.uniqueResult();
			//taskInstance = (TaskInstance) s.load(TaskInstance.class, new Long(taskId));
			taskInstance.getName();
			//System.out.println(taskInstance.getName());
		} catch (HibernateException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return taskInstance;
	}
	/**
	 * �г������б�
	 */
	@SuppressWarnings("unchecked")
	public List<TaskInfo> listTasks(String actorId) {
		
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
		try {
			log.debug("listTasks");
			//�õ��˵�����ʵ����ʱ��Ҫ�����������������ˣ��ֱ���actorId��poolActorId
			List list = jbpmContext.getTaskMgmtSession().findTaskInstances(actorId);
			List listPooled = jbpmContext.getTaskMgmtSession().findPooledTaskInstances(actorId);
			list.addAll(listPooled);
			Map bizParams = null;
			for(int i=0;i<list.size();i++) {
				TaskInstance taskInstance = (TaskInstance)list.get(i);
				ContextInstance contextInstance = taskInstance.getContextInstance();
				String vProcessDesc = (String)contextInstance.getVariable("VIRTUALDEFINITIONINFO_DESC");
				String applyUser = (String)contextInstance.getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
				String virDesc = (String)contextInstance.getVariable("VIRTUALDEFINITIONINFO_DESC");
				bizParams = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
				bizParams.put("vProcessDesc", vProcessDesc);
				bizParams.put("applyUser", applyUser);
				bizParams.put("virDesc", virDesc);
				taskInfos.add(TaskInfo.copy((TaskInstance)list.get(i),bizParams));
			}
			
		} catch (RuntimeException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return taskInfos;
	}
	/**
	 * 
	 * ���þ����������ڵ�����ָ��������
	 * @Methods Name assign
	 * @Create In 2008-3-12 By yang
	 * @param taskId  �����ʶ
	 * @param actorId  �û���ʶ,������û���ѡʱ���м��á�|������
	 * @ReturnType void
	 */
	public void reAssign(long taskId, String actorId) {
		
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("reassign");
			Session s = jbpmContext.getSession();
			TaskInstance taskInstance = (TaskInstance) s.load(TaskInstance.class, new Long(taskId));
			//ʵ��ָ����Ա
			if(actorId.indexOf('|')>0) {
				String[] actorIds = actorId.split("\\|");
				taskInstance.setPooledActors(actorIds);//ֻҪ������ʵ������ָ�����ˣ�ԭ�����˱����ڵ������˸����ǵ���
				taskInstance.setActorId(null);//��ֻ����������ʵ��
			}
			else {
				taskInstance.setPooledActors(new HashSet());
				taskInstance.setActorId(actorId);
			}
			jbpmContext.save(taskInstance);
		} catch (HibernateException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}finally {
			JbpmContextFactory.closeJbpmContext();
		}
	}
	
	/**
	 * 
	 * ���þ�����������ǩ�ڵ�����ָ��������
	 * @Methods Name addSignReAssign
	 * @Create In 2009-12-2 By gaowen
	 * @param taskId  �����ʶ
	 * @param actorId  �û���ʶ,������û���ѡʱ���м��á�|������
	 * @ReturnType void
	 */
     public Long addSignReAssign(long taskId, String actorId) {
		
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Long newTaskIdLong = 0l;
		try {
			log.debug("addSignReAssign");
			Session s = jbpmContext.getSession();
			TaskInstance taskInstance = (TaskInstance) s.load(TaskInstance.class, new Long(taskId));
			TaskInstance newTaskInstance =taskInstance.getTaskMgmtInstance().createTaskInstance(taskInstance.getTask(), taskInstance.getToken());
			newTaskIdLong = newTaskInstance.getId();
			//ʵ��ָ����Ա
			if(actorId.indexOf('|')>0) {
				String[] actorIds = actorId.split("\\|");
				newTaskInstance.setPooledActors(actorIds);//ֻҪ������ʵ������ָ�����ˣ�ԭ�����˱����ڵ������˸����ǵ���
				newTaskInstance.setActorId(null);//��ֻ����������ʵ��
			}
			else {
				newTaskInstance.setPooledActors(new HashSet());
				newTaskInstance.setActorId(actorId);
			}
			jbpmContext.save(newTaskInstance);
		} catch (HibernateException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return newTaskIdLong;
	}

	public ProcessInfo getProcessInfo(long taskId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		ProcessInfo processInfo = null;
		try {
			log.debug("getProcessInfo");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			long processInstanceId = taskInstance.getProcessInstance().getId();
			ProcessInstance processInstance = null;
			processInstance = jbpmContext.loadProcessInstance(processInstanceId);
			processInfo = new ProcessInfo(processInstance);
			
		} catch (HibernateException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return processInfo;
	}

	
	public TaskInfo getTaskInfo(long taskId) {
		
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		TaskInfo taskInfo = null;
		try {
			log.debug("getTaskInfo");
			Session s = jbpmContext.getSession();
			TaskInstance taskInstance = (TaskInstance) s.load(TaskInstance.class, new Long(taskId));
			ContextInstance contextInstance = taskInstance.getContextInstance();
			Map bizParams = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			taskInfo = TaskInfo.copy(taskInstance,bizParams);
		} catch (HibernateException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return taskInfo;
	}

	@SuppressWarnings("unchecked")
	public List<TaskInfo> listAllTasks() {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
		try {
			log.debug("listAllTasks");
			GraphSession graphSession = jbpmContext.getGraphSession();
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			List listDefination = graphSession.findAllProcessDefinitions();
			for(int i=0;i<listDefination.size();i++) {
				ProcessDefinition pd = (ProcessDefinition)listDefination.get(i);				
				List processInstances = graphSession.findProcessInstances(pd.getId());
				for(int j=0;j<processInstances.size();j++) {
					ProcessInstance pi = (ProcessInstance)processInstances.get(j);
					List tasks = taskMgmtSession.findTaskInstancesByProcessInstance(pi);
					for(int k=0;k<tasks.size();k++) {
						TaskInstance taskInstance = (TaskInstance)tasks.get(k);
						ContextInstance contextInstance = taskInstance.getContextInstance();
						Map bizParams = null;
						bizParams = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
						taskInfos.add(TaskInfo.copy(taskInstance,bizParams));
					}
				}
			}
		} catch (RuntimeException e) { 
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return taskInfos;
	}

	/**
	 * ͨ��taskId�õ��������̽ڵ�
	 * @Methods Name getAllNodeByTaskId
	 * @Create In Mar 30, 2009 By guangsa
	 * @param taskId
	 */
	public String getAllNodeByTaskId(long taskId){
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		String json = "{data:[";
		try {
			log.debug("listAllTasks");
			Session s = jbpmContext.getSession();
			TaskInstance taskInstance = (TaskInstance) s.load(TaskInstance.class, new Long(taskId));
			ProcessInstance processInstance=taskInstance.getProcessInstance();
			ProcessDefinition pd=processInstance.getProcessDefinition();
			List<Node> list=pd.getNodes();
			for(Node node : list){
				json+="{id:"+node.getId()+",name:'"+node.getName()+"',desc:'"+node.getDescription()+"'}";
				json += ",";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]}";
		} catch (RuntimeException e) { 
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return json;
	}
	/**
	 * �õ��ڵ�����
	 * @Methods Name getNodeDesc
	 * @Create In Mar 30, 2009 By guangsa
	 * @param taskId
	 * @param nodeId
	 */
	public String getNodeDesc(Long taskId, Long nodeId) {
		// TODO Auto-generated method stub
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		String json = "";
		try {
			log.debug("listAllTasks");
			Session s = jbpmContext.getSession();
			TaskInstance taskInstance = (TaskInstance) s.load(TaskInstance.class, new Long(taskId));
			ProcessInstance processInstance=taskInstance.getProcessInstance();
			ProcessDefinition pd=processInstance.getProcessDefinition();
			List<Node> list=pd.getNodes();
			for(Node node : list){
				if(nodeId.equals(node.getId())){
					json+="{id:"+node.getId()+",nodeName:'"+node.getName()+"',nodeDesc:'"+node.getDescription()+"'}";
				}
			}
		} catch (RuntimeException e) { 
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return json;
	}
	
	
	/**
	 * �õ���һ���ڵ���Ϣ
	 * @Methods Name getNextNodeInfo
	 * @Create In Mar 30, 2009 By guangsa
	 * @param taskId
	 * @return Map
	 */
	public Map getNextNodeInfo(Long taskId){
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Map map = new HashMap();
		try {
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			ContextInstance ci = taskInstance.getContextInstance();
			Map variables = ci.getVariables();
			//�����TASKINFO,���������ѭ�����⡣
			if(variables.containsKey(WorkflowConstants.TASKINFO_KEY)){			
				variables.remove(WorkflowConstants.TASKINFO_KEY);
			}
			JSONObject jo = JSONObject.fromObject(variables);
			String result = (String) jo.get(WorkflowConstants.RESULT_FLAG);
	
			Node node = null;
			List<Transition> transitions = taskInstance.getAvailableTransitions();
			if (transitions.size() == 1) {
				node = transitions.get(0).getTo();
			} else {
				String transtion = result;
				for (Transition trans : transitions) {
					if (trans.getName().equals(transtion)) {
						node = trans.getTo();
					}
				}
			}
			String nextNodeName = node.getName();
			String nextNodeDesc = node.getDescription();
			String nodeType=node.toString();
			Long nextNodeId=node.getId();
			map.put("nodeName", nextNodeName);
			map.put("nodeDesc",nextNodeDesc);
			map.put("nodeId",nextNodeId);
			map.put("nodeType",nodeType);
		} catch (RuntimeException e) { 
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return map;
	}
	/**
	 * ������Ӧ�Ĳ����õ���Ӧ�Ĺ���Ԫ
	 * @Methods Name findRuleConfigUnitByParam
	 * @param nodeId
	 * @param ProcessId
	 * @return
	 */
	public String findRuleConfigUnitByParam(Long nodeId ,Long processId){
		
		Criteria criteria = super.getCriteria(RuleConfigUnit.class);
		criteria.add(Restrictions.eq("nodeId", nodeId));
		criteria.add(Restrictions.eq("processId", processId));
		criteria.setProjection(Projections.property("ruleName"));
		String ruleName = (String)criteria.uniqueResult();
		return ruleName;
	}

	public List<TaskInfo> getAllActiveTaskNodeMessage(String actorId) {
		
		List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
		Criteria criteria = super.getCriteria(WorkflowRecordTaskInfo.class);
		criteria.add(Restrictions.eq("processCreator", actorId));
		List<WorkflowRecordTaskInfo> taskMegs = criteria.list();
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();	
		try{
			for(int i=0;i<taskMegs.size();i++) {
				WorkflowRecordTaskInfo taskRecord = taskMegs.get(i);
				Long processId = taskRecord.getProcessInstanceId();
				Long taskId = taskRecord.getTaskId();
				ProcessInstance pi = jbpmContext.getGraphSession().loadProcessInstance(processId);
				TaskInstance taskInstance = jbpmContext.getTaskInstance(taskId);
				Map bizParams = null;
				bizParams = (Map)pi.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
				String defDesc = (String)pi.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
				bizParams.put("defDesc", defDesc);
				taskInfos.add(TaskInfo.copy(taskInstance,bizParams));
			}
		}catch(Exception e){
			e.printStackTrace();
			jbpmContext.setRollbackOnly();
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		return taskInfos;
	}
}
