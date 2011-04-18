package com.digitalchina.itil.service.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * ����������������ʷ���������Ի������û��˺ŵ����������������ʷ��
 * @Class Name ServiceItemApplyAuditHis
 * @Author sa
 * @Create In 2009-3-20
 */
public class ServiceItemApplyAuditHis extends BaseObject {
	public static Integer STATUS_FINISHED = 1;
	public static Integer STATUS_APPROVING = 2;
	public static Integer STATUS_DRAFT = 0;
	
	private Long id;
	private ServiceItem serviceItem; 
	private Integer sidProcessType; //���������ǳ�����־����ѯʹ��
	
	private Long requirementId;//���Ի�����ID
	private String requirementClass;//���Ի������Ӧ����
	
	private String definitionName; //�������ƣ����ڲ�ѯ���ֵ�ǰ������������
	
	private String comment;
	private Long processId; //�������̺ţ�Ҳ���ǵ�ǰ�����е�����
	private String nodeId;
	private String nodeName;
	private UserInfo approver;
	private String resultFlag;
	private Date approverDate;
	//private String processName;
	
//	public String getProcessName() {
//		return processName;
//	}
//	public void setProcessName(String processName) {
//		this.processName = processName;
//	}
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
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	public Integer getSidProcessType() {
		return sidProcessType;
	}
	public void setSidProcessType(Integer sidProcessType) {
		this.sidProcessType = sidProcessType;
	}
	public Long getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}
	public String getRequirementClass() {
		return requirementClass;
	}
	public void setRequirementClass(String requirementClass) {
		this.requirementClass = requirementClass;
	}
	
	
}
