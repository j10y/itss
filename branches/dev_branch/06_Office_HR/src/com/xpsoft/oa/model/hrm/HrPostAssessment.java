package com.xpsoft.oa.model.hrm;

import java.math.BigDecimal;
import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class HrPostAssessment extends BaseModel{
	protected Long id;
	protected HrPostApply postApply;//关联的转正申请表
	protected Date actualReportDate;//实际报到日期
	protected Date applyPostDate;//拟定转正日期
	protected Date actualPostDate;//实际转正日期
	protected String proKnowledge;//专业知识
	protected String commEffect;//沟通影响
	protected String solveAbility;//问题解决能力
	protected String difficultyManage;//工作难度管理幅度
	protected String businessFieldEffect;//业务领域影响
	protected String ratingResult;//评级结果
	protected Long standardPostId;//被评估者标准岗位ID
	protected String standardPostName;//被评估者标准岗位名称
	protected String postBand;//被评估者岗位层级（Band）
	protected String postGrade;//被评估者岗位层级（档）
	protected Long newSalaryLevelId;//被评估者薪资标准ID
	protected String newSalaryLevelName;//被评估者薪资标准名称
	protected Long oldSalaryLevelId;//转正前薪酬级别ID
	protected String oldSalaryLevelName;//转正前薪酬级别名称
	protected BigDecimal oldSalary;//转正前薪酬金额
	protected BigDecimal newFixedSalary;//转正后固定工资金额
	protected BigDecimal newFloatSalary;//转正后浮动工资金额
	protected BigDecimal yearEndBonusCoefficient;//年终绩效基数金额
	protected BigDecimal totalYearSalary;//年度总薪酬金额
	protected String proPerformance;//员工试用期间工作表现评价
	protected Long postManagerId;//直属经理ID
	protected String postManagerName;//直属经理姓名
	protected Date postManagerAuditDate;//直属经理确认日期
	protected String postOpinion;//本部门/门店意见
	protected String deptOpinion;//主管部门意见（无主管部门可不填写）
	protected String hrOpinion;//人力资源部意见
	protected String bossOpinion;//总经理意见
	protected Integer publishStatus;//状态：0-草稿 1-审核中 2-退回 3-已确认
	protected AppUser createPerson;
	protected Date createDate;
	protected AppUser modifyPerson;
	protected Date modifyDate;
	
	public HrPostAssessment() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HrPostApply getPostApply() {
		return postApply;
	}

	public void setPostApply(HrPostApply postApply) {
		this.postApply = postApply;
	}

	public Date getActualReportDate() {
		return actualReportDate;
	}

	public void setActualReportDate(Date actualReportDate) {
		this.actualReportDate = actualReportDate;
	}

	public Date getApplyPostDate() {
		return applyPostDate;
	}

	public void setApplyPostDate(Date applyPostDate) {
		this.applyPostDate = applyPostDate;
	}

	public Date getActualPostDate() {
		return actualPostDate;
	}

	public void setActualPostDate(Date actualPostDate) {
		this.actualPostDate = actualPostDate;
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

	public Long getStandardPostId() {
		return standardPostId;
	}

	public void setStandardPostId(Long standardPostId) {
		this.standardPostId = standardPostId;
	}

	public String getStandardPostName() {
		return standardPostName;
	}

	public void setStandardPostName(String standardPostName) {
		this.standardPostName = standardPostName;
	}

	public Long getOldSalaryLevelId() {
		return oldSalaryLevelId;
	}

	public void setOldSalaryLevelId(Long oldSalaryLevelId) {
		this.oldSalaryLevelId = oldSalaryLevelId;
	}

	public String getOldSalaryLevelName() {
		return oldSalaryLevelName;
	}

	public void setOldSalaryLevelName(String oldSalaryLevelName) {
		this.oldSalaryLevelName = oldSalaryLevelName;
	}

	public BigDecimal getOldSalary() {
		return oldSalary;
	}

	public void setOldSalary(BigDecimal oldSalary) {
		this.oldSalary = oldSalary;
	}

	public BigDecimal getNewFixedSalary() {
		return newFixedSalary;
	}

	public void setNewFixedSalary(BigDecimal newFixedSalary) {
		this.newFixedSalary = newFixedSalary;
	}

	public BigDecimal getYearEndBonusCoefficient() {
		return yearEndBonusCoefficient;
	}

	public void setYearEndBonusCoefficient(BigDecimal yearEndBonusCoefficient) {
		this.yearEndBonusCoefficient = yearEndBonusCoefficient;
	}

	public BigDecimal getTotalYearSalary() {
		return totalYearSalary;
	}

	public void setTotalYearSalary(BigDecimal totalYearSalary) {
		this.totalYearSalary = totalYearSalary;
	}

	public String getProPerformance() {
		return proPerformance;
	}

	public void setProPerformance(String proPerformance) {
		this.proPerformance = proPerformance;
	}

	public String getPostOpinion() {
		return postOpinion;
	}

	public void setPostOpinion(String postOpinion) {
		this.postOpinion = postOpinion;
	}

	public String getDeptOpinion() {
		return deptOpinion;
	}

	public void setDeptOpinion(String deptOpinion) {
		this.deptOpinion = deptOpinion;
	}

	public String getHrOpinion() {
		return hrOpinion;
	}

	public void setHrOpinion(String hrOpinion) {
		this.hrOpinion = hrOpinion;
	}

	public String getBossOpinion() {
		return bossOpinion;
	}

	public void setBossOpinion(String bossOpinion) {
		this.bossOpinion = bossOpinion;
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

	public Date getPostManagerAuditDate() {
		return postManagerAuditDate;
	}

	public void setPostManagerAuditDate(Date postManagerAuditDate) {
		this.postManagerAuditDate = postManagerAuditDate;
	}

	public String getPostBand() {
		return postBand;
	}

	public void setPostBand(String postBand) {
		this.postBand = postBand;
	}

	public String getPostGrade() {
		return postGrade;
	}

	public void setPostGrade(String postGrade) {
		this.postGrade = postGrade;
	}

	public Long getNewSalaryLevelId() {
		return newSalaryLevelId;
	}

	public void setNewSalaryLevelId(Long newSalaryLevelId) {
		this.newSalaryLevelId = newSalaryLevelId;
	}

	public String getNewSalaryLevelName() {
		return newSalaryLevelName;
	}

	public void setNewSalaryLevelName(String newSalaryLevelName) {
		this.newSalaryLevelName = newSalaryLevelName;
	}

	public BigDecimal getNewFloatSalary() {
		return newFloatSalary;
	}

	public void setNewFloatSalary(BigDecimal newFloatSalary) {
		this.newFloatSalary = newFloatSalary;
	}
}
