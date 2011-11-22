package net.shopin.ldap.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Department implements Serializable {

	/**
	 * 部门标号规则
	 * \
	 * ## ## ## ## .. (财年 第一级 第二级 第三级 ..)
	 * 
	 */
	String parentNo;//父部门编号，在LDAP中为shopin-orgnization的o
	
	String deptNo;//部门编号，在LDAP中为shopin-orgnization的o
	
	String deptName;//部门名称，在LDAP中为shopin-orgnization的displayName 必填项
	
	String deptDesc;//部门描述，在LDAP中为shopin-orgnization的description 非必填
	
	Integer displayOrder;//排序
	
	Integer status;//状态 0正常 1锁定或删除
	
	String erpId;//对应ERP-HR中的部门编码
	
	public static final Integer SATAL_NORMAL = Integer.valueOf(0);
	public static final Integer SATAL_NOT_NORMAL = Integer.valueOf(1);
	
	
	public String getParentNo() {
		return parentNo;
	}
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	/**
	 * @return the deptNo
	 */
	public String getDeptNo() {
		return deptNo;
	}
	/**
	 * @param deptNo the deptNo to set
	 */
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	public String getDeptDesc() {
		return deptDesc;
	}
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErpId() {
		return erpId;
	}
	public void setErpId(String erpId) {
		this.erpId = erpId;
	}
	
}
