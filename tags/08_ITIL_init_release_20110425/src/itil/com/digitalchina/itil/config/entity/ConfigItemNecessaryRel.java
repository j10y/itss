package com.digitalchina.itil.config.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class ConfigItemNecessaryRel extends BaseObject {

	private static final long serialVersionUID = 6528738781605580526L;

	public static final Integer PARENT_CHILD_REL_TYPE = 1;
	public static final Integer CHILD_PARENT_REL_TYPE = 2;

	public static final Integer ISOPTIONAL_YES = 1;
	public static final Integer ISOPTIONAL_NO = 0;

	private ConfigItemType configItemType;

	private ConfigItemType otherConfigItemType;

	private Integer parentOrChildType;

	// 关系类型
	private CIRelationShipType relationShipType;

	// 关系紧密程度
	private CIRelationShipGrade relationShipGrade;

	// 归集系数，手输数字
	private Double attachQuotiety;

	// A端技术信息
	private String atechnoInfo;
	
	private String atechnoInfoDisplayName;
	
	private Integer atechnoInfoAllowBlank;
	
	private String atechnoInfoTip;
	// B端技术信息
	private String btechnoInfo;
	
	private String btechnoInfoDisplayName;
	
	private Integer btechnoInfoAllowBlank;
	
	private String btechnoInfoTip;
	// 其他信息
	private String otherInfo;

	private Integer isOptional;// 是否可选

	private String description;

	private Date createDate;
	private UserInfo createUser;
	private Date modifyDate;
	private UserInfo modifyUser;

	public Integer getParentOrChildType() {
		return parentOrChildType;
	}

	public void setParentOrChildType(Integer parentOrChildType) {
		this.parentOrChildType = parentOrChildType;
	}

	public Integer getIsOptional() {
		return isOptional;
	}

	public void setIsOptional(Integer isOptional) {
		this.isOptional = isOptional;
	}

	public ConfigItemType getOtherConfigItemType() {
		return otherConfigItemType;
	}

	public void setOtherConfigItemType(ConfigItemType otherConfigItemType) {
		this.otherConfigItemType = otherConfigItemType;
	}

	public ConfigItemType getConfigItemType() {
		return configItemType;
	}

	public void setConfigItemType(ConfigItemType configItemType) {
		this.configItemType = configItemType;
	}

	public CIRelationShipType getRelationShipType() {
		return relationShipType;
	}

	public void setRelationShipType(CIRelationShipType relationShipType) {
		this.relationShipType = relationShipType;
	}

	public CIRelationShipGrade getRelationShipGrade() {
		return relationShipGrade;
	}

	public void setRelationShipGrade(CIRelationShipGrade relationShipGrade) {
		this.relationShipGrade = relationShipGrade;
	}

	public Double getAttachQuotiety() {
		return attachQuotiety;
	}

	public void setAttachQuotiety(Double attachQuotiety) {
		this.attachQuotiety = attachQuotiety;
	}

	public String getAtechnoInfo() {
		return atechnoInfo;
	}

	public void setAtechnoInfo(String atechnoInfo) {
		this.atechnoInfo = atechnoInfo;
	}

	public String getBtechnoInfo() {
		return btechnoInfo;
	}

	public void setBtechnoInfo(String btechnoInfo) {
		this.btechnoInfo = btechnoInfo;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public UserInfo getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAtechnoInfoDisplayName() {
		return atechnoInfoDisplayName;
	}

	public void setAtechnoInfoDisplayName(String atechnoInfoDisplayName) {
		this.atechnoInfoDisplayName = atechnoInfoDisplayName;
	}

	public String getAtechnoInfoTip() {
		return atechnoInfoTip;
	}

	public void setAtechnoInfoTip(String atechnoInfoTip) {
		this.atechnoInfoTip = atechnoInfoTip;
	}

	public String getBtechnoInfoDisplayName() {
		return btechnoInfoDisplayName;
	}

	public void setBtechnoInfoDisplayName(String btechnoInfoDisplayName) {
		this.btechnoInfoDisplayName = btechnoInfoDisplayName;
	}

	public String getBtechnoInfoTip() {
		return btechnoInfoTip;
	}

	public void setBtechnoInfoTip(String btechnoInfoTip) {
		this.btechnoInfoTip = btechnoInfoTip;
	}

	public Integer getAtechnoInfoAllowBlank() {
		return atechnoInfoAllowBlank;
	}

	public void setAtechnoInfoAllowBlank(Integer atechnoInfoAllowBlank) {
		this.atechnoInfoAllowBlank = atechnoInfoAllowBlank;
	}

	public Integer getBtechnoInfoAllowBlank() {
		return btechnoInfoAllowBlank;
	}

	public void setBtechnoInfoAllowBlank(Integer btechnoInfoAllowBlank) {
		this.btechnoInfoAllowBlank = btechnoInfoAllowBlank;
	}

}
