package com.zsgj.itil.project.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.config.extlist.entity.SRProjectPlan;

/**
 * ����ʦ������Ŀ�ƻ���д�Ĺ�����������
 * @Class Name ProjectWorkReport
 * @Author sa
 * @Create In 2009-3-10
 */
public class ProjectWorkReport extends BaseObject {
	private Long id;
	private SRProjectPlan projectPlan; //���ĸ���Ŀ�ƻ����ı���
	private Date reportDate; //����ʱ��
	private ProjectPlanProgress progress; //��ǰ��ɵĽ���
	private Integer spendHours; //�ù���ʵ�ʻ��ѵ�ʱ��
	
	private String summary; //���
	private String description; //����
		
	private Date endDate; //�ƻ���������
	
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
