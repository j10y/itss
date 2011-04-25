package com.digitalchina.itil.require.entity;

import com.digitalchina.info.framework.dao.BaseObject;

public class SRErpApplicationTable extends BaseObject {
	private Long id;
	private SpecialRequirementInfo srInfo; //关联ERP非常规需求附属信息
	private SRErpApplication srApp; //对应系统

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpecialRequirementInfo getSrInfo() {
		return srInfo;
	}

	public void setSrInfo(SpecialRequirementInfo srInfo) {
		this.srInfo = srInfo;
	}

	public SRErpApplication getSrApp() {
		return srApp;
	}

	public void setSrApp(SRErpApplication srApp) {
		this.srApp = srApp;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SRErpApplicationTable other = (SRErpApplicationTable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
