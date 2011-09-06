package com.xpsoft.oa.model.hrm;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class HrPromApply extends BaseModel{
	protected Long id;
	protected AppUser applyUser;
	protected Job applyPosition;
	protected Date applyDate;
	protected String applyReason;
	protected String target1;
	protected String target2;
	protected String target3;
	protected String intRecord;
	protected Long postManagerId;
	protected String postManagerName;
	protected Integer publishStatus;
	protected AppUser createPerson;
	protected Date createDate;
	protected AppUser modifyPerson;
	protected Date modifyDate;
	
	public HrPromApply() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUser getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(AppUser applyUser) {
		this.applyUser = applyUser;
	}

	public Job getApplyPosition() {
		return applyPosition;
	}

	public void setApplyPosition(Job applyPosition) {
		this.applyPosition = applyPosition;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getTarget1() {
		return target1;
	}

	public void setTarget1(String target1) {
		this.target1 = target1;
	}

	public String getTarget2() {
		return target2;
	}

	public void setTarget2(String target2) {
		this.target2 = target2;
	}

	public String getTarget3() {
		return target3;
	}

	public void setTarget3(String target3) {
		this.target3 = target3;
	}

	public String getIntRecord() {
		return intRecord;
	}

	public void setIntRecord(String intRecord) {
		this.intRecord = intRecord;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
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

	public Long getPostManagerId() {
		return postManagerId;
	}

	public void setPostManagerId(Long postManagerId) {
		this.postManagerId = postManagerId;
	}

	public String getPostManagerName() {
		return postManagerName;
	}

	public void setPostManagerName(String postManagerName) {
		this.postManagerName = postManagerName;
	}
	
}
