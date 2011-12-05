package com.xpsoft.oa.model.bandpoor;

import com.xpsoft.core.model.BaseModel;

public class Floor extends BaseModel{
	private Long id;
	
	private String floorName;
	
	private String floorDesc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getFloorDesc() {
		return floorDesc;
	}

	public void setFloorDesc(String floorDesc) {
		this.floorDesc = floorDesc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((floorName == null) ? 0 : floorName.hashCode());
		result = prime * result
				+ ((floorDesc == null) ? 0 : floorDesc.hashCode());
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
		final Floor other = (Floor) obj;
		if (floorName == null) {
			if (other.floorName != null)
				return false;
		} else if (!floorName.equals(other.floorName))
			return false;
		if (floorDesc == null) {
			if (other.floorDesc != null)
				return false;
		} else if (!floorDesc.equals(other.floorDesc))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
