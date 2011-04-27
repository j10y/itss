package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * ��������פ������������ʵ�壺�������Ĺ���Ա������Ϣ
 * @Class Name ServerManageInfoTwo
 * @Author lee
 * @Create In Jul 29, 2009
 */
public class ServerManageInfoTwo extends BaseObject{
	private Long id;
	private ServerManage serverManage;	//������������פ����������ʵ��
	private String powerSwitch;			//��Դ����
	private String kvm;					//KVM
	private String port;				//���ں�
	private String dmzAddress;			//������DMZ��ַ
	private String internetAddress;		//Inernetӳ���ַ
	private String remark;				//��ע
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ServerManage getServerManage() {
		return serverManage;
	}
	public void setServerManage(ServerManage serverManage) {
		this.serverManage = serverManage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPowerSwitch() {
		return powerSwitch;
	}
	public void setPowerSwitch(String powerSwitch) {
		this.powerSwitch = powerSwitch;
	}
	public String getKvm() {
		return kvm;
	}
	public void setKvm(String kvm) {
		this.kvm = kvm;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getInternetAddress() {
		return internetAddress;
	}
	public void setInternetAddress(String internetAddress) {
		this.internetAddress = internetAddress;
	}
	public String getDmzAddress() {
		return dmzAddress;
	}
	public void setDmzAddress(String dmzAddress) {
		this.dmzAddress = dmzAddress;
	}
}
