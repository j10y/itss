package com.zsgj.itil.service.entity;
//package com.zsgj.itil.service.entity;
//
//import com.zsgj.info.framework.dao.BaseObject;
//import com.zsgj.itil.actor.entity.Customer;
//
///**
// * 服务目录与客户的关系实体<br>
// * 客户需要能够查询自己订购的服务的信息，服务目录需要和客户进行挂钩
// * @Class Name ServiceCataCustomer
// * @Author sa
// * @Create In 2008-10-21
// */
//public class ServiceCataCustomer extends BaseObject {
//	private Long id;
//	private ServiceCatalogue sc;
//	private Customer customer;
//	public Customer getCustomer() {
//		return customer;
//	}
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public ServiceCatalogue getSc() {
//		return sc;
//	}
//	public void setSc(ServiceCatalogue sc) {
//		this.sc = sc;
//	}
//	@Override
//	public int hashCode() {
//		final int PRIME = 31;
//		int result = super.hashCode();
//		result = PRIME * result + ((customer == null) ? 0 : customer.hashCode());
//		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
//		result = PRIME * result + ((sc == null) ? 0 : sc.hashCode());
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (!super.equals(obj))
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		final ServiceCataCustomer other = (ServiceCataCustomer) obj;
//		if (customer == null) {
//			if (other.customer != null)
//				return false;
//		} else if (!customer.equals(other.customer))
//			return false;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (sc == null) {
//			if (other.sc != null)
//				return false;
//		} else if (!sc.equals(other.sc))
//			return false;
//		return true;
//	}
//	
//}
