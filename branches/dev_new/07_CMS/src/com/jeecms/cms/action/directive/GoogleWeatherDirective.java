/**
 * @Probject Name: CMS
 * @Path: com.jeecms.cms.action.directiveGoogleWeatherDirective.java
 * @Create By Jack
 * @Create In 2011-9-15 下午09:15:22
 * TODO
 */
package com.jeecms.cms.action.directive;

import static com.jeecms.common.web.freemarker.DirectiveUtils.OUT_BEAN;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.jeecms.cms.entity.assist.CurrentWeather;
import com.jeecms.cms.entity.assist.ForecastWeather;
import com.jeecms.cms.entity.assist.WeatherContent;
import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.freemarker.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 获取天气预报信息
 * @Class Name GoogleWeatherDirective
 * @Author Jack
 * @Create In 2011-9-15
 */
public class GoogleWeatherDirective implements TemplateDirectiveModel {
	
	public static final String WEATH_CITY_EN	= "cityNameE";
	public static final String WEATH_CITY_CN	= "cityNameC";
	public static final String ICO_PATH			= "icoPath";

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
	 */
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		CmsSite site = FrontUtils.getSite(env);
		String cityNameE = DirectiveUtils.getString(WEATH_CITY_EN, params);
		String cityNameC = DirectiveUtils.getString(WEATH_CITY_CN, params);
		String icoPath = DirectiveUtils.getString(ICO_PATH, params);
		
		WeatherContent wc = new WeatherContent();
		try{
			
			StringBuilder sbd = new StringBuilder();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			Document doc = getWeather(cityNameE,httpClient);
			
			//获取概况
			NodeList n1 = getNode(doc, "forecast_information", 0);
			wc.setCity(cityNameC);
			wc.setCurrent_date_time(getAttributeValue(n1, 5, 0));
			wc.setForecast_date(getAttributeValue(n1, 4, 0));
			wc.setPostal_code(getAttributeValue(n1, 1, 0));
			wc.setLatitude_e6(getAttributeValue(n1, 2, 0));
			wc.setLongitude_e6(getAttributeValue(n1, 3, 0));
			wc.setUnit_system(getAttributeValue(n1, 6, 0));
			
			//获取当前天气信息
			NodeList n2 = getNode(doc, "current_conditions", 0);
			CurrentWeather cw = new CurrentWeather();
			cw.setCondition(getAttributeValue(n2, 0, 0));
			cw.setTemp_f(getAttributeValue(n2, 1, 0));
			cw.setTemp_c(getAttributeValue(n2, 2, 0));
			cw.setHumidity(getAttributeValue(n2, 3, 0));
			cw.setIcon("/" + icoPath + "/img" +  getAttributeValue(n2, 4, 0));
			if(getAttributeValue(n2, 5, 0) != null){
				cw.setWind_condition(getAttributeValue(n2, 5, 0));
			}
			if(getAttributeValue(n2, 6, 0) != null){
				cw.setWind_condition(getAttributeValue(n2, 5, 0));
			}
			
			
			//获取本日及近3日内天气信息
			List<ForecastWeather> fwList = new ArrayList<ForecastWeather>();
			
			//本日
			int fwSize =  getNodeSize(doc, "forecast_conditions");
			if(fwSize != 0){
				for(int i = 0; i< fwSize; i++){
					NodeList fwN = getNode(doc, "forecast_conditions", i);
					ForecastWeather fw =new ForecastWeather();
					fw.setDay_of_week(getAttributeValue(fwN, 0, 0));
					fw.setLow(getAttributeValue(fwN, 1, 0));
					fw.setHigh(getAttributeValue(fwN, 2, 0));
					fw.setIcon("/" + icoPath + "/img" + getAttributeValue(fwN, 3, 0));
					fw.setCondition(getAttributeValue(fwN, 4, 0));
					fwList.add(fw);
				}
			}
			
			wc.setCw(cw);
			wc.setFw(fwList);
			
			wc.setToday(this.getToday(fwList));
			wc.setNextDay(this.getNextDay(fwList));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(wc));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
		
	}

	/**
	 * 获取今天是周几的信息
	 * @Methods Name getToday
	 * @Create In 2011-9-16 By Jack
	 * @return ForecastWeather
	 */
	public ForecastWeather getToday(List<ForecastWeather> fw){
		Calendar c = Calendar.getInstance();
		String week = convert(c.get(Calendar.DAY_OF_WEEK));
		if(week != null && !"".equalsIgnoreCase(week)){
			for(ForecastWeather item : fw){
				if(item.getDay_of_week().equalsIgnoreCase(week)){
					return item;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取下一天
	 * @Methods Name getNextDay
	 * @Create In 2011-9-16 By Jack
	 * @return ForecastWeather
	 */
	public ForecastWeather getNextDay(List<ForecastWeather> fw){
		Calendar c = Calendar.getInstance();
		int nowW = c.get(Calendar.DAY_OF_WEEK);
		nowW += 1;
		String week = convert(nowW);
		if(week != null && !"".equalsIgnoreCase(week)){
			for(ForecastWeather item : fw){
				if(item.getDay_of_week().equalsIgnoreCase(week)){
					return item;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取对应中文周几
	 * @Methods Name convert
	 * @Create In 2011-9-16 By Jack
	 * @param val
	 * @return String
	 */
	private String convert(int val){
		String retStr = "";
		switch (val) {
			case 1:
				return "周日";
			case 2:
				return "周一";
			case 3:
				return "周二";
			case 4:
				return "周三";
			case 5:
				return "周四";
			case 6:
				return "周五";
			case 7:
				return "周六";
			default:
				break;
		}
		return retStr;
	}
	
	/**
	* 获取节点集合
	* @param doc : Doument 对象
	* @param tagName : 节点名
	* @param index : 找到的第几个
	* @return
	*/
	private static NodeList getNode(Document doc, String tagName, int index) {
		try{
			NodeList n = doc.getElementsByTagName(tagName).item(index).getChildNodes();
			return n;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 获取节点集合长度
	 * @Methods Name getNodeSize
	 * @Create In 2011-9-15 By Jack
	 * @param doc
	 * @param tagName
	 * @return int
	 */
	private static int getNodeSize(Document doc, String tagName){
		return doc.getElementsByTagName(tagName).getLength();
	}

	/**
	* 获取节点内容
	* @param node : nodelist
	* @param index : 节点索引, 也可使用 getNamedItem(String name) 节点名查找
	* @param item : 属性的索引
	* @return
	*/
	private static String getAttributeValue(NodeList node, int index, int item) {
		try{
			String v = node.item(index).getAttributes().item(item).getNodeValue();
			return v;
		}catch(Exception e){
			return null;
		}
	}
	
    
    /**
     *正则匹配
     * @param s
     * @param pattern
     * @return
     */
	private static boolean matcher(String s, String pattern) { 
    	 Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE); 
    	 Matcher matcher = p.matcher(s); 
    	 if (matcher.find()) {
    		 return true;
	     } else {
	    	 return false;
	     }
     }
     
    /** 
     *获取Response内容字符集
     *
     * @param response
     * @return
     */
     private static String getContentCharset(HttpResponse response) {
    	 String charset = "ISO_8859-1";
    	 Header header = response.getEntity().getContentType(); 
    	 if (header != null) { 
    		 String s = header.getValue(); 
    		 if (matcher(s, "(charset)\\s?=\\s?(utf-?8)")) {
    			 charset = "utf-8";
    		 } else if (matcher(s, "(charset)\\s?=\\s?(gbk)")) {
    			 charset = "gbk";
    		 } else if (matcher(s, "(charset)\\s?=\\s?(gb2312)")) {
    			 charset = "gb2312";
    		 }
    	 }
    	 return charset;
     }
	
	
	/**
	 * 获取天气信息
	 * @Methods Name getWeather
	 * @Create In 2011-9-15 By Jack
	 * @param city 城市：输入拼音
	 * @param httpClient
	 * @return Document
	 */
	private static Document getWeather(String city, DefaultHttpClient httpClient) {
		//创建GET方法的实例
		HttpGet httpget = new HttpGet("http://www.google.com/ig/api?hl=zh-cn&weather=" + city);
		HttpParams params = new BasicHttpParams();
		 // HTTP 协议的版本,1.1/1.0/0.9
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	    // 字符集
	    HttpProtocolParams.setContentCharset(params, "UTF-8");
		try{
			HttpResponse resp = httpClient.execute(httpget);
			
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity he = resp.getEntity();
				String xml = EntityUtils.toString(he);
				//System.out.println(xml);
				
				InputStream inp = new ByteArrayInputStream(xml.getBytes("utf-8"));
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inp);
		        return doc;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
	
	
}
