package com.zsgj.itil.require.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.service.entity.ServiceItem;

public class BusinessAccountApplyHis extends BaseObject{
	private Long id;
	private Long dataId;			//�����������ID
	private String definitionName; 	//�������ƣ����ڲ�ѯ���ֵ�ǰ������������
	private Long processId; 		//�������̺ţ�Ҳ���ǵ�ǰ�����е�����
	private String nodeId;
	private String nodeName;
	private String resultFlag;
	private String comment;
	private UserInfo approver;
	private Date approverDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
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
