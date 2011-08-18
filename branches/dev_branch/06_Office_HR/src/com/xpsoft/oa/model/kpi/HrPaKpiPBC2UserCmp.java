package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class HrPaKpiPBC2UserCmp extends BaseModel {
	protected long id;
	protected String pbcName;
	protected String fromPBC;//此处存放来源PBC的ID数组，以“,”分割。
	protected AppUser belongUser;
	protected HrPaDatadictionary frequency;
	protected AppUser createPerson;
	protected Date createDate;
	protected int publishStatus;
	protected float totalScore;
	protected AppUser modifyPerson;
	protected Date modifyDate;
	
	public HrPaKpiPBC2UserCmp() {}

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

	public HrPaDatadictionary getFrequency() {
		return frequency;
	}

	public void setFrequency(HrPaDatadictionary frequency) {
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
}
