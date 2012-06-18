package com.xpsoft.framework.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import net.sf.json.JSONObject;


/**
 * HTTP处理相关的辅助类
 * @Class Name HttpUtil
 * @Author likang
 * @Create In Jul 22, 2010
 */
public final class HttpUtil {
	 /**
	  * 将从前台传递的参数中带有 单引号 双引号 回车换行 \符号 转义
	  * 如果不转义则通过json串传递到前台会出错
	  * @Methods Name coverExcape
	  * @Create In May 6, 2010 By likang
	  * @param source
	  * @return String
	  */
	public static String coverExcape(String source) {
	    	if(source != null) {
	    		return source.replace("\\", "\\\\").replace("\r\n", "\\r\\n").replace("\'", "\\\'").replace("\t", "\\t").replace("\"", "\\\"");
	    	} else {
	    		return "";
	    	}		 
	  }
	  
	/**
	 * 是否是Ajax请求
	 * @Methods Name isAjaxRequest
	 * @Create In Aug 31, 2010 By likang
	 * @param request
	 * @return boolean
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		//在服务器端判断request来自异步还是同步请求
		boolean isAjaxRequest = (request.getHeader("x-requested-with") != null)? true:false; 
		return isAjaxRequest;
	}
	
	/**
	 * 传入Object类型 如果不是null则返回 true
	 * 如果是String类型 不是null且不是空字符串 返回 true
	 * 否则 false
	 * @param object
	 * @return
	 */
	public static boolean isNotNullAndEmpty(Object object) {
		if (object != null) {
			if (object instanceof String) {
				if (((String) object).trim().length() > 0) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 将request属性中的键值对形式的值置入Map
	 * @Methods Name requestAttri2Map
	 * @Create In Jul 22, 2010 By likang
	 * @param request
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> requestAttri2Map(HttpServletRequest request){
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
	 * 将Map中的键值对置入HttpServletRequest的属性attribute中
	 * @Methods Name map2RequestAttri
	 * @Create In Jul 22, 2010 By likang
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
	 * 将请求中的搜索参数置入Map中
	 * @Methods Name requestParam2Map
	 * @Create In Jul 22, 2010 By likang
	 * @param request
	 * @return Map<String,String>
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
	 * 将请求中的搜索参数以Map形式返回，与方法requestParam2Map的区别
	 * @Methods Name requestParamValues2Map
	 * @Create In Jul 22, 2010 By likang
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
	 * 中文编码成utf8
	 * @Methods Name encodeUTF8
	 * @Create In Jul 27, 2011 By likang
	 * @param value
	 * @return String
	 */
	public static String encodeUTF8(String value) {
		// TODO Auto-generated method stub
		if (value != null) {
			try {
				value = URLEncoder.encode(value, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return value;
		} else {
			return "";
		}
	}
    
    /**
     * 返回一个Request请求的参数的整数值，如果参数不存在，返回默认值
     * @param request   HttpRequest
     * @param paramName 参数名称
     * @param defaultValue 默认值
     * @return 
     */
    public static int getInt(HttpServletRequest request, String paramName, int defaultValue) {
        String s = request.getParameter(paramName);
        if(s==null || s.equals(""))
            return defaultValue;
        return Integer.parseInt(s);
    }

    /**
     * 返回一个Request请求的参数的整数值
     * @param request   HttpRequest
     * @param paramName 参数名称
     * @return 
     */  
    public static int getInt(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        return Integer.parseInt(s);
    }

    /**
     * 返回一个Request请求的参数的字符串值，如果参数不存在，返回默认值
     * @param request   HttpRequest
     * @param paramName 参数名称
     * @param defaultValue 默认值
     * @return 
     */
    public static String getString(HttpServletRequest request, String paramName, String defaultValue) {
        String s = request.getParameter(paramName);
        if(s==null || s.equals(""))
            return defaultValue;
        return s;
    }

    /**
     * 返回一个请求参数的字符串值
     * @param request HttpRequest请求
     * @param paramName 请求参数
     * @return 参数的字符串值，不存在则返回null
     */
    public static String getString(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        if(s==null) return null;
        return s;
    }

    /**
     * 返回一个请求参数的boolean值
     * @param request HttpRequest请求
     * @param paramName  请求参数
     * @return 参数的boolean值
     */
    public static boolean getBoolean(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        return Boolean.getBoolean(s);
    }

    /**
     * 返回一个请求参数的boolean，如果不存在，则返回默认值
     * @param request HttpRequest请求
     * @param paramName 请求参数
     * @param defaultValue 默认值
     * @return 返回的boolean值
     */
    public static boolean getBoolean(HttpServletRequest request, String paramName, boolean defaultValue) {
        String s = request.getParameter(paramName);
        if(s==null || s.equals(""))
            return defaultValue;
        return Boolean.parseBoolean(s);
    }

    /**
     * 返回一个请求参数的浮点值
     * @param request HttpReqeust请求
     * @param paramName 请求参数
     * @return 浮点值
     */
    public static float getFloat(HttpServletRequest request, String paramName) {
        String s = request.getParameter(paramName);
        return Float.parseFloat(s);
    }

    /**
     * 将HTML中的特殊字符转义
     * @param text
     * @return 转义后的字符
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
    
    /**
     * 请HTML特殊字符转义，包括换行和回车
     * 换行转换为$#10,回车$#13
     * @param text
     * @return  转义后的字符
     */
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
    
    /**
     * 请HTML特殊字符转义，包括换行和回车
     * 换行=<br/>,回车=""
     * @param text
     * @return  转义后的字符
     */
    public static String htmlOutEncode1(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll(" ", "&nbsp;");
        text = text.replaceAll("\"", "&quot;");
        text = text.replaceAll("\'", "&apos;");
        text = text.replaceAll("\n", "<br/>"); //换行
        return text.replaceAll("\r", ""); //回车
    }

	/**
	 * 字符串类型反转换
	 * @param text
	 * @return
	 */
    public static String htmlDecode(String text) {
        if(text==null || "".equals(text))
            return "";
        text = text.replaceAll("&lt;", "<");
        text = text.replaceAll("&gt;", ">");
        text = text.replaceAll("&nbsp;", " ");
        text = text.replaceAll("&quot;", "\"");
        text = text.replaceAll("<br/>", "\n");
        text = text.replaceAll("&apos;", "\'");
        return text; 

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
    
    /**
     * 将页面传来的Unicode字符编码转换为正常字符
     * @Methods Name ConverUnicode
     * @Create In 2008-6-25 By zhangpeng
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
    
    /**
     * 将Unicode字符串转换成正常的字符串
     * @param source 转换前的字符串
     * @return 转换后的
     */
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
     * 获得浏览器信息
     * @Methods Name getClientBrowserInfo
     * @Create In Oct 22, 2010 By likang
     * @param request
     * @return String
     */
    public static String getClientBrowserInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
    	String browser = request.getHeader("user-agent"); 
	 	if (browser != null) {
	 		return browser;
	 	}
	 	return "";
    }
    
    /**
     * 得到客户端IP
     * @Methods Name getClientIp
     * @Create In Oct 22, 2010 By likang
     * @param request
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
		// TODO Auto-generated method stub
    	String clientIP = request.getHeader("X-Forwarded-For") ;
	 	if (clientIP == null) {
	 		clientIP = request.getRemoteAddr() ;
	 	}
	 	return clientIP;
    }
    /**
     * 将字符串进行encode编码，转换为GBK
     * @param Source 源字符串
     * @return 编码后字符串
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
	 * 将encode后的字符串进行decode解码，GBK
	 * @param Source 源字符串
	 * @return 解码后的字符串
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
	
	/**
	 * 将字符串转换成GBK格式
	 * @param Source 转换前的字符串
	 * @return 转换后的字符串
	 */
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
	 * 保存到cookie
	 * @Methods Name saveCookie
	 * @Create In Aug 11, 2010 By likang
	 * @param key
	 * @param usernameString void
	 * @param response
	 */
	public static void saveCookie(String key,String value,HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		int maxAge = 365*24*3600; // 1小时(3600秒)cookie存活期
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	
	/**
	 * 删除cookie
	 * @Methods Name removeCookie
	 * @Create In Aug 31, 2010 By likang
	 * @param key
	 * @param response
	 */
	public static void removeCookie(String key,HttpServletResponse response) {
		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");               
		response.addCookie(cookie);
	}
	
	/**
	 * 取cookie
	 * @Methods Name removeCookie
	 * @Create In Aug 31, 2010 By likang
	 * @param key
	 * @param request
	 */
	public static String getCookie(String key,HttpServletRequest request) {
		String value = "";
		Cookie cookie[] = request.getCookies();
		if(cookie != null){
			for(int i=0 ; i<cookie.length ; i++){
		     Cookie c = cookie[i];
		     if(c.getName().equals(key)){
		    	 value = c.getValue();
		     }
			}
		}
		return value;
	}
	
	/**
	 * 通过url读取json对象
	 * @Methods Name getJsonObjectByUrl
	 * @Create In Jul 27, 2011 By likang
	 * @param url
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject getJsonObjectByUrl(String url) throws Exception {
		 System.out.println(url);
		 URL accessUrl = new URL(url);
         HttpURLConnection conn = (HttpURLConnection) accessUrl.openConnection();  
         conn.setDoOutput(true);  
         conn.setUseCaches(false);  
         conn.setDefaultUseCaches(false); 
         //设置超时时间为5秒
         conn.setConnectTimeout(5000);  
         conn.getOutputStream();
         conn.connect();  
     	 InputStream inputStream = conn.getInputStream();  
     	 //读回注意编码
		 java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream,"UTF-8"));  
		 String currentLine = "";  
		 String backJsonString = "";  
		 while ((currentLine = reader.readLine()) != null) {
			backJsonString+=currentLine;  
		 }
		JSONObject backJsonObejct = JSONObject.fromObject(backJsonString);
		return backJsonObejct;
	}
	
	/**
	 * 通过url读取json字符串
	 * @Methods Name getJsonStringByUrl
	 * @Create In Aug 2, 2011 By likang
	 * @param url
	 * @return
	 * @throws Exception String
	 */
	public static String getJsonStringByUrl(String url) throws Exception {
		 System.out.println("访问:"+url);
		 URL accessUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) accessUrl.openConnection();  
        conn.setDoOutput(true);  
        conn.setUseCaches(false);  
        conn.setDefaultUseCaches(false); 
        //设置超时时间为5秒
        conn.setConnectTimeout(5000);  
        conn.getOutputStream();
        conn.connect();  
    	 InputStream inputStream = conn.getInputStream();  
    	 //读回注意编码
		 java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream,"UTF-8"));  
		 String currentLine = "";  
		 String backJsonString = "";  
		 while ((currentLine = reader.readLine()) != null) {
			backJsonString+=currentLine;  
		 }
		 System.out.println("接收:" + backJsonString);
		return backJsonString;
	}
	
	/**
	 * 将
	 * a=1&name=李康&sex=1&desc=这是一个申请的网站&page=1&title=&dept=信息管理部
	 * 转换为
	 * 
	 * @Methods Name encodeParamString
	 * @Create In Aug 2, 2011 By Administrator
	 * @param paramString
	 * @return String
	 */
	public static String encodeParamString(String paramString) {
		String backString = "";
		StringBuffer sb = new StringBuffer();
		if (paramString != null) {
			String [] paramArr = paramString.split("&");
			for (int i = 0; i < paramArr.length; i++) {
				String [] keyValueArr = paramArr[i].split("=");
				if (keyValueArr.length == 2) {
					sb.append(keyValueArr[0]);
					sb.append("=");
					sb.append(encodeUTF8(keyValueArr[1]));
					sb.append("&");
				} else {
					sb.append(keyValueArr[0]);
					sb.append("=");
					sb.append("&");
				}
			}
		}
		if (sb.toString().endsWith("&")) {
			backString = sb.toString().substring(0,sb.toString().length()-1);
		}
		return backString;
	}
	
	/**
	 * 通过webserice返回json对象
	 * @Methods Name getWebserviceJsonStrByUrl
	 * @Create In Jul 29, 2011 By likang
	 * @param url	webservice地址
	 * @param methodName 调用方法名称
	 * @param paramArr	 参数数组
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject getWebserviceJsonStrByUrl(String url,String methodName, Object[] paramArr) throws Exception {
		JSONObject backJsonObejct = new JSONObject();
		System.out.println(url);
	        Service service = new Service();
	        Call call = (Call) service.createCall();
	        call.setTargetEndpointAddress(new java.net.URL(url));
	        call.setOperationName(methodName);
	        String backJsonString = (String) call.invoke(paramArr);
	        System.out.println("接收到：" + backJsonString);
			backJsonObejct = JSONObject.fromObject(backJsonString);
	    return backJsonObejct;
	}
	
	
	/**
	 * 上传文件
	 * @Methods Name uploadFileByUrl
	 * @Create In Sep 7, 2011 By likang
	 * @param accessUrl
	 * @param fileBytes
	 * @param fileName
	 * @return
	 * @throws Exception String
	 */
	public static String uploadFileByUrl(String accessUrl,byte [] fileBytes,String fileName,String inputName) throws Exception {
		long begin = System.currentTimeMillis();
		String backJsonString = "";  
		String end = "\r\n";
		 String twoHyphens = "--";
	        String boundary = "*****";
	        try {
	            URL httpUrl = new URL(accessUrl);
	            HttpURLConnection connection = (HttpURLConnection) httpUrl
	                    .openConnection();
	         // 发送POST请求必须设置如下两行
	            connection.setDoInput(true);
	            connection.setDoOutput(true);
	            connection.setUseCaches(false);
	            connection.setRequestMethod("POST");
	            connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	            connection.setRequestProperty("Charset", "UTF-8");
	            connection.setRequestProperty("Content-Type",
	                    "multipart/form-data;boundary=" + boundary);
	            connection.connect();
	            DataOutputStream dos = new DataOutputStream(connection
	                    .getOutputStream());
	            dos.writeBytes(twoHyphens + boundary + end);
	            dos.writeBytes("Content-Disposition: form-data;name=\""+inputName+"\";filename=\""+ URLEncoder.encode(fileName,"utf-8") + "\""
	                    + end);
	            dos.writeBytes(end);
				//缓冲1024
//				int length = fileBytes.length;
//				for (int j = 0; j < fileBytes.length; j=j+1024,length-=1024) {
//					//如果剩余byte长度大于1024 则写入1024
//					if ((length - 1024) >= 0) {
//						dos.write(fileBytes, j, 1024);
//					} else {
//						dos.write(fileBytes, j, length);
//					}
//				}
	            dos.write(fileBytes, 0, fileBytes.length);
	            dos.writeBytes(end);
	            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
	            dos.flush();
	            dos.close();
	            //System.out.println(System.currentTimeMillis() - begin);
			// 定义BufferedReader输入流来读取URL的响应
			 InputStream inputStream = connection.getInputStream();  
			 //读回注意编码
			 java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream,"UTF-8"));  
			 String currentLine = "";  
			 while ((currentLine = reader.readLine()) != null) {
				backJsonString+=currentLine;  
			 }	
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return backJsonString;

	}
	
	/**
	 * 剔出了<html>的标签 
	 * @Methods Name clearHtml
	 * @Create In Aug 5, 2011 By likang
	 * @param source
	 * @return String
	 */
	public static String clearHtml(String source) {
		if (source != null) {
			source = source.replaceAll("</?[^>]+>","");   //剔出了<html>的标签
			source = source.replace("&nbsp;",""); 
			return source;
		} else {
			return "";
		}
		
	}
}
