package com.zsgj.itil.notice.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.config.extlist.entity.NewNoticeType;

/**
 * ������Ϣ
 * @Class Name Notice
 * @Author sa
 * @Create In 2009-2-2
 */
public class NewNotice extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	public static int STATUS_DRAFT = 0;// �ݸ�
	public static int STATUS_FINISHED = 1;// ͨ��
	public static int STATUS_APPROVING = 2;// �ύ������
	public static int STATUS_DELETE = -1;// ��ɾ��

	public static String STATUS_A = "A";//A��:IT�����ࡢIT��ܰ��ʾ��
	public static String STATUS_B = "B";//B��:ITӦ���ƹ�/�����ࡢVIP������
	public static String STATUS_C = "C";//C��:ITΥ���ࡢIT�����Ľ���������
	public static String STATUS_D = "D";//D��:IT��ѵ��
	public static String STATUS_E = "E";//С��ʿ

	private Long id;
	private String title;//����
	private String content;//����
	private String remark;//��ע
	private Integer auditflag;//״̬ �ݸ�״̬������״̬������ͨ��,δͨ��
	private Date beginDate;
	private Date endDate;
	private NewNoticeType newNoticeType;
	private UserInfo createUser;//������
	private Date createDate;//����ʱ��
	private Long alterNoticeId;//���ԭ�����ID

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getAuditflag() {
		return auditflag;
	}
	public void setAuditflag(Integer auditflag) {
		this.auditflag = auditflag;
	}
	public Long getAlterNoticeId() {
		return alterNoticeId;
	}
	public void setAlterNoticeId(Long alterNoticeId) {
		this.alterNoticeId = alterNoticeId;
	}
	public NewNoticeType getNewNoticeType() {
		return newNoticeType;
	}
	public void setNewNoticeType(NewNoticeType newNoticeType) {
		this.newNoticeType = newNoticeType;
	}
	
}
