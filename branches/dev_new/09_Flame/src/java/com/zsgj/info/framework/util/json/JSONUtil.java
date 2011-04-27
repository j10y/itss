package com.zsgj.info.framework.util.json;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;


/**
 * 
 * @author：宋海鹏
 * @Connection:E_mail:ecsun@sohu.com/myecsun@hotmail.com QQ:82676683
 * @Copy Right:www.faceye.com
 * @System:www.faceye.com网络支持系统
 * @Create Time:2007-9-22
 * @Package com.faceye.core.util.helper.JSONUtil.java
 * @Description:JSON格式数据处理
 */
public class JSONUtil {
	
	/**
	 * JSON数据输出
	 * @Methods Name jsonPrint
	 * @Create In 2008-10-23 By sa
	 * @param response
	 * @param json void
	 */
	protected void jsonPrint(HttpServletResponse response, String json) {
		response.setCharacterEncoding("UTF-8");
		try {
			//System.out.println("JSON IS: " + json);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * JAVA对象转成JSON
	 * @Methods Name object2Json
	 * @Create In 2008-10-23 By sa
	 * @param o
	 * @return String
	 */
	protected String object2Json(Object o) {
		String json = null;
		// 将对像转化为json结构
		if (null != o) {
			Class clazz = o.getClass();
			if (ClassUtils.hasMethod(clazz, StringPool.REFLECTION_METHOD_MAP,
					null)) {
				json = JSONArray.fromObject(ReflectionUtils.invokeMethod(
						ClassUtils.getMethodIfAvailable(clazz, StringPool.REFLECTION_METHOD_MAP, null), o))
								
						.toString();
			} else if (ClassUtils.hasMethod(clazz,StringPool.REFLECTION_METHOD_JSON, null)) {
					
				json = "["+ ReflectionUtils.invokeMethod(
						ClassUtils.getMethodIfAvailable(clazz,
								StringPool.REFLECTION_METHOD_JSON,
								null), o).toString()+ "]";
						
			} else {
				json = JSONArray.fromObject(o).toString();
			}
		}
		return json;
	}
	
	/**
	 * 转化为分页所需要的json数据结构。
	 * root是一个对像数组
	 * [{...},{...}...]
	 * @param total
	 * @param root
	 * @return
	 */
   public static String pageJson(int total,String root){
	   String json="";
	   if(total==0){
		   json="{}";
	   }
	   json="{\"total\":"+total+",\"root\":"+root+"}";
	   return json;
   }
   
   /**
	 * 因我们的框架total都是long的，故重载此方法，转化为分页所需要的json数据结构。
	 * root是一个对像数组
	 * [{...},{...}...]
	 * @param total
	 * @param root
	 * @return
	 */
   public static String pageJson(long total,String root){
	   String json="";
	   if(total==0){
		   json="{}";
	   }
	   json="{\"total\":"+total+",\"root\":"+root+"}";
	   return json;
   }
   /**
    * 将单条记录的明细信息，转化为json数据
    * row的数据格式为：[{key:value,key:value...}]
    * @param row
    * @return
    */
   public static String rowJson(String row){
	   if(StringUtils.isNotEmpty(row)){
		   if(row.startsWith("[")){
			   return "{\"success\":true,\"rows\":" + row  + "}";
		   }else{
			   return "{\"success\":true,\"rows\":[" + row  + "]}";
		   }
	   }else{
		   return null;
	   }
	   
   }
   
}
