package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * �տ�ƻ�
 * @Class Name SRIncomePlan
 * @Author lee
 * @Create In Aug 22, 2009
 */
public class SRIncomePlan extends BaseObject {
   private java.lang.Long id;		//�Զ����
   private com.zsgj.itil.require.entity.SpecialRequirement specialRequire;	//��������
   private java.lang.String name;	//�տ�����
   private java.lang.String descn;	//�տ�����
   private java.lang.Double money;	//�տ���
   private java.util.Date startDate;//�ƻ���ʼʱ��
   private java.util.Date endDate;	//�ƻ�����ʱ��
   private java.lang.Integer isFinish;//�Ƿ������

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setSpecialRequire(com.zsgj.itil.require.entity.SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setName(java.lang.String name){
	     this.name=name;
   }
   public void setDescn(java.lang.String descn){
	     this.descn=descn;
   }
   public void setMoney(java.lang.Double money){
	     this.money=money;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.zsgj.itil.require.entity.SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public java.lang.String getName(){
	     return this.name;
   }
   public java.lang.String getDescn(){
	     return this.descn;
   }
   public java.lang.Double getMoney(){
	     return this.money;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.SRIncomePlan other = (com.zsgj.itil.config.extlist.entity.SRIncomePlan) obj;
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
public java.util.Date getStartDate() {
	return startDate;
}
public void setStartDate(java.util.Date startDate) {
	this.startDate = startDate;
}
public java.lang.Integer getIsFinish() {
	return isFinish;
}
public void setIsFinish(java.lang.Integer isFinish) {
	this.isFinish = isFinish;
}
public java.util.Date getEndDate() {
	return endDate;
}
public void setEndDate(java.util.Date endDate) {
	this.endDate = endDate;
}
} 
