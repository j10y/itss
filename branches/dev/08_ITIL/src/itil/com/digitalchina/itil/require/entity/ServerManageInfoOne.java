package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 服务器入驻数据中心需求附实体：服务器管理员处理信息
 * @Class Name ServerManageInfoOne
 * @Author lee
 * @Create In Jul 29, 2009
 */
public class ServerManageInfoOne extends BaseObject{
	private Long id;
	private ServerManage serverManage;	//关联服务器入驻数据中心主实体
	private String serverPosition;			//服务器位置
	private String ipAddress;			//IP地址
	private UserInfo sysremPrincipal;	//系统负责人
	private String principalTel;		//负责人电话
	private String remark;				//备注
	private Date outDate;				//实际迁出时间
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
