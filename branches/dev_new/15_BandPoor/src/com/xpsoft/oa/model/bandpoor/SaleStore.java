package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

public class SaleStore extends BaseModel{
	private Long id;
	private String storeName;
	private String storeDesc;
	private  BusinessArea allowAreaId;
	private Double storeScore;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreDesc() {
		return storeDesc;
	}
	public void setStoreDesc(String storeDesc) {
		this.storeDesc = storeDesc;
	}
	public BusinessArea getAllowAreaId() {
		return allowAreaId;
	}
	public void setAllowAreaId(BusinessArea allowAreaId) {
		this.allowAreaId = allowAreaId;
	}
	public Double getStoreScore() {
		return storeScore;
	}
	public void setStoreScore(Double storeScore) {
		this.storeScore = storeScore;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((allowAreaId == null) ? 0 : allowAreaId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((storeDesc == null) ? 0 : storeDesc.hashCode());
		result = prime * result
				+ ((storeName == null) ? 0 : storeName.hashCode());
		result = prime * result
				+ ((storeScore == null) ? 0 : storeScore.hashCode());
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
		final SaleStore other = (SaleStore) obj;
		if (allowAreaId == null) {
			if (other.allowAreaId != null)
				return false;
		} else if (!allowAreaId.equals(other.allowAreaId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (storeDesc == null) {
			if (other.storeDesc != null)
				return false;
		} else if (!storeDesc.equals(other.storeDesc))
			return false;
		if (storeName == null) {
			if (other.storeName != null)
				return false;
		} else if (!storeName.equals(other.storeName))
			return false;
		if (storeScore == null) {
			if (other.storeScore != null)
				return false;
		} else if (!storeScore.equals(other.storeScore))
			return false;
		return true;
	}
	
	
}
