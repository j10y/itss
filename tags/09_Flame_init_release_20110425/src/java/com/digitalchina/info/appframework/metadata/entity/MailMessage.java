package com.digitalchina.info.appframework.metadata.entity;


import org.springframework.mail.SimpleMailMessage;

public class MailMessage extends SimpleMailMessage {

	private Long id;
	
	private String personName;
	
	private Integer gender;
	
	private String customerDept;
	
	private String tel;
	
	private String email;
	
	private String companyName;
	
	private String url;
	
	private Object object;
	
	private String type;
	public MailMessage() {
		this.object=null;
		this.type="";
		this.id = 0l;
		this.personName ="";
		
		this.gender=0;
		
		this.customerDept="";
		
		this.tel="";
		
		this.email="";
		
		this.companyName="";
		
		this.url="";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getCustomerDept() {
		return customerDept;
	}

	public void setCustomerDept(String customerDept) {
		this.customerDept = customerDept;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
