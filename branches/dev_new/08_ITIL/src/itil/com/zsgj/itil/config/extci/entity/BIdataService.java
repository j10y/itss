package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class BIdataService extends BaseObject {
	/**
	 * @Field long serialVersionUID
	 */
	private static final long serialVersionUID = -7364255079145383819L;
	private java.lang.Long id;
	private java.lang.String biDataInterface;
	private com.zsgj.itil.config.extlist.entity.BIServiceType serviceType;
	private java.util.Date goliveDate;
	private java.lang.String description;
	private java.lang.String cisn;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setBiDataInterface(java.lang.String biDataInterface) {
		this.biDataInterface = biDataInterface;
	}

	public void setServiceType(
			com.zsgj.itil.config.extlist.entity.BIServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public void setGoliveDate(java.util.Date goliveDate) {
		this.goliveDate = goliveDate;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public void setCisn(java.lang.String cisn) {
		this.cisn = cisn;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getBiDataInterface() {
		return this.biDataInterface;
	}

	public com.zsgj.itil.config.extlist.entity.BIServiceType getServiceType() {
		return this.serviceType;
	}

	public java.util.Date getGoliveDate() {
		return this.goliveDate;
	}

	public java.lang.String getDescription() {
		return this.description;
	}

	public java.lang.String getCisn() {
		return this.cisn;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final com.zsgj.itil.config.extci.entity.BIdataService other = (com.zsgj.itil.config.extci.entity.BIdataService) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
