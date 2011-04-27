package com.zsgj.itil.notice.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.config.extlist.entity.NewNoticeType;

/**
 * 公告信息
 * @Class Name Notice
 * @Author sa
 * @Create In 2009-2-2
 */
public class NewNotice extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	public static int STATUS_DRAFT = 0;// 草稿
	public static int STATUS_FINISHED = 1;// 通过
	public static int STATUS_APPROVING = 2;// 提交审批中
	public static int STATUS_DELETE = -1;// 已删除

	public static String STATUS_A = "A";//A类:IT公告类、IT温馨提示类
	public static String STATUS_B = "B";//B类:IT应用推广/海报类、VIP服务类
	public static String STATUS_C = "C";//C类:IT违规类、IT持续改进、流程类
	public static String STATUS_D = "D";//D类:IT培训类
	public static String STATUS_E = "E";//小贴士

	private Long id;
	private String title;//标题
	private String content;//内容
	private String remark;//备注
	private Integer auditflag;//状态 草稿状态，审批状态，审批通过,未通过
	private Date beginDate;
	private Date endDate;
	private NewNoticeType newNoticeType;
	private UserInfo createUser;//创建人
	private Date createDate;//创建时间
	private Long alterNoticeId;//变更原公告的ID

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
