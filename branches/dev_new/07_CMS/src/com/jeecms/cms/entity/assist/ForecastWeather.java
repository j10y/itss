/**
 * @Probject Name: CMS
 * @Path: com.jeecms.cms.entity.assistWeath.java
 * @Create By Jack
 * @Create In 2011-9-15 下午09:24:17
 * TODO
 */
package com.jeecms.cms.entity.assist;

/**
 * 未来天气信息
 * @Class Name Weath
 * @Author Jack
 * @Create In 2011-9-15
 */
public class ForecastWeather {
	
	/**
	 * 星期
	 */
	private String day_of_week;
	
	/**
	 * 最低气温
	 */
	private String low;
	
	/**
	 * 最高气温
	 */
	private String high; 
	
	/**
	 * 天气图标
	 */
	private String icon;
	
	
	/**
	 * 信息提示
	 */
	private String condition;

	/**
	 * @Return the String day_of_week
	 */
	public String getDay_of_week() {
		return day_of_week;
	}

	/**
	 * @Param String day_of_week to set
	 */
	public void setDay_of_week(String day_of_week) {
		this.day_of_week = day_of_week;
	}

	/**
	 * @Return the String low
	 */
	public String getLow() {
		return low;
	}

	/**
	 * @Param String low to set
	 */
	public void setLow(String low) {
		this.low = low;
	}

	/**
	 * @Return the String high
	 */
	public String getHigh() {
		return high;
	}

	/**
	 * @Param String high to set
	 */
	public void setHigh(String high) {
		this.high = high;
	}

	/**
	 * @Return the String icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @Param String icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @Return the String condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @Param String condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
