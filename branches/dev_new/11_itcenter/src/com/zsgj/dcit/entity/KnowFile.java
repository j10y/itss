package com.zsgj.dcit.entity;

import java.util.Date;

/**
 * 知识附件
 * @Class Name KnowFile
 * @Author sa
 * @Create In Mar 31, 2009
 */
@SuppressWarnings("serial")
public class KnowFile  {
	public static int STATUS_DRAFT = 0;// 草稿
	public static int STATUS_APPROVING = 2;// 提交审批中
	public static int STATUS_FINISHED = 1;// 通过
	
	private Long id;
	private String name;
	private String descn;
	private UserInfo createUser;
	private Date createDate;
	private String files; 
	private Integer status;
	private String number;
	private Long readTimes=0L; 			//阅读次数
	private Long knowFileType;			//知识类型
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
	public Long getKnowFileType() {
		return knowFileType;
	}
	public void setKnowFileType(Long knowFileType) {
		this.knowFileType = knowFileType;
	}

	
}
