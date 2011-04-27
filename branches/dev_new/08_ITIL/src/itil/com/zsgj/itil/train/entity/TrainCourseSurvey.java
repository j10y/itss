package com.zsgj.itil.train.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 培训课程与调查问卷关系，一个课程可以有多个调查问卷, 但同时只能有一个生效
 * @Class Name TrainCourseSurvey
 * @Author sa
 * @Create In 2009-2-16
 */
public class TrainCourseSurvey extends BaseObject {
	private Long id;
	private TrainCourse trainCourse;
	private Survey survey;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	public TrainCourse getTrainCourse() {
		return trainCourse;
	}
	public void setTrainCourse(TrainCourse trainCourse) {
		this.trainCourse = trainCourse;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((survey == null) ? 0 : survey.hashCode());
		result = prime * result
				+ ((trainCourse == null) ? 0 : trainCourse.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TrainCourseSurvey other = (TrainCourseSurvey) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (survey == null) {
			if (other.survey != null)
				return false;
		} else if (!survey.equals(other.survey))
			return false;
		if (trainCourse == null) {
			if (other.trainCourse != null)
				return false;
		} else if (!trainCourse.equals(other.trainCourse))
			return false;
		return true;
	}
}
