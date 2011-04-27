package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.require.entity.SpecialRequirement;

/**
 * ���������ͬʵ��
 * @Class Name SRprojectContracts
 * @Author Administrator
 * @Create In 2009-11-4
 */
public class SRprojectContracts extends BaseObject {
   private java.lang.Long id;
   private SpecialRequirement specialRequire;	//��������
   private String contractCode;		//��ͬ���
   private String contractName;		//��ͬ����
   private Double curPrice;			//��ǰ����
   private Double maintainPrice;	//��ά����
   private String shareCostCenter;	//��̯�ɱ�����
   private UserInfo costCenterManager;//�ɱ����ĸ�����
   private String descn;			//��ͬ����
   private Date contractBeginDate;	//��ͬ��ʼʱ��
   private Date contractEndDate;	//��ͬ����ʱ��
   private String serviceManagerName;//�׷�
   private String address;			//�׷���ַ
   private UserInfo linkman;		//�׷���ϵ��
   private String tel;				//�׷���ϵ�˵绰
   private String phone;			//�׷���ϵ���ֻ�
   private String email;			//�׷���ϵ���ʼ�
   private String customerName;		//�ҷ�
   private String customerAddress;	//�ҷ���ַ
   private UserInfo custLinkman;	//�ҷ���ϵ��
   private String custTel;			//�ҷ���ϵ�˵绰
   private String custPhone;		//�ҷ���ϵ���ֻ�
   private String custEmail;		//�ҷ���ϵ���ʼ�
   private Date signDate;			//�׷�ǩ��ʱ��
   private Date custSignDate;		//�ҷ�ǩ��ʱ��
   private UserInfo principal;		//�׷�������
   private UserInfo custPrincipal;	//�ҷ�������
   private String attachment;		//����

   public String getAttachment() {
	return attachment;
}
public void setAttachment(String attachment) {
	this.attachment = attachment;
}
public void setId(Long id){
	     this.id=id;
   }
   public void setSpecialRequire(SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setContractCode(String contractCode){
	     this.contractCode=contractCode;
   }
   public void setContractName(String contractName){
	     this.contractName=contractName;
   }
   public void setDescn(String descn){
	     this.descn=descn;
   }
   public void setContractBeginDate(Date contractBeginDate){
	     this.contractBeginDate=contractBeginDate;
   }
   public void setContractEndDate(Date contractEndDate){
	     this.contractEndDate=contractEndDate;
   }
   public void setServiceManagerName(String serviceManagerName){
	     this.serviceManagerName=serviceManagerName;
   }
   public void setAddress(String address){
	     this.address=address;
   }
   public void setLinkman(UserInfo linkman){
	     this.linkman=linkman;
   }
   public void setTel(String tel){
	     this.tel=tel;
   }
   public void setPhone(String phone){
	     this.phone=phone;
   }
   public void setEmail(String email){
	     this.email=email;
   }
   public void setCustomerName(String customerName){
	     this.customerName=customerName;
   }
   public void setCustomerAddress(String customerAddress){
	     this.customerAddress=customerAddress;
   }
   public void setCustLinkman(UserInfo custLinkman){
	     this.custLinkman=custLinkman;
   }
   public void setCustTel(String custTel){
	     this.custTel=custTel;
   }
   public void setCustPhone(String custPhone){
	     this.custPhone=custPhone;
   }
   public void setCustEmail(String custEmail){
	     this.custEmail=custEmail;
   }
   public void setSignDate(Date signDate){
	     this.signDate=signDate;
   }
   public void setCustSignDate(Date custSignDate){
	     this.custSignDate=custSignDate;
   }
   public void setPrincipal(UserInfo principal){
	     this.principal=principal;
   }
   public void setCustPrincipal(UserInfo custPrincipal){
	     this.custPrincipal=custPrincipal;
   }

   public Long getId(){
	     return this.id;
   }
   public SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public String getContractCode(){
	     return this.contractCode;
   }
   public String getContractName(){
	     return this.contractName;
   }
   public String getDescn(){
	     return this.descn;
   }
   public Date getContractBeginDate(){
	     return this.contractBeginDate;
   }
   public Date getContractEndDate(){
	     return this.contractEndDate;
   }
   public String getServiceManagerName(){
	     return this.serviceManagerName;
   }
   public String getAddress(){
	     return this.address;
   }
   public UserInfo getLinkman(){
	     return this.linkman;
   }
   public String getTel(){
	     return this.tel;
   }
   public String getPhone(){
	     return this.phone;
   }
   public String getEmail(){
	     return this.email;
   }
   public String getCustomerName(){
	     return this.customerName;
   }
   public String getCustomerAddress(){
	     return this.customerAddress;
   }
   public UserInfo getCustLinkman(){
	     return this.custLinkman;
   }
   public String getCustTel(){
	     return this.custTel;
   }
   public String getCustPhone(){
	     return this.custPhone;
   }
   public String getCustEmail(){
	     return this.custEmail;
   }
   public Date getSignDate(){
	     return this.signDate;
   }
   public Date getCustSignDate(){
	     return this.custSignDate;
   }
   public UserInfo getPrincipal(){
	     return this.principal;
   }
   public UserInfo getCustPrincipal(){
	     return this.custPrincipal;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final SRprojectContracts other = (SRprojectContracts) obj;
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
public Double getCurPrice() {
	return curPrice;
}
public void setCurPrice(Double curPrice) {
	this.curPrice = curPrice;
}
public Double getMaintainPrice() {
	return maintainPrice;
}
public void setMaintainPrice(Double maintainPrice) {
	this.maintainPrice = maintainPrice;
}
public String getShareCostCenter() {
	return shareCostCenter;
}
public void setShareCostCenter(String shareCostCenter) {
	this.shareCostCenter = shareCostCenter;
}
public UserInfo getCostCenterManager() {
	return costCenterManager;
}
public void setCostCenterManager(UserInfo costCenterManager) {
	this.costCenterManager = costCenterManager;
}
} 
