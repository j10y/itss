package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpiitem2user extends BaseModel {
	protected Long id;
	protected HrPaKpiPBC2User pbc2User;
	protected Long piId;
	protected Double weight;
	protected Double result;
	protected Double coefficient;
	
	public HrPaKpiitem2user() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HrPaKpiPBC2User getPbc2User() {
		return pbc2User;
	}

	public void setPbc2User(HrPaKpiPBC2User pbc2User) {
		this.pbc2User = pbc2User;
	}

	public Long getPiId() {
		return piId;
	}

	public void setPiId(Long piId) {
		this.piId = piId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

}
