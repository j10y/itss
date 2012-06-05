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
public class NginxSecondAlarmDataUtil {
	
	private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	private static final Log logger = LogFactory.getLog("servicelog");
	
	
	/**
	 * 队列
	 */
	public static final Queue<String> webKeyQueue = new java.util.concurrent.ConcurrentLinkedQueue<String>();	
	public static final Queue<String> appKeyQueue = new java.util.concurrent.ConcurrentLinkedQueue<String>();
	
	
	public static void addWebData(String ngnixConnCount){
		synchronized (webKeyQueue) {
			webKeyQueue.add(ngnixConnCount);
			while (webKeyQueue.size() > 60) {
				webKeyQueue.poll();
			}
		}
	}
	
	public static void addAppData(String ngnixConnCount){
		synchronized (appKeyQueue) {
			appKeyQueue.add(ngnixConnCount);
			while (appKeyQueue.size() > 60) {
				appKeyQueue.poll();
			}
		}
	}

}
