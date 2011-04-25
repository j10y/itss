package com.digitalchina.itil.account.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.Platform;
import com.digitalchina.info.framework.security.entity.SameMailDept;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;

/**
 * 
 * @author gaowen 2009.7.22 个人正式帐号实体
 * 
 */
public class SpecialAccount extends BaseObject {
	private Long id;
	private String  accountName; // 账号名称
	private java.lang.String password; // 账号密码
	private com.digitalchina.itil.account.entity.AccountType accountType;
	private com.digitalchina.info.framework.security.entity.UserInfo accountOldUser; // 帐号原所有者
	private com.digitalchina.info.framework.security.entity.UserInfo accountNowUser; // 帐号现所有者

	private java.lang.String accountState; // 账号状态
	private java.util.Date createDate; // 生效时间
	private java.lang.String registerServiceRightDesc; // 注册服务权限描述
	private java.lang.String sameRightAccount; // 同权限账号
	private java.lang.String rightsDesc; // 权限说明
	private java.lang.String remarkDesc; // 备注说明
	private java.lang.String attachment; // 附件
	private java.lang.String applyReason; // 申请原因
	private com.digitalchina.info.framework.security.entity.UserInfo confirmUser; // 审批人

	private com.digitalchina.itil.config.extlist.entity.MailVolume mailValue; // 邮件容量
	private com.digitalchina.itil.config.extlist.entity.WWWScanType wwwAccountValue; // www浏览额度
	// BI
	private java.lang.String referSalary; // 是否涉及薪酬
	private java.lang.String telephone; // 手机号码
	// erp
	private com.digitalchina.itil.config.extlist.entity.ErpServiceType registerServiceType; // ERP注册服务类型
	private java.lang.String dutyName; // 岗位名称
	private java.lang.String thingCode; // 事物代码
	private java.lang.String controlScope; // 控制范围
	private java.lang.String userRight; // 用户权限
	private java.lang.String operatorScope; // 操作权限
	private java.lang.String erpUserName;// erp用户名
	private com.digitalchina.info.framework.security.entity.WorkSpace workSpace; // 工作地点
	private String mailServer; // 邮件服务器
	private java.util.Date endDate; // 到期时间
	// MSN
	private java.lang.String otherLinkCompany; // 对方联系单位
	private com.digitalchina.itil.config.extlist.entity.AR_DrawSpace drawSpace; // 领卡地点
	private Integer cardState;//令牌卡，电话归还标志
	 private String cardNumber;//令牌卡号
	private Integer ifHold;// 是否保留
    private Integer ifLocked;//是否锁定
    private Integer isConfrim;//是否确认
    private SpecialAccount olodApplyAccount;
	private AccountApplyMainTable applyId;
	private Department department;	//所属部门
	private String costCenterCode;	//成本中心编号
	private SameMailDept sameMailDept;//邮件等价名部门
	
    private String vpnType;//远程接入类型 “0”为硬令牌 “1”为软令牌
    private String pingCode;//软令牌启动密码
    //win7
    private DeviceType deviceType;//设备编号
    private String hardwareId;//硬件序列号
    private Win7PlatForm win7PaltForm;//平台
    private String writerItcode;
    

	public String getWriterItcode() {
		return writerItcode;
	}

	public void setWriterItcode(String writerItcode) {
		this.writerItcode = writerItcode;
	}

	public Win7PlatForm getWin7PaltForm() {
		return win7PaltForm;
	}

	public void setWin7PaltForm(Win7PlatForm win7PaltForm) {
		this.win7PaltForm = win7PaltForm;
	}



	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	
	

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public java.lang.String getPassword() {
		return password;
	}
	

	public Integer getCardState() {
		return cardState;
	}

	public void setCardState(Integer cardState) {
		this.cardState = cardState;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public com.digitalchina.itil.account.entity.AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(
			com.digitalchina.itil.account.entity.AccountType accountType) {
		this.accountType = accountType;
	}

//	public com.digitalchina.info.framework.security.entity.UserInfo getAccountowner() {
//		return accountowner;
//	}
//
//	public void setAccountowner(
//			com.digitalchina.info.framework.security.entity.UserInfo accountowner) {
//		this.accountowner = accountowner;
//	}

	public java.lang.String getAccountState() {
		return accountState;
	}
	

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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

	public java.lang.String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(java.lang.String applyReason) {
		this.applyReason = applyReason;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(
			com.digitalchina.info.framework.security.entity.UserInfo confirmUser) {
		this.confirmUser = confirmUser;
	}

	public com.digitalchina.itil.config.extlist.entity.MailVolume getMailValue() {
		return mailValue;
	}

	public void setMailValue(
			com.digitalchina.itil.config.extlist.entity.MailVolume mailValue) {
		this.mailValue = mailValue;
	}

	public com.digitalchina.itil.config.extlist.entity.WWWScanType getWwwAccountValue() {
		return wwwAccountValue;
	}

	public void setWwwAccountValue(
			com.digitalchina.itil.config.extlist.entity.WWWScanType wwwAccountValue) {
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

	public com.digitalchina.itil.config.extlist.entity.ErpServiceType getRegisterServiceType() {
		return registerServiceType;
	}

	public void setRegisterServiceType(
			com.digitalchina.itil.config.extlist.entity.ErpServiceType registerServiceType) {
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

	

	public com.digitalchina.info.framework.security.entity.WorkSpace getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(
			com.digitalchina.info.framework.security.entity.WorkSpace workSpace) {
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

	public com.digitalchina.itil.config.extlist.entity.AR_DrawSpace getDrawSpace() {
		return drawSpace;
	}

	public void setDrawSpace(
			com.digitalchina.itil.config.extlist.entity.AR_DrawSpace drawSpace) {
		this.drawSpace = drawSpace;
	}

	public Integer getIfHold() {
		return ifHold;
	}

	public void setIfHold(Integer ifHold) {
		this.ifHold = ifHold;
	}
	


	public SpecialAccount getOlodApplyAccount() {
		return olodApplyAccount;
	}

	public void setOlodApplyAccount(SpecialAccount olodApplyAccount) {
		this.olodApplyAccount = olodApplyAccount;
	}

	public AccountApplyMainTable getApplyId() {
		return applyId;
	}

	public void setApplyId(AccountApplyMainTable applyId) {
		this.applyId = applyId;
	}
	
	
	

	public com.digitalchina.info.framework.security.entity.UserInfo getAccountOldUser() {
		return accountOldUser;
	}

	public void setAccountOldUser(
			com.digitalchina.info.framework.security.entity.UserInfo accountOldUser) {
		this.accountOldUser = accountOldUser;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getAccountNowUser() {
		return accountNowUser;
	}

	public void setAccountNowUser(
			com.digitalchina.info.framework.security.entity.UserInfo accountNowUser) {
		this.accountNowUser = accountNowUser;
	}

	public Integer getIfLocked() {
		return ifLocked;
	}

	public void setIfLocked(Integer ifLocked) {
		this.ifLocked = ifLocked;
	}
	
	

	public Integer getIsConfrim() {
		return isConfrim;
	}

	public void setIsConfrim(Integer isConfrim) {
		this.isConfrim = isConfrim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accountName == null) ? 0 : accountName.hashCode());
		return result;
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

	

	public String getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SpecialAccount other = (SpecialAccount) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		return true;
	}

}
