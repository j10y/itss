package com.digitalchina.itil.config.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * ���������������¼
 * @Class Name CIBatchModifyPlan
 * @Author lee
 * @Create In Aug 15, 2009
 */
public class CIBatchModifyPlan extends BaseObject{
	
	private static final long serialVersionUID = 5235647458388761828L;
	public static final Integer MODIFY_SUCCESS=1;//����ɹ�
	public static final Integer MODIFY_UNSUCCESS=0;//δ����ɹ�
	private Long id;	//�Զ����
	private CIBatchModify batchModify;	//�������ʵ��
	private ConfigItem maintenanceCIRel;//ά����ϵ��������
	private ConfigItem newConfigItem;	
	private ConfigItem oldConfigItem;
	private CIRelationShip newCIRelationShip;
	private CIRelationShip oldCIRelationShip;
	private String descn;	//����
	private Date startDate;	//��ʼʱ��
	private Date endDate;	//����ʱ��
	private UserInfo officer;//ʵʩ������
	private Integer result;	//ʵʩ���
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CIBatchModify getBatchModify() {
		return batchModify;
	}
	public void setBatchModify(CIBatchModify batchModify) {
		this.batchModify = batchModify;
	}
	public ConfigItem getNewConfigItem() {
		return newConfigItem;
	}
	public void setNewConfigItem(ConfigItem newConfigItem) {
		this.newConfigItem = newConfigItem;
	}
	public ConfigItem getOldConfigItem() {
		return oldConfigItem;
	}
	public void setOldConfigItem(ConfigItem oldConfigItem) {
		this.oldConfigItem = oldConfigItem;
	}
	public CIRelationShip getNewCIRelationShip() {
		return newCIRelationShip;
	}
	public void setNewCIRelationShip(CIRelationShip newCIRelationShip) {
		this.newCIRelationShip = newCIRelationShip;
	}
	public CIRelationShip getOldCIRelationShip() {
		return oldCIRelationShip;
	}
	public void setOldCIRelationShip(CIRelationShip oldCIRelationShip) {
		this.oldCIRelationShip = oldCIRelationShip;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public UserInfo getOfficer() {
		return officer;
	}
	public void setOfficer(UserInfo officer) {
		this.officer = officer;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public ConfigItem getMaintenanceCIRel() {
		return maintenanceCIRel;
	}
	public void setMaintenanceCIRel(ConfigItem maintenanceCIRel) {
		this.maintenanceCIRel = maintenanceCIRel;
	}
}
