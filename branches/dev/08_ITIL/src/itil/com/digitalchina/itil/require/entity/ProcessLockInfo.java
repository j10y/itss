package com.digitalchina.itil.require.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class ProcessLockInfo extends BaseObject {
	private Long id;	//自动编号
	private Long processId ;//流程编号
	private Long taskId;//当前任务编号
	private UserInfo lockUser;//加锁人
	private Long nodeId;//当前节点
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public UserInfo getLockUser() {
		return lockUser;
	}
	public void setLockUser(UserInfo lockUser) {
		this.lockUser = lockUser;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	
	
}
