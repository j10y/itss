package com.digitalchina.itil.service.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 系统附件表
 * @Class Name EventFile
 * @Author sa
 * @Create In 2009-3-4
 */
@SuppressWarnings("serial")
public class ServiceSystemFile extends BaseObject {
	private Long id;
	
	private String fileName;//附件名称
	private String systemFileName;//系统文件名
	private UserInfo uploadUser;//上传人
	private Date uploadDate;//上传时间
	private String nowtime;

	public String getNowtime() {
		return nowtime;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSystemFileName() {
		return systemFileName;
	}
	public void setSystemFileName(String systemFileName) {
		this.systemFileName = systemFileName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public UserInfo getUploadUser() {
		return uploadUser;
	}
	public void setUploadUser(UserInfo uploadUser) {
		this.uploadUser = uploadUser;
	}
	
}
