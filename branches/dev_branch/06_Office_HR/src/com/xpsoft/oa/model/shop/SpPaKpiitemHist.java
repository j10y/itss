package com.xpsoft.oa.model.shop;

import com.xpsoft.core.model.BaseModel;

public class SpPaKpiitemHist extends BaseModel {
	protected long id;
	protected SpPaKpipbcHist pbc;
	protected long piId;
	protected Double weight;
	protected Double result;
	protected Double coefficient;
	
	public SpPaKpiitemHist() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SpPaKpipbcHist getPbc() {
		return pbc;
	}

	public void setPbc(SpPaKpipbcHist pbc) {
		this.pbc = pbc;
	}

	public long getPiId() {
		return piId;
	}

	public void setPiId(long piId) {
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
