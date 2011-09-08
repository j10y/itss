package com.xpsoft.oa.model.hrm;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class HrPromApply extends BaseModel{
	protected Long id;
	protected AppUser applyUser;//晋升员工
	protected Date accessionTime;//入职时间
	protected Long depId;//所属部门ID
	protected String depName;//所属部门名称
	protected Long nowPositionId;//现职位ID
	protected String nowPositionName;//现职位名称
	protected String workYear;//工作年限
	protected String workHereYear;//本单位工作年限
	protected Long applyPositionId;//拟担任职位ID
	protected String applyPositionName;//拟担任职位名称
	protected Date applyDate;//拟晋升时间
	protected String applyReason;//拟晋升原因
	protected String target1;//工作目标一
	protected String target2;//工作目标二
	protected String target3;//工作目标三
	protected String intRecord;//员工面谈记录
	protected Long postManagerId;//部门领导ID
	protected String postManagerName;//部门领导姓名
	protected Integer publishStatus;//状态 0：草稿 1：审核中 2：退回 3：审核通过 4：删除标记
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

	public Date getAccessionTime() {
		return accessionTime;
	}

	public void setAccessionTime(Date accessionTime) {
		this.accessionTime = accessionTime;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Long getNowPositionId() {
		return nowPositionId;
	}

	public void setNowPositionId(Long nowPositionId) {
		this.nowPositionId = nowPositionId;
	}

	public String getNowPositionName() {
		return nowPositionName;
	}

	public void setNowPositionName(String nowPositionName) {
		this.nowPositionName = nowPositionName;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getWorkHereYear() {
		return workHereYear;
	}

	public void setWorkHereYear(String workHereYear) {
		this.workHereYear = workHereYear;
	}

	public Long getApplyPositionId() {
		return applyPositionId;
	}

	public void setApplyPositionId(Long applyPositionId) {
		this.applyPositionId = applyPositionId;
	}

	public String getApplyPositionName() {
		return applyPositionName;
	}

	public void setApplyPositionName(String applyPositionName) {
		this.applyPositionName = applyPositionName;
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

}
