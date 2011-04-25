package com.digitalchina.info.appframework.template.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/*
 * 合同数据项
 */
public class Contract extends BaseObject {
	private static final long serialVersionUID = -2178857523753983644L;

	private Long id; 
	private Template template;
	private Double contractSum;
	
	protected Double fee; //费用=售前销售费用+售前工程费用+售前支持奖+事业部管理费用分摊+售中销售费用
	protected Double beforeSaleFee; //售前销售费用
	protected Double beforeWorkFee; //售前工程费用
	protected Double beforeSalesTechnicFee; //售前支持奖, 注意供货类没有此属性
	protected Double manageFee; //事业部管理费用分摊

	protected Double middleSalesFee; //售中销售费用=差旅费+招待费+其他
	protected Double tripfee; //拆旅费
	protected Double giftfee; //销售_招待费  
	protected Double otherfee; //其他
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public Double getContractSum() {
		return contractSum;
	}
	public void setContractSum(Double contractSum) {
		this.contractSum = contractSum;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Double getBeforeSaleFee() {
		return beforeSaleFee;
	}
	public void setBeforeSaleFee(Double beforeSaleFee) {
		this.beforeSaleFee = beforeSaleFee;
	}
	public Double getBeforeWorkFee() {
		return beforeWorkFee;
	}
	public void setBeforeWorkFee(Double beforeWorkFee) {
		this.beforeWorkFee = beforeWorkFee;
	}
	public Double getBeforeSalesTechnicFee() {
		return beforeSalesTechnicFee;
	}
	public void setBeforeSalesTechnicFee(Double beforeSalesTechnicFee) {
		this.beforeSalesTechnicFee = beforeSalesTechnicFee;
	}
	public Double getManageFee() {
		return manageFee;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	public Double getMiddleSalesFee() {
		return middleSalesFee;
	}
	public void setMiddleSalesFee(Double middleSalesFee) {
		this.middleSalesFee = middleSalesFee;
	}
	public Double getTripfee() {
		return tripfee;
	}
	public void setTripfee(Double tripfee) {
		this.tripfee = tripfee;
	}
	public Double getGiftfee() {
		return giftfee;
	}
	public void setGiftfee(Double giftfee) {
		this.giftfee = giftfee;
	}
	public Double getOtherfee() {
		return otherfee;
	}
	public void setOtherfee(Double otherfee) {
		this.otherfee = otherfee;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beforeSaleFee == null) ? 0 : beforeSaleFee.hashCode());
		result = prime
				* result
				+ ((beforeSalesTechnicFee == null) ? 0 : beforeSalesTechnicFee
						.hashCode());
		result = prime * result
				+ ((beforeWorkFee == null) ? 0 : beforeWorkFee.hashCode());
		result = prime * result
				+ ((contractSum == null) ? 0 : contractSum.hashCode());
		result = prime * result + ((fee == null) ? 0 : fee.hashCode());
		result = prime * result + ((giftfee == null) ? 0 : giftfee.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((manageFee == null) ? 0 : manageFee.hashCode());
		result = prime * result
				+ ((middleSalesFee == null) ? 0 : middleSalesFee.hashCode());
		result = prime * result
				+ ((otherfee == null) ? 0 : otherfee.hashCode());
		result = prime * result
				+ ((template == null) ? 0 : template.hashCode());
		result = prime * result + ((tripfee == null) ? 0 : tripfee.hashCode());
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
		final Contract other = (Contract) obj;
		if (beforeSaleFee == null) {
			if (other.beforeSaleFee != null)
				return false;
		} else if (!beforeSaleFee.equals(other.beforeSaleFee))
			return false;
		if (beforeSalesTechnicFee == null) {
			if (other.beforeSalesTechnicFee != null)
				return false;
		} else if (!beforeSalesTechnicFee.equals(other.beforeSalesTechnicFee))
			return false;
		if (beforeWorkFee == null) {
			if (other.beforeWorkFee != null)
				return false;
		} else if (!beforeWorkFee.equals(other.beforeWorkFee))
			return false;
		if (contractSum == null) {
			if (other.contractSum != null)
				return false;
		} else if (!contractSum.equals(other.contractSum))
			return false;
		if (fee == null) {
			if (other.fee != null)
				return false;
		} else if (!fee.equals(other.fee))
			return false;
		if (giftfee == null) {
			if (other.giftfee != null)
				return false;
		} else if (!giftfee.equals(other.giftfee))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (manageFee == null) {
			if (other.manageFee != null)
				return false;
		} else if (!manageFee.equals(other.manageFee))
			return false;
		if (middleSalesFee == null) {
			if (other.middleSalesFee != null)
				return false;
		} else if (!middleSalesFee.equals(other.middleSalesFee))
			return false;
		if (otherfee == null) {
			if (other.otherfee != null)
				return false;
		} else if (!otherfee.equals(other.otherfee))
			return false;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		if (tripfee == null) {
			if (other.tripfee != null)
				return false;
		} else if (!tripfee.equals(other.tripfee))
			return false;
		return true;
	}
	

		
}
