package com.zsgj.itil.config.entity;

import java.util.Date;

import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.CustomerType;
import com.zsgj.itil.service.entity.ServiceType;

/**
 * ��Ҫ��¼���ʲ���������Ϣ�������
 * @Class Name ConfigItem
 * @Author sa
 * @Create In 2008-10-20
 */
@SuppressWarnings("serial")
public class ConfigItem extends CIBaseData{
	
	public static final Integer HISTORY_STATUS= 3;//������ʷ
	public static final Integer DRAFT_STATUS= 0;//�ݸ�
	public static final Integer VALID_STATUS = 1;// ��ʽ
	public static final Integer DELETE_STATUS = -1;// ��ɾ��(�߼�ɾ��)
	
	private Long id; 
	//�����������
	private String name;
	
	//�����ͻ�
	private Customer customer;
	
	//�ͻ�����
	private CustomerType customerType;
	
	//�����ߣ��ڲ������߹����ڲ��ͻ���������ʾ
	private Long owner; 
	
	//������
	private UserInfo principal; 
	
	//��������
	private Date buyDate; 
	//ʹ������
	private Date useDate;
	//���ñ�ʶ
	private Integer useFlag; 
	//Ԥ��ֹͣ����
	private Date preStopDate; 
	//Ԥ��ֹͣ����
	private Date acutalStopDate; 
	
	//������״̬
	private ConfigItemStatus configItemStatus;
	//��������������
	private Environment environment;
	
	//����������, ����ѡ������;��������������������Ϣ
	private ConfigItemType configItemType;
	//�������������ͱ������
	private Long typeTableId;
	//������Ϣ
	private ConfigItemFinanceInfo financeInfo;
	
	//�Ƿ�Ӧ�ñ�ʶ
	private Integer appFlag;
	//�Ƿ������豸��ʶ
	private Integer tenancyFlag;
	//�����ʶ
	private Integer serviceFlag;
	//�������
	private ServiceType serviceType;
	//��ע
	private String descn;
	//ɾ����ʶ
	private Integer deleteFlag;
	
	private Integer alterFlag;
	//������״̬λ������ʹ��
	private Integer status;
	
	private String cisn;
	
	
    private UserInfo createUser;
    private Date createDate;
    private UserInfo modifyUser;
    private Date modifyDate;
    
	public UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public UserInfo getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCisn() {
		return cisn;
	}

	public void setCisn(String cisn) {
		this.cisn = cisn;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cisn == null) ? 0 : cisn.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ConfigItem other = (ConfigItem) obj;
		if (cisn == null) {
			if (other.cisn != null)
				return false;
		} else if (!cisn.equals(other.cisn))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}

	public Date getAcutalStopDate() {
		return acutalStopDate;
	}

	public void setAcutalStopDate(Date acutalStopDate) {
		this.acutalStopDate = acutalStopDate;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Date getPreStopDate() {
		return preStopDate;
	}

	public void setPreStopDate(Date preStopDate) {
		this.preStopDate = preStopDate;
	}

	public UserInfo getPrincipal() {
		return principal;
	}

	public void setPrincipal(UserInfo principal) {
		this.principal = principal;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}


	public Integer getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(Integer appFlag) {
		this.appFlag = appFlag;
	}

	public ConfigItemStatus getConfigItemStatus() {
		return configItemStatus;
	}

	public void setConfigItemStatus(ConfigItemStatus configItemStatus) {
		this.configItemStatus = configItemStatus;
	}

	public ConfigItemType getConfigItemType() {
		return configItemType;
	}

	public void setConfigItemType(ConfigItemType configItemType) {
		this.configItemType = configItemType;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Integer getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(Integer serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTenancyFlag() {
		return tenancyFlag;
	}

	public void setTenancyFlag(Integer tenancyFlag) {
		this.tenancyFlag = tenancyFlag;
	}

	public Long getTypeTableId() {
		return typeTableId;
	}

	public void setTypeTableId(Long typeTableId) {
		this.typeTableId = typeTableId;
	}


	public ConfigItemFinanceInfo getFinanceInfo() {
		return financeInfo;
	}

	public void setFinanceInfo(ConfigItemFinanceInfo financeInfo) {
		this.financeInfo = financeInfo;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public Integer getAlterFlag() {
		return alterFlag;
	}

	public void setAlterFlag(Integer alterFlag) {
		this.alterFlag = alterFlag;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
