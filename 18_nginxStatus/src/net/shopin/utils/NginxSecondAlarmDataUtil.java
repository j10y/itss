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
 * ����һСʱ�ı�������<br>
 * ����ϵͳǿ�����ʱ������ݣ�����û��Ҫ����ȷ��avg����ȣ������˷�����ȥ��һЩ���ù�<br>
 * ��ʱ����
 */
public class NginxSecondAlarmDataUtil {
	
	private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	private static final Log logger = LogFactory.getLog("servicelog");
	
	
	/**
	 * ����
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
