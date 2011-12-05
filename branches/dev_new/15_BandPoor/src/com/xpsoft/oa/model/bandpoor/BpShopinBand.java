package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

public class BpShopinBand extends BaseModel{
	private Long id;
	private Band bpBandId;
	private ShopinBand shopinBandId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Band getBpBandId() {
		return bpBandId;
	}
	public void setBpBandId(Band bpBandId) {
		this.bpBandId = bpBandId;
	}
	public ShopinBand getShopinBandId() {
		return shopinBandId;
	}
	public void setShopinBandId(ShopinBand shopinBandId) {
		this.shopinBandId = shopinBandId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bpBandId == null) ? 0 : bpBandId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((shopinBandId == null) ? 0 : shopinBandId.hashCode());
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
		final BpShopinBand other = (BpShopinBand) obj;
		if (bpBandId == null) {
			if (other.bpBandId != null)
				return false;
		} else if (!bpBandId.equals(other.bpBandId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (shopinBandId == null) {
			if (other.shopinBandId != null)
				return false;
		} else if (!shopinBandId.equals(other.shopinBandId))
			return false;
		return true;
	}
	
	
}
