package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

public class MainPrice extends BaseModel{
	private Long id;
	
	private String priceName;
	
	private String priceDesc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public String getPriceDesc() {
		return priceDesc;
	}

	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((priceDesc == null) ? 0 : priceDesc.hashCode());
		result = prime * result
				+ ((priceName == null) ? 0 : priceName.hashCode());
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
		final MainPrice other = (MainPrice) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (priceDesc == null) {
			if (other.priceDesc != null)
				return false;
		} else if (!priceDesc.equals(other.priceDesc))
			return false;
		if (priceName == null) {
			if (other.priceName != null)
				return false;
		} else if (!priceName.equals(other.priceName))
			return false;
		return true;
	}
	
	
}
