package com.zsgj.dcit.entity;

import java.util.Date;

public class Knowledge {
	public static final Integer STATUS_FINISHED = 1;// ����ͨ��������ʽ��

	private Long id;
	private ServiceItem serviceItem; //������
	private String summary; //����������ƣ���������
	private String reason; //ԭ��
	private String resolvent; //�������
	private Long useTime=0L; //ʹ�ô���
	private Long readTimes=0L; //�Ķ�����
	private UserInfo createUser;
	private  Date createDate;
	private Integer status;
	private String knowledgeCisn;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getKnowledgeCisn() {
		return knowledgeCisn;
	}
	public void setKnowledgeCisn(String knowledgeCisn) {
		this.knowledgeCisn = knowledgeCisn;
	}
	
	
	
}
