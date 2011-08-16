package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpiitem extends BaseModel {
	protected long id;
	protected HrPaKpipbc pbc;
	protected HrPaPerformanceindex pi;
	protected double weight;
	protected double result;
	
	public HrPaKpiitem(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
}
