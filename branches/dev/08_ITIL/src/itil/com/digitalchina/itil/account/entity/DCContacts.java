package com.digitalchina.itil.account.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.MailServer;
import com.digitalchina.info.framework.security.entity.PersonnelScope;
import com.digitalchina.info.framework.security.entity.Platform;
import com.digitalchina.info.framework.security.entity.SameMailDept;
import com.digitalchina.info.framework.security.entity.UserType;
import com.digitalchina.info.framework.security.entity.WorkSpace;

/**
 * 通讯录表，用户记录用户在ITSS系统中的信息
 * @Class Name DCContacts
 * @Author lee
 * @Create In Jan 19, 2010
 */
public class DCContacts extends BaseObject{
private static final long serialVersionUID = 4119195113821846672L;
	private Long id;				//自动编号
	private String employeeCode;	//员工编号
    private String userNames;		//用户名(itcode小写)
	private String realName;		//真实姓名
    private String itcode;			//用户ITCODE
	private Long departCode;		//部门编号
	private Department department;	//所属部门
	private Platform platform;		//所属平台
    private String email;			//电子邮件
	private String telephone;		//联系电话
	private String voipPhone;		//IP电话
	private String mobilePhone;		//手机
	private String costCenterCode;	//成本中心编号
	private WorkSpace workSpace;	//工作地点
	private UserType userType;		//员工身份类型
	private PersonnelScope personnelScope;	//人事子范围
	private SameMailDept sameMailDept;//邮件等价名部门
	private java.lang.String mailServer; // 邮件服务器
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getItcode() {
		return itcode;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public Long getDepartCode() {
		return departCode;
	}
	public void setDepartCode(Long departCode) {
		this.departCode = departCode;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	
	public String getCostCenterCode() {
		return costCenterCode;
	}
	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}
	public WorkSpace getWorkSpace() {
		return workSpace;
	}
	public void setWorkSpace(WorkSpace workSpace) {
		this.workSpace = workSpace;
	}
	
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public PersonnelScope getPersonnelScope() {
		return personnelScope;
	}
	public void setPersonnelScope(PersonnelScope personnelScope) {
		this.personnelScope = personnelScope;
	}
	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public SameMailDept getSameMailDept() {
		return sameMailDept;
	}
	public void setSameMailDept(SameMailDept sameMailDept) {
		this.sameMailDept = sameMailDept;
	}
	public java.lang.String getMailServer() {
		return mailServer;
	}
	public void setMailServer(java.lang.String mailServer) {
		this.mailServer = mailServer;
	}
	public String getVoipPhone() {
		return voipPhone;
	}
	public void setVoipPhone(String voipPhone) {
		this.voipPhone = voipPhone;
	}
	
	
	
	
	

}
