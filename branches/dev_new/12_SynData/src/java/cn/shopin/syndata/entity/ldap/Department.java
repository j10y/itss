package cn.shopin.syndata.entity.ldap;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Department implements Serializable {

	/**
	 * 閮ㄩ棬鏍囧彿瑙勫垯
	 * ## ## ## ## .. (璐㈠勾 绗竴绾�绗簩绾�绗笁绾�..)
	 * 
	 */
	String parentNo;//鐖堕儴闂ㄧ紪鍙凤紝鍦↙DAP涓负orgniztionUnit鐨刼u
	
	String deptNo;//閮ㄩ棬缂栧彿锛屽湪LDAP涓负orgniztionUnit鐨刼u
	
	String deptName;//閮ㄩ棬鍚嶇О锛屽湪LDAP涓负orgniztionUnit鐨刣escriptions
	
	
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
	
	
}
