package com.digitalchina.info.framework.workflow.entity;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * PageModelConfigUnit实体是一个配置单元
 * @Class Name PageModelConfigUnit
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class PageModelConfigUnit extends BaseObject{
	private Long id;
	private Long nodeId;
	private String nodeName;
	private String nodeDesc;
	private String processName;//真实流程的名字
	private String roleName;
	private Long roleId;
	private String pageModelName;
	private Long pageModelId;
	private Long processId;//虚拟流程id
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getPageModelName() {
		return pageModelName;
	}
	public void setPageModelName(String pageModelName) {
		this.pageModelName = pageModelName;
	}
	public Long getPageModelId() {
		return pageModelId;
	}
	public void setPageModelId(Long pageModelId) {
		this.pageModelId = pageModelId;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	
}
