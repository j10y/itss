/**
 * @Probject Name: 07_CMS
 * @Path: com.jeecms.cms.entity.assistForecastWeatherCW.java
 * @Create By T520
 * @Create In 2012-1-9 下午09:28:53
 * TODO
 */
package com.jeecms.cms.entity.assist;

/**
 * @Class Name ForecastWeatherCW
 * @Author T520
 * @Create In 2012-1-9
 */
public class ForecastWeatherCW {
	/**
	 * 最低气温
	 */
	private String lowTP = "";
	
	/**
	 * 最高气温
	 */
	private String HighTP = "";
	/**
	 * 白天天气
	 */
	private String weatherD = "";
	/**
	 * 夜间天气
	 */
	private String weathreN = "";
	/**
	 * 风信息
	 */
	private String wind = "";
	/**
	 * @Return the String lowTP
	 */
	public String getLowTP() {
		return lowTP;
	}
	/**
	 * @Param String lowTP to set
	 */
	public void setLowTP(String lowTP) {
		this.lowTP = lowTP;
	}
	/**
	 * @Return the String HighTP
	 */
	public String getHighTP() {
		return HighTP;
	}
	/**
	 * @Param String highTP to set
	 */
	public void setHighTP(String highTP) {
		HighTP = highTP;
	}
	/**
	 * @Return the String weatherD
	 */
	public String getWeatherD() {
		return weatherD;
	}
	/**
	 * @Param String weatherD to set
	 */
	public void setWeatherD(String weatherD) {
		this.weatherD = weatherD;
	}
	/**
	 * @Return the String weathreN
	 */
	public String getWeathreN() {
		return weathreN;
	}
	/**
	 * @Param String weathreN to set
	 */
	public void setWeathreN(String weathreN) {
		this.weathreN = weathreN;
	}
	/**
	 * @Return the String wind
	 */
	public String getWind() {
		return wind;
	}
	/**
	 * @Param String wind to set
	 */
	public void setWind(String wind) {
		this.wind = wind;
	}
	/**
	 * @Return the String fx
	 */
	public String getFx() {
		return fx;
	}
	/**
	 * @Param String fx to set
	 */
	public void setFx(String fx) {
		this.fx = fx;
	}
	/**
	 * @Return the String fl
	 */
	public String getFl() {
		return fl;
	}
	/**
	 * @Param String fl to set
	 */
	public void setFl(String fl) {
		this.fl = fl;
	}
	/**
	 * 风向
	 */
	private String fx = "";
	/**
	 * 风力
	 */
	private String fl = "";
	
	/**
	 * 白天的图标路径
	 */
	private String iconD = "";
	/**
	 * @Return the String icon
	 */
	public String getIconD() {
		return iconD;
	}
	/**
	 * @Param String icon to set
	 */
	public void setIconD(String iconD) {
		this.iconD = iconD;
	}
	/**
	 * 晚上的图标路径
	 */
	private String iconN = "";
	/**
	 * @Return the String icon
	 */
	public String getIconN() {
		return iconN;
	}
	/**
	 * @Param String icon to set
	 */
	public void setIconN(String iconN) {
		this.iconN = iconN;
	}
	
	/**
	 * 当天全天天气
	 */
	private String weathreAllDay = "";
	/**
	 * @Return the String weathreAllDay
	 */
	public String getWeathreAllDay() {
		return weathreAllDay;
	}
	/**
	 * @Param String weathreAllDay to set
	 */
	public void setWeathreAllDay(String weathreAllDay) {
		this.weathreAllDay = weathreAllDay;
	}
	
	/**
	 * 星期
	 */
	public String day_of_week = "";
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
	
	
}
