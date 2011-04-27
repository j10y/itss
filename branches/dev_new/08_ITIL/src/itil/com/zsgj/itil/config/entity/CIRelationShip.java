package com.zsgj.itil.config.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.service.entity.ServiceItemType;

/**
 * 配置项关系实体
 * @Class Name CIRelationShip
 * @Author duxh
 * @Create In Feb 22, 2010
 */
public class CIRelationShip extends BaseObject{
	
	public static final Integer DELETE_STATUS  = -1;//已删除
	public static final Integer VALID_STATUS = 1;//正式
	public static final Integer HISTORY_STATUS= 3;//申请历史
	public static final Integer DRAFT_STATUS= 0;//草稿
	
	private Long id;
	private ConfigItemType parentConfigItemType;
	
	private String parentConfigItemCode;
	
	private ServiceItemType parentServiceItemType;
	
	private String parentServiceItemCode;
	
	private ConfigItemType childConfigItemType;
	
	private String childConfigItemCode;
	
	private ServiceItemType childServiceItemType;
	
	private String childServiceItemCode;
	
	private Integer status;
	
	//关系类型
	private CIRelationShipType relationShipType;

	//关系紧密程度
	private CIRelationShipGrade relationShipGrade; 
	
	//归集系数，手输数字
	private Double attachQuotiety; 
	
	//A端技术信息
	private String atechnoInfo;
	//B端技术信息
	private String btechnoInfo;
	//其他信息
	private String otherInfo;
    private UserInfo createUser;
    private Date createDate;
    private UserInfo modifyUser;
    private Date modifyDate;
   
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getParentConfigItemCode() {
		return parentConfigItemCode;
	}
	public void setParentConfigItemCode(String parentConfigItemCode) {
		this.parentConfigItemCode = parentConfigItemCode;
	}
	public String getParentServiceItemCode() {
		return parentServiceItemCode;
	}
	public void setParentServiceItemCode(String parentServiceItemCode) {
		this.parentServiceItemCode = parentServiceItemCode;
	}
	public String getChildConfigItemCode() {
		return childConfigItemCode;
	}
	public void setChildConfigItemCode(String childConfigItemCode) {
		this.childConfigItemCode = childConfigItemCode;
	}
	public String getChildServiceItemCode() {
		return childServiceItemCode;
	}
	public void setChildServiceItemCode(String childServiceItemCode) {
		this.childServiceItemCode = childServiceItemCode;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public ConfigItemType getParentConfigItemType() {
		return parentConfigItemType;
	}
	public void setParentConfigItemType(ConfigItemType parentConfigItemType) {
		this.parentConfigItemType = parentConfigItemType;
	}
	public ServiceItemType getParentServiceItemType() {
		return parentServiceItemType;
	}
	public void setParentServiceItemType(ServiceItemType parentServiceItemType) {
		this.parentServiceItemType = parentServiceItemType;
	}
	public ConfigItemType getChildConfigItemType() {
		return childConfigItemType;
	}
	public void setChildConfigItemType(ConfigItemType childConfigItemType) {
		this.childConfigItemType = childConfigItemType;
	}
	public ServiceItemType getChildServiceItemType() {
		return childServiceItemType;
	}
	public void setChildServiceItemType(ServiceItemType childServiceItemType) {
		this.childServiceItemType = childServiceItemType;
	}
	
	
}
