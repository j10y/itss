package com.zsgj.itil.account.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 不同类型账号申请SBU加签人信息
 * @Class Name AccountSBUOfficer
 * @Author lee
 * @Create In Jun 1, 2009
 */
public class AccountSBUOfficer extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -5010561921396505447L;
	private Long id;
	private String processNameDescription;		//流程名中文描述(如：BI帐号申请)
	private String nodeName;		//流程节点名
	private String personScope;		//人事子范围
	private String confirmUser;	//节点审批人
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
	
	
	public String getProcessNameDescription() {
		return processNameDescription;
	}
	public void setProcessNameDescription(String processNameDescription) {
		this.processNameDescription = processNameDescription;
	}
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	public String getPersonScope() {
		return personScope;
	}
	public void setPersonScope(String personScope) {
		this.personScope = personScope;
	}
}
