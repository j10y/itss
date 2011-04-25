package com.digitalchina.info.bussutil.protal.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 
 * @author：宋海鹏
 * @Connection:E_mail:ecsun@sohu.com/myecsun@hotmail.com QQ:82676683
 * @Copy Right:www.faceye.com
 * @System:www.faceye.com网络支持系统
 * @Create Time:2007-9-22
 * @Package com.faceye.components.portal.dao.model.PortalTableColumnTemplate.java
 * @Description:portal标签页使用列模式模板
 */
@SuppressWarnings("serial")
public class PortalColumnTemplate extends BaseObject {
	private Long id;
	private String name;
	// 模板预览图图片路径.
	private String imageSrc;
	// 列与列之间的比例,以下格式表示：33:67 或 25:40:15:20
	private String columnScale;
	private Date createTime=new Date();
	/**
	 * 标识是否为默认版式.
	 */
	private String marker;
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getColumnScale() {
		return columnScale;
	}

	public void setColumnScale(String columnScale) {
		this.columnScale = columnScale;
	}

	public String json() {
		StringBuffer sb = new StringBuffer("{");
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
		sb.append("\"imageSrc\":");
		sb.append("\"");
		sb.append(this.getImageSrc());
		sb.append("\"");
		sb.append(",");
		sb.append("\"columnScale\":");
		sb.append("\"");
		sb.append(this.getColumnScale());
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
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
		result = PRIME * result + ((columnScale == null) ? 0 : columnScale.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((imageSrc == null) ? 0 : imageSrc.hashCode());
		result = PRIME * result + ((marker == null) ? 0 : marker.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
		final PortalColumnTemplate other = (PortalColumnTemplate) obj;
		if (columnScale == null) {
			if (other.columnScale != null)
				return false;
		} else if (!columnScale.equals(other.columnScale))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageSrc == null) {
			if (other.imageSrc != null)
				return false;
		} else if (!imageSrc.equals(other.imageSrc))
			return false;
		if (marker == null) {
			if (other.marker != null)
				return false;
		} else if (!marker.equals(other.marker))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
