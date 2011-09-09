package com.xp.commonpart.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {
	
	/**
	 * 生成随机Id�?,随机Id号的组成:四位年份两位月份两位日期两位时两位分两位�?+(3位随机号,不够3位前面补�?),�?:20100803163405346
	 * yyyyMMddHHmmss+3位随机数
	 * @return 随机Id�?
	 */
	public static String getRandomId(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			StringBuffer sb = new StringBuffer(sdf.format(new Date()));
			sb.append(fillZero(3,String.valueOf(new Random().nextInt(1000))));
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("生成随机Id号失�?");
		}
	}
	
	/**
	 * 补零
	 * @param length 补零后的长度
	 * @param source �?要补零的长符�?
	 * @return
	 */
	private static String fillZero(int length, String source) {
		StringBuilder result = new StringBuilder(source);
		for(int i=result.length(); i<length ; i++){
			result.insert(0, '0');
		}
		return result.toString();
	}
	
	 /**
     * 添加cookie //FIXME 用URLEncoder编码吗？
     * @param response
     * @param name cookie的名�?
     * @param value cookie的�??
     * @param maxAge cookie存放的时�?(以秒为单�?,假如存放三天,�?3*24*60*60; 如果值为0,cookie将随浏览器关闭�?�清�?)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {  
    	try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("URL编码异常");
		}
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge>0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    public static void addCookie(HttpServletResponse response, String name, String value) {  
    	try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("URL编码异常");
		}
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        //cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }
    /**
     * 获取cookie的�??
     * @param request
     * @param name cookie的名�?
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
    	Map<String, Cookie> cookieMap = WebUtils.readCookieMap(request);
    	try {
			URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("URL解码异常");
		}
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            String value = cookie.getValue();
            try {
            	value=URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("URL解码异常");
			}
            return value;
        }else{
            return null;
        }
    }
    
    protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }
    
    public static boolean copyRealFile(String srcName, String destName) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(srcName));
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destName));
			int i = 0;
			byte[] buffer = new byte[2048];
			while ((i = in.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
			out.close();
			in.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
}
