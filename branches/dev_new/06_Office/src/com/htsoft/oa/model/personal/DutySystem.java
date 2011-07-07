package com.htsoft.oa.model.personal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.htsoft.core.model.BaseModel;

public class DutySystem extends BaseModel {
	/* 20 */public static final Short DEFAULT = Short.valueOf((short) 1);

	/* 22 */public static final Short NOT_DEFAULT = Short.valueOf((short) 0);
	protected Long systemId;
	protected String systemName;
	protected String systemSetting;
	protected String systemDesc;
	protected Short isDefault;

	public DutySystem() {
	}

	public DutySystem(Long in_systemId) {
		/* 44 */setSystemId(in_systemId);
	}

	public Long getSystemId() {
		/* 54 */return this.systemId;
	}

	public void setSystemId(Long aValue) {
		/* 61 */this.systemId = aValue;
	}

	public String getSystemName() {
		/* 69 */return this.systemName;
	}

	public void setSystemName(String aValue) {
		/* 77 */this.systemName = aValue;
	}

	public String getSystemSetting() {
		/* 85 */return this.systemSetting;
	}

	public void setSystemSetting(String aValue) {
		/* 93 */this.systemSetting = aValue;
	}

	public String getSystemDesc() {
		/* 101 */return this.systemDesc;
	}

	public void setSystemDesc(String aValue) {
		/* 109 */this.systemDesc = aValue;
	}

	public boolean equals(Object object) {
		/* 116 */if (!(object instanceof DutySystem)) {
			/* 117 */return false;
		}
		/* 119 */DutySystem rhs = (DutySystem) object;
		/* 120 */return new EqualsBuilder()
		/* 121 */.append(this.systemId, rhs.systemId)
		/* 122 */.append(this.systemName, rhs.systemName)
		/* 123 */.append(this.systemSetting, rhs.systemSetting)
		/* 124 */.append(this.systemDesc, rhs.systemDesc)
		/* 125 */.isEquals();
	}

	public int hashCode() {
		/* 132 */return new HashCodeBuilder(-82280557, -700257973)
		/* 133 */.append(this.systemId)
		/* 134 */.append(this.systemName)
		/* 135 */.append(this.systemSetting)
		/* 136 */.append(this.systemDesc)
		/* 137 */.toHashCode();
	}

	public String toString() {
		/* 144 */return new ToStringBuilder(this)
		/* 145 */.append("systemId", this.systemId)
		/* 146 */.append("systemName", this.systemName)
		/* 147 */.append("systemSetting", this.systemSetting)
		/* 148 */.append("systemDesc", this.systemDesc)
		/* 149 */.toString();
	}

	public Short getIsDefault() {
		/* 153 */return this.isDefault;
	}

	public void setIsDefault(Short isDefault) {
		/* 157 */this.isDefault = isDefault;
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.model.personal.DutySystem JD-Core Version: 0.6.0
 */