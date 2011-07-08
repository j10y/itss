package com.xpsoft.oa.model.flow;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.xpsoft.core.model.BaseModel;

import flexjson.JSON;

public class ProDefinition extends BaseModel {
	/* 22 */public static final Short IS_DEFAULT = Short.valueOf((short) 1);
	/* 23 */public static final Short IS_NOT_DEFAULT = Short.valueOf((short) 0);
	protected Long defId;
	protected String name;
	protected String description;
	protected Date createtime;
	protected String deployId;
	protected String defXml;
	protected String drawDefXml;
	/* 35 */protected Short isDefault = IS_NOT_DEFAULT;
	protected ProType proType;

	@JSON
	public String getDefXml() {
		/* 41 */return this.defXml;
	}

	public void setDefXml(String defXml) {
		/* 45 */this.defXml = defXml;
	}

	public ProDefinition() {
	}

	public ProDefinition(Long in_defId) {
		/* 61 */setDefId(in_defId);
	}

	public ProType getProType() {
		/* 66 */return this.proType;
	}

	public void setProType(ProType in_proType) {
		/* 70 */this.proType = in_proType;
	}

	public void setProTypeId(Long proTypeId) {
		/* 74 */if (this.proType == null) {
			/* 75 */this.proType = new ProType();
		}
		/* 77 */this.proType.setTypeId(proTypeId);
	}

	public Long getProTypeId() {
		/* 81 */return this.proType == null ? null : this.proType.getTypeId();
	}

	public Long getDefId() {
		/* 89 */return this.defId;
	}

	public void setDefId(Long aValue) {
		/* 96 */this.defId = aValue;
	}

	public Long getTypeId() {
		/* 103 */return getProType() == null ? null : getProType().getTypeId();
	}

	public void setTypeId(Long aValue) {
		/* 110 */if (aValue == null)
			/* 111 */this.proType = null;
		/* 112 */else if (this.proType == null) {
			/* 113 */this.proType = new ProType(aValue);
		} else
			/* 116 */this.proType.setTypeId(aValue);
	}

	public String getName() {
		/* 125 */return this.name;
	}

	public void setName(String aValue) {
		/* 133 */this.name = aValue;
	}

	public String getDescription() {
		/* 141 */return this.description;
	}

	public void setDescription(String aValue) {
		/* 148 */this.description = aValue;
	}

	public Date getCreatetime() {
		/* 156 */return this.createtime;
	}

	public void setCreatetime(Date aValue) {
		/* 163 */this.createtime = aValue;
	}

	public String getDeployId() {
		/* 171 */return this.deployId;
	}

	public void setDeployId(String aValue) {
		/* 179 */this.deployId = aValue;
	}

	public String getDrawDefXml() {
		/* 184 */return this.drawDefXml;
	}

	public void setDrawDefXml(String drawDefXml) {
		/* 188 */this.drawDefXml = drawDefXml;
	}

	public boolean equals(Object object) {
		/* 195 */if (!(object instanceof ProDefinition)) {
			/* 196 */return false;
		}
		/* 198 */ProDefinition rhs = (ProDefinition) object;
		/* 199 */return new EqualsBuilder()
		/* 200 */.append(this.defId, rhs.defId)
		/* 201 */.append(this.name, rhs.name)
		/* 202 */.append(this.description, rhs.description)
		/* 203 */.append(this.createtime, rhs.createtime)
		/* 204 */.append(this.deployId, rhs.deployId)
		/* 205 */.isEquals();
	}

	public int hashCode() {
		/* 212 */return new HashCodeBuilder(-82280557, -700257973)
		/* 213 */.append(this.defId).append(this.name)
		/* 214 */.append(this.description)
		/* 215 */.append(this.createtime)
		/* 216 */.append(this.deployId)
		/* 217 */.toHashCode();
	}

	public String toString() {
		/* 224 */return new ToStringBuilder(this)
		/* 225 */.append("defId", this.defId)
		/* 226 */.append("name", this.name)
		/* 227 */.append("description", this.description)
		/* 228 */.append("createtime", this.createtime)
		/* 229 */.append("deployId", this.deployId)
		/* 230 */.toString();
	}

	public Short getIsDefault() {
		/* 234 */return this.isDefault;
	}

	public void setIsDefault(Short isDefault) {
		/* 238 */this.isDefault = isDefault;
	}
}