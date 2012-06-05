package net.shopin.utils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 保存一小时的报警数据<br>
 * 由于系统强调最近时间的数据，这里没必要做精确的avg计算等，反而浪费性能去做一些无用功<br>
 * 暂时保留
 */
public class NginxMinuteArgAlarmDataUtil {
	
	private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	private static final Log logger = LogFactory.getLog("servicelog");
	
	/**
	 * key:yyyy-mm-dd hh:MM
	 * value:avg(ngnixConnCount*count),count
	 */
	public static final Map<String, String> webDataMap = Collections.synchronizedMap(new LinkedHashMap<String, String>());
	
	/**
	 * key队列
	 */
	public static final Queue<String> webKeyQueue = new java.util.concurrent.ConcurrentLinkedQueue<String>();
	
	
	public static final Map<String, String> appDataMap = Collections.synchronizedMap(new LinkedHashMap<String, String>());
	
	public static final Queue<String> appKeyQueue = new java.util.concurrent.ConcurrentLinkedQueue<String>();
	
	
	public static void addWebData(Date date, int ngnixConnCount){
		synchronized (webDataMap) {
			String timeKey = sf.format(date);
			if (webDataMap.containsKey(timeKey)) {
				String[] datas = webDataMap.get(timeKey).split(",");
				int avg = Integer.valueOf(datas[0]);
				int count = Integer.valueOf(datas[1]);
				webDataMap.put(timeKey, (int) ((avg * count + ngnixConnCount)
						/ (count + 1)) + "," + (++count));
			} else {
				webDataMap.put(timeKey, ngnixConnCount + ",1");
				
				webKeyQueue.add(timeKey);
			}
			while(webDataMap.size() > 60 || webKeyQueue.size()> 60){
				webDataMap.remove(webKeyQueue.poll());
			}
		}
		System.out.println("WEB Ngnix key:" + webKeyQueue + "\nMap" + webDataMap);
	}
	
	public static void addAppData(Date date, int ngnixConnCount){
		synchronized (appDataMap) {
			String timeKey = sf.format(date);
			if (appDataMap.containsKey(timeKey)) {
				String[] datas = webDataMap.get(timeKey).split(",");
				int avg = Integer.valueOf(datas[0]);
				int count = Integer.valueOf(datas[1]);
				appDataMap.put(timeKey, (int) ((avg * count + ngnixConnCount)
						/ (count + 1)) + "," + (++count));
			} else {
				appDataMap.put(timeKey, ngnixConnCount + ",1");
				
				appKeyQueue.add(timeKey);
				
			}
			while(appDataMap.size() > 60 || appKeyQueue.size() > 60){
				appDataMap.remove(appKeyQueue.poll());
			}
		}
		System.out.println("APP Ngnix Key:" + appKeyQueue + "\nMap: " + appDataMap);
	}

}
