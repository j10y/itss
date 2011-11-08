package com.xp.commonpart.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public final class HttpUtil {

	/**
	 * 将request属�?�中的键值对形式的�?�置入Map
	 * @Methods Name requestAttri2Map
	 * @param request
	 * @return Map
	 */
	public static Map requestAttri2Map(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enume = request.getAttributeNames();
		while(enume.hasMoreElements()){
			Object item = enume.nextElement();
			String attribute = item.toString();
			Object value = request.getAttribute(attribute);
			map.put(attribute, value);
		}
		return map;
	}
	
	/**
	 * 将Map中的键�?�对置入HttpServletRequest的属性attribute�?
	 * @Methods Name Map2RequestAttri
	 * @param request
	 * @param requestParams
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest map2RequestAttri(HttpServletRequest request, Map requestParams){
		Set keyset = requestParams.keySet();
		Iterator iter = keyset.iterator();
		while(iter.hasNext()){
			String attribute = (String) iter.next();
			Object value = requestParams.get(attribute);
			request.setAttribute(attribute, value);
		}
		return request;
	}
	
	/**
	 * 将请求中的搜索参数置入Map�?
	 * @Methods Name requestParam2Map
	 * @param request
	 * @return Map
	 */
	public static Map<String, String> requestParam2Map(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		Enumeration enume = request.getParameterNames();
		while(enume.hasMoreElements()){
			Object item = enume.nextElement();
			String paramName = item.toString();
			String value = request.getParameter(paramName);
			String[] values = request.getParameterValues(paramName);
			if(values.length>1){
				String va=values[0];
				for(int i=1;i<values.length;i++){
					va+=","+values[i];
				}
				value=va;
			}
			map.put(paramName, value);
		}
		return map;
	}
	
	/**
	 * 将请求中的搜索参数以Map形式返回，与方法requestParam2Map的区�?
	 * 是request中的参数值是多�?�类型，如页面的复�?�列�?
	 * @Methods Name requestParamValues2Map
	 * @param request
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> requestParamValues2Map(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enume = request.getParameterNames();
		while(enume.hasMoreElements()){
			Object item = enume.nextElement();
			String paramName = item.toString();
			String[] values = request.getParameterValues(paramName);
			map.put(paramName, values);
						
		}
		return map;
	}
	
	/**
	 * 将请求中的搜索参数以Map形式返回，与方法requestParam2Map的区�?
	 * 是request中的参数值可能是多�?�类型，也可能是单�?�类型的
	 * @Methods Name requestParamValuesOrValue2Map
	 * @param request
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> requestParamValuesOrValue2Map(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enume = request.getParameterNames();
		while(enume.hasMoreElements()){
			Object item = enume.nextElement();
			String paramName = item.toString();
			String[] values = request.getParameterValues(paramName);
			if(values.length>1){
				map.put(paramName, values);
			}else if(values.length==1){
				map.put(paramName, values[0]);
			}
				
		}
		return map;
	}
	
	/**
	 * 获取当前URL的全路径，如应用根路�?+请求的查询字符串
	 * @Methods Name getURL
	 * @param request
	 * @return String
	 */
    public static String getURL(HttpServletRequest request) {
        StringBuffer sb = request.getRequestURL();
        String queryString = request.getQueryString();
        if(queryString!=null)
            return sb.toString() + "?" + queryString;
        return sb.toString();
    }

    /**
     * Get Integer parameter from request. If specified parameter name 
     * is not found, the default value will be returned.
     */
    public static int getInt(HttpServletRequest request, String paramName, int defaultValue) {
        String s = request.getParameter(paramName);
        if(s==null || s.equals(""))
            return defaultValue;
        return Integer.parseInt(s);
    }

    /**
     * Get Integer parameter from request. If specified parameter name 
     * is not found, an Exception will be thrown.
     */
    public static int getInt(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        return Integer.parseInt(s);
    }

    /**
     * Get String parameter from request. If specified parameter name 
     * is not found, the default value will be returned.
     */
    public static String getString(HttpServletRequest request, String paramName, String defaultValue) {
        String s = request.getParameter(paramName);
        if(s==null || s.equals(""))
            return defaultValue;
        return s;
    }

    /**
     * Get String parameter from request. If specified parameter name 
     * is not found or empty, an Exception will be thrown.
     */
    public static String getString(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        if(s==null) return null;
        /*if(s==null || s.equals(""))
            throw new NullPointerException("Null parameter: " + paramName);*/
        return s;
    }

    /**
     * Get Boolean parameter from request. If specified parameter name 
     * is not found, an Exception will be thrown.
     */
    public static boolean getBoolean(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        return Boolean.getBoolean(s);
    }

    /**
     * Get Boolean parameter from request. If specified parameter name 
     * is not found, the default value will be returned.
     */
    public static boolean getBoolean(HttpServletRequest request, String paramName, boolean defaultValue) {
        String s = request.getParameter(paramName);
        if(s==null || s.equals(""))
            return defaultValue;
        return Boolean.parseBoolean(s);
    }

    /**
     * Get float parameter from request. If specified parameter name 
     * is not found, an Exception will be thrown.
     */
    public static float getFloat(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        return Float.parseFloat(s);
    }

    /**
     * 暂且将jdk1.4.2.15不支持replace(string, string)的地方改�?
     * replaceAll();
     * @param text
     * @return
     */
    public static String htmlEncode(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll(" ", "&nbsp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("\'", "&apos;");
        return text.replaceAll("\n", "<br/>");
    }
    
    public static String htmlOutEncode(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll(" ", "&nbsp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("\'", "&apos;");
        text = text.replaceAll("\n", "&#10"); //换行
        return text.replaceAll("\r", "&#13"); //回车
    }
    
    public static String htmlOutEncode1(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll(" ", "&nbsp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("\'", "&apos;");
        text = text.replaceAll("\n", "<br />"); //换行
        return text.replaceAll("\r", ""); //回车
    }
    public static String htmlOutEncode2(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("'", ",");
        text = text.replaceAll(":", ";");
        text = text.replaceAll("�?", ";");
        text = text.replaceAll("\n", "  "); //换行
        return text.replaceAll("\r", ""); //回车
    }
    /**
     * GBK转换到UTF8
     * @Methods Name GBKtoUTF8
     * @Create In 2008-6-24 By zhangpeng
     * @param source
     * @return String
     */
    public static String GBKtoUTF8(String source){
    	String dest;
    	
    	if(source == null || source.equals("")){
    		return "";
    	}else{
    		try {
				dest = new String(source.getBytes("gbk"),"ISO-8859-1");
				dest = new String(dest.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return source;
			}
			
			return dest;
    	}
    }
    public static String ISO8859toGBK(String source){
    	String dest;
    	
    	if(source == null || source.equals("")){
    		return "";
    	}else{
    		try {
				dest = new String(source.getBytes("ISO-8859-1"),"GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return source;
			}
			
			return dest;
    	}
    }
    
    public static String ISO8859toUTF8(String source){
    	String dest;
    	
    	if(source == null || source.equals("")){
    		return "";
    	}else{
    		try {
				dest = new String(source.getBytes("ISO-8859-1"),"UTF8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return source;
			}
			
			return dest;
    	}
    }
    /**
     * 将页面传来的Unicode字符编码转换为正常字�?
     * @Methods Name ConverUnicode
     * @param source
     * @return String
     */
    public static String ConverUnicode(String source){
    	StringBuffer  sb=new  StringBuffer();  
    	if(source != null && !source.equalsIgnoreCase("")){
    		String[] sa = source.split(";");
    		
    		for(int i=0; i<sa.length; i++){
    			String tp = sa[i];
    			tp = tp.replace("&#", "");
    			int charCode = Integer.parseInt(tp);  
    			sb.append((char)charCode);
    		}
    		
    		return sb.toString();
    	}else{
    		return "";
    	}
    }
    
    public static String ConverUnicodeToString(String source){
    	 StringBuffer tmp = new StringBuffer();
         tmp.ensureCapacity(source.length());
         int lastPos = 0, pos = 0;
         char ch;
         while (lastPos < source.length()) {
            pos = source.indexOf("%", lastPos);
            if (pos == lastPos) {
               if (source.charAt(pos + 1) == 'u') {
                  ch = (char) Integer.parseInt(source.substring(pos + 2, pos + 6), 16);
                  tmp.append(ch);
                  lastPos = pos + 6;
               }
               else {
                  ch = (char) Integer.parseInt(source.substring(pos + 1, pos + 3), 16);
                  tmp.append(ch);
                  lastPos = pos + 3;
               }
            }
            else {
               if (pos == -1) {
                  tmp.append(source.substring(lastPos));
                  lastPos = source.length();
               }
               else {
                  tmp.append(source.substring(lastPos, pos));
                  lastPos = pos;
               }
            }
         }
         return tmp.toString();
    }
    
	/**
	 * GBK转换到URL
	 * 
	 * @Methods Name GBKtoURL
	 * @param Source
	 *            GB2312编码的字符串
	 * @return String URL编码的字符串
	 */
	public static String GBKtoURL(String Source) {
		String Dest;
		try {
			if (Source == null)
				return "";
			Dest = java.net.URLEncoder.encode(Source, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Dest = Source;
		}
		return Dest;
	}

	/**
	 * URL转换到GBK
	 * 
	 * @param Source
	 *            URL编码的字符串
	 * @return GB2312编码的字符串
	 */
	public static String URLtoGBK(String Source) {
		String Dest;
		try {
			if (Source == null)
				return "";
			Dest = java.net.URLDecoder.decode(Source, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Dest = Source;
		}
		return Dest;
	}
	
	public static String ConverToGBK(String Source){
		byte [] b;
		String utf8_value;
		try{
			utf8_value = Source;
			if(utf8_value == null)
				return "";
			b = utf8_value.getBytes("utf-8"); 
			String Dest = new String(b, "utf-8"); 
			return Dest;
		}catch(IOException e){
			return Source;
		}
	}
	
	/**
	 * 转义HTML标识�?
	 * @Methods Name ConverHtml
	 * @Create In Jul 4, 2008 By itnova
	 * @param Source
	 * @return String
	 */
	public static String ConverHtml(String Source){
		String destValue = Source;
		while(destValue.indexOf("\"") != -1){
			String dest = destValue.replace("\"", "&quot;");
			destValue = dest;
		}
		while(destValue.indexOf("'") != -1){
			String dest = destValue.replace("'", "&acute;");
			destValue = dest;
		}
		while(destValue.indexOf("<") != -1){
			String dest = destValue.replace("<", "&lt;");
			destValue = dest;
		}
		while(destValue.indexOf(">") != -1){
			String dest = destValue.replace(">", "&gt;");
			destValue = dest;
		}
//		while(destValue.indexOf("&") != -1){
//			String dest = destValue.replace("&", "&amp;");
//			destValue = dest;
//		}
//		while(destValue.indexOf(".") != -1){
//			String dest = destValue.replace("'", "&middot;");
//			destValue = dest;
//		}
		return destValue;
	}
	
	 /**
     * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
     * @param s 原文件名
     * @return 重新编码后的文件�?
     */
    public static String toUtf8String(String s) {
	StringBuffer sb = new StringBuffer();
	for (int i=0;i<s.length();i++) {
	    char c = s.charAt(i);
	    if (c >= 0 && c <= 255) {
		sb.append(c);
	    } else {
		byte[] b;
		try {
		    b = Character.toString(c).getBytes("utf-8");
		} catch (Exception ex) {
		    System.out.println(ex);
		    b = new byte[0];
		}
		for (int j = 0; j < b.length; j++) {
		    int k = b[j];
		    if (k < 0) k += 256;
		    sb.append("%" + Integer.toHexString(k).
		    toUpperCase());
		}
	    }
	}
	return sb.toString();
    }
}
