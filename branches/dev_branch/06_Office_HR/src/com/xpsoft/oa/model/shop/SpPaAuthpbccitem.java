package com.xpsoft.oa.model.shop;

import com.xpsoft.core.model.BaseModel;

public class SpPaAuthpbccitem extends BaseModel {
	protected Long id;
	protected SpPaAuthorizepbc authorPbc;
	protected Long akpiItem2uId;
	protected Double result;
	protected Double weight;
	
	public SpPaAuthpbccitem(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpPaAuthorizepbc getAuthorPbc() {
		return authorPbc;
	}

	public void setAuthorPbc(SpPaAuthorizepbc authorPbc) {
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

}
