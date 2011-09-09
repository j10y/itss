package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpiitem2userCmp extends BaseModel {
	protected Long id;
	protected HrPaKpiPBC2UserCmp pbc2User;
	protected Long piId;
	protected double weight;
	protected double result;
	protected Double coefficient;
	
	public HrPaKpiitem2userCmp() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HrPaKpiPBC2UserCmp getPbc2User() {
		return pbc2User;
	}

	public void setPbc2User(HrPaKpiPBC2UserCmp pbc2User) {
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
