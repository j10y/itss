package com.digitalchina.info.framework.workflow.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/** 
 * @author 光顺安 E-mail: guangsa@info.com
 * @version 创建时间：Apr 13, 2009 9:11:04 AM 
 * 类说明 
 */

public class SubProcessConfigUnit extends BaseObject{
  private Long id;
  private Long superProcessId;//主流程的id(虚拟流程）
  private Long subProcessId;//子流程的id(虚拟流程）
  private Long nodeId;//节点的id
  private int version;
  private String applyType;
  private String applyTypeName;
  private String param;
public String getApplyType() {
	return applyType;
}
public void setApplyType(String applyType) {
	this.applyType = applyType;
}
public String getApplyTypeName() {
	return applyTypeName;
}
public void setApplyTypeName(String applyTypeName) {
	this.applyTypeName = applyTypeName;
}
public String getParam() {
	return param;
}
public void setParam(String param) {
	this.param = param;
}
public SubProcessConfigUnit() {
	super();
	// TODO Auto-generated constructor stub
}
public SubProcessConfigUnit(Long id) {
	super(id);
	// TODO Auto-generated constructor stub
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Long getSuperProcessId() {
	return superProcessId;
}
public void setSuperProcessId(Long superProcessId) {
	this.superProcessId = superProcessId;
}
public Long getSubProcessId() {
	return subProcessId;
}
public void setSubProcessId(Long subProcessId) {
	this.subProcessId = subProcessId;
}
public Long getNodeId() {
	return nodeId;
}
public void setNodeId(Long nodeId) {
	this.nodeId = nodeId;
}
public int getVersion() {
	return version;
}
public void setVersion(int version) {
	this.version = version;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
	result = prime * result
			+ ((subProcessId == null) ? 0 : subProcessId.hashCode());
	result = prime * result
			+ ((superProcessId == null) ? 0 : superProcessId.hashCode());
	result = prime * result + version;
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (!super.equals(obj))
		return false;
	if (getClass() != obj.getClass())
		return false;
	final SubProcessConfigUnit other = (SubProcessConfigUnit) obj;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (nodeId == null) {
		if (other.nodeId != null)
			return false;
	} else if (!nodeId.equals(other.nodeId))
		return false;
	if (subProcessId == null) {
		if (other.subProcessId != null)
			return false;
	} else if (!subProcessId.equals(other.subProcessId))
		return false;
	if (superProcessId == null) {
		if (other.superProcessId != null)
			return false;
	} else if (!superProcessId.equals(other.superProcessId))
		return false;
	if (version != other.version)
		return false;
	return true;
}
}
