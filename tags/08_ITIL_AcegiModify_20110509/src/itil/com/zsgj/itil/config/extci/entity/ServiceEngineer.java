package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 服务工程师
 * @Class Name ServiceEngineer
 * @Author duxh
 * @Create In Jul 2, 2010
 */
public class ServiceEngineer extends BaseObject {

	private static final long serialVersionUID = 1226716103987131611L;

	private Long id;
	
	private UserInfo userInfo; 

	private String employeeCode;
	
	private String email;
	
	private String phone;
	
	private String mobile;
	
    private Date startServiceDate;
    
    private Date endServiceDate;
    
	private String techAbility;
	
	private String cisn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getStartServiceDate() {
		return startServiceDate;
	}

	public void setStartServiceDate(Date startServiceDate) {
		this.startServiceDate = startServiceDate;
	}

	public Date getEndServiceDate() {
		return endServiceDate;
	}

	public void setEndServiceDate(Date endServiceDate) {
		this.endServiceDate = endServiceDate;
	}

	public String getTechAbility() {
		return techAbility;
	}

	public void setTechAbility(String techAbility) {
		this.techAbility = techAbility;
	}

	public String getCisn() {
		return cisn;
	}

	public void setCisn(String cisn) {
		this.cisn = cisn;
	}
	
}
