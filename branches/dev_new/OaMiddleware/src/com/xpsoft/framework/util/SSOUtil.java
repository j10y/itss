package com.xpsoft.framework.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SSOUtil {
	
	/*单点登录参数加密密钥*/
	private static String AES_Key = "BBFAD6D0A3ACD2BBB4AECAFDC2EBD7F7";
	
	/*允许的验证间隔*/
	public static long ALLOW_TIME = 5*60*1000;//5分钟
	
	/*当前系统编号*/
	public static String SYS_ID  = "";
	
	/*支持的系统（系统编号规定为两位）*/
	private static HashMap<String, String> SSO_SYSTEM = new HashMap<String, String>();
	
	/*各系统的中转URL*/
	private static HashMap<String, String> SSO_FORWARD_URL = new HashMap<String, String>();

	/*各系统的验证URL（内网）*/
	private static HashMap<String, String> SSO_VALIDATE_URL = new HashMap<String, String>();
	
	static{
		String system = PropertiesUtil.getProperties("sso.systems");//所以支持单点的系统
		String[] sysa = system.split(",");
		String thissys = PropertiesUtil.getProperties("sso.this.sys");//当前系统
		
		for(String sys : sysa){
			String sysid = PropertiesUtil.getProperties("sso.sysid."+sys);
			String syshost = PropertiesUtil.getProperties("sso.syshost."+sys);
			String forward = PropertiesUtil.getProperties("sso.forward.url."+sys);
			String validate = PropertiesUtil.getProperties("sso.validate.url."+sys);
			
			if(sysid==null || syshost==null){
				continue;
			}
			
			SSO_SYSTEM.put(syshost, sysid);
			if(forward!=null){
				SSO_FORWARD_URL.put(sysid, forward);
			}
			if(validate!=null){
				SSO_VALIDATE_URL.put(sysid, validate);
			}
			if(sys.equals(thissys)){
				SYS_ID = sysid;
			}
		}
	}

	/**
	 * 生成唯一标识
	 */
	public static String getUUID(){
	    String uuid = UUID.randomUUID().toString().replaceAll("-", ""); //UUID
	    uuid += (int)(10+Math.random()*90); //随机数
	    uuid += (new Date()).getTime();     //时间戳
	    int len = uuid.length();
        if (len % 2 == 1) {
            uuid = uuid.substring(0, len-1);
        }
		return uuid.toUpperCase();
	}

	/**
	 * 获取目标系统编号
	 */
	public static String getTagSysID(String url){
		//防止url为空
		if(url==null || "".equals(url)){
			return null;
		}
		//判断url是否合法
		if(url.startsWith("http://")){
			url = url.substring("http://".length());
		}else if(url.startsWith("https://")){
			url = url.substring("https://".length());
		}else{
			return null;
		}
		//判断是否已经定义
		String key = "";
		String[] array = url.split("/");
		for(String str : array){
			key += "/"+str;
			if(key.startsWith("/")){
				key = key.substring(1);
			}
			String id = SSO_SYSTEM.get(key);
			if(id!=null){
				return id;
			}
		}
		return null;
	}
	
	/**
	 * 获取跳转地址
	 * @param key
	 * @param param
	 * @return
	 */
	public static String getForwardURL(String id, String param){
		String url = SSO_FORWARD_URL.get(id);
		url += param;
//		if(url.indexOf("?")>-1){
//			url += "&" + param;
//		}else{
//			url += "?" + param;
//		}
		return url;
	}
	
	/**
	 * 获取验证地址
	 * @param key
	 * @param param
	 * @return
	 */
	public static String getValidateURL(String id, String param){
		String url = SSO_VALIDATE_URL.get(id);
		url += param;
//		if(url.indexOf("?")>-1){
//			url += "&" + param;
//		}else{
//			url += "?" + param;
//		}
		return url;
	}

	/**
	 * 获取验证结果
	 * @param id
	 * @param param
	 * @return
	 */
	public static String getValidateInfo(String id, String param){
		String url = getValidateURL(id, param);
		return getValidateInfo(url);
	}
	
	/**
	 * 获取验证结果
	 * @param url
	 * @return
	 */
	public static String getValidateInfo(String url){
		try {
			URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();  
            conn.setDoOutput(true);  
            conn.setUseCaches(false);  
            conn.setDefaultUseCaches(false); 
            conn.setConnectTimeout(5000);  
            InputStream in = conn.getInputStream();
        	StringBuilder sb = new StringBuilder();
            if (in != null) {
            	String line;
            	try {
            		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            		while ((line = reader.readLine()) != null) {
            			sb.append(line);
            		}
            	} finally {
            		in.close();
            	}
            }
            return sb.toString();
			
//			HttpClient client = new HttpClient(); 
//			HttpMethod  http = new GetMethod(url); 
//			http.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(1, false));  
//			client.executeMethod(http);
//			String uStr = http.getResponseBodyAsString();
//			return uStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 判断是否是合法时间
	 * @param bgn
	 * @param end
	 * @return
	 */
	public static boolean isAllowTime(Date bgn, Date end){
		long b = bgn.getTime();
		long e = end.getTime();
		return (e-b)<ALLOW_TIME;
	}
	
	/**
	 * 参数加密
	 * @param param
	 * @return
	 */
	public static String encryptParam(String param){
		try {
            byte[] msgby = AES.hex2byte(param);
            byte[] keyby = AES_Key.getBytes("ASCII");
            byte[] debyt = AES.encrypt(msgby, keyby);
            String temp = AES.byte2hex(debyt);
            return temp;
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
	}
	/**
	 * 参数解密
	 * @param param
	 * @return
	 */
	public static String decryptParam(String param){
		try {
            byte[] msgby = AES.hex2byte(param);
            byte[] keyby = AES_Key.getBytes("ASCII");
            byte[] debyt = AES.decrypt(msgby, keyby);
            String temp = AES.byte2hex(debyt);
            return temp;
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
	}
}
