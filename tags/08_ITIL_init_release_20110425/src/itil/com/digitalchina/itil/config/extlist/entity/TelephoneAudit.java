package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class TelephoneAudit extends BaseObject {
	private java.lang.Long id;
	private java.lang.String workSpace;
	private java.lang.String auditManger;
	private java.lang.String vpnAuditManger;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setWorkSpace(java.lang.String workSpace) {
		this.workSpace = workSpace;
	}

	public void setAuditManger(java.lang.String auditManger) {
		this.auditManger = auditManger;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getWorkSpace() {
		return this.workSpace;
	}

	public java.lang.String getAuditManger() {
		return this.auditManger;
	}

	public java.lang.String getVpnAuditManger() {
		return vpnAuditManger;
	}

	public void setVpnAuditManger(java.lang.String vpnAuditManger) {
		this.vpnAuditManger = vpnAuditManger;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final com.digitalchina.itil.config.extlist.entity.TelephoneAudit other = (com.digitalchina.itil.config.extlist.entity.TelephoneAudit) obj;
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
