package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

public class BandStyle extends BaseModel{
	private Long id;
	private String styleNum;
	private String styleName;
	private String styleDesc;
	private ProClass proClassId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStyleNum() {
		return styleNum;
	}
	public void setStyleNum(String styleNum) {
		this.styleNum = styleNum;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	public String getStyleDesc() {
		return styleDesc;
	}
	public void setStyleDesc(String styleDesc) {
		this.styleDesc = styleDesc;
	}
	public ProClass getProClassId() {
		return proClassId;
	}
	public void setProClassId(ProClass proClassId) {
		this.proClassId = proClassId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((proClassId == null) ? 0 : proClassId.hashCode());
		result = prime * result
				+ ((styleDesc == null) ? 0 : styleDesc.hashCode());
		result = prime * result
				+ ((styleName == null) ? 0 : styleName.hashCode());
		result = prime * result
				+ ((styleNum == null) ? 0 : styleNum.hashCode());
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
		final BandStyle other = (BandStyle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (proClassId == null) {
			if (other.proClassId != null)
				return false;
		} else if (!proClassId.equals(other.proClassId))
			return false;
		if (styleDesc == null) {
			if (other.styleDesc != null)
				return false;
		} else if (!styleDesc.equals(other.styleDesc))
			return false;
		if (styleName == null) {
			if (other.styleName != null)
				return false;
		} else if (!styleName.equals(other.styleName))
			return false;
		if (styleNum == null) {
			if (other.styleNum != null)
				return false;
		} else if (!styleNum.equals(other.styleNum))
			return false;
		return true;
	}
	
	
}
