package com.digitalchina.info.framework.workflow.info;

import java.util.Date;
import java.util.List;

import org.jbpm.graph.exe.Comment;
/**
 * 包装任务信息
 * @Class Name HistoryInfo
 * @Author yang
 * @Create In 2008-6-10
 */
public class HistoryInfo extends BaseInfo{
	String actorId;
	String date;
	String definitionName;
	long processId;
	String nodeName;
	String taskName;
	CommentsInfo comments;
	long taskId;
	/**
	 * 见常量定义类WorkFlowConstants说明
	 */
	long logType;
	
	public HistoryInfo() {	
	}
	
	//把任务信息打包成历史信息，用于列表
	public HistoryInfo(TaskInfo ti) {
		setActorId(ti.getActorId());
 		setDate(ti.getStart());
 		setDefinitionName(ti.getDefinitionName());
 		setName(ti.getName());
 		setTaskName(ti.getName());
 		setNodeName(ti.getNodeName());
 		setProcessId(ti.getId());
 		setProcessId(ti.getProcessId());
 		setComments(ti.getComments());
	}
	
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
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
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public long getLogType() {
		return logType;
	}
	public void setLogType(long logType) {
		this.logType = logType;
	}

	public CommentsInfo getComments() {
		return comments;
	}

	public void setComments(CommentsInfo comments) {
		this.comments = comments;
	}
}
