package com.digitalchina.itil.config.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 配置项批量变更记录
 * @Class Name CIBatchModifyPlan
 * @Author lee
 * @Create In Aug 15, 2009
 */
public class CIBatchModifyPlan extends BaseObject{
	
	private static final long serialVersionUID = 5235647458388761828L;
	public static final Integer MODIFY_SUCCESS=1;//变更成功
	public static final Integer MODIFY_UNSUCCESS=0;//未变更成功
	private Long id;	//自动编号
	private CIBatchModify batchModify;	//批量变更实体
	private ConfigItem maintenanceCIRel;//维护关系的配置项
	private ConfigItem newConfigItem;	
	private ConfigItem oldConfigItem;
	private CIRelationShip newCIRelationShip;
	private CIRelationShip oldCIRelationShip;
	private String descn;	//内容
	private Date startDate;	//开始时间
	private Date endDate;	//结束时间
	private UserInfo officer;//实施负责人
	private Integer result;	//实施结果
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
