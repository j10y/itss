package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class SRProjectPlan extends BaseObject {
   private java.lang.Long id;
   private java.lang.String planName;
   private com.zsgj.itil.project.entity.ProjectPlanStatus status;
   private com.zsgj.itil.project.entity.ProjectPlanProgress progress;
   private java.lang.String descn;
   private java.util.Date beginDate;
   private java.util.Date endDate;
   private java.lang.Integer intendHours;
   private com.zsgj.info.framework.security.entity.UserInfo planOwner;
   private com.zsgj.itil.require.entity.SpecialRequirement specialRequire;
   private com.zsgj.itil.config.extlist.entity.SRProjectPlan parentPlan;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setPlanName(java.lang.String planName){
	     this.planName=planName;
   }
   public void setStatus(com.zsgj.itil.project.entity.ProjectPlanStatus status){
	     this.status=status;
   }
   public void setProgress(com.zsgj.itil.project.entity.ProjectPlanProgress progress){
	     this.progress=progress;
   }
   public void setDescn(java.lang.String descn){
	     this.descn=descn;
   }
   public void setBeginDate(java.util.Date beginDate){
	     this.beginDate=beginDate;
   }
   public void setEndDate(java.util.Date endDate){
	     this.endDate=endDate;
   }
   public void setIntendHours(java.lang.Integer intendHours){
	     this.intendHours=intendHours;
   }
   public void setPlanOwner(com.zsgj.info.framework.security.entity.UserInfo planOwner){
	     this.planOwner=planOwner;
   }
   public void setSpecialRequire(com.zsgj.itil.require.entity.SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setParentPlan(com.zsgj.itil.config.extlist.entity.SRProjectPlan parentPlan){
	     this.parentPlan=parentPlan;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getPlanName(){
	     return this.planName;
   }
   public com.zsgj.itil.project.entity.ProjectPlanStatus getStatus(){
	     return this.status;
   }
   public com.zsgj.itil.project.entity.ProjectPlanProgress getProgress(){
	     return this.progress;
   }
   public java.lang.String getDescn(){
	     return this.descn;
   }
   public java.util.Date getBeginDate(){
	     return this.beginDate;
   }
   public java.util.Date getEndDate(){
	     return this.endDate;
   }
   public java.lang.Integer getIntendHours(){
	     return this.intendHours;
   }
   public com.zsgj.info.framework.security.entity.UserInfo getPlanOwner(){
	     return this.planOwner;
   }
   public com.zsgj.itil.require.entity.SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public com.zsgj.itil.config.extlist.entity.SRProjectPlan getParentPlan(){
	     return this.parentPlan;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.SRProjectPlan other = (com.zsgj.itil.config.extlist.entity.SRProjectPlan) obj;
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
