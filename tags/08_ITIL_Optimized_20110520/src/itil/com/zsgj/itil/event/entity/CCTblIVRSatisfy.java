package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * CC�����
 * @Class Name CCTblIVRSatisfy
 * @Author lee
 * @Create In Aug 13, 2009
 */
public class CCTblIVRSatisfy  extends BaseObject{
	private Long id;
	private String serviceId;	//ͬhandleֵ����ʵ������
	private String handle;		//��¼Ψһ��ʾ
	private String agentcode;	//��ϯ����
	private String agentDevice;	//�����ֻ�
	private String code;		//����ȣ�1�ǳ����⣻2���⣻3������
	private String ani;			//�������
	private String time;		//��¼ʱ��
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getAgentcode() {
		return agentcode;
	}
	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}
	public String getAgentDevice() {
		return agentDevice;
	}
	public void setAgentDevice(String agentDevice) {
		this.agentDevice = agentDevice;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
//[id] [bigint] IDENTITY (1, 1) NOT NULL ,
//[SERVICE_ID] [varchar] (40) COLLATE Chinese_PRC_CI_AS NULL ,
//[HANDLE] [varchar] (40) COLLATE Chinese_PRC_CI_AS NULL ,
//[AGENTCODE] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
//[AGENTDEVICE] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
//[CODE] [varchar] (1) COLLATE Chinese_PRC_CI_AS NULL ,
//[ANI] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
//[TIME] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL 