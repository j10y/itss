package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.config.extlist.entity.B2BbusinessType;

public class B2Binterface extends BaseObject{
	
	private Long id;
	private String cisn;//编号生成器
	private String name;//接口实例名称,定义规则：”对方名称缩写+业务描述缩写“，如IBM采购
	private String description;//接口描述
	private B2BbusinessType businessType;//业务类型
	private Date   goLiveDate;//上线日期
	private String partnerName;//对方公司名称
	private String partnerBU;//对方业务部门
	private String partnerSystem;//对方系统名称
	private String partnerAdmin;//对方联系人
	private String partyEmail;//对方联系人email
	private String partyTel;//对方联系人电话
	private String DCBU;//我方业务部门
	private String costCenter;//成本中心
	private String technicalDoc;
	public Long getId() {
		return id;
	}
	public String getTechnicalDoc() {
		return technicalDoc;
	}
	public void setTechnicalDoc(String technicalDoc) {
		this.technicalDoc = technicalDoc;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCisn() {
		return cisn;
	}
	public void setCisn(String cisn) {
		this.cisn = cisn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getGoLiveDate() {
		return goLiveDate;
	}
	public void setGoLiveDate(Date goLiveDate) {
		this.goLiveDate = goLiveDate;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerBU() {
		return partnerBU;
	}
	public void setPartnerBU(String partnerBU) {
		this.partnerBU = partnerBU;
	}
	public String getPartnerSystem() {
		return partnerSystem;
	}
	public void setPartnerSystem(String partnerSystem) {
		this.partnerSystem = partnerSystem;
	}
	public String getPartnerAdmin() {
		return partnerAdmin;
	}
	public void setPartnerAdmin(String partnerAdmin) {
		this.partnerAdmin = partnerAdmin;
	}
	public String getPartyEmail() {
		return partyEmail;
	}
	public void setPartyEmail(String partyEmail) {
		this.partyEmail = partyEmail;
	}
	public String getPartyTel() {
		return partyTel;
	}
	public void setPartyTel(String partyTel) {
		this.partyTel = partyTel;
	}
	public String getDCBU() {
		return DCBU;
	}
	public void setDCBU(String dcbu) {
		DCBU = dcbu;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public B2BbusinessType getBusinessType() {
		return businessType;
	}
	public void setBusinessType(B2BbusinessType businessType) {
		this.businessType = businessType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((DCBU == null) ? 0 : DCBU.hashCode());
		result = prime * result
				+ ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result + ((cisn == null) ? 0 : cisn.hashCode());
		result = prime * result
				+ ((costCenter == null) ? 0 : costCenter.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((goLiveDate == null) ? 0 : goLiveDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((partnerAdmin == null) ? 0 : partnerAdmin.hashCode());
		result = prime * result
				+ ((partnerBU == null) ? 0 : partnerBU.hashCode());
		result = prime * result
				+ ((partnerName == null) ? 0 : partnerName.hashCode());
		result = prime * result
				+ ((partnerSystem == null) ? 0 : partnerSystem.hashCode());
		result = prime * result
				+ ((partyEmail == null) ? 0 : partyEmail.hashCode());
		result = prime * result
				+ ((partyTel == null) ? 0 : partyTel.hashCode());
		result = prime * result
				+ ((technicalDoc == null) ? 0 : technicalDoc.hashCode());
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
		final B2Binterface other = (B2Binterface) obj;
		if (DCBU == null) {
			if (other.DCBU != null)
				return false;
		} else if (!DCBU.equals(other.DCBU))
			return false;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		if (cisn == null) {
			if (other.cisn != null)
				return false;
		} else if (!cisn.equals(other.cisn))
			return false;
		if (costCenter == null) {
			if (other.costCenter != null)
				return false;
		} else if (!costCenter.equals(other.costCenter))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (goLiveDate == null) {
			if (other.goLiveDate != null)
				return false;
		} else if (!goLiveDate.equals(other.goLiveDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partnerAdmin == null) {
			if (other.partnerAdmin != null)
				return false;
		} else if (!partnerAdmin.equals(other.partnerAdmin))
			return false;
		if (partnerBU == null) {
			if (other.partnerBU != null)
				return false;
		} else if (!partnerBU.equals(other.partnerBU))
			return false;
		if (partnerName == null) {
			if (other.partnerName != null)
				return false;
		} else if (!partnerName.equals(other.partnerName))
			return false;
		if (partnerSystem == null) {
			if (other.partnerSystem != null)
				return false;
		} else if (!partnerSystem.equals(other.partnerSystem))
			return false;
		if (partyEmail == null) {
			if (other.partyEmail != null)
				return false;
		} else if (!partyEmail.equals(other.partyEmail))
			return false;
		if (partyTel == null) {
			if (other.partyTel != null)
				return false;
		} else if (!partyTel.equals(other.partyTel))
			return false;
		if (technicalDoc == null) {
			if (other.technicalDoc != null)
				return false;
		} else if (!technicalDoc.equals(other.technicalDoc))
			return false;
		return true;
	}
	
}
