package com.zsgj.itil.knowledge.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 知识附件
 * @Class Name KnowFile
 * @Author sa
 * @Create In Mar 31, 2009
 */
@SuppressWarnings("serial")
public class KnowFile extends BaseObject {
	public static final Integer STATUS_DRAFT = 0;// 草稿：1.变更未通过的; 2.不是从事件解决得来的且审批未通过的
	public static final Integer STATUS_FINISHED = 1;// 审批通过（或使用中）
	public static final Integer STATUS_APPROVING = 2;// 提交审批中
	// 2010-5-11 add by huzh for 增加状态 begin
	public static final Integer STATUS_TIMEOUT = 3;// 过期：文件变更的原文件
	public static final Integer STATUS_CHANGEDRAFT= 4;// 变更草稿：1.变更未通过的; 2.变更暂存
	// 2010-5-11 add by huzh for 增加状态 begin	
	private Long id;
	private String name;
	private String descn;
	private UserInfo createUser;
	private Date createDate;
	private String files; //所有附件 
	private Integer status;
	private String number;	//编号 add by lee for add number in 20090911
	private Long readTimes=0L; //阅读次数 add by lee in 20100315
	// 2010-5-11 add by huzh for 用于知识变更 begin
	private KnowFile oldKnowFile;//原文件，用于文件变更
	// 2010-5-11 add by huzh for 用于知识变更 end
	// 2010-5-31 add by huzh for 用户需求 begin
	private KnowFileType knowFileType ;//文件类型
	// 2010-5-31 add by huzh for 用户需求 end
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
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public KnowFile getOldKnowFile() {
		return oldKnowFile;
	}
	public void setOldKnowFile(KnowFile oldKnowFile) {
		this.oldKnowFile = oldKnowFile;
	}
	public KnowFileType getKnowFileType() {
		return knowFileType;
	}
	public void setKnowFileType(KnowFileType knowFileType) {
		this.knowFileType = knowFileType;
	}
	
}
