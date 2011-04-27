package com.zsgj.itil.jfreeChart.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

public class StatisticsColumn extends BaseObject{
	private Long id;
	private SystemMainTableColumn sysMainTableColumn;
//	private SystemMainTableColumn itemName;//每个元素的不同颜色代表的东西
//	private SystemMainTableColumn lableTickName;//每一种分类的名称.在图的底部	
	private StatisticsPicture statPicture;//该字段对应的统计图
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final StatisticsColumn other = (StatisticsColumn) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
//	public SystemMainTableColumn getItemName() {
//		return itemName;
//	}
//	public void setItemName(SystemMainTableColumn itemName) {
//		this.itemName = itemName;
//	}
//	public SystemMainTableColumn getLableTickName() {
//		return lableTickName;
//	}
//	public void setLableTickName(SystemMainTableColumn lableTickName) {
//		this.lableTickName = lableTickName;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemMainTableColumn getSysMainTableColumn() {
		return sysMainTableColumn;
	}
	public void setSysMainTableColumn(SystemMainTableColumn sysMainTableColumn) {
		this.sysMainTableColumn = sysMainTableColumn;
	}
	public StatisticsPicture getStatPicture() {
		return statPicture;
	}
	public void setStatPicture(StatisticsPicture statPicture) {
		this.statPicture = statPicture;
	}
}
