package com.zsgj.dcit.entity;

/**
 * 服务项
 * @Class Name ServiceItem
 * @Author lee
 * @Create In May 31, 2010
 */
public class ServiceItem {
	private Long id;
	//服务编号
	private String serviceItemCode;
	//服务名称
	private String name;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceItemCode() {
		return serviceItemCode;
	}

	public void setServiceItemCode(String serviceItemCode) {
		this.serviceItemCode = serviceItemCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((serviceItemCode == null) ? 0 : serviceItemCode.hashCode());
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
		final ServiceItem other = (ServiceItem) obj;
		if (serviceItemCode == null) {
			if (other.serviceItemCode != null)
				return false;
		} else if (!serviceItemCode.equals(other.serviceItemCode))
			return false;
		return true;
	}

}	
