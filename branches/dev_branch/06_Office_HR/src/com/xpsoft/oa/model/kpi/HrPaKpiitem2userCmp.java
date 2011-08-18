package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpiitem2userCmp extends BaseModel {
	protected long id;
	protected HrPaKpiPBC2UserCmp pbc2User;
	protected long piId;
	protected double weight;
	protected double result;
	
	public HrPaKpiitem2userCmp() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HrPaKpiPBC2UserCmp getPbc2User() {
		return pbc2User;
	}

	public void setPbc2User(HrPaKpiPBC2UserCmp pbc2User) {
		this.pbc2User = pbc2User;
	}

	public long getPiId() {
		return piId;
	}

	public void setPiId(long piId) {
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
}
