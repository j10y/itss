package com.zsgj.itil.train.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 信息调查，此功能分在单独的模块实现。涉及到类似考试系统
 * 中的试卷，实体，答案等关联。
 * 
 * 允许一个调查项目中有多道题目Quest，每道题目Quest可以有多个答案Answer，答案可以为多选和单选，以及手工录入
 * 
 * 
 * @Class Name Survey
 * @Author Administrator
 * @Create In 2008-7-11
 */
public class Survey extends BaseObject{
	private Long id;
	private String surveyName;
	private Integer deployFlag; //设置为当前调查标记
	private SurveyType surveyType; 
	private Date beginDate;
	private Date endDate;
	//private String imageUrl; //此调查在首页显示的图片
	//private TrainCourse trainCourse;//此调查所属课程
	private Set quests = new HashSet();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSurveyName() {
		return surveyName;
	}
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	public Set getQuests() {
		return quests;
	}
	public void setQuests(Set quests) {
		this.quests = quests;
	}
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((surveyName == null) ? 0 : surveyName.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Survey other = (Survey) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (surveyName == null) {
			if (other.surveyName != null)
				return false;
		} else if (!surveyName.equals(other.surveyName))
			return false;
		return true;
	}
//	public Integer getCurrentFlag() {
//		return deployFlag;
//	}
//	public void setCurrentFlag(Integer deployFlag) {
//		this.deployFlag = deployFlag;
//	}
//	public String getImageUrl() {
//		return imageUrl;
//	}
//	public void setImageUrl(String imageUrl) {
//		this.imageUrl = imageUrl;
//	}
//	public TrainCourse getTrainCourse() {
//		return trainCourse;
//	}
//	public void setTrainCourse(TrainCourse trainCourse) {
//		this.trainCourse = trainCourse;
//	}
	public Integer getCurrentFlag() {
		return deployFlag;
	}
	public void setCurrentFlag(Integer deployFlag) {
		this.deployFlag = deployFlag;
	}
	public Integer getDeployFlag() {
		return deployFlag;
	}
	public void setDeployFlag(Integer deployFlag) {
		this.deployFlag = deployFlag;
	}
	public SurveyType getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(SurveyType surveyType) {
		this.surveyType = surveyType;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
