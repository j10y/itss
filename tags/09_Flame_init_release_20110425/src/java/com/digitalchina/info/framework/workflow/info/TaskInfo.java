package com.digitalchina.info.framework.workflow.info;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
/**
 * 包装任务实例
 * @Class Name TaskInfo
 * @Author guangsa
 * @Create In 20100704
 */
public class TaskInfo  extends BaseInfo{
	long processId;//流程标识
	String definitionName;//定义名称	
	String definitionDesc;//定义的中文描述
	String processCreateDate;//流程创建时间
	String start;//流程流转到本环节时间
	String nodeName;//节点名称
	String nodeDisc;//节点描述
	
	String actorId;//指派人员标识
	Date dueDate;//最后期限
	int priority;//优先级
	String previousActorId;//上一个执行人
	CommentsInfo comments;//评审意见
	Map BizParams;//业务参数
	
	public TaskInfo() {
	}
	
	//这是为了回退到开始节点时准备的Task参数
	private TaskInfo(Long nodeId,String nodeName ,TaskInstance ti,Map bizParam){
		start = ti.getStart().toString();
		nodeName = ti.getTask().getTaskNode().getName();
		actorId = ti.getActorId();//得到当前任务事例用户名
		nodeDisc = ti.getTask().getTaskNode().getDescription();
		dueDate = ti.getDueDate();
		priority = ti.getPriority();
		previousActorId = ti.getPreviousActorId();
		processId = ti.getProcessInstance().getId();
		definitionName = ti.getProcessInstance().getProcessDefinition().getName();
		definitionDesc = ti.getProcessInstance().getProcessDefinition().getDescription();
		BizParams = bizParam;
		setId(nodeId);
		setName(nodeName);
		
	}
	
	@SuppressWarnings("unchecked")
	private TaskInfo(TaskInstance ti,Map bizParams) {
//		if(ti.getId()==67609||ti.getId()==67610||ti.getId()==67611||ti.getId()==67612){
//			System.out.println(ti.getId()+"取出======================"+ti.getVariable("specialBusniessKey"));
//		}
		Task task = ti.getTask();
		Node taskNode = null;
		if(task!=null&&!"".equals(task)){
			taskNode = task.getTaskNode();
		}
		if(taskNode!=null&&!"".equals(taskNode)){
			nodeName = ti.getTask().getTaskNode().getName();
			nodeDisc = ti.getTask().getTaskNode().getDescription();
		}
		//add by guangsa for 需要显示流程开始的时间（用户提交的那个时间）in 20100830 begin
		ProcessInstance pi = ti.getProcessInstance();
		if(pi!=null&&!"".equals(pi)){
			processCreateDate = pi.getStart().toString().split("\\.")[0];
		}
		
		//add by guangsa for 流程到我这个环节的时间 in 20100830 end
		start = ti.getCreate().toString().split("\\.")[0];
		//add by guangsa for 流程到我这个环节的时间 in 20100830 begin
		actorId = ti.getActorId();//得到当前任务事例用户名
		
		Set pooledActors = ti.getPooledActors();
		if(pooledActors != null&&!pooledActors.isEmpty()) {
			String actorIds = actorId==null?"":(actorId+"|");
			Iterator it = pooledActors.iterator();
			while(it.hasNext()) {
				PooledActor pactor = (PooledActor)it.next();
				if(actorId==null||(actorId!=null&&!actorId.trim().equalsIgnoreCase(pactor.getActorId().trim()))) {
					actorIds += pactor.getActorId()+"|";
				}				
			}
			if(actorIds.endsWith("|")) {//判断是不是以指定字符串结束
				//actorId = "["+actorIds.substring(0,actorIds.length()-1)+"]";
				actorId = actorIds.substring(0,actorIds.length()-1);//不要加括号
			}
		}
		dueDate = ti.getDueDate();
		priority = ti.getPriority();
		previousActorId = ti.getPreviousActorId();
		processId = ti.getProcessInstance().getId();
		definitionName = ti.getProcessInstance().getProcessDefinition().getName();
		definitionDesc = ti.getProcessInstance().getProcessDefinition().getDescription();
		comments = new CommentsInfo(ti.getComments());
		this.BizParams = bizParams;
		setId(ti.getId());
		
		//add by guangsa for 会签节点多个任务实例多个不同用途 in 20100729 begin
		String specialBusniessValue = (String)ti.getDescription();//.getVariable("specialBusniessKey");
		if(specialBusniessValue!=null&&!"".equals(specialBusniessValue)){
			String  specailName = specialBusniessValue;//ti.getName()+"("++")";
			setName(specailName);
		}else{
			setName(ti.getName());
		}
		//add by guangsa for 会签节点多个任务实例多个不同用途 in 20100729 end
		
		
	}
	
	public static TaskInfo copy(TaskInstance ti,Map bizParams) {
		return new TaskInfo(ti,bizParams);
	}
	
	public static TaskInfo copy(TaskInstance ti) {
		return new TaskInfo(ti,null);
	}
	
	public static TaskInfo copy(Long nodeId,String nodeName ,TaskInstance ti,Map bizParam){
		return new TaskInfo(nodeId,nodeName,ti,bizParam);
	}
	
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public long getProcessId() {
		return processId;
	}
	public void setProcessId(long processId) {
		this.processId = processId;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	
	public String getPreviousActorId() {
		return previousActorId;
	}
	public void setPreviousActorId(String previousActorId) {
		this.previousActorId = previousActorId;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public CommentsInfo getComments() {
		return comments;
	}

	public void setComments(CommentsInfo comments) {
		this.comments = comments;
	}

	public Map getBizParams() {
		return BizParams;
	}

	public void setBizParams(Map bizParams) {
		BizParams = bizParams;
	}

	public String getNodeDisc() {
		return nodeDisc;
	}

	public void setNodeDisc(String nodeDisc) {
		this.nodeDisc = nodeDisc;
	}

	public String getDefinitionDesc() {
		return definitionDesc;
	}

	public void setDefinitionDesc(String definitionDesc) {
		this.definitionDesc = definitionDesc;
	}

	public String getProcessCreateDate() {
		return processCreateDate;
	}

	public void setProcessCreateDate(String processCreateDate) {
		this.processCreateDate = processCreateDate;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

}
