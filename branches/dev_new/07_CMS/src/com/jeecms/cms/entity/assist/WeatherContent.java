/**
 * @Probject Name: CMS
 * @Path: com.jeecms.cms.entity.assistWeatherContent.java
 * @Create By Jack
 * @Create In 2011-9-15 下午09:46:13
 * TODO
 */
package com.jeecms.cms.entity.assist;

import java.util.Calendar;
import java.util.List;

/**
 * @Class Name WeatherContent
 * @Author Jack
 * @Create In 2011-9-15
 */
public class WeatherContent {
	
	/**
	 * 被查询城市
	 */
	private String city;
	
	/**
	 * 城市代码
	 */
	private String postal_code ;
	
	/**
	 * 精度
	 */
	private String latitude_e6 ;
	
	/**
	 * 维度
	 */
	private String longitude_e6 ;
	
	/**
	 * 预报时间
	 */
	private String forecast_date ;
	
	/**
	 * 当前时间
	 */
	private String current_date_time ;
	
	/**
	 * 时间系统
	 */
	private String unit_system ;
	
	/**
	 * 当天
	 */
	private ForecastWeather today;
	
	/**
	 * 明天
	 */
	private ForecastWeather nextDay;
	
	
	
	/**
	 * 当前具体天气信息
	 */
	private CurrentWeather cw;

	/**
	 * 未来天气,包括本日及近三天的
	 */
	private List<ForecastWeather> fw;
	
	/**
	 * @Return the String city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @Param String city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @Return the String postal_code
	 */
	public String getPostal_code() {
		return postal_code;
	}

	/**
	 * @Param String postal_code to set
	 */
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	/**
	 * @Return the String latitude_e6
	 */
	public String getLatitude_e6() {
		return latitude_e6;
	}

	/**
	 * @Param String latitude_e6 to set
	 */
	public void setLatitude_e6(String latitude_e6) {
		this.latitude_e6 = latitude_e6;
	}

	/**
	 * @Return the String longitude_e6
	 */
	public String getLongitude_e6() {
		return longitude_e6;
	}

	/**
	 * @Param String longitude_e6 to set
	 */
	public void setLongitude_e6(String longitude_e6) {
		this.longitude_e6 = longitude_e6;
	}

	/**
	 * @Return the String forecast_date
	 */
	public String getForecast_date() {
		return forecast_date;
	}

	/**
	 * @Param String forecast_date to set
	 */
	public void setForecast_date(String forecast_date) {
		this.forecast_date = forecast_date;
	}

	/**
	 * @Return the String current_date_time
	 */
	public String getCurrent_date_time() {
		return current_date_time;
	}

	/**
	 * @Param String current_date_time to set
	 */
	public void setCurrent_date_time(String current_date_time) {
		this.current_date_time = current_date_time;
	}

	/**
	 * @Return the String unit_system
	 */
	public String getUnit_system() {
		return unit_system;
	}

	/**
	 * @Param String unit_system to set
	 */
	public void setUnit_system(String unit_system) {
		this.unit_system = unit_system;
	}

	/**
	 * @Return the CurrentWeather cw
	 */
	public CurrentWeather getCw() {
		return cw;
	}

	/**
	 * @Param CurrentWeather cw to set
	 */
	public void setCw(CurrentWeather cw) {
		this.cw = cw;
	}

	/**
	 * @Return the List<ForecastWeather> fw
	 */
	public List<ForecastWeather> getFw() {
		return fw;
	}

	/**
	 * @Param List<ForecastWeather> fw to set
	 */
	public void setFw(List<ForecastWeather> fw) {
		this.fw = fw;
	}

	public void setNextDay(ForecastWeather nextDay) {
		this.nextDay = nextDay;
	}

	public ForecastWeather getNextDay() {
		return nextDay;
	}

	public void setToday(ForecastWeather today) {
		this.today = today;
	}

	public ForecastWeather getToday() {
		return today;
	}
 

}
