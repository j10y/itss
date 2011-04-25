package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.itil.actor.entity.SupportGroup;
import com.digitalchina.itil.config.entity.CIBaseData;
import com.digitalchina.itil.config.entity.CIBaseType;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.entity.ConfigItemType;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemType;

/**
 * 事件实体
 * @Class Name Incident
 * @Author sa
 * @Create In 2008-11-9
 */
@SuppressWarnings("serial")
public class Event extends BaseObject {
	public static Integer TYPE_CID = 1;
	public static Integer TYPE_SCID = 0;
	
	public static Integer EVENT_TYPE_CC = 0;
	public static Integer EVENT_TYPE_USER = 1;
	
	public static Integer EVENT_SELFRESOLVE_TRUE = 1;//工程师自解决标记：1为自解决
	private Long id;
	private ServiceItemType scidType;	//服务项类型
	private ServiceItem scidData;	//所属关联服务项
	private Integer type;		//超过两个工作日仍未确认，系统自动处理标记
	private String eventName;		//事件名称(废弃)
	private EventFrequency frequency;//出现频率
	private EventPonderance ponderance;//事件严重性
	
	private String summary; //摘要（事件名称）
	private String description; //描述
	private String remark; //附加信息
	
	private EventStatus eventStatus; //事件状态
	
	private EventViewFlag userViewFlag; //查看标记
	
	private Integer userViewFlag_$$$$; //是否所有人可见，后台配置成是否列表 
	
	private Integer selfResolveFlag; //自解决标记,1为自解决
	
	//事件提交用户, 事件提出人
	private UserInfo submitUser;
	//事件创建人，也就是热线工程师
	private UserInfo createUser;
	//话务ID,对于热情报过来的事件，记录当时的话务ID，不知道CC那边什么类型
	//故先用字符串类型
	private String telephonId;
	
	//事件的联系人名称
	private String contactUser;
	//事件联系人邮件
	private String contactEmail;
	//事件联系人电话
	private String contactPhone;

	//事件提交时间
	private Date submitDate;
	
	//add by guoxl in 2009/09/11 begin
	
	//事件实际关闭时间，为以后报表统计用，
	private Date praCloseDate;
	
	//add by guoxl in 2009/09/11 begin
	
	//事件修改时间
	private Date modifyDate;
	//事件结束时间
	private Date closedDate;
	//附件地址
	private String appendix;
	//事件处理人
	private UserInfo dealuser;
	//add by guoxl in 20090803 begin
	private String eventCisn;//事件编号
	//add by guoxl in 20090803 end
	private EventDealType eventDealType;//2010-06-25 add by huzh for 用户要求添加“事件处理方式”，用于报表统计
	private SupportGroup supportGroup;//2010-07-20 add by huzh for 添加处理人所在支持组
	private ProblemSort problemSort;//2010-07-23 add by huzh for 问题类别（吴俊杰提出的）
	
	public static Integer SUBMIT_YES = 1;
	public static Integer SUBMIT_NO = 0;
	public static Integer SEND_YES = 1;
	public static Integer SEND_NO = 0;
	private Integer knowSubmitFlag;//2010-09-07 add by huzh for 是否提交知识标记（用户需求）：1为提交了，0为未提交
	private Integer knowSendFlag;//2010-09-07 add by huzh for 是否立即发送解决方案标记（用户需求）：1为发送了，0为未发送
	
	
	
	public Integer getKnowSubmitFlag() {
		return knowSubmitFlag;
	}

	public void setKnowSubmitFlag(Integer knowSubmitFlag) {
		this.knowSubmitFlag = knowSubmitFlag;
	}

	public Integer getKnowSendFlag() {
		return knowSendFlag;
	}

	public void setKnowSendFlag(Integer knowSendFlag) {
		this.knowSendFlag = knowSendFlag;
	}

	public ProblemSort getProblemSort() {
		return problemSort;
	}

	public void setProblemSort(ProblemSort problemSort) {
		this.problemSort = problemSort;
	}

	public SupportGroup getSupportGroup() {
		return supportGroup;
	}

	public void setSupportGroup(SupportGroup supportGroup) {
		this.supportGroup = supportGroup;
	}

	/**
	 * @Return the ServiceItemType scidType
	 */
	public ServiceItemType getScidType() {
		return scidType;
	}

	/**
	 * @Param ServiceItemType scidType to set
	 */
	public void setScidType(ServiceItemType scidType) {
		this.scidType = scidType;
	}

	/**
	 * @Return the ServiceItem scidData
	 */
	public ServiceItem getScidData() {
		return scidData;
	}

	/**
	 * @Param ServiceItem scidData to set
	 */
	public void setScidData(ServiceItem scidData) {
		this.scidData = scidData;
	}

	public UserInfo getDealuser() {
		return dealuser;
	}

	public void setDealuser(UserInfo dealuser) {
		this.dealuser = dealuser;
	}

	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	public String getEventName() {
		return eventName;
	}

	/**
	 * @Param String eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public CIBaseType getScidTypeObj(){
		Service service = (Service) ContextHolder.getBean("baseService");
		if(this.type!=null&& this.type.intValue()==Event.TYPE_CID){
			ConfigItemType cit = (ConfigItemType) service.find(ConfigItemType.class, String.valueOf(this.scidType));
			return cit;
		}else{
			ServiceItemType cit = (ServiceItemType) service.find(ServiceItemType.class, String.valueOf(this.scidType));
			return cit;
		}
	}
	
	public CIBaseData getScidDataObj(){
		Service service = (Service) ContextHolder.getBean("baseService");
		if(this.type!=null&& this.type.intValue()==Event.TYPE_CID){
			ConfigItem cit = (ConfigItem) service.find(ConfigItem.class, String.valueOf(this.scidData));
			return cit;
		}else{
			ServiceItem cit = (ServiceItem) service.find(ConfigItem.class, String.valueOf(this.scidData));
			return cit;
		}
	}

	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @Return the Integer type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @Param Integer type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @Return the EventFrequency frequency
	 */
	public EventFrequency getFrequency() {
		return frequency;
	}

	/**
	 * @Param EventFrequency frequency to set
	 */
	public void setFrequency(EventFrequency frequency) {
		this.frequency = frequency;
	}

	/**
	 * @Return the EventPonderance ponderance
	 */
	public EventPonderance getPonderance() {
		return ponderance;
	}

	/**
	 * @Param EventPonderance ponderance to set
	 */
	public void setPonderance(EventPonderance ponderance) {
		this.ponderance = ponderance;
	}

	/**
	 * @Return the String summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @Param String summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @Return the String description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @Param String description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @Return the String remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @Param String remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @Return the EventStatus eventStatus
	 */
	public EventStatus getEventStatus() {
		return eventStatus;
	}

	/**
	 * @Param EventStatus eventStatus to set
	 */
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	/**
	 * @Return the Integer userViewFlag
	 */
	public EventViewFlag getUserViewFlag() {
		return userViewFlag;
	}

	/**
	 * @Param Integer userViewFlag to set
	 */
	public void setUserViewFlag(EventViewFlag userViewFlag) {
		this.userViewFlag = userViewFlag;
	}

	/**
	 * @Return the Integer selfResolveFlag
	 */
	public Integer getSelfResolveFlag() {
		return selfResolveFlag;
	}

	/**
	 * @Param Integer selfResolveFlag to set
	 */
	public void setSelfResolveFlag(Integer selfResolveFlag) {
		this.selfResolveFlag = selfResolveFlag;
	}

	/**
	 * @Return the UserInfo submitUser
	 */
	public UserInfo getSubmitUser() {
		return submitUser;
	}

	/**
	 * @Param UserInfo submitUser to set
	 */
	public void setSubmitUser(UserInfo submitUser) {
		this.submitUser = submitUser;
	}

	/**
	 * @Return the String contactUser
	 */
	public String getContactUser() {
		return contactUser;
	}

	/**
	 * @Param String contactUser to set
	 */
	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	/**
	 * @Return the String contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @Param String contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @Return the String contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @Param String contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @Return the Date submitDate
	 */
	public Date getSubmitDate() {
		return submitDate;
	}

	/**
	 * @Param Date submitDate to set
	 */
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	/**
	 * @Return the Date modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @Param Date modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @Return the Date closedDate
	 */
	public Date getClosedDate() {
		return closedDate;
	}

	/**
	 * @Param Date closedDate to set
	 */
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}

	public String getTelephonId() {
		return telephonId;
	}

	public void setTelephonId(String telephonId) {
		this.telephonId = telephonId;
	}

	public String getEventCisn() {
		return eventCisn;
	}

	public void setEventCisn(String eventCisn) {
		this.eventCisn = eventCisn;
	}

	public Date getPraCloseDate() {
		return praCloseDate;
	}

	public void setPraCloseDate(Date praCloseDate) {
		this.praCloseDate = praCloseDate;
	}

	public EventDealType getEventDealType() {
		return eventDealType;
	}

	public void setEventDealType(EventDealType eventDealType) {
		this.eventDealType = eventDealType;
	}
	
	
	

}
