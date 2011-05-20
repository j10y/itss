package com.zsgj.itil.project.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.config.extlist.entity.SRProjectPlan;

/**
 * 工程师根据项目计划填写的工单（报工）
 * @Class Name ProjectWorkReport
 * @Author sa
 * @Create In 2009-3-10
 */
public class ProjectWorkReport extends BaseObject {
	private Long id;
	private SRProjectPlan projectPlan; //对哪个项目计划做的报工
	private Date reportDate; //报告时间
	private ProjectPlanProgress progress; //当前完成的进度
	private Integer spendHours; //该工单实际花费的时间
	
	private String summary; //简介
	private String description; //概述
		
	private Date endDate; //计划结束日期
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProjectPlanProgress getProgress() {
		return progress;
	}
	public void setProgress(ProjectPlanProgress progress) {
		this.progress = progress;
	}
	
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getSpendHours() {
		return spendHours;
	}
	public void setSpendHours(Integer spendHours) {
		this.spendHours = spendHours;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public SRProjectPlan getProjectPlan() {
		return projectPlan;
	}
	public void setProjectPlan(SRProjectPlan projectPlan) {
		this.projectPlan = projectPlan;
	}
}
