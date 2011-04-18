package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class SRManager extends BaseObject {
   private java.lang.Long id;
   private com.digitalchina.itil.require.entity.SpecialRequirement specialRequire;
   private com.digitalchina.info.framework.security.entity.UserInfo mainManager;
   private com.digitalchina.info.framework.security.entity.UserInfo assistanEngineer;
   private java.lang.Integer isNewFactory;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setSpecialRequire(com.digitalchina.itil.require.entity.SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setMainManager(com.digitalchina.info.framework.security.entity.UserInfo mainManager){
	     this.mainManager=mainManager;
   }
   public void setAssistanEngineer(com.digitalchina.info.framework.security.entity.UserInfo assistanEngineer){
	     this.assistanEngineer=assistanEngineer;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.digitalchina.itil.require.entity.SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public com.digitalchina.info.framework.security.entity.UserInfo getMainManager(){
	     return this.mainManager;
   }
   public com.digitalchina.info.framework.security.entity.UserInfo getAssistanEngineer(){
	     return this.assistanEngineer;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.SRManager other = (com.digitalchina.itil.config.extlist.entity.SRManager) obj;
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
public java.lang.Integer getIsNewFactory() {
	return isNewFactory;
}
public void setIsNewFactory(java.lang.Integer isNewFactory) {
	this.isNewFactory = isNewFactory;
}
} 
