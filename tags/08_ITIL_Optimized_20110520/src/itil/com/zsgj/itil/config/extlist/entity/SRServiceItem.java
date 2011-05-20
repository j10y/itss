package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class SRServiceItem extends BaseObject {
   private java.lang.Long id;
   private com.zsgj.itil.require.entity.SpecialRequirement specialRequire;
   private com.zsgj.itil.service.entity.ServiceItem serviceItem;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setSpecialRequire(com.zsgj.itil.require.entity.SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setServiceItem(com.zsgj.itil.service.entity.ServiceItem serviceItem){
	     this.serviceItem=serviceItem;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.zsgj.itil.require.entity.SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public com.zsgj.itil.service.entity.ServiceItem getServiceItem(){
	     return this.serviceItem;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.SRServiceItem other = (com.zsgj.itil.config.extlist.entity.SRServiceItem) obj;
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
