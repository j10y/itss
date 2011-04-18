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
 * �¼�ʵ��
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
	
	public static Integer EVENT_SELFRESOLVE_TRUE = 1;//����ʦ�Խ����ǣ�1Ϊ�Խ��
	private Long id;
	private ServiceItemType scidType;	//����������
	private ServiceItem scidData;	//��������������
	private Integer type;		//����������������δȷ�ϣ�ϵͳ�Զ�������
	private String eventName;		//�¼�����(����)
	private EventFrequency frequency;//����Ƶ��
	private EventPonderance ponderance;//�¼�������
	
	private String summary; //ժҪ���¼����ƣ�
	private String description; //����
	private String remark; //������Ϣ
	
	private EventStatus eventStatus; //�¼�״̬
	
	private EventViewFlag userViewFlag; //�鿴���
	
	private Integer userViewFlag_$$$$; //�Ƿ������˿ɼ�����̨���ó��Ƿ��б� 
	
	private Integer selfResolveFlag; //�Խ�����,1Ϊ�Խ��
	
	//�¼��ύ�û�, �¼������
	private UserInfo submitUser;
	//�¼������ˣ�Ҳ�������߹���ʦ
	private UserInfo createUser;
	//����ID,�������鱨�������¼�����¼��ʱ�Ļ���ID����֪��CC�Ǳ�ʲô����
	//�������ַ�������
	private String telephonId;
	
	//�¼�����ϵ������
	private String contactUser;
	//�¼���ϵ���ʼ�
	private String contactEmail;
	//�¼���ϵ�˵绰
	private String contactPhone;

	//�¼��ύʱ��
	private Date submitDate;
	
	//add by guoxl in 2009/09/11 begin
	
	//�¼�ʵ�ʹر�ʱ�䣬Ϊ�Ժ󱨱�ͳ���ã�
	private Date praCloseDate;
	
	//add by guoxl in 2009/09/11 begin
	
	//�¼��޸�ʱ��
	private Date modifyDate;
	//�¼�����ʱ��
	private Date closedDate;
	//������ַ
	private String appendix;
	//�¼�������
	private UserInfo dealuser;
	//add by guoxl in 20090803 begin
	private String eventCisn;//�¼����
	//add by guoxl in 20090803 end
	private EventDealType eventDealType;//2010-06-25 add by huzh for �û�Ҫ����ӡ��¼�����ʽ�������ڱ���ͳ��
	private SupportGroup supportGroup;//2010-07-20 add by huzh for ��Ӵ���������֧����
	private ProblemSort problemSort;//2010-07-23 add by huzh for ��������⿡������ģ�
	
	public static Integer SUBMIT_YES = 1;
	public static Integer SUBMIT_NO = 0;
	public static Integer SEND_YES = 1;
	public static Integer SEND_NO = 0;
	private Integer knowSubmitFlag;//2010-09-07 add by huzh for �Ƿ��ύ֪ʶ��ǣ��û����󣩣�1Ϊ�ύ�ˣ�0Ϊδ�ύ
	private Integer knowSendFlag;//2010-09-07 add by huzh for �Ƿ��������ͽ��������ǣ��û����󣩣�1Ϊ�����ˣ�0Ϊδ����
	
	
	
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
