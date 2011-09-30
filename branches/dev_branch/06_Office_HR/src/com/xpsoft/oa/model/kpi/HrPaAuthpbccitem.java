package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaAuthpbccitem extends BaseModel {
	protected Long id;
	protected HrPaAuthorizepbc authorPbc;
	protected Long akpiItem2uId;
	protected Double result;
	protected Double weight;
	protected String remark;//实际完成情况
	
	public HrPaAuthpbccitem(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HrPaAuthorizepbc getAuthorPbc() {
		return authorPbc;
	}

	public void setAuthorPbc(HrPaAuthorizepbc authorPbc) {
		this.authorPbc = authorPbc;
	}

	public Long getAkpiItem2uId() {
		return akpiItem2uId;
	}

	public void setAkpiItem2uId(Long akpiItem2uId) {
		this.akpiItem2uId = akpiItem2uId;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
