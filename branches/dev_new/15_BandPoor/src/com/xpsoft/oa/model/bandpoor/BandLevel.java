package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

public class BandLevel extends BaseModel{
	public static final Integer CREATE = new Integer(1);//新建
	public static final Integer DELETE = new Integer(0);//删除
	private Long id;
	private String levelName;
	private String levelDesc;
	private Double startValue;
	private Double endValue;
	private ProClass proClassId;//品类id
	private String proClassName;//品类
		
	public ProClass getProClassId() {
		return proClassId;
	}
	public void setProClassId(ProClass proClassId) {
		this.proClassId = proClassId;
	}
	public String getProClassName() {
		return proClassName;
	}
	public void setProClassName(String proClassName) {
		this.proClassName = proClassName;
	}
	public Double getStartValue() {
		return startValue;
	}
	public void setStartValue(Double startValue) {
		this.startValue = startValue;
	}
	
	
	public Double getEndValue() {
		return endValue;
	}
	public void setEndValue(Double endValue) {
		this.endValue = endValue;
	}
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
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelDesc() {
		return levelDesc;
	}
	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
                + ((proClassName == null) ? 0 : proClassName.hashCode());
		result = prime * result
		        + ((proClassId == null) ? 0 : proClassId.hashCode());
		result = prime * result
				+ ((levelDesc == null) ? 0 : levelDesc.hashCode());
		result = prime * result
				+ ((levelName == null) ? 0 : levelName.hashCode());
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
		final BandLevel other = (BandLevel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (proClassName == null) {
			if (other.proClassName != null)
				return false;
		} else if (!proClassName.equals(other.proClassName))
			return false;
		if (proClassId == null) {
			if (other.proClassId != null)
				return false;
		} else if (!proClassId.equals(other.proClassId))
			return false;
		if (levelDesc == null) {
			if (other.levelDesc != null)
				return false;
		} else if (!levelDesc.equals(other.levelDesc))
			return false;
		if (levelName == null) {
			if (other.levelName != null)
				return false;
		} else if (!levelName.equals(other.levelName))
			return false;
		return true;
	}
	
	
	
}
