package com.digitalchina.itil.train.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 培训计划和课程的关系信息
 * @Class Name TrainPlanCourse
 * @Author sa
 * @Create In 2009-2-2
 */
public class TrainPlanCourse extends BaseObject {
	private Long id;
	private TrainPlan trainPlan;
	private TrainCourse trainCourse;
	private String remark;
	private Integer deleteFlag; //1,0
	private Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TrainPlan getTrainPlan() {
		return trainPlan;
	}
	public void setTrainPlan(TrainPlan trainPlan) {
		this.trainPlan = trainPlan;
	}
	public TrainCourse getTrainCourse() {
		return trainCourse;
	}
	public void setTrainCourse(TrainCourse trainCourse) {
		this.trainCourse = trainCourse;
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
				+ ((deleteFlag == null) ? 0 : deleteFlag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((trainCourse == null) ? 0 : trainCourse.hashCode());
		result = prime * result
				+ ((trainPlan == null) ? 0 : trainPlan.hashCode());
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
		final TrainPlanCourse other = (TrainPlanCourse) obj;
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
		if (trainPlan == null) {
			if (other.trainPlan != null)
				return false;
		} else if (!trainPlan.equals(other.trainPlan))
			return false;
		return true;
	}
	
}
