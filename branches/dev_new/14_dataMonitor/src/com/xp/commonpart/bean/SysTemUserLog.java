package com.xp.commonpart.bean;

import java.util.Date;

public class SysTemUserLog {
	private Long id;//编号 
	private String operateUserName;//操作用户名
	private Date operateDate;//操作日期
	private String operateDetail;//操作内容
	private String operateIP;//操作ip
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getOperateDetail() {
		return operateDetail;
	}
	public void setOperateDetail(String operateDetail) {
		this.operateDetail = operateDetail;
	}
	public String getOperateIP() {
		return operateIP;
	}
	public void setOperateIP(String operateIP) {
		this.operateIP = operateIP;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((operateDate == null) ? 0 : operateDate.hashCode());
		result = prime * result
				+ ((operateDetail == null) ? 0 : operateDetail.hashCode());
		result = prime * result
				+ ((operateIP == null) ? 0 : operateIP.hashCode());
		result = prime * result
				+ ((operateUserName == null) ? 0 : operateUserName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SysTemUserLog other = (SysTemUserLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (operateDate == null) {
			if (other.operateDate != null)
				return false;
		} else if (!operateDate.equals(other.operateDate))
			return false;
		if (operateDetail == null) {
			if (other.operateDetail != null)
				return false;
		} else if (!operateDetail.equals(other.operateDetail))
			return false;
		if (operateIP == null) {
			if (other.operateIP != null)
				return false;
		} else if (!operateIP.equals(other.operateIP))
			return false;
		if (operateUserName == null) {
			if (other.operateUserName != null)
				return false;
		} else if (!operateUserName.equals(other.operateUserName))
			return false;
		return true;
	}
	
}
