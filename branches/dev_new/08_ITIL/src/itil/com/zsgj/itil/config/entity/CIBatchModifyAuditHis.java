package com.zsgj.itil.config.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 配置项批量变更审批历史
 * @Class Name CIBatchModifyAuditHis
 * @Author lee
 * @Create In Aug 24, 2009
 */
public class CIBatchModifyAuditHis extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 7349162655599752503L;
	private Long id;
	private CIBatchModify modify;
	private String comment;
	private Long processId;
	private String nodeId;
	private String nodeName;
	private UserInfo approver;
	private String resultFlag;
	private Date approverDate;
	private String alterFlag;
	private String processName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CIBatchModify getModify() {
		return modify;
	}
	public void setModify(CIBatchModify modify) {
		this.modify = modify;
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
	public String getAlterFlag() {
		return alterFlag;
	}
	public void setAlterFlag(String alterFlag) {
		this.alterFlag = alterFlag;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
}
