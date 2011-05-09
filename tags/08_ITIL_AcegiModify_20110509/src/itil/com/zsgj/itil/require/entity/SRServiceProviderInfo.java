package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.config.extci.entity.DeliveryTeam;
import com.zsgj.itil.config.extci.entity.ServiceEngineer;
import com.zsgj.itil.config.extlist.entity.RequirementBigType;
import com.zsgj.itil.config.extlist.entity.RequirementSmallType;
/**
 * 服务商及服务类型实体（技术总监审批用）
 * @Class Name SRServiceProviderInfo
 * @Author lee
 * @Create In Jun 25, 2009
 */
public class SRServiceProviderInfo extends BaseObject {
	private Long id;
	private SpecialRequirement specialRequire;
	private DeliveryTeam deliveryTeam;
	private ServiceEngineer mainEngineer;
	private ServiceEngineer assistanEngineer;
	private RequirementBigType bigType;
	private RequirementSmallType smallType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpecialRequirement getSpecialRequire() {
		return specialRequire;
	}

	public void setSpecialRequire(SpecialRequirement specialRequire) {
		this.specialRequire = specialRequire;
	}
	
	public DeliveryTeam getDeliveryTeam() {
		return deliveryTeam;
	}

	public void setDeliveryTeam(DeliveryTeam deliveryTeam) {
		this.deliveryTeam = deliveryTeam;
	}

	public ServiceEngineer getMainEngineer() {
		return mainEngineer;
	}

	public void setMainEngineer(ServiceEngineer mainEngineer) {
		this.mainEngineer = mainEngineer;
	}

	public ServiceEngineer getAssistanEngineer() {
		return assistanEngineer;
	}

	public void setAssistanEngineer(ServiceEngineer assistanEngineer) {
		this.assistanEngineer = assistanEngineer;
	}

	public RequirementBigType getBigType() {
		return bigType;
	}

	public void setBigType(RequirementBigType bigType) {
		this.bigType = bigType;
	}

	public RequirementSmallType getSmallType() {
		return smallType;
	}

	public void setSmallType(RequirementSmallType smallType) {
		this.smallType = smallType;
	}
	
	 public boolean equals(Object obj) {
			if (this == obj)
	   		return true;
	   	if (!super.equals(obj))
	   		return false;
	   	if (getClass() != obj.getClass())
	   		return false;
	   	final SRServiceProviderInfo other = (SRServiceProviderInfo) obj;
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
