package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

/**
 * 品牌列表
 * @author jptong
 *
 */
public class Band extends BaseModel{
	public static final Integer CREATE = new Integer(1);//新建
	public static final Integer DELETE = new Integer(0);//删除
	    
	private Long id;
	
	private String bandChName;
	
	private String bandEnName;
	
	private String bandDesc;
	
	private Integer bandStatus;
	private Integer flag;
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBandChName() {
		return bandChName;
	}

	public void setBandChName(String bandChName) {
		this.bandChName = bandChName;
	}

	public String getBandEnName() {
		return bandEnName;
	}

	public void setBandEnName(String bandEnName) {
		this.bandEnName = bandEnName;
	}

	public String getBandDesc() {
		return bandDesc;
	}

	public void setBandDesc(String bandDesc) {
		this.bandDesc = bandDesc;
	}

	public Integer getBandStatus() {
		return bandStatus;
	}

	public void setBandStatus(Integer bandStatus) {
		this.bandStatus = bandStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bandChName == null) ? 0 : bandChName.hashCode());
		result = prime * result
				+ ((bandDesc == null) ? 0 : bandDesc.hashCode());
		result = prime * result
				+ ((bandEnName == null) ? 0 : bandEnName.hashCode());
		result = prime * result
				+ ((bandStatus == null) ? 0 : bandStatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final Band other = (Band) obj;
		if (bandChName == null) {
			if (other.bandChName != null)
				return false;
		} else if (!bandChName.equals(other.bandChName))
			return false;
		if (bandDesc == null) {
			if (other.bandDesc != null)
				return false;
		} else if (!bandDesc.equals(other.bandDesc))
			return false;
		if (bandEnName == null) {
			if (other.bandEnName != null)
				return false;
		} else if (!bandEnName.equals(other.bandEnName))
			return false;
		if (bandStatus == null) {
			if (other.bandStatus != null)
				return false;
		} else if (!bandStatus.equals(other.bandStatus))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
