package com.digitalchina.itil.config.extci.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
/**
 * 服务商
 * @Class Name ServiceProvider
 * @Author duxh
 * @Create In Jul 2, 2010
 */
public class ServiceProvider extends BaseObject {
	
	public static final Integer ServiceProvider_IN=1;//内部
	public static final Integer ServiceProvider_OUT=0;//外部
	
	private static final long serialVersionUID = -967577835324303858L;
	
	private java.lang.Long id;
	
	private Integer isIn;//类型（内或外）
	
	private ServiceProviderType serviceProviderType;
	
    private String name;
    
    private String postcode;//邮编
    
    private String address;//地址
    
    private String bank;//开户行
    
    private String bankAccount;//银行账号
    
    private String principal;//负责人
    
    private String contactUser;//接口人
    
    private String telNumber;
    
    private String email;
    
    private String services;//服务领域
    
    private Date startServiceDate;//开始服务时间
    
    private Date endServiceDate;//结束服务时间
    
    private String cisn;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public Integer getIsIn() {
		return isIn;
	}

	public void setIsIn(Integer isIn) {
		this.isIn = isIn;
	}

	public ServiceProviderType getServiceProviderType() {
		return serviceProviderType;
	}

	public void setServiceProviderType(ServiceProviderType serviceProviderType) {
		this.serviceProviderType = serviceProviderType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getCisn() {
		return cisn;
	}

	public void setCisn(String cisn) {
		this.cisn = cisn;
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

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
