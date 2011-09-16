/**
 * @Probject Name: CMS
 * @Path: com.jeecms.cms.entity.assistCurrentWeather.java
 * @Create By Jack
 * @Create In 2011-9-15 下午09:42:57
 * TODO
 */
package com.jeecms.cms.entity.assist;

/**
 * 当前天气情况
 * @Class Name CurrentWeather
 * @Author Jack
 * @Create In 2011-9-15
 */
public class CurrentWeather {
	
	/**
	 * 信息提示
	 */
	private String condition ;
	
	/**
	 * 华氏温度
	 */
	private String temp_f;
	
	/**
	 * 摄氏温度
	 */
	private String temp_c;
	
	/**
	 * 湿度
	 */
	private String humidity;
	
	/**
	 * 天气图标
	 */
	private String icon ;
	
	/**
	 * 风向
	 */
	private String wind_condition ;

	/**
	 * @Return the String wind_condition
	 */
	public String getWind_condition() {
		return wind_condition;
	}

	/**
	 * @Param String wind_condition to set
	 */
	public void setWind_condition(String wind_condition) {
		this.wind_condition = wind_condition;
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

	/**
	 * @Return the String temp_f
	 */
	public String getTemp_f() {
		return temp_f;
	}

	/**
	 * @Param String temp_f to set
	 */
	public void setTemp_f(String temp_f) {
		this.temp_f = temp_f;
	}

	/**
	 * @Return the String temp_c
	 */
	public String getTemp_c() {
		return temp_c;
	}

	/**
	 * @Param String temp_c to set
	 */
	public void setTemp_c(String temp_c) {
		this.temp_c = temp_c;
	}

	/**
	 * @Return the String humidity
	 */
	public String getHumidity() {
		return humidity;
	}

	/**
	 * @Param String humidity to set
	 */
	public void setHumidity(String humidity) {
		this.humidity = humidity;
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
	
	

}
