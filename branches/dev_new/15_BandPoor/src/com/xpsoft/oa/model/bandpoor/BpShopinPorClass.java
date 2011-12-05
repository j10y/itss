package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

public class BpShopinPorClass extends BaseModel{
	private Long id;
	
	private ProClass bpProClassId;
	
	private ShopinProClass shopinProClassId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProClass getBpProClassId() {
		return bpProClassId;
	}

	public void setBpProClassId(ProClass bpProClassId) {
		this.bpProClassId = bpProClassId;
	}

	public ShopinProClass getShopinProClassId() {
		return shopinProClassId;
	}

	public void setShopinProClassId(ShopinProClass shopinProClassId) {
		this.shopinProClassId = shopinProClassId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bpProClassId == null) ? 0 : bpProClassId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((shopinProClassId == null) ? 0 : shopinProClassId.hashCode());
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
		final BpShopinPorClass other = (BpShopinPorClass) obj;
		if (bpProClassId == null) {
			if (other.bpProClassId != null)
				return false;
		} else if (!bpProClassId.equals(other.bpProClassId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (shopinProClassId == null) {
			if (other.shopinProClassId != null)
				return false;
		} else if (!shopinProClassId.equals(other.shopinProClassId))
			return false;
		return true;
	}
	
	
}
