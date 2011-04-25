package com.digitalchina.itil.knowledge.entity;

import java.util.Date;

import org.springframework.context.support.StaticApplicationContext;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.ConfigItem;

public class KnowledgeAuditHis extends BaseObject {
	public static final Integer OBJTYPE_KNOWLEDGE = 0;
	public static final Integer OBJTYPE_KNOWLEDGEFILE = 1;
	public static final Integer OBJTYPE_KNOWLEDGECONTRACT = 2;
	private Long id;
	private Knowledge knowledge ;
//	private Integer objType;
	private String comment;
	private Long processId;
	private String nodeId;
	private String nodeName;
	private UserInfo approver;
	private String resultFlag;
	private Date approverDate;
	private Integer alterFlag;
	private String processName;
	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Date getApproverDate() {
		return approverDate;
	}
	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getAlterFlag() {
		return alterFlag;
	}
	public void setAlterFlag(Integer alterFlag) {
		this.alterFlag = alterFlag;
	}
	public Knowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
//	public Integer getObjType() {
//		return objType;
//	}
//	public void setObjType(Integer objType) {
//		this.objType = objType;
//	}
}
