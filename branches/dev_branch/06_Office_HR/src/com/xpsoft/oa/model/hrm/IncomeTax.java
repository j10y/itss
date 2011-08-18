package com.xpsoft.oa.model.hrm;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

public class IncomeTax extends BaseModel{
	
	@Expose
	protected Long itId;
	@Expose
	protected String name;
	@Expose
	protected Date beginDate;
	@Expose
	protected Date endDate;
	@Expose
	protected String remark;
	@Expose
	protected Date publishDate;
	@Expose
	protected AppUser publishPerson;
	@Expose
	protected BigDecimal basicAmount;

	public BigDecimal getBasicAmount() {
		return basicAmount;
	}
	public void setBasicAmount(BigDecimal basicAmount) {
		this.basicAmount = basicAmount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getItId() {
		return itId;
	}
	public void setItId(Long itId) {
		this.itId = itId;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public AppUser getPublishPerson() {
		return publishPerson;
	}
	public void setPublishPerson(AppUser publishPerson) {
		this.publishPerson = publishPerson;
	}
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
	       .append(this.itId)
	       .append(this.name)
	       .append(this.beginDate)
	       .append(this.endDate)
	       .append(this.remark)
	       .append(this.publishDate)
	       .append(this.publishPerson)
	       .append(this.remark)
	       .append(this.basicAmount)
	       .toHashCode();
	}
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof IncomeTax)) {
			return false;
		}
		IncomeTax rhs = (IncomeTax)object;
		return new EqualsBuilder()
			.append(this.itId, rhs.itId)
			.append(this.name, rhs.name)
			.append(this.beginDate, rhs.beginDate)
			.append(this.endDate, rhs.endDate)
			.append(this.remark, rhs.remark)
			.append(this.basicAmount, rhs.basicAmount)
			.append(this.publishDate, rhs.publishDate)
			.append(this.publishPerson, rhs.publishPerson)
			.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("itId", this.itId)
			.append("name", this.name)
			.append("beginDate", this.beginDate)
			.append("endDate", this.endDate)
			.append("publishDate", this.publishDate)
			.append("publishPerson", this.publishPerson)
			.append("remark", this.remark)
			.append("basicAmount", this.basicAmount)
			.toString();
	}

	
}
