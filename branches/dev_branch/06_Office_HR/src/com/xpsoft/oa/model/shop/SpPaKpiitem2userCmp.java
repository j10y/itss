package com.xpsoft.oa.model.shop;

import com.xpsoft.core.model.BaseModel;

public class SpPaKpiitem2userCmp extends BaseModel {
	protected Long id;
	protected SpPaKpiPBC2UserCmp pbc2User;
	protected Long piId;
	protected double weight;
	protected double result;
	protected Double coefficient;
	
	public SpPaKpiitem2userCmp() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpPaKpiPBC2UserCmp getPbc2User() {
		return pbc2User;
	}

	public void setPbc2User(SpPaKpiPBC2UserCmp pbc2User) {
		this.pbc2User = pbc2User;
	}

	public Long getPiId() {
		return piId;
	}

	public void setPiId(Long piId) {
		this.piId = piId;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

}
