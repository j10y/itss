package com.zsgj.itil.config.extci.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.config.extlist.entity.ComputerType;

public class ComputerEvent extends BaseObject {
   private java.lang.Long id;
   private java.lang.String brand;
   private java.lang.String model;
   private java.lang.String remark;
   private ComputerType type;
   private String cisn;
   private String assetsNum;
   
   
   public String getCisn() {
	return cisn;
	}
	public void setCisn(String cisn) {
		this.cisn = cisn;
	}
	public String getAssetsNum() {
		return assetsNum;
	}
	public void setAssetsNum(String assetsNum) {
		this.assetsNum = assetsNum;
	}
   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setBrand(java.lang.String brand){
	     this.brand=brand;
   }
   public void setModel(java.lang.String model){
	     this.model=model;
   }
   public void setRemark(java.lang.String remark){
	     this.remark=remark;
   }
   public void setType(ComputerType type){
	     this.type=type;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getBrand(){
	     return this.brand;
   }
   public java.lang.String getModel(){
	     return this.model;
   }
   public java.lang.String getRemark(){
	     return this.remark;
   }
   public ComputerType getType(){
	     return this.type;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extci.entity.ComputerEvent other = (com.zsgj.itil.config.extci.entity.ComputerEvent) obj;
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
