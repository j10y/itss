package com.zsgj.itil.notice.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class NoticeAuditHis extends BaseObject{
	private Long id;
	private NewNotice newNotice;
	private String comment;
	private Long processId;
	private String nodeId;
	private String nodeName;
	private UserInfo approver;
	private String resultFlag;
	private Date approverDate;
	private String  alterFlag;
	public String  getAlterFlag() {
		return alterFlag;
	}
	public void setAlterFlag(String  alterFlag) {
		this.alterFlag = alterFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public NewNotice getNewNotice() {
		return newNotice;
	}
	public void setNewNotice(NewNotice newNotice) {
		this.newNotice = newNotice;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public UserInfo getApprover() {
		return approver;
	}
	public void setApprover(UserInfo approver) {
		this.approver = approver;
	}
	public String getResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}
	public Date getApproverDate() {
		return approverDate;
	}
	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}
}
