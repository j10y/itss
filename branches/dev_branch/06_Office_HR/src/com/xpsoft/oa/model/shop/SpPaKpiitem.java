package com.xpsoft.oa.model.shop;

import com.xpsoft.core.model.BaseModel;

public class SpPaKpiitem extends BaseModel {
	protected long id;
	protected SpPaKpipbc pbc;
	protected SpPaPerformanceindex pi;
	protected double weight;
	protected double result;
	protected Double coefficient;
	
	public SpPaKpiitem(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SpPaKpipbc getPbc() {
		return pbc;
	}

	public void setPbc(SpPaKpipbc pbc) {
		this.pbc = pbc;
	}

	public SpPaPerformanceindex getPi() {
		return pi;
	}

	public void setPi(SpPaPerformanceindex pi) {
		this.pi = pi;
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
