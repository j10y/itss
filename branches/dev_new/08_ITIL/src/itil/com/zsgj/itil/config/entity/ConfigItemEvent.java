package com.zsgj.itil.config.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
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
public class ConfigItemEvent extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -2265154414074439701L;
	public static int STATUS_DRAFT = 0;// �ݸ�
	public static int STATUS_FINISHED = 1;// ͨ��
	public static int STATUS_APPROVING = 2;// �ύ������
	
	public static int STATUS_DELETE = -1;// ��ɾ��

	private Long id; 
	//�����������
	private String name;
	private Long configItem;
	//�����ͻ�
	private Customer customer;
	//private Customer customer;
	
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
	
	private ConfigItem olderConfigItem;
	
	private Integer version;//�汾����
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((customer == null) ? 0 : customer.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
		final ConfigItemEvent other = (ConfigItemEvent) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Date getAcutalStopDate() {
		return acutalStopDate;
	}
	public void setAcutalStopDate(Date acutalStopDate) {
		this.acutalStopDate = acutalStopDate;
	}
	public Integer getAlterFlag() {
		return alterFlag;
	}
	public void setAlterFlag(Integer alterFlag) {
		this.alterFlag = alterFlag;
	}
	public Integer getAppFlag() {
		return appFlag;
	}
	public void setAppFlag(Integer appFlag) {
		this.appFlag = appFlag;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
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
	public ConfigItemFinanceInfo getFinanceInfo() {
		return financeInfo;
	}
	public void setFinanceInfo(ConfigItemFinanceInfo financeInfo) {
		this.financeInfo = financeInfo;
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
	public Long getOwner() {
		return owner;
	}
	public void setOwner(Long owner) {
		this.owner = owner;
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
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public Integer getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}
	public Long getConfigItem() {
		return configItem;
	}
	public void setConfigItem(Long configItem) {
		this.configItem = configItem;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public ConfigItem getOlderConfigItem() {
		return olderConfigItem;
	}
	public void setOlderConfigItem(ConfigItem olderConfigItem) {
		this.olderConfigItem = olderConfigItem;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getCisn() {
		return cisn;
	}
	public void setCisn(String cisn) {
		this.cisn = cisn;
	}
	
}
