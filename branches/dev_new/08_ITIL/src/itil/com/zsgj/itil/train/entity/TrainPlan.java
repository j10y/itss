package com.zsgj.itil.train.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * ≈‡—µº∆ªÆ
 * @Class Name TrainPlan
 * @Author sa
 * @Create In 2009-2-2
 */
public class TrainPlan extends BaseObject {
	private Long id;
	private String planName;
	private String planDescn;
	private Date planBeginTime;
	private Date planEndTime;
	private String remark;
	
	private Integer deleteFlag; //1,0
	private Integer status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getPlanBeginTime() {
		return planBeginTime;
	}
	public void setPlanBeginTime(Date planBeginTime) {
		this.planBeginTime = planBeginTime;
	}
	public String getPlanDescn() {
		return planDescn;
	}
	public void setPlanDescn(String planDescn) {
		this.planDescn = planDescn;
	}
	public Date getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((deleteFlag == null) ? 0 : deleteFlag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((planBeginTime == null) ? 0 : planBeginTime.hashCode());
		result = prime * result
				+ ((planDescn == null) ? 0 : planDescn.hashCode());
		result = prime * result
				+ ((planEndTime == null) ? 0 : planEndTime.hashCode());
		result = prime * result
				+ ((planName == null) ? 0 : planName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		final TrainPlan other = (TrainPlan) obj;
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
		if (planBeginTime == null) {
			if (other.planBeginTime != null)
				return false;
		} else if (!planBeginTime.equals(other.planBeginTime))
			return false;
		if (planDescn == null) {
			if (other.planDescn != null)
				return false;
		} else if (!planDescn.equals(other.planDescn))
			return false;
		if (planEndTime == null) {
			if (other.planEndTime != null)
				return false;
		} else if (!planEndTime.equals(other.planEndTime))
			return false;
		if (planName == null) {
			if (other.planName != null)
				return false;
		} else if (!planName.equals(other.planName))
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
		return true;
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
	
	
	
}
