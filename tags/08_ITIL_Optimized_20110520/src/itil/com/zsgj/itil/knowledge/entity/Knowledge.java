package com.zsgj.itil.knowledge.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.service.entity.ServiceItem;

/**
 * 解决方案
 * @Class Name Knowledge
 * @Author sa
 * @Create In Mar 31, 2009
 */
public class Knowledge extends BaseObject {
	public static final Integer STATUS_DRAFT = 0;// 草稿：1.不是从事件解决得来的且审批未通过的;2.暂存未提交的
	public static final Integer STATUS_FINISHED = 1;// 审批通过（或正式）
	public static final Integer STATUS_APPROVING = 2;// 审批中
	// 2010-5-11 add by huzh for 增加状态 begin
	public static final Integer STATUS_TIMEOUT = 3;// 过期：变更的原解决方案
	public static final Integer STATUS_CHANGEDRAFT= 4;// 变更草稿：1.变更未通过的; 2.变更暂存
	public static final Integer STATUS_RENEW= 5;// 待补充：从事件解决得来的且审批未通过的
	// 2010-5-11 add by huzh for 增加状态 end
	
	private Long id;
	
	private ServiceItem serviceItem; //服务项
	private KnowProblemType knowProblemType; //问题类型
	
	private String summary; //解决方案名称，具体问题
	
	private String reason; //原因
	private String resolvent; //解决方法
	
	private Integer userViewFlag; //是否所有可见
	
	private Long useTime=0L; //使用次数
	
	private Long readTimes=0L; //阅读次数
	
	private String description; //描述，废弃
	
	private UserInfo createUser;
	private  Date createDate;
	
	private Integer status;
	
	private String knowledgeCisn;
	// 2010-5-11 add by huzh for 用于知识变更 begin
	private Knowledge oldKnowledge;//原解决方案，用于解决方案变更
	// 2010-5-11 add by huzh for 用于知识变更 end
	
	public String getKnowledgeCisn() {
		return knowledgeCisn;
	}
	public void setKnowledgeCisn(String knowledgeCisn) {
		this.knowledgeCisn = knowledgeCisn;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @Return the UserInfo createUser
	 */
	public UserInfo getCreateUser() {
		return createUser;
	}
	/**
	 * @Param UserInfo createUser to set
	 */
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
	/**
	 * @Return the Date createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @Param Date createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	//private String remark; //附加信息
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public KnowProblemType getKnowProblemType() {
		return knowProblemType;
	}
	public void setKnowProblemType(KnowProblemType knowProblemType) {
		this.knowProblemType = knowProblemType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResolvent() {
		return resolvent;
	}
	public void setResolvent(String resolvent) {
		this.resolvent = resolvent;
	}
	public Integer getUserViewFlag() {
		return userViewFlag;
	}
	public void setUserViewFlag(Integer userViewFlag) {
		this.userViewFlag = userViewFlag;
	}
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	public Long getUseTime() {
		return useTime;
	}
	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}
	public Long getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(Long readTimes) {
		this.readTimes = readTimes;
	}
	public Knowledge getOldKnowledge() {
		return oldKnowledge;
	}
	public void setOldKnowledge(Knowledge oldKnowledge) {
		this.oldKnowledge = oldKnowledge;
	}
	
	
	//知识检索时只检索事件或问题而不查询知识描述。看解决方案只关心
	//方案里，不需要反查事件或问题
	
	//合同类文档类，事件问题累的（包括手工录入的选配置项目）
	//一个配置项可能即是合同类
	
	//还可能事件或问题的解决方案，没有描述只有附件
	//也就是自己直接在知识管理里录入的
	
	//或者只选择了类型，没有选择具体配置项，如服务器类的知识
	
	
	//所有知识都有配置项目	
}
