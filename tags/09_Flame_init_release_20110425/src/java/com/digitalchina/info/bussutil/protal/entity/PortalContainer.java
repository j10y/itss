package com.digitalchina.info.bussutil.protal.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.DateUtil;

/**
 * 
 * @author：宋海鹏
 * @Connection:E_mail:ecsun@sohu.com/myecsun@hotmail.com QQ:82676683
 * @Copy Right:www.faceye.com
 * @System:www.faceye.com网络支持系统
 * @Create Time:2007-9-22
 * @Package com.faceye.components.portal.dao.model.PortalContainer.java
 * @Description:容纳所有protal的容器 可以容纳不同的面板页,每个面板页由不同的porlet组成. 所有的面板页共同组成portal
 */
@SuppressWarnings("serial")
public class PortalContainer extends BaseObject {
	
	private Long id;
	private String name;
	
	//portal所属用户
	private UserInfo userInfo;
	//portal所使用的样式表
    private PortalStyle portalStyle;
    private Date createTime=new Date();
    private Set portals=new HashSet(0);
	public PortalStyle getPortalStyle() {
		return portalStyle;
	}

	public void setPortalStyle(PortalStyle portalStyle) {
		this.portalStyle = portalStyle;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo UserInfo) {
		this.userInfo = UserInfo;
	}

	
	public Set getPortals() {
		return portals;
	}

	public void setPortals(Set portals) {
		this.portals = portals;
	}

	public String json(){
		StringBuffer sb=new StringBuffer("{");
		sb.append("\"id\":");
		sb.append("\"");
		sb.append(this.getId());
		sb.append("\"");
		sb.append(",");
		sb.append("\"name\":");
		sb.append("\"");
		sb.append(this.getName());
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	public Map map(){
		Map map=new HashMap();
		map.put("id", this.getId());
		map.put("name", this.getName());
		map.put("username", this.getUserInfo().getUserName());
		map.put("UserInfo_id", this.getUserInfo().getId());
		map.put("createTime", DateUtil.convertDateToString(this.getCreateTime()));
		return map;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((portalStyle == null) ? 0 : portalStyle.hashCode());
		result = PRIME * result + ((userInfo == null) ? 0 : userInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PortalContainer other = (PortalContainer) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (portalStyle == null) {
			if (other.portalStyle != null)
				return false;
		} else if (!portalStyle.equals(other.portalStyle))
			return false;
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
	}
}
