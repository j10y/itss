package com.zsgj.dcit.entity;

/**
 * �û���Ϣ
 * @Class Name UserInfo
 * @Author lee
 * @Create In May 31, 2010
 */
public class UserInfo {
	private Long id;				//�Զ����
	private String employeeCode;	//Ա�����
	private String userName;		//�û���(itcodeСд)
	private String realName;		//��ʵ����
	private String itcode;			//�û�ITCODE
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
