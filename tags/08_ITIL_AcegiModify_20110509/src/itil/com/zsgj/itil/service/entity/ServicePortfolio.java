package com.zsgj.itil.service.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.ServiceManager;

/**
 * 服务组合。
 * 主要反映了一个服务的生命周期，从开始筹划到设计、使用以及最后的退出。
 * 所有的服务，都必须在服务组合中。
 * 比如神码是一套服务组合，华为是另一套服务组合，涉及的配置项就不一样了
 * @Class Name ServicePortfolio
 * @Author sa
 * @Create In 2008-11-9
 * @Deprecated in 20091121 by lee
 */
@Deprecated
public class ServicePortfolio extends BaseObject {
	private static final long serialVersionUID = 3509423524048693774L;
	private Long id;
	//服务组合名称，比如神码
	private String name;
	//集团客户
	private Customer dcCustomer;
	//服务管理者
	private ServiceManager serviceManager;
	//起始日期
	private Date beginDate;
	//服务的状态
	private ServiceStatus serviceStatus;
	//服务组合说明
	private String descn;
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((dcCustomer == null) ? 0 : dcCustomer.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ServicePortfolio other = (ServicePortfolio) obj;
		if (dcCustomer == null) {
			if (other.dcCustomer != null)
				return false;
		} else if (!dcCustomer.equals(other.dcCustomer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Customer getDcCustomer() {
		return dcCustomer;
	}
	public void setDcCustomer(Customer dcCustomer) {
		this.dcCustomer = dcCustomer;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
