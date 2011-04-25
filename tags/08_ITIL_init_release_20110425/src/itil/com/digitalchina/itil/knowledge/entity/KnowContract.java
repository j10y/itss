package com.digitalchina.itil.knowledge.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 知识管理部分合同
 * @Class Name KnowContract
 * @Author sa
 * @Create In Mar 31, 2009
 */
@SuppressWarnings("serial")
public class KnowContract extends BaseObject {
	public static final Integer STATUS_DRAFT = 0;// 草稿：1.变更未通过的; 2.不是从事件解决得来的且审批未通过的
	public static final Integer STATUS_FINISHED = 1;// 审批通过（或使用中）
	public static final Integer STATUS_APPROVING = 2;// 提交审批中
	// 2010-5-11 add by huzh for 增加状态 begin
	public static final Integer STATUS_TIMEOUT = 3;// 过期：合同变更的原合同
	public static final Integer STATUS_CHANGEDRAFT= 4;// 变更草稿：1.变更未通过的; 2.变更暂存
	// 2010-5-11 add by huzh for 增加 end
	private Long id;
	private String name;
	private String descn;//合同描述
	private String files; //所有附件 
	private UserInfo createUser;
	private  Date createDate;
	private Integer status;
	private String number;
	private Long readTimes=0L; //阅读次数
	// 2010-5-11 add by huzh for 用于知识变更 begin
	private KnowContract oldKnowContract;//原合同，用于合同变更
	// 2010-5-11 add by huzh for 用于知识变更 end
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
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
	/**
	 * @Return the String descn
	 */
	public String getDescn() {
		return descn;
	}
	/**
	 * @Param String descn to set
	 */
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Long getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(Long readTimes) {
		this.readTimes = readTimes;
	}
	public KnowContract getOldKnowContract() {
		return oldKnowContract;
	}
	public void setOldKnowContract(KnowContract oldKnowContract) {
		this.oldKnowContract = oldKnowContract;
	}
	
	
}
