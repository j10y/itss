package com.xpsoft.framework.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.xpsoft.framework.dao.BaseObject;


/**
 * Bean工具类，从HttpServletRequest字段获取请求参数值，赋给指定类型bean的各个属性。
 * 包括嵌套的Object类型的属性，字段初始化级联对象，并给记录对象的id属性赋初值。底层实现使用spring的bean包装器。
 * @Class Name BeanUtil
 * @Author likang
 * @Create In Jul 22, 2010
 */
public class BeanUtil {
	
	/**
	 * 将对象转化成Map，对象的属性名称将作为key，属性值作为value。
	 * 因框架部分接口是map形式，但如果手头新得到的是对象，可利用该方法进行类型转换。
	 * @Methods Name object2Map
	 * @Create In Jul 22, 2010 By likang
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
	 * 将Map中的值以对象的形式返回，基本数据类型与String可以自动转换，
	 * 同时对于关联对象，传递Long或string类型参数即可自动完成类型转换，对应属性以
	 * 关联对象形式返回，以减少前端代码量
	 * @Methods Name getObject
	 * @Create In Jul 22, 2010 By likang
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
                   
                    try {
                        //获取属性的名称，取setXyz后面的Xyz部分，因为X是大写，所以要转换成小写
                        String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
                       
                        if(requestParams.get(property)!=null && !requestParams.get(property).equals("")
                        		&& !requestParams.get(property).toString().equals("null")) { 
                        	 Object paramValue = requestParams.get(property);
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
                            	 //如果参数是日期类型，则直接转换，否则先转换成字符串，然后再通过字符串转换成日期
                            	 if(paramValue instanceof Date){
                            		 dateValue = (Date)paramValue;
                            	 }else{
                            		 dateValue = DateUtil.convertStringToDate(paramValue.toString());
                            	 }
                            	 bwComp.setPropertyValue(property, dateValue);
                             }
                             else { 
                            	 //对于关联对象，只通过页面的数值设置关联对象的id属性
 	                        	Class clazz = Class.forName(type);
 	                        	Object object = clazz.newInstance();
 	                        	if(object instanceof BaseObject){
	 	                        	if(paramValue instanceof BaseObject){
	 	                        		try {
	 	                        			bwComp.setPropertyValue(property, paramValue );
										} catch (RuntimeException e) {
											bwComp.setPropertyValue(property, null);
											e.printStackTrace();
										}
	 	                        	}else if(paramValue instanceof java.lang.String){
	 	                        		bwComp.setPropertyValue(property, object );
	 	                        		try {
											bwComp.setPropertyValue(property+".id", Long.valueOf(paramValue.toString()));
										} catch (RuntimeException e) {
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
	 * @Create In Jul 22, 2010 By likang
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
	                    try {
	                        String property = Character.toLowerCase(name.charAt(3)) + name.substring(4);
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
	 	                        	if(object instanceof BaseObject){
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
