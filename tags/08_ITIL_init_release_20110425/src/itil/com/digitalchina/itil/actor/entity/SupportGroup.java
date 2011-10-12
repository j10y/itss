package com.digitalchina.itil.actor.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ֧���飨support group��
 * 
 * @Class Name SupportGroup
 * @Author sa
 * @Create In 2009-3-16
 */
public class SupportGroup extends BaseObject {
	public static final Integer DELETEFLAG_DELETE=1;
	public static final Integer DELETEFLAG_USEING=0;
	private java.lang.Long id;
	private java.lang.String groupName;
	// ��ϵͳ�û�����ѡ��
	private com.digitalchina.info.framework.security.entity.UserInfo groupLeader;
	//add by lee for group confirmer in 20090812 begin
	private com.digitalchina.info.framework.security.entity.UserInfo groupConfirmer;//����������
	//add by lee for group confirmer in 20090812 end
	//add by guoxl for group confirmer in 20090812 begin
	private com.digitalchina.info.framework.security.entity.UserInfo groupContractOrFileer;//��ͬ�ļ�������
	
	private com.digitalchina.info.framework.security.entity.UserInfo groupSolutioner;//�������������
	
	private com.digitalchina.info.framework.security.entity.UserInfo groupAlterer;//���������
	//add by guoxl for group confirmer in 20090812 end
	private com.digitalchina.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;
	private Integer deleteFlag; // 0ʹ���� 1 ����ɾ��
	private SupportGroupRank groupRank;

	public SupportGroupRank getGroupRank() {
		return groupRank;
	}

	public void setGroupRank(SupportGroupRank groupRank) {
		this.groupRank = groupRank;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setGroupName(java.lang.String groupName) {
		this.groupName = groupName;
	}

	public void setGroupLeader(
			com.digitalchina.info.framework.security.entity.UserInfo groupLeader) {
		this.groupLeader = groupLeader;
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

	public java.lang.String getGroupName() {
		return this.groupName;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getGroupLeader() {
		return this.groupLeader;
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

	public com.digitalchina.info.framework.security.entity.UserInfo getGroupConfirmer() {
		return groupConfirmer;
	}

	public void setGroupConfirmer(
			com.digitalchina.info.framework.security.entity.UserInfo groupConfirmer) {
		this.groupConfirmer = groupConfirmer;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getGroupContractOrFileer() {
		return groupContractOrFileer;
	}

	public void setGroupContractOrFileer(
			com.digitalchina.info.framework.security.entity.UserInfo groupContractOrFileer) {
		this.groupContractOrFileer = groupContractOrFileer;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getGroupSolutioner() {
		return groupSolutioner;
	}

	public void setGroupSolutioner(
			com.digitalchina.info.framework.security.entity.UserInfo groupSolutioner) {
		this.groupSolutioner = groupSolutioner;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getGroupAlterer() {
		return groupAlterer;
	}

	public void setGroupAlterer(
			com.digitalchina.info.framework.security.entity.UserInfo groupAlterer) {
		this.groupAlterer = groupAlterer;
	}
}