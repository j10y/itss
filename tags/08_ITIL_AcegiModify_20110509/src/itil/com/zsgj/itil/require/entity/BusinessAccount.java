package com.zsgj.itil.require.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * �����������ʵ��
 * @Class Name BusinessAccount
 * @Author lee
 * @Create In Aug 22, 2009
 */
public class BusinessAccount extends BaseObject{
	public static int STATUS_DRAFT = 0;// �ݸ�
	public static int STATUS_APPROVING = 1;// �ύ������
	public static int STATUS_FINISHED = 2;// ͨ��
	public static int STATUS_DELETE = -1;// ��ɾ��
	
	private Long id;		//�Զ����
	private String applyNum;//������
	private SpecialRequirement require;//������������
	private String descn;		//��������
	private UserInfo relationUser;//��ϵ��
	private UserInfo applyUser;	//������
	private Date applyDate;		//����ʱ��
	private Integer status;		//��ǰ״̬ 0���ݸ壬1�������У�2���������
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApplyNum() {
		return applyNum;
	}
	
	public UserInfo getRelationUser() {
		return relationUser;
	}
	public void setRelationUser(UserInfo relationUser) {
		this.relationUser = relationUser;
	}
	public void setApplyNum(String applyNum) {
		this.applyNum = applyNum;
	}
	public SpecialRequirement getRequire() {
		return require;
	}
	public void setRequire(SpecialRequirement require) {
		this.require = require;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public UserInfo getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(UserInfo applyUser) {
		this.applyUser = applyUser;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
