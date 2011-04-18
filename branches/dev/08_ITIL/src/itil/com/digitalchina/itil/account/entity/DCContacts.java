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
 * ͨѶ¼���û���¼�û���ITSSϵͳ�е���Ϣ
 * @Class Name DCContacts
 * @Author lee
 * @Create In Jan 19, 2010
 */
public class DCContacts extends BaseObject{
private static final long serialVersionUID = 4119195113821846672L;
	private Long id;				//�Զ����
	private String employeeCode;	//Ա�����
    private String userNames;		//�û���(itcodeСд)
	private String realName;		//��ʵ����
    private String itcode;			//�û�ITCODE
	private Long departCode;		//���ű��
	private Department department;	//��������
	private Platform platform;		//����ƽ̨
    private String email;			//�����ʼ�
	private String telephone;		//��ϵ�绰
	private String voipPhone;		//IP�绰
	private String mobilePhone;		//�ֻ�
	private String costCenterCode;	//�ɱ����ı��
	private WorkSpace workSpace;	//�����ص�
	private UserType userType;		//Ա���������
	private PersonnelScope personnelScope;	//�����ӷ�Χ
	private SameMailDept sameMailDept;//�ʼ��ȼ�������
	private java.lang.String mailServer; // �ʼ�������
	
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
