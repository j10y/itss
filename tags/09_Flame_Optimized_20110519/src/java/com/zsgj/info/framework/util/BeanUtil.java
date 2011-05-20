package com.zsgj.info.framework.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.jspsmart.upload.Request;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.service.Service;

/**
 * Bean工具类，从HttpServletRequest字段获取请求参数值，赋给指定类型bean的各个属性。
 * 包括嵌套的Object类型的属性，字段初始化级联对象，并给记录对象的id属性赋初值。底层实现使用spring的bean包装器。
 * @Class Name BeanUtil
 * @Author xiaofeng
 * @Create In 2008-3-25
 */
public class BeanUtil {
	
	/**
	 * 将对象转化成Map，对象的属性名称将作为key，属性值作为value。
	 * 因框架部分接口是map形式，但如果手头新得到的是对象，可利用该方法进行类型转换。
	 * @Methods Name object2Map
	 * @Create In 2008-10-20 By sa
	 * @param obj
	 * @return Map
	 */
	public static Map object2Map(Object obj) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		try {
		   Class c = obj.getClass();
		   Method m[] = c.getDeclaredMethods();
		   for (int i = 0; i < m.length; i++) {
		   if (m[i].getName().indexOf("get")==0) {
			  String propertyName = Character.toLowerCase(m[i].getName().charAt(3)) + m[i].getName().substring(4);
		      hashMap.put(propertyName, m[i].invoke(obj, new Object[0]));
		   }
		  }
		} catch (Throwable e) {
		   System.err.println(e);
		}
		return hashMap;
	}
	
	/**
	 * 框架2期间功能，将对象转化成Map，指定表名前缀加"$"加对象的属性名称将作为key，属性值作为value。
	 * @Methods Name object2PanelMap
	 * @Create In 2008-12-3 By sa
	 * @param obj
	 * @param keyPrefix
	 * @return Map
	 */
	public static Map object2Map(Object obj, String keyPrefix) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		try {
		   Class c = obj.getClass();
		   Method m[] = c.getDeclaredMethods();
		   for (int i = 0; i < m.length; i++) {
		   if (m[i].getName().indexOf("get")==0) {
			  String propertyName = Character.toLowerCase(m[i].getName().charAt(3)) + m[i].getName().substring(4);
		      hashMap.put(keyPrefix+"$"+propertyName, m[i].invoke(obj, new Object[0]));
		   }
		  }
		} catch (Throwable e) {
		   System.err.println(e);
		}
		return hashMap;
	}
	
	
	public static List<Map<String,Object>> listObject2Map(List<Object> objects, String keyPrefix) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Object obj : objects){
			Map objectMap = object2Map(obj, keyPrefix);
			list.add(objectMap);
		}
		return list;
	}
	
	/**
	 * 将Map中的值以对象的形式返回，基本数据类型与String可以自动转换，
	 * 同时对于关联对象，专递Long或string类型参数即可自动完成类型转换，对应属性以
	 * 关联对象形式返回，以减少前端代码量
	 * @Methods Name getObject
	 * @Create In 2008-10-6 By sa
	 * @param requestParams
	 * @param c
	 * @return Object
	 */
	public static Object getObject(Map requestParams, Class c) {
        Object bean;
        BeanWrapper bwComp = null;
        try {
            bean = c.newInstance();
            bwComp = new BeanWrapperImpl(bean);
        }
        catch (Exception e) {
            return new Object();
        }
        Method[] ms = c.getMethods();
        for(int i=0; i<ms.length; i++) {
            String name = ms[i].getName();
            if(name.startsWith("set")) {
                Class[] cc = ms[i].getParameterTypes();
                if(cc.length==1) {
                    String type = cc[0].getName(); // parameter type
                    //System.out.println("type: "+type);
  
                   
                    try {
                        // get property name:
                        String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
                        // get parameter value:
                       
                        if(requestParams.get(property)!=null && !requestParams.get(property).equals("")
                        		&& !requestParams.get(property).toString().equals("null")) { 
                        	
                        	 Object paramValue = requestParams.get(property);
                        	
                        	// paramValue = paramValue.trim();//给表单数据加去空格功能
                        	 
                        	 if(type.equals("java.lang.String")) {
                        		String strValue = paramValue.toString().trim();
     	                    	bwComp.setPropertyValue(property, strValue);
                             }
                             else if(type.equals("int") || type.equals("java.lang.Integer")) {
                            	 Integer intValue = Integer.valueOf(paramValue.toString());
                            	 bwComp.setPropertyValue(property, intValue);
                             } 
                             else if(type.equals("long") || type.equals("java.lang.Long")) {
                            	 Long longValue = Long.valueOf(paramValue.toString());
                            	 bwComp.setPropertyValue(property, longValue);
                             }
                             else if(type.equals("boolean") || type.equals("java.lang.Boolean")) {
                            	 Boolean boolValue = Boolean.valueOf(paramValue.toString());
                            	 bwComp.setPropertyValue(property, boolValue);
                             }
                             else if(type.equals("float") || type.equals("java.lang.Float")) {
                            	 Float floatValue = Float.valueOf(paramValue.toString());
                            	 bwComp.setPropertyValue(property, floatValue);
                             }
                        	 
                             else if(type.equals("double") || type.equals("java.lang.Double")) {
                            	 Double doubleValue = Double.valueOf(paramValue.toString());
                            	 bwComp.setPropertyValue(property, doubleValue);
                             }
                             else if(type.equals("java.util.Date")) {
                            	 Date dateValue=null;
                            	 if(paramValue instanceof Date){
//                            		 if(requestParams.get("id")==null){
//                            			 if(property.equalsIgnoreCase("createDate")){
//                            				 paramValue = DateUtil.getCurrentDateTime();
//                            			 }
//                            		 }else{
//                            			 if(property.equalsIgnoreCase("modifyDate")){
//                            				 paramValue = DateUtil.getCurrentDateTime();
//                            			 }
//                            		 }
                            		 dateValue = (Date)paramValue;
                            	 }else{
//                            		 if(requestParams.get("id")==null){
//                            			 if(property.equalsIgnoreCase("createDate")){
//                            				 paramValue = DateUtil.getCurrentDateTime();
//                            			 }
//                            		 }else{
//                            			 if(property.equalsIgnoreCase("modifyDate")){
//                            				 paramValue = DateUtil.getCurrentDateTime();
//                            			 }
//                            		 }
                            	     dateValue = DateUtil.convertStringToDate(paramValue.toString());
                            	 }
                            	 bwComp.setPropertyValue(property, dateValue);
                             }
                             else { //对于关联对象，只通过页面的数值设置关联对象的id属性
 	                        	Class clazz = Class.forName(type);
 	                        	Object object = clazz.newInstance();
 	                        	if(object instanceof com.zsgj.info.framework.dao.BaseObject){
	 	                        	if(paramValue instanceof com.zsgj.info.framework.dao.BaseObject){
	 	                        		try {
	 	                        			bwComp.setPropertyValue(property, paramValue );
											//bwComp.setPropertyValue(property+".id", id);
										} catch (RuntimeException e) {
											bwComp.setPropertyValue(property, null);
											e.printStackTrace();
										}
	 	                        	}else if(paramValue instanceof java.lang.String){
	 	                        		bwComp.setPropertyValue(property, object );
	 	                        		try {
											bwComp.setPropertyValue(property+".id", Long.valueOf(paramValue.toString()));
										} catch (RuntimeException e) {
											Service service = (Service) ContextHolder.getBean("baseService");
											BaseObject baseObject = (BaseObject) object;
											String uniqueName = baseObject.getUniquePropName();
											Object result = service.findUnique(clazz, uniqueName, paramValue.toString());
											if(result==null){
												//begin如果前端因为某种原因没有传递ID，而是传递了"管理员/admin/神州数码"这样的文本
												String className = clazz.getName();
												if(className.equalsIgnoreCase("com.zsgj.info.framework.security.entity.UserInfo")){
													String value = paramValue.toString();
													int firstBias = value.indexOf("/");
													//System.out.println("firstBias:"+ firstBias);
													int secondBias = StringUtils.indexOf(value, "/", firstBias+1);
													//System.out.println("secondBias:"+ secondBias);
													String middle = value.substring(firstBias+1, secondBias);
													//System.out.println("middle:"+ middle);
													result = service.findUnique(clazz, uniqueName, middle);
												}
												//end
											}
											bwComp.setPropertyValue(property, result);
											e.printStackTrace();
										}
	 	                        	}else if(paramValue instanceof java.lang.Integer){
	 	                        		bwComp.setPropertyValue(property, object );
	 	                        		try {
											bwComp.setPropertyValue(property+".id", Long.valueOf(paramValue.toString()));
										} catch (RuntimeException e) {
											bwComp.setPropertyValue(property, null);
											e.printStackTrace();
										}
	 	                        	}else if(paramValue instanceof java.lang.Long){
	 	                        		bwComp.setPropertyValue(property, object );
	 	                        		try {
											bwComp.setPropertyValue(property+".id", paramValue);
										} catch (RuntimeException e) {
											bwComp.setPropertyValue(property, null);
											e.printStackTrace();
										}
	 	                        	}
 	                        	}
                             }
                        	 /* 
                        	 else if(type.equals("com.digitalchina.info.framework.dao.BaseObject")) {
                        	 	BaseObject strValue = (BaseObject) paramValue;
                        	 	bwComp.setPropertyValue(property, strValue);
                         	}*/

                        }
                    }
                    catch(Exception e) {
                    }
                }
            }
        }
        return bwComp.getWrappedInstance();
    }
	
	public static Object getJsonObject(Map requestParams, Class c) {
        Object bean;
        BeanWrapper bwComp = null;
        try {
            bean = c.newInstance();
            bwComp = new BeanWrapperImpl(bean);
        }
        catch (Exception e) {
            return new Object();
        }
        Method[] ms = c.getMethods();
        for(int i=0; i<ms.length; i++) {
            String name = ms[i].getName();
            if(name.startsWith("set")) {
                Class[] cc = ms[i].getParameterTypes();
                if(cc.length==1) {
                    String type = cc[0].getName(); // parameter type
                   
                    try {
                        // get property name:
                        String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
                        // get parameter value:
                       
                        if(requestParams.get(property)!=null && !requestParams.get(property).equals("")
                        		&& !requestParams.get(property).toString().equals("null")) { 
                        	
                        	 Object paramValue = requestParams.get(property);
                        	

                        }
                    }
                    catch(Exception e) {
                    }
                }
            }
        }
        return bwComp.getWrappedInstance();
    }
	
	
	/**
	 * 将request里的参数以对象的形式返回，实现类似ActionForm功能
	 * @Methods Name getObject
	 * @Create In 2008-10-6 By sa
	 * @param request
	 * @param c
	 * @return Object
	 */
	 public static Object getObject(HttpServletRequest request, Class c) {
	        Object bean;
	        BeanWrapper bwComp = null;
	        try {
	            bean = c.newInstance();
	            bwComp = new BeanWrapperImpl(bean);
	        }
	        catch (Exception e) {
	            return new Object();
	        }
	        Method[] ms = c.getMethods();
	        for(int i=0; i<ms.length; i++) {
	            String name = ms[i].getName();
	            if(name.startsWith("set")) {
	                Class[] cc = ms[i].getParameterTypes();
	                if(cc.length==1) {
	                    String type = cc[0].getName(); // parameter type
	                    //System.out.println("type: "+type);
	  
	                   
	                    try {
	                        // get property name:
	                        String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
	                        // get parameter value:
	                        String paramValue = HttpUtil.getString(request, property);
	                        if(paramValue!=null && !paramValue.equals("")) { //如果请求参数中无此属性，则不修改实体，以防止破坏原属性值
	                        	 paramValue = paramValue.trim();//给表单数据加去空格功能
	                        	 if(type.equals("java.lang.String")) {
	     	                    	bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("int") || type.equals("java.lang.Integer")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("long") || type.equals("java.lang.Long")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("boolean") || type.equals("java.lang.Boolean")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("float") || type.equals("java.lang.Float")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("double") || type.equals("java.lang.Double")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("java.util.Date")) {
	                            	 bwComp.setPropertyValue(property, DateUtil.convertStringToDate(paramValue));
	                             }
	                             else { //对于关联对象，只通过页面的数值设置关联对象的id属性
	 	                        	Class clazz = Class.forName(type);
	 	                        	Object object = clazz.newInstance();
	 	                        	if(object instanceof com.zsgj.info.framework.dao.BaseObject){
	 	                        		bwComp.setPropertyValue(property, object );
	 	                        		try {
											bwComp.setPropertyValue(property+".id", paramValue);
										} catch (RuntimeException e) {
											bwComp.setPropertyValue(property, null);
										}
	 	                        	}
	                             }

	                        }
	                    }
	                    catch(Exception e) {
	                    }
	                }
	            }
	        }
	        return bwComp.getWrappedInstance();
	    }
	 
	 
	 public static Object getSmartObject(Request request, Class c) {
	        Object bean;
	        BeanWrapper bwComp = null;
	        try {
	            bean = c.newInstance();
	            bwComp = new BeanWrapperImpl(bean);
	        }
	        catch (Exception e) {
	            return new Object();
	        }
	        Method[] ms = c.getMethods();
	        for(int i=0; i<ms.length; i++) {
	            String name = ms[i].getName();
	            if(name.startsWith("set")) {
	                Class[] cc = ms[i].getParameterTypes();
	                if(cc.length==1) {
	                    String type = cc[0].getName(); // parameter type
	                    //System.out.println("type: "+type);
	  
	                   
	                    try {
	                        // get property name:
	                        String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
	                        // get parameter value:
	                        String paramValue = request.getParameter(property);
	                        if(paramValue!=null && !paramValue.equals("")) { //如果请求参数中无此属性，则不修改实体，以防止破坏原属性值
	                        	 paramValue = paramValue.trim();//给表单数据加去空格功能
	                        	 if(type.equals("java.lang.String")) {
	     	                    	bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("int") || type.equals("java.lang.Integer")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("long") || type.equals("java.lang.Long")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("boolean") || type.equals("java.lang.Boolean")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("float") || type.equals("java.lang.Float")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("double") || type.equals("java.lang.Double")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("java.util.Date")) {
	                            	 bwComp.setPropertyValue(property, DateUtil.convertStringToDate(paramValue));
	                             }
	                             else { //对于关联对象，只通过页面的数值设置关联对象的id属性
	 	                        	Class clazz = Class.forName(type);
	 	                        	Object object = clazz.newInstance();
	 	                        	if(object instanceof com.zsgj.info.framework.dao.BaseObject){
	 	                        		bwComp.setPropertyValue(property, object );
	 	                        		try {
											bwComp.setPropertyValue(property+".id", paramValue);
										} catch (RuntimeException e) {
											bwComp.setPropertyValue(property, null);
										}
	 	                        	}
	                             }

	                        }
	                    }
	                    catch(Exception e) {
	                    }
	                }
	            }
	        }
	        return bwComp.getWrappedInstance();
	    }
	 
	 /**
	  * 将request里的参数以对象的形式返回，实现类似ActionForm功能，但是底层做了中文编码处理
	  * @Methods Name getEncodeObject
	  * @Create In 2008-10-6 By sa
	  * @param request
	  * @param c
	  * @return Object
	  */
	 public static Object getEncodeObject(HttpServletRequest request, Class c) {
	        Object bean;
	        BeanWrapper bwComp = null;
	        try {
	            bean = c.newInstance();
	            bwComp = new BeanWrapperImpl(bean);
	        }
	        catch (Exception e) {
	            return new Object();
	        }
	        Method[] ms = c.getMethods();
	        for(int i=0; i<ms.length; i++) {
	            String name = ms[i].getName();
	            if(name.startsWith("set")) {
	                Class[] cc = ms[i].getParameterTypes();
	                if(cc.length==1) {
	                    String type = cc[0].getName(); // parameter type
	                    //System.out.println("type: "+type);
	  
	                   
	                    try {
	                        // get property name:
	                        String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
	                        // get parameter value:
	                        String paramValue = HttpUtil.ConverUnicode(HttpUtil.getString(request, property));
	                        if(paramValue!=null && !paramValue.equals("")) { //如果请求参数中无此属性，则不修改实体，以防止破坏原属性值
	                        	 paramValue = paramValue.trim();//给表单数据加去空格功能
	                        	 if(type.equals("java.lang.String")) {
	     	                    	bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("int") || type.equals("java.lang.Integer")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("long") || type.equals("java.lang.Long")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("boolean") || type.equals("java.lang.Boolean")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("float") || type.equals("java.lang.Float")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("double") || type.equals("java.lang.Double")) {
	                            	 bwComp.setPropertyValue(property, paramValue);
	                             }
	                             else if(type.equals("java.util.Date")) {
	                            	 bwComp.setPropertyValue(property, DateUtil.convertStringToDate(paramValue));
	                             }
	                             else { //对于关联对象，只通过页面的数值设置关联对象的id属性
	 	                        	Class clazz = Class.forName(type);
	 	                        	Object object = clazz.newInstance();
	 	                        	if(object instanceof com.zsgj.info.framework.dao.BaseObject){
	 	                        		bwComp.setPropertyValue(property, object );
	 	                        		try {
											bwComp.setPropertyValue(property+".id", paramValue);
										} catch (RuntimeException e) {
											bwComp.setPropertyValue(property, null);
										}
	 	                        	}
	                             }

	                        }
	                    }
	                    catch(Exception e) {
	                    }
	                }
	            }
	        }
	        return bwComp.getWrappedInstance();
	    }
	 
}
