package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * ��������פ������������ʵ�壺����������Ա������Ϣ
 * @Class Name ServerManageInfoOne
 * @Author lee
 * @Create In Jul 29, 2009
 */
public class ServerManageInfoOne extends BaseObject{
	private Long id;
	private ServerManage serverManage;	//������������פ����������ʵ��
	private String serverPosition;			//������λ��
	private String ipAddress;			//IP��ַ
	private UserInfo sysremPrincipal;	//ϵͳ������
	private String principalTel;		//�����˵绰
	private String remark;				//��ע
	private Date outDate;				//ʵ��Ǩ��ʱ��
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public UserInfo getSysremPrincipal() {
		return sysremPrincipal;
	}
	public void setSysremPrincipal(UserInfo sysremPrincipal) {
		this.sysremPrincipal = sysremPrincipal;
	}
	public String getPrincipalTel() {
		return principalTel;
	}
	public void setPrincipalTel(String principalTel) {
		this.principalTel = principalTel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getServerPosition() {
		return serverPosition;
	}
	public void setServerPosition(String serverPosition) {
		this.serverPosition = serverPosition;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
}
