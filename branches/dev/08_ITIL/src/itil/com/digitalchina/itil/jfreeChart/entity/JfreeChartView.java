package com.digitalchina.itil.jfreeChart.entity;

import com.digitalchina.info.framework.dao.BaseObject;

public class JfreeChartView extends BaseObject{
	private Long id;
	private String dataId;
	private String filePath;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
