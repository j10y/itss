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
 * ����ӵ��Action����
 * @Class Name TaskAction
 * @Author yang
 * @Create In Jun 19, 2008
 */
public  abstract class TaskAction extends BaseAction{
	public final static String EVENTTYPE_NODE_LEAVE = Event.EVENTTYPE_NODE_LEAVE;
	public final static String EVENTTYPE_NODE_ENTER = Event.EVENTTYPE_NODE_ENTER;
	
	/**
	 * �˺����ڲ���ִ����ı����������йع��ܣ���leaveNode()
	 */
	public abstract void execute(ExecutionContext ec) throws Exception;
	/**
	 * �������̶�������
	 */
	public abstract String getDefinitionName();
	/**
	 * �����ڵ�����
	 */
	public abstract String getNodeName();

	/**
	 * ������Ӧ���¼�����
	 */
	public abstract String getEventType();
	
	//�ϳɼ�ֵ
	public String getKey() {
		String key = getDefinitionName().trim()+"_";
		key += getNodeName().trim()+"_";
		key += getEventType().trim();
		return key;
		
	}
	/**
	 * ���������л�ȡ������Ϣ
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
	 * �ռ����̵��й���Ϣ��
	 * @Methods Name inform
	 * @Create In Jul 4, 2008 By yang
	 * @param ec
	 * @return 
	 * @ReturnType Map<br>
	 * creator:���̴�����<br>
	 * thisNodeName:��ǰ�ڵ���������<br>
	 * toNodeName:��һ�ڵ���������<br>
	 * thisActorId:��ǰ�ڵ�����ִ����<br>
	 * toActorId:��һ�ڵ�����ִ����<br>	
	 * definationName:���̶�������<br>
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
