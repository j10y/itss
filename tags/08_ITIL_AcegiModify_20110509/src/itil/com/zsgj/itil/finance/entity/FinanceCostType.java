package com.zsgj.itil.finance.entity;

import com.zsgj.info.framework.dao.BaseObject;
/**
 * 财务费用类型表
 * 数据来源：不定期从BI或是ERP导入数据
 * @author guangsa
 *
 */
public class FinanceCostType extends BaseObject{

	private Long id;
	private String costTypeCode;//费用类型编码
	private String costTypeName;//费用类型名称
	private int isERPType;//是否是ERP科目 1：是 0：否
	private int isUsedApportion;//是否用于分摊 1：是 0：否
	private int isUsualSubjects;//是否常用科目 1：是 0：否
	private String costDesc;//保留字段
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCostTypeCode() {
		return costTypeCode;
	}
	public void setCostTypeCode(String costTypeCode) {
		this.costTypeCode = costTypeCode;
	}
	public String getCostTypeName() {
		return costTypeName;
	}
	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}
	public int getIsERPType() {
		return isERPType;
	}
	public void setIsERPType(int isERPType) {
		this.isERPType = isERPType;
	}
	public int getIsUsedApportion() {
		return isUsedApportion;
	}
	public void setIsUsedApportion(int isUsedApportion) {
		this.isUsedApportion = isUsedApportion;
	}
	public int getIsUsualSubjects() {
		return isUsualSubjects;
	}
	public void setIsUsualSubjects(int isUsualSubjects) {
		this.isUsualSubjects = isUsualSubjects;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((costDesc == null) ? 0 : costDesc.hashCode());
		result = prime * result
				+ ((costTypeCode == null) ? 0 : costTypeCode.hashCode());
		result = prime * result
				+ ((costTypeName == null) ? 0 : costTypeName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + isERPType;
		result = prime * result + isUsedApportion;
		result = prime * result + isUsualSubjects;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FinanceCostType other = (FinanceCostType) obj;
		if (costDesc == null) {
			if (other.costDesc != null)
				return false;
		} else if (!costDesc.equals(other.costDesc))
			return false;
		if (costTypeCode == null) {
			if (other.costTypeCode != null)
				return false;
		} else if (!costTypeCode.equals(other.costTypeCode))
			return false;
		if (costTypeName == null) {
			if (other.costTypeName != null)
				return false;
		} else if (!costTypeName.equals(other.costTypeName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isERPType != other.isERPType)
			return false;
		if (isUsedApportion != other.isUsedApportion)
			return false;
		if (isUsualSubjects != other.isUsualSubjects)
			return false;
		return true;
	}
	public String getCostDesc() {
		return costDesc;
	}
	public void setCostDesc(String costDesc) {
		this.costDesc = costDesc;
	}
}
