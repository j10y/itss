package com.xp.commonpart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonOper {
	private static Map protocolMap = null;
	
	public static String converBinaryIpToStrIp(String binaryip) {
		int i = 0;
		StringBuffer ip = new StringBuffer();
		for (int j = 0; j < 32; j += 8) {
			String tempip = binaryip.substring(j, j + 8);
			ip.append(Integer.parseInt(tempip, 2));
			ip.append(".");
		}
		ip.deleteCharAt(ip.length() - 1);
		return ip.toString();
	}
	
	public static String converIpToBinaryIp(String ip){
		String[] ips = ip.split("\\.");
		String s= "";
		String temp ="";
		for (int i = 0; i < ips.length; i++) {
			temp = Integer.toBinaryString(Integer.parseInt(ips[i]));
			int num = 8-temp.length();
			if(temp.length()!=8){
				
				for (int j = 0; j <num; j++) {
					temp = "0"+temp ;
				}
			}
			s += temp ;
		}
		return s ;
	}
	
	public static Map getProtocolMap(){
		if(protocolMap == null){
			protocolMap = new HashMap();
			protocolMap.put("0", "hopopt");
			protocolMap.put("1", "icmp");
			protocolMap.put("2", "igmp");
			protocolMap.put("6", "tcp");
			protocolMap.put("17", "udp");
		}
		return protocolMap;
	}
	
	public static String setSqlparam(String sql, String... params) {
		String tempsql = sql;
		for (int i = 0; i < params.length; i++) {
			tempsql = tempsql.replaceFirst("#", params[i]);
		}
		return tempsql;
	}
	
	
	public static String calculatePercent(String bytes, String all) {
		double allbyte, tempbytes, percent = 0;
		String resutl;
		if (!all.endsWith("0") && all.length() > 0 && bytes.length() > 0) {
			tempbytes = Double.parseDouble(bytes);
			allbyte = Double.parseDouble(all);
			percent = tempbytes / allbyte;
			percent = percent * 100;
		}
		resutl= String.valueOf(percent);
		String[] decimal = resutl.split("\\.");
		if(decimal[1].length()>1)
			resutl = decimal[0]+"."+decimal[1].substring(0, 2);
		return resutl;
	}
	
	
	
	public static String getUniqueData(List dataList){
		Map tempMap;
		String allbytes=null;
		if (dataList.size() > 0) {
			tempMap = (Map) dataList.get(0);
			if (null != tempMap && null != tempMap.get("ALLBYTES"))
				allbytes = tempMap.get("ALLBYTES").toString();
			else
				allbytes =  null;
		}
		return allbytes;
	}
	public static void main(String[] args) {    
		System.out.println(converBinaryIpToStrIp("00001011000010110100110101010110")); 
		System.out.println(Integer.toBinaryString(86));
	}
}
