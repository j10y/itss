package com.digitalchina.info.bussutil.protal.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 
 * @author：宋海鹏
 * @Connection:E_mail:ecsun@sohu.com/myecsun@hotmail.com QQ:82676683
 * @Copy Right:www.faceye.com
 * @System:www.faceye.com网络支持系统
 * @Create Time:2007-9-22
 * @Package com.faceye.components.portal.dao.model.PortalTable.java
 * @Description:容纳多个portlet的标签页.
 */
@SuppressWarnings("serial")
public class PortalColumn extends BaseObject {
	
	private Long id;
	private String name;
	
	//本标签页(Ext.Panel)使用的icon
    private String icon;
  //本标签页(Ext.Panel)使用的iconCls
    private String iconCls;
    private Portal portal;
    private Date createTime=new Date();
    private String singleColumnScale;
    private Set portletSubscribes=new HashSet(0);
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Set getPortletSubscribes() {
		return portletSubscribes;
	}
	public void setPortletSubscribes(Set portletSubscribes) {
		this.portletSubscribes = portletSubscribes;
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
		sb.append(",");
		sb.append("\"icon\":");
		sb.append("\"");
		sb.append(this.getIcon());
		sb.append("\"");
		sb.append(",");
		sb.append("\"iconCls\":");
		sb.append("\"");
		sb.append(this.getIconCls());
		sb.append("\"");
		sb.append(",");
		sb.append("\"singleColumnScale\":");
		sb.append("\"");
		sb.append(this.getSingleColumnScale());
		sb.append("\"");
		sb.append(",");
		sb.append("\"createTime\":");
		sb.append("\"");
		sb.append(this.getCreateTime());
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}
	public Portal getPortal() {
		return portal;
	}
	public void setPortal(Portal portal) {
		this.portal = portal;
	}
	public String getSingleColumnScale() {
		return singleColumnScale;
	}
	public void setSingleColumnScale(String singleColumnScale) {
		this.singleColumnScale = singleColumnScale;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((portal == null) ? 0 : portal.hashCode());
		result = PRIME * result + ((singleColumnScale == null) ? 0 : singleColumnScale.hashCode());
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
		final PortalColumn other = (PortalColumn) obj;
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
		if (portal == null) {
			if (other.portal != null)
				return false;
		} else if (!portal.equals(other.portal))
			return false;
		if (singleColumnScale == null) {
			if (other.singleColumnScale != null)
				return false;
		} else if (!singleColumnScale.equals(other.singleColumnScale))
			return false;
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
