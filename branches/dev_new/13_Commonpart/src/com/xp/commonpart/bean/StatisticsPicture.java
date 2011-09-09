package com.xp.commonpart.bean;

import java.util.Set;

public class StatisticsPicture{
	private Long id;
	private String tjPictureName;
	private String tjPictureURL;
	private String tjPictureType;
	private String sqlString;
	private String XName;
	private String YName;
	private MainTable sysMainTable;
	private MainTableColumn itemName;////每个元素的不同颜色代表的东西
	private MainTableColumn lableTickName;////每一种分类的名称.在图的底部
	private MainTableColumn numberName;//数量
	public MainTableColumn getNumberName() {
		return numberName;
	}
	public void setNumberName(MainTableColumn numberName) {
		this.numberName = numberName;
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
	public String getTjPictureType() {
		return tjPictureType;
	}
	public void setTjPictureType(String tjPictureType) {
		this.tjPictureType = tjPictureType;
	}
	public String getSqlString() {
		return sqlString;
	}
	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}
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
	public MainTable getSysMainTable() {
		return sysMainTable;
	}
	public void setSysMainTable(MainTable sysMainTable) {
		this.sysMainTable = sysMainTable;
	}
	public MainTableColumn getItemName() {
		return itemName;
	}
	public void setItemName(MainTableColumn itemName) {
		this.itemName = itemName;
	}
	public MainTableColumn getLableTickName() {
		return lableTickName;
	}
	public void setLableTickName(MainTableColumn lableTickName) {
		this.lableTickName = lableTickName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((XName == null) ? 0 : XName.hashCode());
		result = prime * result + ((YName == null) ? 0 : YName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result
				+ ((lableTickName == null) ? 0 : lableTickName.hashCode());
		result = prime * result
				+ ((sqlString == null) ? 0 : sqlString.hashCode());
		result = prime * result
				+ ((sysMainTable == null) ? 0 : sysMainTable.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StatisticsPicture other = (StatisticsPicture) obj;
		if (XName == null) {
			if (other.XName != null)
				return false;
		} else if (!XName.equals(other.XName))
			return false;
		if (YName == null) {
			if (other.YName != null)
				return false;
		} else if (!YName.equals(other.YName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (lableTickName == null) {
			if (other.lableTickName != null)
				return false;
		} else if (!lableTickName.equals(other.lableTickName))
			return false;
		if (sqlString == null) {
			if (other.sqlString != null)
				return false;
		} else if (!sqlString.equals(other.sqlString))
			return false;
		if (sysMainTable == null) {
			if (other.sysMainTable != null)
				return false;
		} else if (!sysMainTable.equals(other.sysMainTable))
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
	
}
