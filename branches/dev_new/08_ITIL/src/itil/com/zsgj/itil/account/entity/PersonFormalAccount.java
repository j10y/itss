package com.zsgj.itil.account.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.SameMailDept;
import com.zsgj.itil.config.extlist.entity.TelephoneType;
import com.zsgj.itil.require.entity.AccountApplyMainTable;

/**
 * 
 * @author gaowen 2009.7.22 个人正式帐号实体
 * 
 */
public class PersonFormalAccount extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -617515541763274286L;
	private Long id;
	private java.lang.String accountName; // 账号名称
	private java.lang.String password; // 账号密码
	private com.zsgj.itil.account.entity.AccountType accountType;
	private com.zsgj.info.framework.security.entity.UserInfo accountowner; // 使用人
	private java.lang.String accountState; // 账号状态
	private java.util.Date createDate; // 生效时间
	private java.lang.String registerServiceRightDesc; // 注册服务权限描述
	private java.lang.String sameRightAccount; // 同权限账号
	private java.lang.String rightsDesc; // 权限说明,特殊需求
	private java.lang.String remarkDesc; // 备注说明
	private java.lang.String attachment; // 附件
	private java.lang.String applyReason; // 申请原因
	private com.zsgj.info.framework.security.entity.UserInfo confirmUser; // 审批人

	private com.zsgj.itil.config.extlist.entity.MailVolume mailValue; // 邮件容量
	private com.zsgj.itil.config.extlist.entity.WWWScanType wwwAccountValue; // www浏览额度
	// BI
	private java.lang.String referSalary; // 是否涉及薪酬
	private java.lang.String telephone; // 手机号码
	// erp
	private com.zsgj.itil.config.extlist.entity.ErpServiceType registerServiceType; // ERP注册服务类型
	private java.lang.String dutyName; // 岗位名称
	private java.lang.String thingCode; // 事物代码
	private java.lang.String controlScope; // 控制范围
	private java.lang.String userRight; // 用户权限
	private java.lang.String operatorScope; // 操作权限
	private java.lang.String erpUserName;// erp用户名
	private com.zsgj.info.framework.security.entity.WorkSpace workSpace; // 工作地点
	private java.lang.String mailServer; // 邮件服务器
	private java.util.Date endDate; // 到期时间
	// MSN
	private java.lang.String otherLinkCompany; // 对方联系单位
	private com.zsgj.itil.config.extlist.entity.AR_DrawSpace drawSpace; // 领卡地点
	private Integer ifHold;// 是否保留
	private PersonFormalAccount olodApplyAccount;
	private AccountApplyMainTable applyId;
    private String stationNumber;//工位号码
	private Double yearMoney;//财年额度
	private TelephoneType telephoneType;//电话类型
    private String telephoneNumber;//电话号码
    private Integer ifSysn;//是否同步通讯录
    private Integer cardState;//令牌卡，电话归还标志
    private String cardNumber;//令牌卡号
    private Double beyondMoney;//超出额度
    private Long departCode;		//部门编号
	private Department department;	//所属部门
    private String costCenterCode;	//成本中心编号
    private SameMailDept sameMailDept;//邮件等价名部门
    private String voip;
    private String departTelephone;//"0" 为个人电话  "1"为部门电话
    
    private String vpnType;//远程接入类型 “0”为硬令牌 “1”为软令牌
    private String pingCode;//软令牌启动密码
 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.lang.String getAccountName() {
		return accountName;
	}

	public void setAccountName(java.lang.String accountName) {
		this.accountName = accountName;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public com.zsgj.itil.account.entity.AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(
			com.zsgj.itil.account.entity.AccountType accountType) {
		this.accountType = accountType;
	}
	

	public Long getDepartCode() {
		return departCode;
	}

	public void setDepartCode(Long departCode) {
		this.departCode = departCode;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getAccountowner() {
		return accountowner;
	}

	public void setAccountowner(
			com.zsgj.info.framework.security.entity.UserInfo accountowner) {
		this.accountowner = accountowner;
	}

	public java.lang.String getAccountState() {
		return accountState;
	}

	public void setAccountState(java.lang.String accountState) {
		this.accountState = accountState;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getRegisterServiceRightDesc() {
		return registerServiceRightDesc;
	}

	public void setRegisterServiceRightDesc(
			java.lang.String registerServiceRightDesc) {
		this.registerServiceRightDesc = registerServiceRightDesc;
	}

	public java.lang.String getSameRightAccount() {
		return sameRightAccount;
	}

	public void setSameRightAccount(java.lang.String sameRightAccount) {
		this.sameRightAccount = sameRightAccount;
	}

	public java.lang.String getRightsDesc() {
		return rightsDesc;
	}

	public void setRightsDesc(java.lang.String rightsDesc) {
		this.rightsDesc = rightsDesc;
	}

	public java.lang.String getRemarkDesc() {
		return remarkDesc;
	}

	public void setRemarkDesc(java.lang.String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}

	public java.lang.String getAttachment() {
		return attachment;
	}

	public void setAttachment(java.lang.String attachment) {
		this.attachment = attachment;
	}
	

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public java.lang.String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(java.lang.String applyReason) {
		this.applyReason = applyReason;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(
			com.zsgj.info.framework.security.entity.UserInfo confirmUser) {
		this.confirmUser = confirmUser;
	}

	public com.zsgj.itil.config.extlist.entity.MailVolume getMailValue() {
		return mailValue;
	}

	public void setMailValue(
			com.zsgj.itil.config.extlist.entity.MailVolume mailValue) {
		this.mailValue = mailValue;
	}

	public com.zsgj.itil.config.extlist.entity.WWWScanType getWwwAccountValue() {
		return wwwAccountValue;
	}

	public void setWwwAccountValue(
			com.zsgj.itil.config.extlist.entity.WWWScanType wwwAccountValue) {
		this.wwwAccountValue = wwwAccountValue;
	}

	public java.lang.String getReferSalary() {
		return referSalary;
	}

	public void setReferSalary(java.lang.String referSalary) {
		this.referSalary = referSalary;
	}

	public java.lang.String getTelephone() {
		return telephone;
	}

	public void setTelephone(java.lang.String telephone) {
		this.telephone = telephone;
	}

	public com.zsgj.itil.config.extlist.entity.ErpServiceType getRegisterServiceType() {
		return registerServiceType;
	}

	public void setRegisterServiceType(
			com.zsgj.itil.config.extlist.entity.ErpServiceType registerServiceType) {
		this.registerServiceType = registerServiceType;
	}

	public java.lang.String getDutyName() {
		return dutyName;
	}

	public void setDutyName(java.lang.String dutyName) {
		this.dutyName = dutyName;
	}

	public java.lang.String getThingCode() {
		return thingCode;
	}

	public void setThingCode(java.lang.String thingCode) {
		this.thingCode = thingCode;
	}

	public java.lang.String getControlScope() {
		return controlScope;
	}

	public void setControlScope(java.lang.String controlScope) {
		this.controlScope = controlScope;
	}

	public java.lang.String getUserRight() {
		return userRight;
	}

	public void setUserRight(java.lang.String userRight) {
		this.userRight = userRight;
	}

	public java.lang.String getOperatorScope() {
		return operatorScope;
	}

	public void setOperatorScope(java.lang.String operatorScope) {
		this.operatorScope = operatorScope;
	}

	public java.lang.String getErpUserName() {
		return erpUserName;
	}

	public void setErpUserName(java.lang.String erpUserName) {
		this.erpUserName = erpUserName;
	}
	



	public com.zsgj.info.framework.security.entity.WorkSpace getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(
			com.zsgj.info.framework.security.entity.WorkSpace workSpace) {
		this.workSpace = workSpace;
	}

	public java.lang.String getMailServer() {
		return mailServer;
	}

	public void setMailServer(java.lang.String mailServer) {
		this.mailServer = mailServer;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public java.lang.String getOtherLinkCompany() {
		return otherLinkCompany;
	}

	public void setOtherLinkCompany(java.lang.String otherLinkCompany) {
		this.otherLinkCompany = otherLinkCompany;
	}

	public com.zsgj.itil.config.extlist.entity.AR_DrawSpace getDrawSpace() {
		return drawSpace;
	}

	public void setDrawSpace(
			com.zsgj.itil.config.extlist.entity.AR_DrawSpace drawSpace) {
		this.drawSpace = drawSpace;
	}

	public Integer getIfHold() {
		return ifHold;
	}

	public void setIfHold(Integer ifHold) {
		this.ifHold = ifHold;
	}
	


	public PersonFormalAccount getOlodApplyAccount() {
		return olodApplyAccount;
	}

	public void setOlodApplyAccount(PersonFormalAccount olodApplyAccount) {
		this.olodApplyAccount = olodApplyAccount;
	}

	public AccountApplyMainTable getApplyId() {
		return applyId;
	}

	public void setApplyId(AccountApplyMainTable applyId) {
		this.applyId = applyId;
	}
	
	
	

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public Double getYearMoney() {
		return yearMoney;
	}

	public void setYearMoney(Double yearMoney) {
		this.yearMoney = yearMoney;
	}

	public TelephoneType getTelephoneType() {
		return telephoneType;
	}

	public void setTelephoneType(TelephoneType telephoneType) {
		this.telephoneType = telephoneType;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public Integer getIfSysn() {
		return ifSysn;
	}

	public void setIfSysn(Integer ifSysn) {
		this.ifSysn = ifSysn;
	}
	
	

	public Double getBeyondMoney() {
		return beyondMoney;
	}

	public void setBeyondMoney(Double beyondMoney) {
		this.beyondMoney = beyondMoney;
	}

	public Integer getCardState() {
		return cardState;
	}

	public void setCardState(Integer cardState) {
		this.cardState = cardState;
	}
	

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public SameMailDept getSameMailDept() {
		return sameMailDept;
	}

	public void setSameMailDept(SameMailDept sameMailDept) {
		this.sameMailDept = sameMailDept;
	}
	
	
	
	

	public String getVoip() {
		return voip;
	}

	public void setVoip(String voip) {
		this.voip = voip;
	}
	
	
	

	public String getDepartTelephone() {
		return departTelephone;
	}

	public void setDepartTelephone(String departTelephone) {
		this.departTelephone = departTelephone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accountName == null) ? 0 : accountName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PersonFormalAccount other = (PersonFormalAccount) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		return true;
	}

	public String getVpnType() {
		return vpnType;
	}

	public void setVpnType(String vpnType) {
		this.vpnType = vpnType;
	}

	public String getPingCode() {
		return pingCode;
	}

	public void setPingCode(String pingCode) {
		this.pingCode = pingCode;
	}

}
