package com.xpsoft.oa.model.hrm;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class IncomeTaxItem extends BaseModel{
	
	@Expose
	protected Long itiId;
	@Expose
	protected IncomeTax incomeTax;
	@Expose
	protected BigDecimal limitAmount;
	@Expose
	protected BigDecimal lowerAmount;
	@Expose
	protected BigDecimal taxValue;

	@Expose
	protected BigDecimal deductValue;

	public IncomeTax getIncomeTax() {
		return incomeTax;
	}
	public void setIncomeTax(IncomeTax incomeTax) {
		this.incomeTax = incomeTax;
	}
	public Long getItiId() {
		return itiId;
	}
	public void setItiId(Long itiId) {
		this.itiId = itiId;
	}
	public BigDecimal getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}
	public BigDecimal getLowerAmount() {
		return lowerAmount;
	}
	public void setLowerAmount(BigDecimal lowerAmount) {
		this.lowerAmount = lowerAmount;
	}
	public BigDecimal getTaxValue() {
		return taxValue;
	}
	public void setTaxValue(BigDecimal taxValue) {
		this.taxValue = taxValue;
	}
	
	
	public BigDecimal getDeductValue() {
		return deductValue;
	}
	public void setDeductValue(BigDecimal deductValue) {
		this.deductValue = deductValue;
	}
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
	       .append(this.itiId)
	       .append(this.incomeTax)
	       .append(this.limitAmount)
	       .append(this.lowerAmount)
	       .append(this.taxValue)
	       .append(this.deductValue)
	       .toHashCode();
	}
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof IncomeTaxItem)) {
			return false;
		}
		IncomeTaxItem rhs = (IncomeTaxItem)object;
		return new EqualsBuilder()
			.append(this.itiId, rhs.itiId)
			.append(this.incomeTax, rhs.incomeTax)
			.append(this.limitAmount, rhs.limitAmount)
			.append(this.lowerAmount, rhs.lowerAmount)
			.append(this.taxValue, rhs.taxValue)
			.append(this.deductValue, rhs.deductValue)
			.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("itiId", this.itiId)
			.append("incomeTax", this.incomeTax)
			.append("limitAmount", this.limitAmount)
			.append("lowerAmount", this.lowerAmount)
			.append("taxValue", this.taxValue)
			.append("deductValue", this.deductValue)
			.toString();
	}

	
}
