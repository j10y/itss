package com.xp.commonpart.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ExceptionMessage {
	public static void sendMessage(String message,String phone,String realName){
		String sms =realName+"你好："+message+"[上品折扣数据监控系统]";// "您好，订单" + orderNO + "已经确认，感谢您在上品折扣网购物。10月27日至11月6日上品11周年庆，在此期间的订单可能造成配送延缓，敬请谅解！上品折扣网";	   
		sms=sms.replace(".", "_");
		URL U = null;
	     try {
//	    		 sms = new String(sms.getBytes("ISO-8859-1"),
//							"UTF-8");
	         String url = "http://114.255.71.158:8061/?username=spsy&password=shangpin&message=" + URLEncoder.encode(sms, "GBK") + "&phone=" + phone+ "&epid=106181&linkid=&subcode=";
	         System.out.print("发送短信地址为"+url);
	         U = new URL(url);
	         URLConnection connection = U.openConnection();
	         connection.connect();
	         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	         String line;
	         while ((line = in.readLine()) != null) {
	             System.out.println("datamonitor:"+line);
	         }
	         in.close();
	     } catch (Exception e) {
	         try {
	             Thread.sleep(500);
	             String url = "http://114.255.71.158:8061/?username=spsy&password=shangpin&message=" + URLEncoder.encode(sms, "utf-8") + "&phone=" + phone+ "&epid=106181&linkid=&subcode=";
	             System.out.print("发送短信地址为"+url);
	             U = new URL(url);
	             URLConnection connection = U.openConnection();
	             connection.connect();
	             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	             String line;
	             while ((line = in.readLine()) != null) {
	            	 System.out.println("datamonitor:"+line);
	             }
	             in.close();
	         } catch (Exception e1) {
	             System.out.println(e1.getMessage());
	         }
	     }
	}
	public static void main(String [] args){
		ExceptionMessage d=new ExceptionMessage();
		d.sendMessage("测试172.16.180.11", "15811119460", "童俊彭");
	}
}
