package com.xpsoft.oa.model.kpi;


import com.xpsoft.core.model.BaseModel;

public class HrPaKpiitem extends BaseModel {
	protected Long id;
	protected HrPaKpipbc pbc;
	protected HrPaPerformanceindex pi;
	protected Double weight;
	protected Double result;
	protected Double coefficient;
	
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public HrPaKpiitem(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HrPaKpipbc getPbc() {
		return pbc;
	}

	public void setPbc(HrPaKpipbc pbc) {
		this.pbc = pbc;
	}

	public HrPaPerformanceindex getPi() {
		return pi;
	}

	public void setPi(HrPaPerformanceindex pi) {
		this.pi = pi;
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

	
}
