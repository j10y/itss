package com.htsoft.oa.model.archive;

import com.htsoft.core.model.BaseModel;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchFlowConf extends BaseModel {
	/* 19 */public static Short ARCH_SEND_TYPE = Short.valueOf((short) 0);
	/* 20 */public static Short ARCH_REC_TYPE = Short.valueOf((short) 1);
	protected Long configId;
	protected String processName;
	protected Long processDefId;
	protected Short archType;

	public ArchFlowConf() {
	}

	public ArchFlowConf(Long in_configId) {
		/* 40 */setConfigId(in_configId);
	}

	public Long getConfigId() {
		/* 50 */return this.configId;
	}

	public void setConfigId(Long aValue) {
		/* 57 */this.configId = aValue;
	}

	public String getProcessName() {
		/* 65 */return this.processName;
	}

	public void setProcessName(String aValue) {
		/* 73 */this.processName = aValue;
	}

	public Long getProcessDefId() {
		/* 81 */return this.processDefId;
	}

	public void setProcessDefId(Long aValue) {
		/* 88 */this.processDefId = aValue;
	}

	public Short getArchType() {
		/* 96 */return this.archType;
	}

	public void setArchType(Short aValue) {
		/* 103 */this.archType = aValue;
	}

	public boolean equals(Object object) {
		/* 110 */if (!(object instanceof ArchFlowConf)) {
			/* 111 */return false;
		}
		/* 113 */ArchFlowConf rhs = (ArchFlowConf) object;
		/* 114 */return new EqualsBuilder()
		/* 115 */.append(this.configId, rhs.configId)
		/* 116 */.append(this.processName, rhs.processName)
		/* 117 */.append(this.processDefId, rhs.processDefId)
		/* 118 */.append(this.archType, rhs.archType)
		/* 119 */.isEquals();
	}

	public int hashCode() {
		/* 126 */return new HashCodeBuilder(-82280557, -700257973)
		/* 127 */.append(this.configId)
		/* 128 */.append(this.processName)
		/* 129 */.append(this.processDefId)
		/* 130 */.append(this.archType)
		/* 131 */.toHashCode();
	}

	public String toString() {
		/* 138 */return new ToStringBuilder(this)
		/* 139 */.append("configId", this.configId)
		/* 140 */.append("processName", this.processName)
		/* 141 */.append("processDefId", this.processDefId)
		/* 142 */.append("archType", this.archType)
		/* 143 */.toString();
	}
}
