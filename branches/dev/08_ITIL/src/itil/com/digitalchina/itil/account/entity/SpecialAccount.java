package com.digitalchina.itil.account.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.Platform;
import com.digitalchina.info.framework.security.entity.SameMailDept;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;

/**
 * 
 * @author gaowen 2009.7.22 ������ʽ�ʺ�ʵ��
 * 
 */
public class SpecialAccount extends BaseObject {
	private Long id;
	private String  accountName; // �˺�����
	private java.lang.String password; // �˺�����
	private com.digitalchina.itil.account.entity.AccountType accountType;
	private com.digitalchina.info.framework.security.entity.UserInfo accountOldUser; // �ʺ�ԭ������
	private com.digitalchina.info.framework.security.entity.UserInfo accountNowUser; // �ʺ���������

	private java.lang.String accountState; // �˺�״̬
	private java.util.Date createDate; // ��Чʱ��
	private java.lang.String registerServiceRightDesc; // ע�����Ȩ������
	private java.lang.String sameRightAccount; // ͬȨ���˺�
	private java.lang.String rightsDesc; // Ȩ��˵��
	private java.lang.String remarkDesc; // ��ע˵��
	private java.lang.String attachment; // ����
	private java.lang.String applyReason; // ����ԭ��
	private com.digitalchina.info.framework.security.entity.UserInfo confirmUser; // ������

	private com.digitalchina.itil.config.extlist.entity.MailVolume mailValue; // �ʼ�����
	private com.digitalchina.itil.config.extlist.entity.WWWScanType wwwAccountValue; // www������
	// BI
	private java.lang.String referSalary; // �Ƿ��漰н��
	private java.lang.String telephone; // �ֻ�����
	// erp
	private com.digitalchina.itil.config.extlist.entity.ErpServiceType registerServiceType; // ERPע���������
	private java.lang.String dutyName; // ��λ����
	private java.lang.String thingCode; // �������
	private java.lang.String controlScope; // ���Ʒ�Χ
	private java.lang.String userRight; // �û�Ȩ��
	private java.lang.String operatorScope; // ����Ȩ��
	private java.lang.String erpUserName;// erp�û���
	private com.digitalchina.info.framework.security.entity.WorkSpace workSpace; // �����ص�
	private String mailServer; // �ʼ�������
	private java.util.Date endDate; // ����ʱ��
	// MSN
	private java.lang.String otherLinkCompany; // �Է���ϵ��λ
	private com.digitalchina.itil.config.extlist.entity.AR_DrawSpace drawSpace; // �쿨�ص�
	private Integer cardState;//���ƿ����绰�黹��־
	 private String cardNumber;//���ƿ���
	private Integer ifHold;// �Ƿ���
    private Integer ifLocked;//�Ƿ�����
    private Integer isConfrim;//�Ƿ�ȷ��
    private SpecialAccount olodApplyAccount;
	private AccountApplyMainTable applyId;
	private Department department;	//��������
	private String costCenterCode;	//�ɱ����ı��
	private SameMailDept sameMailDept;//�ʼ��ȼ�������
	
    private String vpnType;//Զ�̽������� ��0��ΪӲ���� ��1��Ϊ������
    private String pingCode;//��������������
    //win7
    private DeviceType deviceType;//�豸���
    private String hardwareId;//Ӳ�����к�
    private Win7PlatForm win7PaltForm;//ƽ̨
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
