package com.zsgj.itil.config.extlist.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.config.extci.entity.DeliveryTeam;

public class SRServiceProvider extends BaseObject {
	private java.lang.Long id;
	private com.zsgj.itil.require.entity.SpecialRequirement specialRequire;
	private DeliveryTeam deliveryTeam;
	private com.zsgj.itil.config.extlist.entity.RequirementBigType bigType;
	private com.zsgj.itil.config.extlist.entity.RequirementSmallType smallType;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setSpecialRequire(
			com.zsgj.itil.require.entity.SpecialRequirement specialRequire) {
		this.specialRequire = specialRequire;
	}

	public void setBigType(
			com.zsgj.itil.config.extlist.entity.RequirementBigType bigType) {
		this.bigType = bigType;
	}

	public void setSmallType(
			com.zsgj.itil.config.extlist.entity.RequirementSmallType smallType) {
		this.smallType = smallType;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public com.zsgj.itil.require.entity.SpecialRequirement getSpecialRequire() {
		return this.specialRequire;
	}

	public com.zsgj.itil.config.extlist.entity.RequirementBigType getBigType() {
		return this.bigType;
	}

	public com.zsgj.itil.config.extlist.entity.RequirementSmallType getSmallType() {
		return this.smallType;
	}

	public DeliveryTeam getDeliveryTeam() {
		return deliveryTeam;
	}

	public void setDeliveryTeam(DeliveryTeam deliveryTeam) {
		this.deliveryTeam = deliveryTeam;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final com.zsgj.itil.config.extlist.entity.SRServiceProvider other = (com.zsgj.itil.config.extlist.entity.SRServiceProvider) obj;
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
