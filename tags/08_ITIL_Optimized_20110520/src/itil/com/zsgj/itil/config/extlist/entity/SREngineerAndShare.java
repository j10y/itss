package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class SREngineerAndShare extends BaseObject {
   private java.lang.Long id;
   private com.zsgj.info.framework.security.entity.UserInfo engineer;
   private java.lang.Double shareMoney;
   private com.zsgj.itil.require.entity.SpecialRequirement specialRequire;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setEngineer(com.zsgj.info.framework.security.entity.UserInfo engineer){
	     this.engineer=engineer;
   }
   public void setShareMoney(java.lang.Double shareMoney){
	     this.shareMoney=shareMoney;
   }
   public void setSpecialRequire(com.zsgj.itil.require.entity.SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.zsgj.info.framework.security.entity.UserInfo getEngineer(){
	     return this.engineer;
   }
   public java.lang.Double getShareMoney(){
	     return this.shareMoney;
   }
   public com.zsgj.itil.require.entity.SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.SREngineerAndShare other = (com.zsgj.itil.config.extlist.entity.SREngineerAndShare) obj;
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
