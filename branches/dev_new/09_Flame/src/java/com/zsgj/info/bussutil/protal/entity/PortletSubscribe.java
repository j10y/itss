package com.zsgj.info.bussutil.protal.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * @author：宋海鹏
 * @Connection:E_mail:ecsun@sohu.com/myecsun@hotmail.com QQ:82676683
 * @Copy Right:www.faceye.com
 * @System:www.faceye.com网络支持系统
 * @Create Time:2007-9-22
 * @Package com.faceye.components.portal.dao.model.PortletSubscribe.java
 * @Description:用户征订portlet
 */
public class PortletSubscribe extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 5040717620713156160L;
	private Long id;
	private String name;
	// 正在征订的portlet
	private Portlet protlet;
	// 将portlet征订到哪一个portalColumn
	private PortalColumn portalColumn;
	/**
	 * 本portlet显示在portalColumn中的坐标,x.y均从0开始,并不代表
	 * Ext.getCmpgetX只代表portlet之间的相对位置,左上角为坐标原点
	 * 
	 */
	private String x;
	private String y;
	private Integer order;
	private Date createTime=new Date();
	
	private Long refresh;
	
	public String jsonRefersh(){
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
		sb.append("\"refresh\":");
		sb.append("\"");
		sb.append(this.getRefresh() == null ? 1 : this.getRefresh());
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Portlet getProtlet() {
		return protlet;
	}
	public void setProtlet(Portlet protlet) {
		this.protlet = protlet;
	}
	public PortalColumn getPortalColumn() {
		return portalColumn;
	}
	public void setPortalColumn(PortalColumn portalColumn) {
		this.portalColumn = portalColumn;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
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
		result = PRIME * result + ((order == null) ? 0 : order.hashCode());
		result = PRIME * result + ((portalColumn == null) ? 0 : portalColumn.hashCode());
		result = PRIME * result + ((protlet == null) ? 0 : protlet.hashCode());
		result = PRIME * result + ((x == null) ? 0 : x.hashCode());
		result = PRIME * result + ((y == null) ? 0 : y.hashCode());
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
		final PortletSubscribe other = (PortletSubscribe) obj;
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
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (portalColumn == null) {
			if (other.portalColumn != null)
				return false;
		} else if (!portalColumn.equals(other.portalColumn))
			return false;
		if (protlet == null) {
			if (other.protlet != null)
				return false;
		} else if (!protlet.equals(other.protlet))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	/**
	 * @Return the Long refresh
	 */
	public Long getRefresh() {
		return refresh;
	}
	/**
	 * @Param Long refresh to set
	 */
	public void setRefresh(Long refresh) {
		this.refresh = refresh;
	}

}
