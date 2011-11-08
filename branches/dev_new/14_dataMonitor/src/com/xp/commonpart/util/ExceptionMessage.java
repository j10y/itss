package com.xp.commonpart.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ExceptionMessage {
	public static void sendMessage(String message,String phone,String realName){
		String sms =realName+"你好："+message+"[上品折扣数据监控系统]";// "您好，订单" + orderNO + "已经确认，感谢您在上品折扣网购物。10月27日至11月6日上品11周年庆，在此期间的订单可能造成配送延缓，敬请谅解！上品折扣网";	   
		URL U = null;
	     try {
	         String url = "http://219.238.160.81/interface/limitnew.asp?username=spsy&password=123456&message=" + URLEncoder.encode(sms, "GBK") + "&phone=" + phone+ "&epid=6181&linkid=";
	         U = new URL(url);
	         URLConnection connection = U.openConnection();
	         connection.connect();
	         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	         String line;
	         while ((line = in.readLine()) != null) {
	             //System.out.println(line);
	         }
	         in.close();
	     } catch (Exception e) {
	         try {
	             Thread.sleep(500);
	             String url = "http://219.238.160.81/interface/limitnew.asp?username=spsy&password=123456&message=" + URLEncoder.encode(sms, "GBK") + "&phone=" + phone + "&epid=6181&linkid=";
	             U = new URL(url);
	             URLConnection connection = U.openConnection();
	             connection.connect();
	             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	             String line;
	             while ((line = in.readLine()) != null) {
	                 //System.out.println(line);
	             }
	             in.close();
	         } catch (Exception e1) {
	             System.out.println(e1.getMessage());
	         }
	     }
	}
}
