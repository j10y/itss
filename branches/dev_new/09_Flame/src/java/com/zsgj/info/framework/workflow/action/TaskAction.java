package com.zsgj.info.framework.workflow.action;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.graph.def.Event;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.info.TaskInfo;

/**
 * 任务接点的Action基类
 * @Class Name TaskAction
 * @Author yang
 * @Create In Jun 19, 2008
 */
public  abstract class TaskAction extends BaseAction{
	public final static String EVENTTYPE_NODE_LEAVE = Event.EVENTTYPE_NODE_LEAVE;
	public final static String EVENTTYPE_NODE_ENTER = Event.EVENTTYPE_NODE_ENTER;
	
	/**
	 * 此函数内不能执行与改变流程流向有关功能，如leaveNode()
	 */
	public abstract void execute(ExecutionContext ec) throws Exception;
	/**
	 * 给出流程定义名称
	 */
	public abstract String getDefinitionName();
	/**
	 * 给出节点名称
	 */
	public abstract String getNodeName();

	/**
	 * 给出对应的事件类型
	 */
	public abstract String getEventType();
	
	//合成键值
	public String getKey() {
		String key = getDefinitionName().trim()+"_";
		key += getNodeName().trim()+"_";
		key += getEventType().trim();
		return key;
		
	}
	/**
	 * 从上下文中获取任务信息
	 * @Methods Name getTaskInfo
	 * @Create In Jul 4, 2008 By yang
	 * @param ec
	 * @return 
	 * @ReturnType TaskInfo
	 */
	public TaskInfo getTaskInfo(ExecutionContext ec) {
		TaskInstance ti = (TaskInstance)ec.getContextInstance().getVariable(WorkflowConstants.TASKINFO_KEY);
		if(ti==null) {
			return null;
		}
		TaskInfo taskInfo = TaskInfo.copy(ti);
		return taskInfo;
	}
	
	/**
	 * 收集流程的有关信息，
	 * @Methods Name inform
	 * @Create In Jul 4, 2008 By yang
	 * @param ec
	 * @return 
	 * @ReturnType Map<br>
	 * creator:流程创建者<br>
	 * thisNodeName:当前节点任务名称<br>
	 * toNodeName:下一节点任务名称<br>
	 * thisActorId:当前节点任务执行者<br>
	 * toActorId:下一节点任务执行者<br>	
	 * definationName:流程定义名称<br>
	 */
	public Map<String, String> inform(ExecutionContext ec) {
		Node toNode = ec.getTransition().getTo();
		Node thisNode = ec.getNode();
		String thisActorId = (String)ec.getContextInstance().getVariable(WorkflowConstants.NODE_KEY+thisNode.getId());
		String toActorId = (String)ec.getContextInstance().getVariable(WorkflowConstants.NODE_KEY+toNode.getId());
		String creator = (String)ec.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
		Map<String, String> map = new HashMap<String, String>();
		map.put("creator", creator);
		map.put("thisActorId", thisActorId);
		map.put("toActorId", toActorId);		
		map.put("thisNodeName", thisNode.getName());
		map.put("toNodeName", toNode.getName());
		map.put("definationName", ec.getProcessDefinition().getName());
		return map;
	}
	
	
}
