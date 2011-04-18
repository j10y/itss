package com.digitalchina.itil.config.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * �������������ʵ��
 * 
 * @Class Name CIBatchModify
 * @Author lee
 * @Create In Aug 15, 2009
 */
public class CIBatchModify extends BaseObject {

	public final static Integer STATUS_DRAFT = 0;// �ݸ�
	public final static Integer STATUS_PROCESSING = 2;// �ύ������
	public final static Integer STATUS_PASSED = 1;// ͨ��
	public final static Integer STATUS_GIVEUP = 3;// ����

	private Long id; // �Զ����
	private String modifyNo; // ������
	private String name; // �������
	private String descn; // �������
	private String reason; // ���ԭ��
	// private String plan; //����ƻ�
	private Integer emergent; // �Ƿ������1Ϊ���������0Ϊ�������
	private UserInfo applyUser; // ����ύ��
	private Date applyDate; // ����ύʱ��
	private Integer status; // ״̬
	private String attachment; // ����

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModifyNo() {
		return modifyNo;
	}

	public void setModifyNo(String modifyNo) {
		this.modifyNo = modifyNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	// public String getPlan() {
	// return plan;
	// }
	// public void setPlan(String plan) {
	// this.plan = plan;
	// }
	public Integer getEmergent() {
		return emergent;
	}

	public void setEmergent(Integer emergent) {
		this.emergent = emergent;
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

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
