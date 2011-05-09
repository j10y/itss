package com.zsgj.itil.knowledge.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * ֪ʶ����
 * @Class Name KnowFile
 * @Author sa
 * @Create In Mar 31, 2009
 */
@SuppressWarnings("serial")
public class KnowFile extends BaseObject {
	public static final Integer STATUS_DRAFT = 0;// �ݸ壺1.���δͨ����; 2.���Ǵ��¼����������������δͨ����
	public static final Integer STATUS_FINISHED = 1;// ����ͨ������ʹ���У�
	public static final Integer STATUS_APPROVING = 2;// �ύ������
	// 2010-5-11 add by huzh for ����״̬ begin
	public static final Integer STATUS_TIMEOUT = 3;// ���ڣ��ļ������ԭ�ļ�
	public static final Integer STATUS_CHANGEDRAFT= 4;// ����ݸ壺1.���δͨ����; 2.����ݴ�
	// 2010-5-11 add by huzh for ����״̬ begin	
	private Long id;
	private String name;
	private String descn;
	private UserInfo createUser;
	private Date createDate;
	private String files; //���и��� 
	private Integer status;
	private String number;	//��� add by lee for add number in 20090911
	private Long readTimes=0L; //�Ķ����� add by lee in 20100315
	// 2010-5-11 add by huzh for ����֪ʶ��� begin
	private KnowFile oldKnowFile;//ԭ�ļ��������ļ����
	// 2010-5-11 add by huzh for ����֪ʶ��� end
	// 2010-5-31 add by huzh for �û����� begin
	private KnowFileType knowFileType ;//�ļ�����
	// 2010-5-31 add by huzh for �û����� end
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
