package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.knowledge.entity.Knowledge;

/**
 * �������CallCenterʹ����־
 * @Class Name EventTelephoneInfo
 * @Author sa
 * @Create In 2009-3-11
 */
@SuppressWarnings("serial")
public class CCCallInfo extends BaseObject {
	public static final Integer SELFFLAG_YES=1;
	public static final Integer SELFFLAG_NO=0;
	public static final Integer MAILFLAG_YES=1;
	public static final Integer MAILFLAG_NO=0;
	public static final Integer TELFLAG_YES=1;
	private Long id;
	private Event event;
	private Knowledge knowledge; //�������
	
	private String customerItcode; 		//�ͻ�Ա��ITCODE
	private String submitUserItcode; 	//��ϯԱ��ITCODE
	private String callId; 				//����ID
	private String callPhone; 			//�������
	private String callName; 			//����������
	private String department; 			//��������
	private Date createDate; 			//����ʱ��
	
	private Integer telSynFlag; 		//����ͬ����־
	private Integer satisSynFlag; 		//�Ƿ�����˻�������ȷ�����־��1Ϊ�����˻�������ȵ���
	
	//add by lee for add some flags in 20090813 begin
	private Integer selfFlag;			//�¼��������ͣ�1Ϊ��ϯ�Խ����0Ϊ�ύ�¼����
	private Integer mailFlag;			//�Ƿ��ѷ����ʼ���1Ϊ�ѷ��ͣ�0Ϊδ����
	//add by lee for add some flags in 20090813 end
	
	public String getCallName() {
		return callName;
	}

	public void setCallName(String callName) {
		this.callName = callName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerItcode() {
		return customerItcode;
	}
	public void setCustomerItcode(String customerItcode) {
		this.customerItcode = customerItcode;
	}
	public String getSubmitUserItcode() {
		return submitUserItcode;
	}
	public void setSubmitUserItcode(String submitUserItcode) {
		this.submitUserItcode = submitUserItcode;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getCallPhone() {
		return callPhone;
	}
	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}
	public Knowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getTelSynFlag() {
		return telSynFlag;
	}
	public void setTelSynFlag(Integer telSynFlag) {
		this.telSynFlag = telSynFlag;
	}
	public Integer getSatisSynFlag() {
		return satisSynFlag;
	}
	public void setSatisSynFlag(Integer satisSynFlag) {
		this.satisSynFlag = satisSynFlag;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

	public Integer getSelfFlag() {
		return selfFlag;
	}

	public void setSelfFlag(Integer selfFlag) {
		this.selfFlag = selfFlag;
	}

	public Integer getMailFlag() {
		return mailFlag;
	}

	public void setMailFlag(Integer mailFlag) {
		this.mailFlag = mailFlag;
	}
	
	
	
}