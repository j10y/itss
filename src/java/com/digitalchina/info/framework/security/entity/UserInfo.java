package com.digitalchina.info.framework.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.util.PropertiesUtil;

/**
 * ϵͳ�û�ʵ��
 * @Class Name UserInfo
 * @Author peixf
 * @Create In 2008-3-5
 */
public class UserInfo extends BaseObject{
	private static final long serialVersionUID = 4119195113821846672L;
	
	private Long id;				//�Զ����
	private String employeeCode;	//Ա�����
	
	private String userName;		//�û���(itcodeСд)
	private String password;		//�û�����
	private String realName;		//��ʵ����
//	--------------------------------------------------
	private String itcode;			//�û�ITCODE
	private String gender;			//�û��Ա�
	private Long departCode;		//���ű��
	private Department department;	//��������
	private Platform platform;		//����ƽ̨

	private Long titleCode;			//��λ���
	//-------------------------------------------
	//private Department department;
	private Set workDepts = new HashSet();
	
	private Integer enabled;		//�Ƿ����
	private Integer isLock;			//�Ƿ�����
	
	private String email;			//�����ʼ�
	private String telephone;		//��ϵ�绰
	private String mobilePhone;		//�ֻ�
	private String postCode;		//ְλ���
	
	private Integer specialUser;	//�Ƿ������û�
	private Integer externalFlag;	//�Ƿ��ⲿ�û�
	
	private SameMailDept sameMailDept;//�ʼ��ȼ�������
	private String costCenterCode;	//�ɱ����ı��
	private WorkSpace workSpace;	//�����ص�
	private MailServer mailServer;		//�ʼ�������
	private UserType userType;		//Ա���������
	private PersonnelScope personnelScope;	//�����ӷ�Χ
	
	//private Integer isTemp;			//�Ƿ�Ϊ��ʱ�û�--����ɾ������
	//private Integer	isAccredited;	//�Ƿ�Ϊ��ǲ�û�--����ɾ������
	
	private String userViewStyle;	//ҳ����

	private Set userTableSettings = new HashSet();

	private HashMap<String,List> userMenuItem = new HashMap<String,List>();
	private Set roles = new HashSet(0);
	
	private Integer timeCard;
	//�¼�����	
	private String postName;//ְλ���ƣ���λ����
	
	private String costCenterName;//�ɱ���������
	
	private Date joinDate;//��ְ����
	
	private Date leaveDate;//��ְ����
	
	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	public Integer getTimeCard() {
		return timeCard;
	}

	public void setTimeCard(Integer timeCard) {
		this.timeCard = timeCard;
	}
	public String getRealNameAndDept() {
		String deptName = this.getDepartment().getDepartName();
		String result = realName+"/"+userName+"/"+deptName;
		return result;
	}
	
	/**
	 * �û��Ƿ��������û�
	 * @Methods Name isSpecialUser
	 * @Create In 2008-5-16 By peixf
	 * @return boolean
	 */
	public boolean isSpecialUserInfo(){
		if(this.getSpecialUser()==null) return false;
		if(this.getSpecialUser().intValue()==1) return true;
		return false;
	}
	
	public String getUniquePropName() {
		return PropertiesUtil.getProperties("system.user.uniquecolumn", "userName");
	}

	/**
	 * �û��Ƿ����
	 * @Methods Name isEnabled
	 * @Create In 2008-3-11 By peixf
	 * @return boolean
	 */
	public boolean isUserEnabled(){
		boolean result = false;
		if(this.enabled!=null&& this.enabled.intValue()==1){
			result = true;
		}
		return result;
	}
	
	/**
	 * �û��Ƿ�û�б�����
	 * @Methods Name isUserNonLock
	 * @Create In 2009-3-13 By ����
	 * @return boolean
	 * �û��Ƿ�������״̬Ϊ: 0--û�б����� 1--������
	 */
	public boolean isUserNonLock(){
		boolean result = false;
		if(this.isLock != null&& this.isLock.intValue() == 0){
			result = true;
		}
		return result;
	}
	/**
	 * �����û�Ψһ�ֶΣ����������Ŀ��Ҫ�������ļ�������system.user.uniquecolumn
	 */
	public String getName() {
		return PropertiesUtil.getProperties("system.user.uniquecolumn", "userName");
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserInfo other = (UserInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}

	public Set getUserTableSettings() {
		return userTableSettings;
	}

	public void setUserTableSettings(Set userTableSettings) {
		this.userTableSettings = userTableSettings;
	}

	public Long getDepartCode() {
		return departCode;
	}

	public void setDepartCode(Long departCode) {
		this.departCode = departCode;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getItcode() {
		return itcode;
	}

	public void setItcode(String itcode) {
		this.itcode = itcode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(Long titleCode) {
		this.titleCode = titleCode;
	}

	public Set getWorkDepts() {
		return workDepts;
	}

	public void setWorkDepts(Set workDepts) {
		this.workDepts = workDepts;
	}


	public Integer getSpecialUser() {
		return specialUser;
	}

	public void setSpecialUser(Integer specialUser) {
		this.specialUser = specialUser;
	}

	public String getUserViewStyle() {
		return userViewStyle;
	}

	public void setUserViewStyle(String userViewStyle) {
		this.userViewStyle = userViewStyle;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public UserInfo(Long id) {
		super();
		this.id = id;
	}
	public UserInfo() {
		
	}

	/**
	 * @Return the HashMap<String,UserMenuItem> userMenuItem
	 */
	public HashMap<String, List> getUserMenuItem() {
		return userMenuItem;
	}

	/**
	 * @Param HashMap<String,UserMenuItem> userMenuItem to set
	 */
	public void setUserMenuItem(HashMap<String, List> userMenuItem) {
		this.userMenuItem = userMenuItem;
	}
	
	/**
	 * ���ݸ��˵���ȡ�û��˵���
	 * @Methods Name getUserInfoMenuItemList
	 * @Create In Sep 26, 2008 By ����
	 * @param id
	 * @return List
	 */
	public List getUserInfoMenuItemList(String id){
		return this.userMenuItem.get(id);
	}

	public Integer getExternalFlag() {
		return externalFlag;
	}

	public void setExternalFlag(Integer externalFlag) {
		this.externalFlag = externalFlag;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public SameMailDept getSameMailDept() {
		return sameMailDept;
	}

	public void setSameMailDept(SameMailDept sameMailDept) {
		this.sameMailDept = sameMailDept;
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

	public MailServer getMailServer() {
		return mailServer;
	}

	public void setMailServer(MailServer mailServer) {
		this.mailServer = mailServer;
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

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	

}
