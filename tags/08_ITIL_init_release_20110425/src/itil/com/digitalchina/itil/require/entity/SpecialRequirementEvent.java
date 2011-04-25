package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.extci.entity.DeliveryTeam;
import com.digitalchina.itil.config.extci.entity.ServiceEngineer;
import com.digitalchina.itil.config.extlist.entity.RequirementBigType;
import com.digitalchina.itil.config.extlist.entity.RequirementLevel;
import com.digitalchina.itil.config.extlist.entity.RequirementSmallType;
import com.digitalchina.itil.finance.entity.BatchType;
import com.digitalchina.itil.finance.entity.RequirementFinanceType;

public class SpecialRequirementEvent extends BaseObject {
	private java.lang.Long id;
	private java.lang.Long rootId;
	private String name; // ��������
	private SpecialRequirement oldApply; // ԭ����
	private Integer processType; // �������ͣ������ã�
	private Integer status; // ״̬��0�ݸ壬1�����У�2ͨ����
	private Integer deleteFlag; // ɾ�����
	private Long serviceItem; // ����������
	private String applyNum; // ������
	private Date applyDate; // ��������
	private Department applyDept; // ��������������
	private String costConter; // �����˳ɱ�����
	private UserInfo applyUser; // ������
	private String tel; // �����������绰
	private String descn; // ��������
	private RequirementLevel requireLevel; // ���뼶���Ƿ�Ӽ�
	private String reason; // ����ԭ��
	private String includeAndExpend; // Ͷ�������ֵ
	private String attachment; // ����
	private String otherInfo; // ��ע��������Ϣ
	private UserInfo createUser; // ������
	private Date createDate; // ��������
	private UserInfo modifyUser; // �޸���
	private Date modifyDate; // �޸�����
	private RequireApplyDefaultAudit flat; // SBU��Ϣ����̨ά����
	private String mobilePhone; // �������ֻ�
	private UserInfo confirmUser; // ������
	private ConfigItem appConfigItem; // Ӧ��ϵͳ
	private UserInfo appManager; // Ӧ��ϵͳ����Ա
	private DeliveryTeam deliveryTeam; //�����Ŷ�
	private ServiceEngineer mainEngineer; // ��������
	private ServiceEngineer assistanEngineer; // ��������ʦ
	private RequirementBigType bigType; // �������
	private RequirementSmallType smallType; // ����С��
	private Integer isShare; // ��̯����
	private UserInfo homeFinanceManager; // ����������
	private UserInfo groupFinanceManager; // ���Ų�����
	private RequirementFinanceType financeType; // �������
	private BatchType batchType; // ���������
	private Integer isNewFactory; // �Ƿ��½�����
	private Date finishDate; // �û������������
	private Date realStartDate; // ��ʵ��ʼʵʩʱ��
	private Date realTestBeginDate; // ��ʵ���Կ�ʼʱ��
	private Date realTestEndDate; // ��ʵ���Խ���ʱ��
	private Date realFinishDate; // ��ʵ����ʱ��

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getRootId() {
		return rootId;
	}

	public void setRootId(java.lang.Long rootId) {
		this.rootId = rootId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SpecialRequirement getOldApply() {
		return oldApply;
	}

	public void setOldApply(SpecialRequirement oldApply) {
		this.oldApply = oldApply;
	}

	public Integer getProcessType() {
		return processType;
	}

	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(Long serviceItem) {
		this.serviceItem = serviceItem;
	}

	public String getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(String applyNum) {
		this.applyNum = applyNum;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Department getApplyDept() {
		return applyDept;
	}

	public void setApplyDept(Department applyDept) {
		this.applyDept = applyDept;
	}

	public String getCostConter() {
		return costConter;
	}

	public void setCostConter(String costConter) {
		this.costConter = costConter;
	}

	public UserInfo getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(UserInfo applyUser) {
		this.applyUser = applyUser;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public RequirementLevel getRequireLevel() {
		return requireLevel;
	}

	public void setRequireLevel(RequirementLevel requireLevel) {
		this.requireLevel = requireLevel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIncludeAndExpend() {
		return includeAndExpend;
	}

	public void setIncludeAndExpend(String includeAndExpend) {
		this.includeAndExpend = includeAndExpend;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public UserInfo getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public RequireApplyDefaultAudit getFlat() {
		return flat;
	}

	public void setFlat(RequireApplyDefaultAudit flat) {
		this.flat = flat;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public UserInfo getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(UserInfo confirmUser) {
		this.confirmUser = confirmUser;
	}

	public ConfigItem getAppConfigItem() {
		return appConfigItem;
	}

	public void setAppConfigItem(ConfigItem appConfigItem) {
		this.appConfigItem = appConfigItem;
	}

	public UserInfo getAppManager() {
		return appManager;
	}

	public void setAppManager(UserInfo appManager) {
		this.appManager = appManager;
	}

	public DeliveryTeam getDeliveryTeam() {
		return deliveryTeam;
	}

	public void setDeliveryTeam(DeliveryTeam deliveryTeam) {
		this.deliveryTeam = deliveryTeam;
	}

	public ServiceEngineer getMainEngineer() {
		return mainEngineer;
	}

	public void setMainEngineer(ServiceEngineer mainEngineer) {
		this.mainEngineer = mainEngineer;
	}

	public ServiceEngineer getAssistanEngineer() {
		return assistanEngineer;
	}

	public void setAssistanEngineer(ServiceEngineer assistanEngineer) {
		this.assistanEngineer = assistanEngineer;
	}

	public RequirementBigType getBigType() {
		return bigType;
	}

	public void setBigType(RequirementBigType bigType) {
		this.bigType = bigType;
	}

	public RequirementSmallType getSmallType() {
		return smallType;
	}

	public void setSmallType(RequirementSmallType smallType) {
		this.smallType = smallType;
	}

	public Integer getIsShare() {
		return isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	public UserInfo getHomeFinanceManager() {
		return homeFinanceManager;
	}

	public void setHomeFinanceManager(UserInfo homeFinanceManager) {
		this.homeFinanceManager = homeFinanceManager;
	}

	public UserInfo getGroupFinanceManager() {
		return groupFinanceManager;
	}

	public void setGroupFinanceManager(UserInfo groupFinanceManager) {
		this.groupFinanceManager = groupFinanceManager;
	}

	public RequirementFinanceType getFinanceType() {
		return financeType;
	}

	public void setFinanceType(RequirementFinanceType financeType) {
		this.financeType = financeType;
	}

	public BatchType getBatchType() {
		return batchType;
	}

	public void setBatchType(BatchType batchType) {
		this.batchType = batchType;
	}

	public Integer getIsNewFactory() {
		return isNewFactory;
	}

	public void setIsNewFactory(Integer isNewFactory) {
		this.isNewFactory = isNewFactory;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getRealStartDate() {
		return realStartDate;
	}

	public void setRealStartDate(Date realStartDate) {
		this.realStartDate = realStartDate;
	}

	public Date getRealTestBeginDate() {
		return realTestBeginDate;
	}

	public void setRealTestBeginDate(Date realTestBeginDate) {
		this.realTestBeginDate = realTestBeginDate;
	}

	public Date getRealTestEndDate() {
		return realTestEndDate;
	}

	public void setRealTestEndDate(Date realTestEndDate) {
		this.realTestEndDate = realTestEndDate;
	}

	public Date getRealFinishDate() {
		return realFinishDate;
	}

	public void setRealFinishDate(Date realFinishDate) {
		this.realFinishDate = realFinishDate;
	}

}
