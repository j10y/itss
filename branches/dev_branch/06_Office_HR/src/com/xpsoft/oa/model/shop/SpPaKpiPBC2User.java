package com.xpsoft.oa.model.shop;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class SpPaKpiPBC2User extends BaseModel {
	protected long id;
	protected String pbcName;
	protected String fromPBC;//此处存放来源PBC的ID数组，以“,”分割。
	protected AppUser belongUser;
	protected SpPaDatadictionary frequency;
	protected AppUser createPerson;
	protected Date createDate;
	protected int publishStatus;
	protected float totalScore;
	protected AppUser modifyPerson;
	protected Date modifyDate;
	protected Double coefficient;
	protected Long fromPi;
	protected Long pbcType;
	
	public SpPaKpiPBC2User() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPbcName() {
		return pbcName;
	}

	public void setPbcName(String pbcName) {
		this.pbcName = pbcName;
	}

	public String getFromPBC() {
		return fromPBC;
	}

	public void setFromPBC(String fromPBC) {
		this.fromPBC = fromPBC;
	}

	public AppUser getBelongUser() {
		return belongUser;
	}

	public void setBelongUser(AppUser belongUser) {
		this.belongUser = belongUser;
	}

	public SpPaDatadictionary getFrequency() {
		return frequency;
	}

	public void setFrequency(SpPaDatadictionary frequency) {
		this.frequency = frequency;
	}

	public AppUser getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(AppUser createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(int publishStatus) {
		this.publishStatus = publishStatus;
	}

	public float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}

	public AppUser getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(AppUser modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public Long getFromPi() {
		return fromPi;
	}

	public void setFromPi(Long fromPi) {
		this.fromPi = fromPi;
	}

	public Long getPbcType() {
		return pbcType;
	}

	public void setPbcType(Long pbcType) {
		this.pbcType = pbcType;
	}
}
