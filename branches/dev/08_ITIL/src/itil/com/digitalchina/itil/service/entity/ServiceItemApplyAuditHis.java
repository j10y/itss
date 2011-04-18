package com.digitalchina.itil.service.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 服务项申请审批历史（常规或个性化服务用户账号等申请或需求审批历史）
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
	private Integer sidProcessType; //申请变更还是撤销标志，查询使用
	
	private Long requirementId;//个性化需求ID
	private String requirementClass;//个性化需求对应类名
	
	private String definitionName; //流程名称，便于查询区分当前审批是深流程
	
	private String comment;
	private Long processId; //最大的流程号，也就是当前进行中的流程
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
