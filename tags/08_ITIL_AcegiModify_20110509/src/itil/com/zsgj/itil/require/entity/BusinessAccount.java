package com.zsgj.itil.require.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 商务结算申请实体
 * @Class Name BusinessAccount
 * @Author lee
 * @Create In Aug 22, 2009
 */
public class BusinessAccount extends BaseObject{
	public static int STATUS_DRAFT = 0;// 草稿
	public static int STATUS_APPROVING = 1;// 提交审批中
	public static int STATUS_FINISHED = 2;// 通过
	public static int STATUS_DELETE = -1;// 已删除
	
	private Long id;		//自动编号
	private String applyNum;//申请编号
	private SpecialRequirement require;//关联需求申请
	private String descn;		//申请内容
	private UserInfo relationUser;//联系人
	private UserInfo applyUser;	//申请人
	private Date applyDate;		//申请时间
	private Integer status;		//当前状态 0：草稿，1：处理中，2：处理结束
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
