package com.digitalchina.itil.knowledge.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * ֪ʶ�����ֺ�ͬ
 * @Class Name KnowContract
 * @Author sa
 * @Create In Mar 31, 2009
 */
@SuppressWarnings("serial")
public class KnowContract extends BaseObject {
	public static final Integer STATUS_DRAFT = 0;// �ݸ壺1.���δͨ����; 2.���Ǵ��¼����������������δͨ����
	public static final Integer STATUS_FINISHED = 1;// ����ͨ������ʹ���У�
	public static final Integer STATUS_APPROVING = 2;// �ύ������
	// 2010-5-11 add by huzh for ����״̬ begin
	public static final Integer STATUS_TIMEOUT = 3;// ���ڣ���ͬ�����ԭ��ͬ
	public static final Integer STATUS_CHANGEDRAFT= 4;// ����ݸ壺1.���δͨ����; 2.����ݴ�
	// 2010-5-11 add by huzh for ���� end
	private Long id;
	private String name;
	private String descn;//��ͬ����
	private String files; //���и��� 
	private UserInfo createUser;
	private  Date createDate;
	private Integer status;
	private String number;
	private Long readTimes=0L; //�Ķ�����
	// 2010-5-11 add by huzh for ����֪ʶ��� begin
	private KnowContract oldKnowContract;//ԭ��ͬ�����ں�ͬ���
	// 2010-5-11 add by huzh for ����֪ʶ��� end
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
