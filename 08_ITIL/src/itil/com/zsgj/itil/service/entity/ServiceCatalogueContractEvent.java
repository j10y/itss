package com.zsgj.itil.service.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.CustomerType;
/**
 * ����Ŀ¼��ͬʵ��
 * ʱ�䣬��ͬ������˫������������˵����˫��ǩ�����ڣ�˫��������
 * @Class Name ServiceCatalogueContract
 * @Author sa
 * @Create In 2009-2-27

 */
@SuppressWarnings("serial")
public class ServiceCatalogueContractEvent extends BaseObject {
	private Long id;
	//��������Ŀ¼��������
	private ServiceCatalogueEvent serviceCatalogueEvent;
	//��ͬ���
	private String contractCode;
	//��ͬ����
	private String contractName;
//	��ͬ���
	private Double contractPrice;
//	��������˵��
	private String descn;
	
	//������ʼ����
	private Date serviceBeginDate;
	//������ֹ����
	private Date serviceEndDate;

	
	//�ͻ�ID�������򣬴ӷ���Ŀ¼�д���
	private Long customerId;
	//�ͻ����ͣ������򣬴ӷ���Ŀ¼�д���
	private CustomerType customerType;
	
	//�ͻ����ƣ����ں�ͬ��ʾ��Ҳ�Ǵӷ���Ŀ¼�Ŀͻ�Ĭ�ϴ���������ʾ
	private String customerName;
	
	//��ͬ�ҷ�ǩ������
	private Date signDate;
	//��ͬ�ͻ���ǩ������
	private Date custSignDate;
	
	//��������߸�����
	private UserInfo principal; 
	//��������߸�������ϵ�绰
	private String tel;
	//��������߸������ֻ�
	private String phone;
	//��������߸������ʼ�
	private String email;
	
	
	//�ͻ���������
	private UserInfo custPrincipal; 
	//�ͻ�����������ϵ�绰
	private String custTel;
	//�ͻ����������ֻ�
	private String custPhone;
	//�ͻ����������ʼ�
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
