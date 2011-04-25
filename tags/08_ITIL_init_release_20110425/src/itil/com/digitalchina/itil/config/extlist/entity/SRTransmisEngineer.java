package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class SRTransmisEngineer extends BaseObject {
	public static final Integer NEED = 1;
	public static final Integer NOTNEED = 0;
   private java.lang.Long id;
   private java.lang.Integer needOrNot;
   private com.digitalchina.itil.require.entity.SpecialRequirement specialRequire;
   private com.digitalchina.info.framework.security.entity.UserInfo userInfo;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setNeedOrNot(java.lang.Integer needOrNot){
	     this.needOrNot=needOrNot;
   }
   public void setSpecialRequire(com.digitalchina.itil.require.entity.SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setUserInfo(com.digitalchina.info.framework.security.entity.UserInfo userInfo){
	     this.userInfo=userInfo;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.Integer getNeedOrNot(){
	     return this.needOrNot;
   }
   public com.digitalchina.itil.require.entity.SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public com.digitalchina.info.framework.security.entity.UserInfo getUserInfo(){
	     return this.userInfo;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.SRTransmisEngineer other = (com.digitalchina.itil.config.extlist.entity.SRTransmisEngineer) obj;
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
