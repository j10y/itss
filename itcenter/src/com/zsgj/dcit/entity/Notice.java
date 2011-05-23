package com.zsgj.dcit.entity;

import java.util.Date;

/**
 * ���棬С��ʿ����ѵ����ӦITSS��NewNotice��
 * @Class Name Notice
 * @Author lee
 * @Create In May 31, 2010
 */
public class Notice {
	public static int STATUS_FINISHED = 1;// ͨ����

	public static Long STATUS_A = 1L;
	public static Long STATUS_B = 2L;
	public static Long STATUS_C = 3L;
	public static Long STATUS_D = 4L;
	public static Long STATUS_E = 5L;

	private Long id;
	private String title;//����
	private String content;//����
	private String remark;//��ע
	private Integer auditflag;//״̬ �ݸ�״̬������״̬������ͨ��,δͨ��
	private Date beginDate;	//������ʼʱ��
	private Date endDate;	//��������ʱ��
	private Long newNoticeType;	//��������
	private UserInfo createUser;//������
	private Date createDate;//����ʱ��
	private Long readTimes=0L ;//���ʴ���
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getAuditflag() {
		return auditflag;
	}
	public void setAuditflag(Integer auditflag) {
		this.auditflag = auditflag;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getNewNoticeType() {
		return newNoticeType;
	}
	public void setNewNoticeType(Long newNoticeType) {
		this.newNoticeType = newNoticeType;
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
	public Long getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(Long readTimes) {
		this.readTimes = readTimes;
	}
	
	
	
}
