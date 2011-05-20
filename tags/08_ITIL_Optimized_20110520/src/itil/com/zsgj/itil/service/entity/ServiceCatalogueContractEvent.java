package com.zsgj.itil.service.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.CustomerType;
/**
 * 服务目录合同实体
 * 时间，合同金额，买卖双方，服务条款说明，双方签订日期，双方负责人
 * @Class Name ServiceCatalogueContract
 * @Author sa
 * @Create In 2009-2-27

 */
@SuppressWarnings("serial")
public class ServiceCatalogueContractEvent extends BaseObject {
	private Long id;
	//所属服务目录，隐藏域
	private ServiceCatalogueEvent serviceCatalogueEvent;
	//合同编号
	private String contractCode;
	//合同名称
	private String contractName;
//	合同金额
	private Double contractPrice;
//	服务条款说明
	private String descn;
	
	//服务起始日期
	private Date serviceBeginDate;
	//服务终止日期
	private Date serviceEndDate;

	
	//客户ID，隐藏域，从服务目录中带出
	private Long customerId;
	//客户类型，隐藏域，从服务目录中带出
	private CustomerType customerType;
	
	//客户名称，用于合同显示，也是从服务目录的客户默认带出名称显示
	private String customerName;
	
	//合同我方签署日期
	private Date signDate;
	//合同客户方签署日期
	private Date custSignDate;
	
	//服务管理者负责人
	private UserInfo principal; 
	//服务管理者负责人联系电话
	private String tel;
	//服务管理者负责人手机
	private String phone;
	//服务管理者负责人邮件
	private String email;
	
	
	//客户方负责人
	private UserInfo custPrincipal; 
	//客户方负责人联系电话
	private String custTel;
	//客户方负责人手机
	private String custPhone;
	//客户方负责人邮件
	private String custEmail;
	
	
	
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Double getContractPrice() {
		return contractPrice;
	}
	public void setContractPrice(Double contractPrice) {
		this.contractPrice = contractPrice;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ServiceCatalogueEvent getServiceCatalogueEvent() {
		return serviceCatalogueEvent;
	}
	public void setServiceCatalogueEvent(ServiceCatalogueEvent serviceCatalogueEvent) {
		this.serviceCatalogueEvent = serviceCatalogueEvent;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getCustSignDate() {
		return custSignDate;
	}
	public void setCustSignDate(Date custSignDate) {
		this.custSignDate = custSignDate;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public UserInfo getCustPrincipal() {
		return custPrincipal;
	}
	public void setCustPrincipal(UserInfo custPrincipal) {
		this.custPrincipal = custPrincipal;
	}
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
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
	public UserInfo getPrincipal() {
		return principal;
	}
	public void setPrincipal(UserInfo principal) {
		this.principal = principal;
	}
	public Date getServiceBeginDate() {
		return serviceBeginDate;
	}
	public void setServiceBeginDate(Date serviceBeginDate) {
		this.serviceBeginDate = serviceBeginDate;
	}
	public Date getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
