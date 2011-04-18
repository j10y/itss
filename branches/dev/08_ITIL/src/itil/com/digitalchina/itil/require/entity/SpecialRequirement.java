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

/**
 * IT需求定制类申请实体（后台配置生成）
 * 
 * @Class Name SpecialRequirement
 * @Author lee
 * @Create In Nov 24, 2009
 */
public class SpecialRequirement extends BaseObject {
	private Long id; // 自动编号
	private String name; // 需求名称
	private SpecialRequirement oldApply; // 原申请
	private Integer processType; // 申请类型（流程用）
	private Integer status; // 状态（0草稿，1申请中，2通过）
	private Integer deleteFlag; // 删除标记
	private Long serviceItem; // 关联服务项
	private String applyNum; // 申请编号
	private Date applyDate; // 申请日期
	private Department applyDept; // 申请人所属部门
	private String costConter; // 申请人成本中心
	private UserInfo applyUser; // 申请人
	private String tel; // 申请人座机电话
	private String descn; // 申请描述
	private RequirementLevel requireLevel; // 申请级别，是否加急
	private String reason; // 申请原因
	private String includeAndExpend; // 投入产出价值
	private String attachment; // 附件
	private String otherInfo; // 备注，其他信息
	private UserInfo createUser; // 创建人
	private Date createDate; // 创建日期
	private UserInfo modifyUser; // 修改人
	private Date modifyDate; // 修改日期
	private RequireApplyDefaultAudit flat; // SBU信息（后台维护）
	private String mobilePhone; // 申请人手机
	private UserInfo confirmUser; // IT审批人
	private ConfigItem appConfigItem; // 应用系统
	private UserInfo appManager; // 应用系统管理员
	private DeliveryTeam deliveryTeam; // 交付团队
	private ServiceEngineer mainEngineer; // 交付经理
	private ServiceEngineer assistanEngineer; // 辅助工程师
	private RequirementBigType bigType; // 需求大类
	private RequirementSmallType smallType; // 需求小类
	private Integer isShare; // 分摊类型
	private Double shareFee; // 分摊金额 add zhangzy for 售前申请需要分摊金额字段 in 2009 12 9
	private UserInfo homeFinanceManager; // 本部财务经理
	private UserInfo groupFinanceManager; // 集团财务经理
	private RequirementFinanceType financeType; // 财务分类
	private BatchType batchType; // 批处理分类
	private Integer isNewFactory; // 是否新建工厂
	private Date finishDate; // 用户切望完成日期
	private Date realStartDate; // 真实开始实施时间
	private Date realTestBeginDate; // 真实测试开始时间
	private Date realTestEndDate; // 真实测试结束时间
	private Date realFinishDate; // 真实上线时间

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public void setOldApply(
			com.digitalchina.itil.require.entity.SpecialRequirement oldApply) {
		this.oldApply = oldApply;
	}

	public void setProcessType(java.lang.Integer processType) {
		this.processType = processType;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public void setDeleteFlag(java.lang.Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setServiceItem(java.lang.Long serviceItem) {
		this.serviceItem = serviceItem;
	}

	public void setApplyDate(java.util.Date applyDate) {
		this.applyDate = applyDate;
	}

	public void setApplyDept(
			com.digitalchina.info.framework.security.entity.Department applyDept) {
		this.applyDept = applyDept;
	}

	public void setCostConter(java.lang.String costConter) {
		this.costConter = costConter;
	}

	public void setApplyUser(
			com.digitalchina.info.framework.security.entity.UserInfo applyUser) {
		this.applyUser = applyUser;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	public void setDescn(java.lang.String descn) {
		this.descn = descn;
	}

	public void setRequireLevel(
			com.digitalchina.itil.config.extlist.entity.RequirementLevel requireLevel) {
		this.requireLevel = requireLevel;
	}

	public void setReason(java.lang.String reason) {
		this.reason = reason;
	}

	public void setIncludeAndExpend(java.lang.String includeAndExpend) {
		this.includeAndExpend = includeAndExpend;
	}

	public void setAttachment(java.lang.String attachment) {
		this.attachment = attachment;
	}

	public void setOtherInfo(java.lang.String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public void setCreateUser(
			com.digitalchina.info.framework.security.entity.UserInfo createUser) {
		this.createUser = createUser;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public void setModifyUser(
			com.digitalchina.info.framework.security.entity.UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}

	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public com.digitalchina.itil.require.entity.SpecialRequirement getOldApply() {
		return this.oldApply;
	}

	public java.lang.Integer getProcessType() {
		return this.processType;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public java.lang.Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public java.lang.Long getServiceItem() {
		return this.serviceItem;
	}

	public java.util.Date getApplyDate() {
		return this.applyDate;
	}

	public com.digitalchina.info.framework.security.entity.Department getApplyDept() {
		return this.applyDept;
	}

	public java.lang.String getCostConter() {
		return this.costConter;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getApplyUser() {
		return this.applyUser;
	}

	public java.lang.String getTel() {
		return this.tel;
	}

	public java.lang.String getDescn() {
		return this.descn;
	}

	public com.digitalchina.itil.config.extlist.entity.RequirementLevel getRequireLevel() {
		return this.requireLevel;
	}

	public java.lang.String getReason() {
		return this.reason;
	}

	public java.lang.String getIncludeAndExpend() {
		return this.includeAndExpend;
	}

	public java.lang.String getAttachment() {
		return this.attachment;
	}

	public java.lang.String getOtherInfo() {
		return this.otherInfo;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getCreateUser() {
		return this.createUser;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getModifyUser() {
		return this.modifyUser;
	}

	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}

	public com.digitalchina.itil.require.entity.RequireApplyDefaultAudit getFlat() {
		return flat;
	}

	public void setFlat(
			com.digitalchina.itil.require.entity.RequireApplyDefaultAudit flat) {
		this.flat = flat;
	}

	public java.lang.String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(java.lang.String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(
			com.digitalchina.info.framework.security.entity.UserInfo confirmUser) {
		this.confirmUser = confirmUser;
	}

	public java.util.Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(java.util.Date finishDate) {
		this.finishDate = finishDate;
	}

	public java.util.Date getRealFinishDate() {
		return realFinishDate;
	}

	public void setRealFinishDate(java.util.Date realFinishDate) {
		this.realFinishDate = realFinishDate;
	}

	public java.util.Date getRealStartDate() {
		return realStartDate;
	}

	public void setRealStartDate(java.util.Date realStartDate) {
		this.realStartDate = realStartDate;
	}

	public java.util.Date getRealTestBeginDate() {
		return realTestBeginDate;
	}

	public void setRealTestBeginDate(java.util.Date realTestBeginDate) {
		this.realTestBeginDate = realTestBeginDate;
	}

	public java.util.Date getRealTestEndDate() {
		return realTestEndDate;
	}

	public void setRealTestEndDate(java.util.Date realTestEndDate) {
		this.realTestEndDate = realTestEndDate;
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

	public UserInfo getAppManager() {
		return appManager;
	}

	public void setAppManager(UserInfo appManager) {
		this.appManager = appManager;
	}

	public ConfigItem getAppConfigItem() {
		return appConfigItem;
	}

	public void setAppConfigItem(ConfigItem appConfigItem) {
		this.appConfigItem = appConfigItem;
	}

	public java.lang.String getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(java.lang.String applyNum) {
		this.applyNum = applyNum;
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

	public Double getShareFee() {
		return shareFee;
	}

	public void setShareFee(Double shareFee) {
		this.shareFee = shareFee;
	}

}
