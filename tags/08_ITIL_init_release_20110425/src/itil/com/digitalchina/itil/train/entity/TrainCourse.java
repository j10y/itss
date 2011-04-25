package com.digitalchina.itil.train.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

public class TrainCourse extends BaseObject {
	private Long id;
	private String courseName;
	private String courseContent;
	private Date signupBeginDate;
	private Date signupEndDate;
	private TrainInstructor instructor;
	private String remark;
	private Integer deleteFlag; //1,0
	private Integer status;
	
	public Long getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((courseContent == null) ? 0 : courseContent.hashCode());
		result = prime * result
				+ ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result
				+ ((deleteFlag == null) ? 0 : deleteFlag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((instructor == null) ? 0 : instructor.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((signupBeginDate == null) ? 0 : signupBeginDate.hashCode());
		result = prime * result
				+ ((signupEndDate == null) ? 0 : signupEndDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		final TrainCourse other = (TrainCourse) obj;
		if (courseContent == null) {
			if (other.courseContent != null)
				return false;
		} else if (!courseContent.equals(other.courseContent))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (deleteFlag == null) {
			if (other.deleteFlag != null)
				return false;
		} else if (!deleteFlag.equals(other.deleteFlag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instructor == null) {
			if (other.instructor != null)
				return false;
		} else if (!instructor.equals(other.instructor))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (signupBeginDate == null) {
			if (other.signupBeginDate != null)
				return false;
		} else if (!signupBeginDate.equals(other.signupBeginDate))
			return false;
		if (signupEndDate == null) {
			if (other.signupEndDate != null)
				return false;
		} else if (!signupEndDate.equals(other.signupEndDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseContent() {
		return courseContent;
	}
	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}
	public Date getSignupBeginDate() {
		return signupBeginDate;
	}
	public void setSignupBeginDate(Date signupBeginDate) {
		this.signupBeginDate = signupBeginDate;
	}
	public Date getSignupEndDate() {
		return signupEndDate;
	}
	public void setSignupEndDate(Date signupEndDate) {
		this.signupEndDate = signupEndDate;
	}
	public TrainInstructor getInstructor() {
		return instructor;
	}
	public void setInstructor(TrainInstructor instructor) {
		this.instructor = instructor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
