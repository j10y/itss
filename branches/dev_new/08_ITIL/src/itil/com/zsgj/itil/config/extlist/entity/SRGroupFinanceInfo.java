package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class SRGroupFinanceInfo extends BaseObject {
   private java.lang.Long id;
   private com.zsgj.itil.require.entity.SpecialRequirement specialRequire;
   private com.zsgj.itil.finance.entity.RequirementFinanceType financeType;
   private com.zsgj.itil.finance.entity.BatchType batchType;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setSpecialRequire(com.zsgj.itil.require.entity.SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setFinanceType(com.zsgj.itil.finance.entity.RequirementFinanceType financeType){
	     this.financeType=financeType;
   }
   public void setBatchType(com.zsgj.itil.finance.entity.BatchType batchType){
	     this.batchType=batchType;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.zsgj.itil.require.entity.SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public com.zsgj.itil.finance.entity.RequirementFinanceType getFinanceType(){
	     return this.financeType;
   }
   public com.zsgj.itil.finance.entity.BatchType getBatchType(){
	     return this.batchType;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.SRGroupFinanceInfo other = (com.zsgj.itil.config.extlist.entity.SRGroupFinanceInfo) obj;
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
