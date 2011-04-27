package com.zsgj.itil.config.entity;

import java.util.Date;

import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.CustomerType;
import com.zsgj.itil.service.entity.ServiceType;

/**
 * 需要记录的资产和配置信息（配置项）
 * @Class Name ConfigItem
 * @Author sa
 * @Create In 2008-10-20
 */
@SuppressWarnings("serial")
public class ConfigItem extends CIBaseData{
	
	public static final Integer HISTORY_STATUS= 3;//申请历史
	public static final Integer DRAFT_STATUS= 0;//草稿
	public static final Integer VALID_STATUS = 1;// 正式
	public static final Integer DELETE_STATUS = -1;// 已删除(逻辑删除)
	
	private Long id; 
	//配置项的名称
	private String name;
	
	//隶属客户
	private Customer customer;
	
	//客户类型
	private CustomerType customerType;
	
	//所有者，内部所有者关联内部客户，否则不显示
	private Long owner; 
	
	//负责人
	private UserInfo principal; 
	
	//购置日期
	private Date buyDate; 
	//使用日期
	private Date useDate;
	//在用标识
	private Integer useFlag; 
	//预计停止日期
	private Date preStopDate; 
	//预计停止日期
	private Date acutalStopDate; 
	
	//配置项状态
	private ConfigItemStatus configItemStatus;
	//配置项所属环境
	private Environment environment;
	
	//配置项类型, 根据选择的类型决定配置项的其他关联信息
	private ConfigItemType configItemType;
	//关联配置项类型表的主键
	private Long typeTableId;
	//财务信息
	private ConfigItemFinanceInfo financeInfo;
	
	//是否应用标识
	private Integer appFlag;
	//是否租赁设备标识
	private Integer tenancyFlag;
	//服务标识
	private Integer serviceFlag;
	//服务分类
	private ServiceType serviceType;
	//备注
	private String descn;
	//删除标识
	private Integer deleteFlag;
	
	private Integer alterFlag;
	//配置项状态位，程序使用
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
