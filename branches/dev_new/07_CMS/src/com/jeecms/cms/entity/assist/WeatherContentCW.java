/**
 * @Probject Name: 07_CMS
 * @Path: com.jeecms.cms.entity.assistWeatherContentCW.java
 * @Create By T520
 * @Create In 2012-1-9 下午09:22:18
 * TODO
 */
package com.jeecms.cms.entity.assist;

import java.util.List;

/**
 * 中国天气网天气信息
 * @Class Name WeatherContentCW
 * @Author T520
 * @Create In 2012-1-9
 */
public class WeatherContentCW {

	/**
	 * 城市名称
	 */
	private String cityName = "";
	/**
	 * 城市ID
	 */
	private String cityId = "";
	/**
	 * 温度
	 */
	private String tp = "";
	/**
	 * 风向
	 */
	private String wd = "";
	/**
	 * 风力
	 */
	private String ws = "";
	/**
	 * 湿度
	 */
	private String sd = "";
	/**
	 * 风力，数值
	 */
	private String wse = "";
	/**
	 * 更新时间
	 */
	private String time = "";
	/**
	 * @Return the String cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @Param String cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @Return the String cityId
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * @Param String cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	/**
	 * @Return the String tp
	 */
	public String getTp() {
		return tp;
	}
	/**
	 * @Param String tp to set
	 */
	public void setTp(String tp) {
		this.tp = tp;
	}
	/**
	 * @Return the String wd
	 */
	public String getWd() {
		return wd;
	}
	/**
	 * @Param String wd to set
	 */
	public void setWd(String wd) {
		this.wd = wd;
	}
	/**
	 * @Return the String ws
	 */
	public String getWs() {
		return ws;
	}
	/**
	 * @Param String ws to set
	 */
	public void setWs(String ws) {
		this.ws = ws;
	}
	/**
	 * @Return the String sd
	 */
	public String getSd() {
		return sd;
	}
	/**
	 * @Param String sd to set
	 */
	public void setSd(String sd) {
		this.sd = sd;
	}
	/**
	 * @Return the String wse
	 */
	public String getWse() {
		return wse;
	}
	/**
	 * @Param String wse to set
	 */
	public void setWse(String wse) {
		this.wse = wse;
	}
	/**
	 * @Return the String time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @Param String time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @Return the String isRadar
	 */
	public String getIsRadar() {
		return isRadar;
	}
	/**
	 * @Param String isRadar to set
	 */
	public void setIsRadar(String isRadar) {
		this.isRadar = isRadar;
	}
	/**
	 * @Return the String radar
	 */
	public String getRadar() {
		return radar;
	}
	/**
	 * @Param String radar to set
	 */
	public void setRadar(String radar) {
		this.radar = radar;
	}
	/**
	 * 是否有雷达图
	 */
	private String isRadar = "";
	/**
	 * 雷达图地址
	 */
	private String radar = "";
	
	/**
	 * 当前时间
	 */
	private String date = "";
	/**
	 * @Return the String date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @Param String date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * 未来6天天气
	 */
	private List<ForecastWeatherCW> fw;
	/**
	 * @Return the List<ForecastWeatherCW> fw
	 */
	public List<ForecastWeatherCW> getFw() {
		return fw;
	}
	/**
	 * @Param List<ForecastWeatherCW> fw to set
	 */
	public void setFw(List<ForecastWeatherCW> fw) {
		this.fw = fw;
	}
	
	/**
	 * 穿衣气象指数
	 */
	private String index = "";
	/**
	 * @Return the String index
	 */
	public String getIndex() {
		return index;
	}
	/**
	 * @Param String index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}
	/**
	 * 穿衣气象指数建议信息
	 */
	private String index_d = "";
	/**
	 * @Return the String index_d
	 */
	public String getIndex_d() {
		return index_d;
	}
	/**
	 * @Param String index_d to set
	 */
	public void setIndex_d(String index_d) {
		this.index_d = index_d;
	}
}
