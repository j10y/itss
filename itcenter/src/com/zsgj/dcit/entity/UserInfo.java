package com.zsgj.dcit.entity;

/**
 * 用户信息
 * @Class Name UserInfo
 * @Author lee
 * @Create In May 31, 2010
 */
public class UserInfo {
	private Long id;				//自动编号
	private String employeeCode;	//员工编号
	private String userName;		//用户名(itcode小写)
	private String realName;		//真实姓名
	private String itcode;			//用户ITCODE
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
}
