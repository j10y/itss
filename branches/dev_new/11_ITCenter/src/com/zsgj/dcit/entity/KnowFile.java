package com.zsgj.dcit.entity;

import java.util.Date;

/**
 * ֪ʶ����
 * @Class Name KnowFile
 * @Author sa
 * @Create In Mar 31, 2009
 */
@SuppressWarnings("serial")
public class KnowFile  {
	public static int STATUS_DRAFT = 0;// �ݸ�
	public static int STATUS_APPROVING = 2;// �ύ������
	public static int STATUS_FINISHED = 1;// ͨ��
	
	private Long id;
	private String name;
	private String descn;
	private UserInfo createUser;
	private Date createDate;
	private String files; 
	private Integer status;
	private String number;
	private Long readTimes=0L; 			//�Ķ�����
	private Long knowFileType;			//֪ʶ����
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
