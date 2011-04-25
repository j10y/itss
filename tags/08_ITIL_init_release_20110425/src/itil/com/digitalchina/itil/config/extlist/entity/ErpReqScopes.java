package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class ErpReqScopes extends BaseObject {
   private java.lang.Long id;
   private com.digitalchina.itil.require.entity.ERP_NormalNeed erpReq;
   private com.digitalchina.itil.config.extlist.entity.ErpUseScope useScope;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setErpReq(com.digitalchina.itil.require.entity.ERP_NormalNeed repReq){
	     this.erpReq=repReq;
   }
   public void setUseScope(com.digitalchina.itil.config.extlist.entity.ErpUseScope useScope){
	     this.useScope=useScope;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.digitalchina.itil.require.entity.ERP_NormalNeed getErpReq(){
	     return this.erpReq;
   }
   public com.digitalchina.itil.config.extlist.entity.ErpUseScope getUseScope(){
	     return this.useScope;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.ErpReqScopes other = (com.digitalchina.itil.config.extlist.entity.ErpReqScopes) obj;
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
