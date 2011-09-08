package com.xpsoft.oa.model.hrm;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class HrPromAssessment extends BaseModel{
	protected Long id;
	protected HrPromApply promApply;//关联的晋升申请表
	protected String reached1;//工作目标一完成情况
	protected String reached2;//工作目标二完成情况
	protected String reached3;//工作目标三完成情况
	protected String performResult;//绩效结果
	protected String proKnowledge;//专业知识
	protected String commEffect;//沟通影响
	protected String solveAbility;//问题解决能力
	protected String difficultyManage;//工作难度管理幅度
	protected String businessFieldEffect;//业务领域影响
	protected String ratingResult;//评级结果
	protected Long postManagerId;//直线经理ID
	protected String postManagerName;//直线经理姓名
	protected Long deptManagerId;//部门主管ID
	protected String deptManagerName;//部门主管姓名
	protected Date appointDate;//正式任命时间
	protected String postRank;//岗位职级
	protected Long salaryLevelId;//薪资等级ID
	protected String salaryLevelName;//薪资等级名称
	protected String promIntRecord;//晋升面谈记录
	protected Long auditHrId;//人力资源审核人员ID
	protected String auditHrName;//人力资源审核人员姓名
	protected Integer publishStatus;//状态 0：草稿 1：审核中 2：退回 3：审核通过 4：删除标记
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

	public String getPostRank() {
		return postRank;
	}

	public void setPostRank(String postRank) {
		this.postRank = postRank;
	}

	public Long getSalaryLevelId() {
		return salaryLevelId;
	}

	public void setSalaryLevelId(Long salaryLevelId) {
		this.salaryLevelId = salaryLevelId;
	}

	public String getSalaryLevelName() {
		return salaryLevelName;
	}

	public void setSalaryLevelName(String salaryLevelName) {
		this.salaryLevelName = salaryLevelName;
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
	
}
