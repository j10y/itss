package com.xpsoft.oa.model.hrm;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class HrPromAssessment extends BaseModel{
	protected Long id;
	protected HrPromApply promApply;
	protected String reached1;
	protected String reached2;
	protected String reached3;
	protected String performResult;
	protected String proKnowledge;
	protected String commEffect;
	protected String solveAbility;
	protected String difficultyManage;
	protected String businessFieldEffect;
	protected String ratingResult;
	protected Long postManagerId;
	protected String postManagerName;
	protected Long deptManagerId;
	protected String deptManagerName;
	protected Date appointDate;
	protected String postRank;
	protected StandSalary salaryLevel;
	protected String promIntRecord;
	protected Long auditHrId;
	protected String auditHrName;
	protected Integer publishStatus;
	protected AppUser createPerson;
	protected Date createDate;
	protected AppUser modifyPerson;
	protected Date modifyDate;
	
	public HrPromAssessment() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HrPromApply getPromApply() {
		return promApply;
	}

	public void setPromApply(HrPromApply promApply) {
		this.promApply = promApply;
	}

	public String getReached1() {
		return reached1;
	}

	public void setReached1(String reached1) {
		this.reached1 = reached1;
	}

	public String getReached2() {
		return reached2;
	}

	public void setReached2(String reached2) {
		this.reached2 = reached2;
	}

	public String getReached3() {
		return reached3;
	}

	public void setReached3(String reached3) {
		this.reached3 = reached3;
	}

	public String getPerformResult() {
		return performResult;
	}

	public void setPerformResult(String performResult) {
		this.performResult = performResult;
	}

	public String getProKnowledge() {
		return proKnowledge;
	}

	public void setProKnowledge(String proKnowledge) {
		this.proKnowledge = proKnowledge;
	}

	public String getCommEffect() {
		return commEffect;
	}

	public void setCommEffect(String commEffect) {
		this.commEffect = commEffect;
	}

	public String getSolveAbility() {
		return solveAbility;
	}

	public void setSolveAbility(String solveAbility) {
		this.solveAbility = solveAbility;
	}

	public String getDifficultyManage() {
		return difficultyManage;
	}

	public void setDifficultyManage(String difficultyManage) {
		this.difficultyManage = difficultyManage;
	}

	public String getBusinessFieldEffect() {
		return businessFieldEffect;
	}

	public void setBusinessFieldEffect(String businessFieldEffect) {
		this.businessFieldEffect = businessFieldEffect;
	}

	public String getRatingResult() {
		return ratingResult;
	}

	public void setRatingResult(String ratingResult) {
		this.ratingResult = ratingResult;
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

	public Long getDeptManagerId() {
		return deptManagerId;
	}

	public void setDeptManagerId(Long deptManagerId) {
		this.deptManagerId = deptManagerId;
	}

	public String getDeptManagerName() {
		return deptManagerName;
	}

	public void setDeptManagerName(String deptManagerName) {
		this.deptManagerName = deptManagerName;
	}

	public Date getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(Date appointDate) {
		this.appointDate = appointDate;
	}

	public String getPromIntRecord() {
		return promIntRecord;
	}

	public void setPromIntRecord(String promIntRecord) {
		this.promIntRecord = promIntRecord;
	}

	public Long getAuditHrId() {
		return auditHrId;
	}

	public void setAuditHrId(Long auditHrId) {
		this.auditHrId = auditHrId;
	}

	public String getAuditHrName() {
		return auditHrName;
	}

	public void setAuditHrName(String auditHrName) {
		this.auditHrName = auditHrName;
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

	public String getPostRank() {
		return postRank;
	}

	public void setPostRank(String postRank) {
		this.postRank = postRank;
	}

	public StandSalary getSalaryLevel() {
		return salaryLevel;
	}

	public void setSalaryLevel(StandSalary salaryLevel) {
		this.salaryLevel = salaryLevel;
	}
	
}
