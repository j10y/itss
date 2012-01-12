/**
 * 中国天气网获取信息
 */
package com.jeecms.cms.action.directive;

import static com.jeecms.common.web.freemarker.DirectiveUtils.OUT_BEAN;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

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

import com.jeecms.cms.entity.assist.ForecastWeatherCW;
import com.jeecms.cms.entity.assist.WeatherContentCW;
import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.freemarker.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 从中国天气网获取天气预报
 * @Class Name ChinaWeatherDirective
 * @Author T520
 * @Create In 2012-1-5
 */
public class ChinaWeatherDirective implements TemplateDirectiveModel {

	public static final String WEATH_CITY_CODE		= "cityCode";
	public static final String ICO_PATH				= "icoPath";
	
	/* (non-Javadoc)
	 * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
	 */
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		CmsSite site = FrontUtils.getSite(env);
		String cityNameE = DirectiveUtils.getString(WEATH_CITY_CODE, params);
		String icoPath = DirectiveUtils.getString(ICO_PATH, params);
		//获取未来6天天气
		Map<String, String> weatherInfo = getWeather(cityNameE);
		List<ForecastWeatherCW> fw = new ArrayList<ForecastWeatherCW>();
		//获取当前天气
		Map<String, String> weatherInfoNow = getWeatherNow(cityNameE);
		WeatherContentCW wc = new WeatherContentCW ();
		if(weatherInfoNow != null && weatherInfoNow.size() >0 && weatherInfo !=null && weatherInfo.size() >0){
			
			//获取当前最新天气 begin
			wc.setCityName(weatherInfoNow.get("city"));
			//获取当前时间
			Date now = new Date();
			String week = convert(now.getDay()) ;
			wc.setDate(week);
			
			//貌似是实时更新时间
			wc.setTime(weatherInfoNow.get("time"));
			wc.setCityId(weatherInfo.get("cityid"));
			wc.setTp(weatherInfoNow.get("temp"));
			wc.setSd(weatherInfoNow.get("SD"));
			wc.setWd(weatherInfoNow.get("WD"));
			wc.setWs(weatherInfoNow.get("WS"));
			wc.setWse(weatherInfoNow.get("WSE"));
			wc.setIsRadar(weatherInfoNow.get("isRadar"));
			wc.setRadar(weatherInfoNow.get("Radar"));
			wc.setIndex_d(weatherInfo.get("index_d"));
			wc.setIndex(weatherInfo.get("index"));
			//获取当前最新天气 end
			
			//获取从当天开始的6天内的天气  begin
			
			//保存第二天天气，以提供使用。
			Map<String, String> nd = new HashMap<String, String>();
			
			//获取天气的更新时间，以判断第一条记录的温度是当天最高与最低气温还是当天晚上到第二天上午的天气
			int utd = new Integer(weatherInfo.get("fchh")).intValue();
			//获取更新时间
			SimpleDateFormat sdf  =   new  SimpleDateFormat( "yyyy年MM月dd日 HH:mm:ss" ); 
			Date updateDate = null;
			try {
				updateDate = sdf.parse(weatherInfo.get("date_y") + " " + utd + ":00:00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				updateDate = now;
			}
			
			for(int i=1; i<7; i++){
				ForecastWeatherCW item = new ForecastWeatherCW();
				String tp = weatherInfo.get("temp" + i);
				
				Calendar c = Calendar.getInstance();
		        c.setTime(new Date());   //设置当前日期
		        c.add(Calendar.DATE, (i-1)); //日期加1
		        Date date = c.getTime(); //结果
				item.setDay_of_week(convert(date.getDay()));
				
				if(now.getDate() > updateDate.getDate()){
					if(i % 2 >0){
						item.setHighTP(tp.substring(tp.indexOf("~")+1));
					}else{
						item.setHighTP(tp.substring(tp.indexOf("~")+1));
						fw.get(i-1).setLowTP(tp.substring(0, tp.indexOf("~")));
					}
				}else{
					int tpF = new Integer(tp.substring(0, tp.indexOf("~")).substring(0, tp.substring(0, tp.indexOf("~")).indexOf("℃"))).intValue();
					int tpE = new Integer(tp.substring(tp.indexOf("~")+1).substring(0, tp.substring(tp.indexOf("~")+1).indexOf("℃"))).intValue();
					
					if(tpF > tpE){
						item.setHighTP(tp.substring(0, tp.indexOf("~")));
						item.setLowTP(tp.substring(tp.indexOf("~")+1));
					}else{
						if(i == 1){
							item.setHighTP(wc.getTp());
							nd.put("tph"+i, tp.substring(tp.indexOf("~")+1));
						}else{
							item.setHighTP(nd.get("tph"+(i-1)));
							nd.put("tph"+i, tp.substring(tp.indexOf("~")+1));
						}
						item.setLowTP(tp.substring(0, tp.indexOf("~")));
					}
				}		

				item.setWeathreAllDay(weatherInfo.get("weather" + i));
				item.setWind(weatherInfo.get("wind" + i));
				item.setFl(weatherInfo.get("fl" + i));
				item.setFx(weatherInfo.get("fx" + i));
				
				item.setWeatherD(weatherInfo.get("img_title" + (i*2-1)));
				item.setWeathreN(weatherInfo.get("img_title"+ (i*2)) );
				String iconD = weatherInfo.get("img" + (i*2-1));
				String iconN = weatherInfo.get("img" + (i*2));
				
				if(iconD.length()<2){
					item.setIconD("/" + icoPath + "/img/chinaweathre/d/d0" +  iconD + ".gif");
				}else{
					item.setIconD("/" + icoPath + "/img/chinaweathre/d/d" +  iconD + ".gif");
				}
				//判断晚间天气情况
				if(!"99".equals(iconN)){
					if(iconN.length()<2){
						item.setIconN("/" + icoPath + "/img/chinaweathre/n/n0" +  iconN + ".gif");
					}else{
						item.setIconN("/" + icoPath + "/img/chinaweathre/n/n" +  iconN + ".gif");
					}
				}else{
					if(iconD.length()<2){
						item.setIconN("/" + icoPath + "/img/chinaweathre/n/n0" +  iconD + ".gif");
					}else{
						item.setIconN("/" + icoPath + "/img/chinaweathre/n/n" +  iconD + ".gif");
					}
				}
				
				fw.add(item);
			}
		}
		
		wc.setFw(fw);
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(wc));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
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
			case 0:
				return "周日";
			case 1:
				return "周一";
			case 2:
				return "周二";
			case 3:
				return "周三";
			case 4:
				return "周四";
			case 5:
				return "周五";
			case 6:
				return "周六";
			default:
				break;
		}
		return retStr;
	}
	
	/**
	 * 获取天气信息，未来7天
	 * @Methods Name getWeather
	 * @Create In 2012-1-5 By T520
	 * @param cityNameE
	 * @return Map<String,String>
	 */
	private Map<String, String> getWeather(String cityNameE) {
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
		Map<String, String> resMap = null;
        HttpGet httpget = new HttpGet("http://m.weather.com.cn/data/" + cityNameE + ".html");
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
				xml = xml.substring(xml.indexOf(":")+1);
	            resMap = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(xml), Map.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
        return resMap;
	}
	
	/**
	 * 获取当前天气
	 * @Methods Name getWeatherNow
	 * @Create In 2012-1-9 By T520
	 * @param cityNameE
	 * @return Map<String,String>
	 */
	private Map<String, String> getWeatherNow(String cityNameE) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		Map<String, String> resMap = null;
        HttpGet httpget = new HttpGet("http://www.weather.com.cn/data/sk/" + cityNameE + ".html");
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
				
				xml = xml.substring(xml.indexOf(":")+1);
	            resMap = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(xml), Map.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
        return resMap;
	}
	
	public static void main(String[] args) {
		URL U = null;
		Map<String, String> resMap = null;
        try {
            String url = "http://www.weather.com.cn/data/sk/101010100.html";
            U = new URL(url);
            URLConnection connection = U.openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String res = "";
            while ((line = in.readLine()) != null) {
            	res += line;
            }
            in.close();
            in = null;
            res = res.substring(res.indexOf(":")+1);
            resMap = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(res), Map.class);
            
        } catch (Exception e) {
        	resMap = new HashMap<String, String>();
        	e.printStackTrace();
        }
    }
}
