package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 问题实体
 * @Class Name Problem
 * @Author sa
 * @Create In 2008-11-9
 */
public class Problem extends BaseObject {
	private Long id;
	//private String problemName;//问题名称
	private String summary; //摘要
	private String description; //描述
	private String remark; //附加信息,备注
	
	private ProblemStatus status;//状态
	//问题提交人
	private UserInfo submitUser;
	//问题的联系人名称
	private UserInfo contactUser;
	//问题联系人邮件
	private String contactEmail;
	//问题联系人电话
	private String contactPhone;
	
	//问题创建时间
	private Date submitTime;
	//问题最后修改时间
	private Date modifyTime;
	//问题关闭日期
	private Date closedDate;
	//add by guoxl in 20090803 begin
	private String problemCisn;
	//add by guoxl in 20090803 end
	private String files; //所有附件 
	public Date getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public UserInfo getContactUser() {
		return contactUser;
	}
	public void setContactUser(UserInfo contactUser) {
		this.contactUser = contactUser;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ProblemStatus getStatus() {
		return status;
	}
	public void setStatus(ProblemStatus status) {
		this.status = status;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public UserInfo getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(UserInfo submitUser) {
		this.submitUser = submitUser;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getProblemCisn() {
		return problemCisn;
	}
	public void setProblemCisn(String problemCisn) {
		this.problemCisn = problemCisn;
	}
	
//	//问题联系人时区
	
//	//问题关联客户
//	private Customer customer;
//	//问题种类
//	private Category category; 
//	//问题优先级
//	private Priority priority;
	//默认分配的支持组
//	private SupportGroup supportGroup;
	
//	//问题的影响分析
//	private String impactAnalysis;
//	
//	//预计完成小时数
//	private Double devEstTime;
//	//时间完成小时数
//	private Double devTime;
//	//已花费时间
//	private Double timeSpent;
//	//首次回应日期
//	private Date firstResponseDate;
//	//末次回应日期
//	private Date lastResponseDate;
//	//最后一次用户修改问题时间
//	private Date lastCustActionDate;
//	//用户启动解决日期
//	private Date expectedResolveDate;
	
//	private String contactTimezone;

//	查看标记
//	private Integer userViewFlag; //
//	是否自解决标记
//	private Integer selfResolveFlag;
	
}
