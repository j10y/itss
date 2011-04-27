package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class MatchMode extends BaseObject{
	private static final long serialVersionUID = 1004034705823264298L;
	private Long id;
	private String matchModeName;
	private String matchModeCnName;
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((matchModeName == null) ? 0 : matchModeName.hashCode());
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
		final MatchMode other = (MatchMode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (matchModeName == null) {
			if (other.matchModeName != null)
				return false;
		} else if (!matchModeName.equals(other.matchModeName))
			return false;
		return true;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMatchModeCnName() {
		return matchModeCnName;
	}
	public void setMatchModeCnName(String matchModeCnName) {
		this.matchModeCnName = matchModeCnName;
	}
	public String getMatchModeName() {
		return matchModeName;
	}
	public void setMatchModeName(String matchModeName) {
		this.matchModeName = matchModeName;
	}
	public MatchMode(Long id) {
		super();
		this.id = id;
	}
	public MatchMode(Integer id) {
		super();
		this.id = new Long(id.intValue());
	}
	public MatchMode() {
		super();
	}
}
