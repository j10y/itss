package com.xpsoft.oa.model.bandpoor;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class SaleAssessmentForBP extends BaseModel{
	public final static Integer DELETE = new Integer(0);//删除
	public final static Integer CREATE = new Integer(1);//新建
	public final static Integer PASS = new Integer(2);//达成通过
	public final static Integer UNPASS = new Integer(3);//未达成
	
	private Long id;
	private BandPoor bpId;
	private String targetShop;
	private Double targetValue;
	private Double requireValue;
	private String targetShopTwo;
	private Double targetValueTwo;
	private Double requireValueTwo;
	
	private Integer status;
	private Date createDate;
	private AppUser createUser;
	private Integer bandRankValue;
	private Integer selBandRankValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BandPoor getBpId() {
		return bpId;
	}
	public void setBpId(BandPoor bpId) {
		this.bpId = bpId;
	}
	public Double getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(Double targetValue) {
		this.targetValue = targetValue;
	}
	public Double getRequireValue() {
		return requireValue;
	}
	public void setRequireValue(Double requireValue) {
		this.requireValue = requireValue;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public AppUser getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AppUser createUser) {
		this.createUser = createUser;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bpId == null) ? 0 : bpId.hashCode());
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result
				+ ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((requireValue == null) ? 0 : requireValue.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((targetValue == null) ? 0 : targetValue.hashCode());
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
		final SaleAssessmentForBP other = (SaleAssessmentForBP) obj;
		if (bpId == null) {
			if (other.bpId != null)
				return false;
		} else if (!bpId.equals(other.bpId))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requireValue == null) {
			if (other.requireValue != null)
				return false;
		} else if (!requireValue.equals(other.requireValue))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (targetValue == null) {
			if (other.targetValue != null)
				return false;
		} else if (!targetValue.equals(other.targetValue))
			return false;
		return true;
	}
	public Integer getBandRankValue() {
		return bandRankValue;
	}
	public void setBandRankValue(Integer bandRankValue) {
		this.bandRankValue = bandRankValue;
	}
	public Integer getSelBandRankValue() {
		return selBandRankValue;
	}
	public void setSelBandRankValue(Integer selBandRankValue) {
		this.selBandRankValue = selBandRankValue;
	}
	public String getTargetShop() {
		return targetShop;
	}
	public void setTargetShop(String targetShop) {
		this.targetShop = targetShop;
	}
	public String getTargetShopTwo() {
		return targetShopTwo;
	}
	public void setTargetShopTwo(String targetShopTwo) {
		this.targetShopTwo = targetShopTwo;
	}
	public Double getTargetValueTwo() {
		return targetValueTwo;
	}
	public void setTargetValueTwo(Double targetValueTwo) {
		this.targetValueTwo = targetValueTwo;
	}
	public Double getRequireValueTwo() {
		return requireValueTwo;
	}
	public void setRequireValueTwo(Double requireValueTwo) {
		this.requireValueTwo = requireValueTwo;
	}
	
}
