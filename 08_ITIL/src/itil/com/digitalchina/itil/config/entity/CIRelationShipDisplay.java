package com.digitalchina.itil.config.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * �������ϵ��ʾ����
 * @Class Name CIRelationShip
 * @Author duxh
 * @Create In Feb 22, 2010
 */
public class CIRelationShipDisplay extends BaseObject{
	
	private Long id;
	
	private String parentItem;
	
	private String parentItemCode;
	
	private String parentItemType;
	
	private String parentItemName;
	
	private String childItem;
	
	private String childItemCode;
	
	private String childItemType;
	
	private String childItemName;
	
	//��ϵ����
	private String relationShipType;

	//��ϵ���̶ܳ�
	private String relationShipGrade; 
	
	//�鼯ϵ��
	private Double attachQuotiety; 
	
	//A�˼�����Ϣ
	private String atechnoInfo;
	//B�˼�����Ϣ
	private String btechnoInfo;
	//������Ϣ
	private String otherInfo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParentItem() {
		return parentItem;
	}
	public void setParentItem(String parentItem) {
		this.parentItem = parentItem;
	}
	public String getParentItemCode() {
		return parentItemCode;
	}
	public void setParentItemCode(String parentItemCode) {
		this.parentItemCode = parentItemCode;
	}
	public String getParentItemType() {
		return parentItemType;
	}
	public void setParentItemType(String parentItemType) {
		this.parentItemType = parentItemType;
	}
	public String getParentItemName() {
		return parentItemName;
	}
	public void setParentItemName(String parentItemName) {
		this.parentItemName = parentItemName;
	}
	public String getChildItem() {
		return childItem;
	}
	public void setChildItem(String childItem) {
		this.childItem = childItem;
	}
	public String getChildItemCode() {
		return childItemCode;
	}
	public void setChildItemCode(String childItemCode) {
		this.childItemCode = childItemCode;
	}
	public String getChildItemType() {
		return childItemType;
	}
	public void setChildItemType(String childItemType) {
		this.childItemType = childItemType;
	}
	public String getChildItemName() {
		return childItemName;
	}
	public void setChildItemName(String childItemName) {
		this.childItemName = childItemName;
	}
	public String getRelationShipType() {
		return relationShipType;
	}
	public void setRelationShipType(String relationShipType) {
		this.relationShipType = relationShipType;
	}
	public String getRelationShipGrade() {
		return relationShipGrade;
	}
	public void setRelationShipGrade(String relationShipGrade) {
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
	
}
