package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;

public class PageModelTaskRoleNode extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 5120368872894774364L;
	private Long id;
	private String definitionName;//key
	private String nodeName;
	private WorkflowRole workflowRole;
	/**
	 * @Return the String email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @Param String email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @Return the String webServiceURL
	 */
	public String getWebServiceURL() {
		return webServiceURL;
	}
	/**
	 * @Param String webServiceURL to set
	 */
	public void setWebServiceURL(String webServiceURL) {
		this.webServiceURL = webServiceURL;
	}
	/**
	 * @Return the String erpFun
	 */
	public String getErpFun() {
		return erpFun;
	}
	/**
	 * @Param String erpFun to set
	 */
	public void setErpFun(String erpFun) {
		this.erpFun = erpFun;
	}
	private Role role;
	private PageModel pageModel;
	private String ruleFile; //规则文件路径
	private String email;
	private String webServiceURL;
	private String erpFun;
	
	private Integer deleteFlag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public WorkflowRole getWorkflowRole() {
		return workflowRole;
	}
	public void setWorkflowRole(WorkflowRole workflowRole) {
		this.workflowRole = workflowRole;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getRuleFile() {
		return ruleFile;
	}
	public void setRuleFile(String ruleFile) {
		this.ruleFile = ruleFile;
	}
	
}
