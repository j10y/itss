package com.digitalchina.itil.jfreeChart.entity;

import java.util.Set;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.framework.dao.BaseObject;

public class StatisticsPicture extends BaseObject{
	private Long id;
	private String tjPictureName;
	private String tjPictureURL;
	private String tjPictureType;
	private String sqlString;
//	private StatisticsColumn staColumn;
	private String XName;
	private String YName;
	private SystemMainTable sysMainTable;
	private SystemMainTableColumn itemName;//每个元素的不同颜色代表的东西
	private SystemMainTableColumn lableTickName;//每一种分类的名称.在图的底部
	public SystemMainTableColumn getItemName() {
		return itemName;
	}
	public void setItemName(SystemMainTableColumn itemName) {
		this.itemName = itemName;
	}
	public SystemMainTableColumn getLableTickName() {
		return lableTickName;
	}
	public void setLableTickName(SystemMainTableColumn lableTickName) {
		this.lableTickName = lableTickName;
	}
	//	private Set staColumns;
//	public Set getStaColumns() {
//		return staColumns;
//	}
//	public void setStaColumns(Set staColumns) {
//		this.staColumns = staColumns;
//	}
	public String getXName() {
		return XName;
	}
	public void setXName(String name) {
		XName = name;
	}
	public String getYName() {
		return YName;
	}
	public void setYName(String name) {
		YName = name;
	}
//	public StatisticsColumn getStaColumn() {
//		return staColumn;
//	}
//	public void setStaColumn(StatisticsColumn staColumn) {
//		this.staColumn = staColumn;
//	}
	public String getSqlString() {
		return sqlString;
	}
	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}
	public String getTjPictureType() {
		return tjPictureType;
	}
	public void setTjPictureType(String tjPictureType) {
		this.tjPictureType = tjPictureType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTjPictureName() {
		return tjPictureName;
	}
	public void setTjPictureName(String tjPictureName) {
		this.tjPictureName = tjPictureName;
	}
	public String getTjPictureURL() {
		return tjPictureURL;
	}
	public void setTjPictureURL(String tjPictureURL) {
		this.tjPictureURL = tjPictureURL;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((sqlString == null) ? 0 : sqlString.hashCode());
		result = prime * result
				+ ((tjPictureName == null) ? 0 : tjPictureName.hashCode());
		result = prime * result
				+ ((tjPictureType == null) ? 0 : tjPictureType.hashCode());
		result = prime * result
				+ ((tjPictureURL == null) ? 0 : tjPictureURL.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StatisticsPicture other = (StatisticsPicture) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sqlString == null) {
			if (other.sqlString != null)
				return false;
		} else if (!sqlString.equals(other.sqlString))
			return false;
		if (tjPictureName == null) {
			if (other.tjPictureName != null)
				return false;
		} else if (!tjPictureName.equals(other.tjPictureName))
			return false;
		if (tjPictureType == null) {
			if (other.tjPictureType != null)
				return false;
		} else if (!tjPictureType.equals(other.tjPictureType))
			return false;
		if (tjPictureURL == null) {
			if (other.tjPictureURL != null)
				return false;
		} else if (!tjPictureURL.equals(other.tjPictureURL))
			return false;
		return true;
	}
	public SystemMainTable getSysMainTable() {
		return sysMainTable;
	}
	public void setSysMainTable(SystemMainTable sysMainTable) {
		this.sysMainTable = sysMainTable;
	}
}
