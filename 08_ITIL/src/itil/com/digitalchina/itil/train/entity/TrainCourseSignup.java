package com.digitalchina.itil.train.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * �γ̱���	
 * @Class Name TrainCourseSign
 * @Author sa
 * @Create In 2009-2-2
 */
public class TrainCourseSignup extends BaseObject{
	private Long id;
	private TrainCourse trainCourse;
	private UserInfo signupUser;
	private Integer affirmFlag; 
	private String remark;
	
	private Integer deleteFlag; //1,0
	private Integer status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TrainCourse getTrainCourse() {
		return trainCourse;
	}
	public void setTrainCourse(TrainCourse trainCourse) {
		this.trainCourse = trainCourse;
	}
	public UserInfo getSignupUser() {
		return signupUser;
	}
	public void setSignupUser(UserInfo signupUser) {
		this.signupUser = signupUser;
	}
	public Integer getAffirmFlag() {
		return affirmFlag;
	}
	public void setAffirmFlag(Integer affirmFlag) {
		this.affirmFlag = affirmFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((affirmFlag == null) ? 0 : affirmFlag.hashCode());
		result = prime * result
				+ ((deleteFlag == null) ? 0 : deleteFlag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((signupUser == null) ? 0 : signupUser.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		final TrainCourseSignup other = (TrainCourseSignup) obj;
		if (affirmFlag == null) {
			if (other.affirmFlag != null)
				return false;
		} else if (!affirmFlag.equals(other.affirmFlag))
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (signupUser == null) {
			if (other.signupUser != null)
				return false;
		} else if (!signupUser.equals(other.signupUser))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (trainCourse == null) {
			if (other.trainCourse != null)
				return false;
		} else if (!trainCourse.equals(other.trainCourse))
			return false;
		return true;
	}
	
}
