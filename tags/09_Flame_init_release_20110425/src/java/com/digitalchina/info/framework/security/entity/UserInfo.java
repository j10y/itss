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
 * 系统用户实体
 * @Class Name UserInfo
 * @Author peixf
 * @Create In 2008-3-5
 */
public class UserInfo extends BaseObject{
	private static final long serialVersionUID = 4119195113821846672L;
	
	private Long id;				//自动编号
	private String employeeCode;	//员工编号
	
	private String userName;		//用户名(itcode小写)
	private String password;		//用户密码
	private String realName;		//真实姓名
//	--------------------------------------------------
	private String itcode;			//用户ITCODE
	private String gender;			//用户性别
	private Long departCode;		//部门编号
	private Department department;	//所属部门
	private Platform platform;		//所属平台

	private Long titleCode;			//岗位编号
	//-------------------------------------------
	//private Department department;
	private Set workDepts = new HashSet();
	
	private Integer enabled;		//是否可用
	private Integer isLock;			//是否被锁定
	
	private String email;			//电子邮件
	private String telephone;		//联系电话
	private String mobilePhone;		//手机
	private String postCode;		//职位编号
	
	private Integer specialUser;	//是否特殊用户
	private Integer externalFlag;	//是否外部用户
	
	private SameMailDept sameMailDept;//邮件等价名部门
	private String costCenterCode;	//成本中心编号
	private WorkSpace workSpace;	//工作地点
	private MailServer mailServer;		//邮件服务器
	private UserType userType;		//员工身份类型
	private PersonnelScope personnelScope;	//人事子范围
	
	//private Integer isTemp;			//是否为临时用户--后期删除无用
	//private Integer	isAccredited;	//是否为派遣用户--后期删除无用
	
	private String userViewStyle;	//页面风格

	private Set userTableSettings = new HashSet();

	private HashMap<String,List> userMenuItem = new HashMap<String,List>();
	private Set roles = new HashSet(0);
	
	private Integer timeCard;
	//新家类型	
	private String postName;//职位名称，岗位名称
	
	private String costCenterName;//成本中心名称
	
	private Date joinDate;//入职日期
	
	private Date leaveDate;//离职日期
	
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
	 * 用户是否是特殊用户
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
	 * 用户是否可用
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
	 * 用户是否没有被锁定
	 * @Methods Name isUserNonLock
	 * @Create In 2009-3-13 By 张鹏
	 * @return boolean
	 * 用户是否被锁定的状态为: 0--没有被锁定 1--被锁定
	 */
	public boolean isUserNonLock(){
		boolean result = false;
		if(this.isLock != null&& this.isLock.intValue() == 0){
			result = true;
		}
		return result;
	}
	/**
	 * 返回用户唯一字段，具体根据项目需要到属性文件里配置system.user.uniquecolumn
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
	 * 根据父菜单获取用户菜单项
	 * @Methods Name getUserInfoMenuItemList
	 * @Create In Sep 26, 2008 By 张鹏
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
