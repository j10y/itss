package com.zsgj.info.framework.util.json;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;


/**
 * 
 * @author���κ���
 * @Connection:E_mail:ecsun@sohu.com/myecsun@hotmail.com QQ:82676683
 * @Copy Right:www.faceye.com
 * @System:www.faceye.com����֧��ϵͳ
 * @Create Time:2007-9-22
 * @Package com.faceye.core.util.helper.JSONUtil.java
 * @Description:JSON��ʽ���ݴ���
 */
public class JSONUtil {
	
	/**
	 * JSON�������
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
	 * JAVA����ת��JSON
	 * @Methods Name object2Json
	 * @Create In 2008-10-23 By sa
	 * @param o
	 * @return String
	 */
	protected String object2Json(Object o) {
		String json = null;
		// ������ת��Ϊjson�ṹ
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
	 * ת��Ϊ��ҳ����Ҫ��json���ݽṹ��
	 * root��һ����������
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
	 * �����ǵĿ��total����long�ģ������ش˷�����ת��Ϊ��ҳ����Ҫ��json���ݽṹ��
	 * root��һ����������
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
    * ��������¼����ϸ��Ϣ��ת��Ϊjson����
    * row�����ݸ�ʽΪ��[{key:value,key:value...}]
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
