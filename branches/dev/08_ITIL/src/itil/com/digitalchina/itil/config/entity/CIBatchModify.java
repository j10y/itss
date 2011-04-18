package com.digitalchina.itil.config.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 配置项批量变更实体
 * 
 * @Class Name CIBatchModify
 * @Author lee
 * @Create In Aug 15, 2009
 */
public class CIBatchModify extends BaseObject {

	public final static Integer STATUS_DRAFT = 0;// 草稿
	public final static Integer STATUS_PROCESSING = 2;// 提交处理中
	public final static Integer STATUS_PASSED = 1;// 通过
	public final static Integer STATUS_GIVEUP = 3;// 放弃

	private Long id; // 自动编号
	private String modifyNo; // 变更编号
	private String name; // 变更名称
	private String descn; // 变更描述
	private String reason; // 变更原因
	// private String plan; //变更计划
	private Integer emergent; // 是否紧急，1为紧急变更，0为正常变更
	private UserInfo applyUser; // 变更提交人
	private Date applyDate; // 变更提交时间
	private Integer status; // 状态
	private String attachment; // 附件

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
