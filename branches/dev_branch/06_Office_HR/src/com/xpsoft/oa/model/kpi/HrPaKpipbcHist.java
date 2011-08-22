package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpipbcHist extends BaseModel {
	protected long id;
	protected String pbcName;
	protected long belongDept;
	protected long belongPost;
	protected long frequency;
	protected Date createDate;
	protected long createPerson;
	protected int publishStatus;
	protected int totalScore;
	protected Date modifyDate;
	protected long modifyPerson;
	protected long fromPbc;
	
	public HrPaKpipbcHist() {}

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

	public long getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(long belongDept) {
		this.belongDept = belongDept;
	}

	public long getBelongPost() {
		return belongPost;
	}

	public void setBelongPost(long belongPost) {
		this.belongPost = belongPost;
	}

	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(long createPerson) {
		this.createPerson = createPerson;
	}

	public int getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(int publishStatus) {
		this.publishStatus = publishStatus;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public long getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(long modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public long getFromPbc() {
		return fromPbc;
	}

	public void setFromPbc(long fromPbc) {
		this.fromPbc = fromPbc;
	}
}
