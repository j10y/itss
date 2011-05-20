package com.zsgj.info.framework.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
	/**
	 * 将Map中的值以Jason对象的形式返回，基本数据类型与String可以自动转换，
	 * 同时对于关联对象，专递Long或string类型参数即可自动完成类型转换，对应属性以
	 * 关联对象形式返回，以减少前端代码量
	 * @Methods Name getJsonObject
	 * @Create In 2011-5-19 By Jack
	 * @param requestParams
	 * @param c
	 * @return Object
	 */
	public static Object getJsonObject(Map requestParams, Class c) {
		Object tagObj = getObject(requestParams,c);
		
        
        return bean2Json(tagObj);
    }
	/**
	 * Jason对象到Bean对象的转换
	 * @Methods Name json2Bean
	 * @Create In 2011-5-19 By Jack
	 * @param json
	 * @param beanClass
	 * @param collectionComponentTypes
	 * @return
	 * @throws Exception Object
	 */
	public static Object json2Bean(JSONObject json, Class beanClass, Map<String, Class> collectionComponentTypes) throws Exception{
        Object bean = null;
        bean = beanClass.newInstance();
                
        Method[] setters = getSetters(beanClass);
        for(int i = 0; i < setters.length; i++){
            Method setter = setters[i];
            String property = getProperty(setter.getName());
            if(property == null || json.get(property) == null) continue;
            Class paramType = setter.getParameterTypes()[0];
            if(isPrimitive(paramType)){
                        setter.invoke(bean, json.get(property));
            } else if(isPrimitiveArray(paramType)){
                JSONArray jarr = json.getJSONArray(property);
                Object arr = Array.newInstance(getType(paramType), jarr.length());
                for(int j = 0; j < jarr.length(); j++)
                    Array.set(arr, j, jarr.get(j));
                setter.invoke(bean, arr);
            }else if(paramType == java.util.Date.class){
                setter.invoke(bean, new java.util.Date((long)json.getDouble(property)));
            }else if(paramType == java.sql.Date.class){
                setter.invoke(bean, new java.sql.Date((long)json.getDouble(property)));
            }else if(paramType == java.sql.Time.class){
                setter.invoke(bean, new java.sql.Time((long)json.getDouble(property)));
            }else if(paramType == java.sql.Timestamp.class){
                setter.invoke(bean, new java.sql.Timestamp((long)json.getDouble(property)));
            }else if(paramType == java.util.Calendar.class){
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTimeInMillis((long)json.getDouble(property));
                setter.invoke(bean, c);
            }else if(paramType == java.util.Set.class){
                Class componentType = null;
                if(collectionComponentTypes != null)
                    componentType = collectionComponentTypes.get(property);
                java.util.Set s = new java.util.HashSet();
                initCollection(s, json.getJSONArray(property), componentType, collectionComponentTypes);
                setter.invoke(bean, s);
            }else if(paramType == java.util.List.class ||
                    paramType == java.util.Collection.class){
                Class componentType = null;
                if(collectionComponentTypes != null)
                    componentType = collectionComponentTypes.get(property);
                java.util.List l = new java.util.ArrayList();
                initCollection(l, json.getJSONArray(property), componentType, collectionComponentTypes);
                setter.invoke(bean, l);
            }else if(paramType.getName().startsWith("[")){
                Object[] arr = toObjectArray(paramType, json.getJSONArray(property), collectionComponentTypes);
                setter.invoke(bean, arr);
            }else{
                setter.invoke(bean, json2Bean(json.getJSONObject(property), paramType, collectionComponentTypes));
            }
        }
        
        return bean;
    }
    
    private static Class<?> getType(Class paramType) {
        Class type = null;
        for(int i = 0; i < arrayClasses.length; i++){
            if(paramType == arrayClasses[i]){
                type = classes[i];
            }
        }
        return type;
    }

    private static Object[] toObjectArray(Class paramType, JSONArray jarr, Map<String, Class> collectionComponentTypes) throws Exception{
        Object[] objs = new Object[jarr.length()];
        Class beanClass = Class.forName(paramType.getName().substring(2));
        for(int i = 0; i < objs.length; i++){
            objs[i] = json2Bean(jarr.getJSONObject(i), beanClass, collectionComponentTypes);
        }
        return objs;
    }

    private static void initCollection(Collection c, JSONArray jarr, Class componentType, Map<String, Class> collectionComponentTypes) throws Exception{
        //component of collection is primitive
        if(componentType == null || isPrimitive(componentType)){
            for(int i = 0; i < jarr.length(); i++ )
                c.add(jarr.get(i));
        }else if(componentType == java.util.Date.class){
            for(int i = 0; i < jarr.length(); i++)
                c.add(new java.util.Date((long)jarr.getDouble(i)));
        }else if(componentType == java.sql.Date.class){
            for(int i = 0; i < jarr.length(); i++)
                c.add(new java.sql.Date((long)jarr.getDouble(i)));
        }else if(componentType == java.sql.Time.class){
            for(int i = 0; i < jarr.length(); i++)
                c.add(new java.sql.Time((long)jarr.getDouble(i)));
        }else if(componentType == java.sql.Timestamp.class){
            for(int i = 0; i < jarr.length(); i++)
                c.add(new java.sql.Timestamp((long)jarr.getDouble(i)));
        }else if(componentType == java.util.Calendar.class){
            for(int i = 0; i < jarr.length(); i++){
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTimeInMillis((long)jarr.getDouble(i));
                c.add(cal);
            }
        }else if(componentType != null){
            for(int i = 0; i < jarr.length(); i++){
                c.add(json2Bean(jarr.getJSONObject(i), componentType, collectionComponentTypes));
            }
        }
    }
    
    /**
     * Bean到Jason的转换
     * @Methods Name bean2Json
     * @Create In 2011-5-19 By Jack
     * @param bean
     * @return JSONObject
     */
    public static JSONObject bean2Json(Object bean){
        Class clazz = bean.getClass();
        JSONObject json = new JSONObject();
        Method[] getters = getGetters(clazz);
        int len =  getters.length;
        for(int i = 0; i < len; i++){
            Method m = getters[i];
            String property = getProperty(m.getName());
            Class retType = m.getReturnType();
            if(property == null || property.equals("class") )continue;
            try {
                Object value = m.invoke(bean, null);
                if(isPrimitive(retType))
                    json.put(property, value);
                else if(isPrimitiveArray(retType)){
                    JSONArray jarr = priArray(value);
                    json.put(property, jarr);
                }else if(isDate(value)){
                    json.put(property, getTime(value));
                }else if(value instanceof Collection){
                    json.put(property, collectionToJson(value));
                }else if(retType.getName().startsWith("[")){
                    json.put(property, arrayToJarr(value));
                }else if(value instanceof Map){
                    json.put(property, new JSONObject((Map)value));
                }
                else{
                    json.put(property, bean2Json(value));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error to get value");
            }
        }
        
        return json;
    }
    
    public static JSONArray arrayToJarr(Object value) {
        // TODO Auto-generated method stub
        Object[] arr = (Object[])value;
        Collection c = new ArrayList();
        for(Object o : arr)
            c.add(o);
        return collectionToJson(c);
    }

    public static JSONArray collectionToJson(Object value) {
        // TODO Auto-generated method stub
        JSONArray jarr = new JSONArray();
        Collection c = (Collection)value;
        for(Object o : c){
            Class type = o.getClass();
            if(isPrimitive(type)) jarr.put(o);
            else if(isPrimitiveArray(type)) jarr.put(priArray(o));
            else if(isDate(o)) jarr.put(getTime(o));
            else if(o instanceof Collection) jarr.put(collectionToJson(o));
            else if(type.getName().startsWith("[")) jarr.put(arrayToJarr(o));
            else if(o instanceof Map) jarr.put(new JSONObject((Map)o));
            else jarr.put(bean2Json(o));
        }
        
        return jarr;
    }
    
	private static Class[] classes = {
        byte.class, short.class, int.class,long.class,
        float.class, double.class, boolean.class, char.class,
        Byte.class, Short.class, Integer.class, Long.class,
        Float.class, Double.class, Boolean.class, Character.class,
        String.class
    };
    
    private static Class[] arrayClasses = {
        byte[].class, short[].class, int[].class,long[].class,
        float[].class, double[].class, boolean[].class, char[].class,
        Byte[].class, Short[].class, Integer[].class, Long[].class,
        Float[].class, Double[].class, Boolean[].class, Character[].class,
        String[].class
    };
    private static Object getTime(Object value) {
        // TODO Auto-generated method stub
        if(value instanceof Date)
            return ((Date)value).getTime() ;
        return ((Calendar)value).getTimeInMillis();
    }

    private static boolean isDate(Object value){
        return value instanceof Date ||
         value instanceof Calendar;
    }
    
    public static JSONArray priArray(Object value){
        JSONArray jarr = new JSONArray();
        int arLen = Array.getLength(value);
        for(int j = 0; j < arLen; j++){
            jarr.put(Array.get(value, j));
        }
        
        return jarr;
    }
    
    private static String getProperty(String name) {
        // TODO Auto-generated method stub
        if(name.startsWith("get") || name.startsWith("set")) name = name.substring(3);
        else name = name.substring(2);
        if(name.length() > 0){
            StringBuilder sb = new StringBuilder(name.length());
            char first = name.charAt(0);
            if(name.length() > 1){
                char second = name.charAt(1);
                if(second >='A' && second <='Z');
                else first += 32;
            }
            sb.append(first).append(name.substring(1));
            return sb.toString();
        }
        
        return null;
    }

    private static Method[] getGetters(Class clazz){
        Method[] methods = clazz.getMethods();
        ArrayList<Method> getters = new ArrayList<Method>();
        for(int i = 0; i < methods.length; i++){
            if(methods[i].getName().startsWith("get") ||
                methods[i].getName().startsWith("is") ){
                Class[] paramTypes = methods[i].getParameterTypes();
                if(paramTypes == null || paramTypes.length == 0 )
                    getters.add(methods[i]);
            }
        }
        
        Method[] retMethods = new Method[getters.size()];
        int i = 0;
        for(Method m : getters){
            retMethods[i++] = m;
        }
        
        return retMethods;
    }
    
    private static Method[] getSetters(Class clazz){
        Method[] methods = clazz.getMethods();
        ArrayList<Method> setters = new ArrayList<Method>();
        for(int i = 0; i < methods.length; i++){
            if(methods[i].getName().startsWith("set")){
                if(methods[i].getParameterTypes() != null &&
                        methods[i].getParameterTypes().length == 1) setters.add(methods[i]);
            }
        }
        
        Method[] retMethods = new Method[setters.size()];
        int i = 0;
        for(Method m : setters){
            retMethods[i++] = m;
        }
        
        return retMethods;
    }
    
    private static boolean isPrimitive(Class clazz){
        for(int i = 0; i < classes.length; i++){
            if(classes[i] == clazz) return true;
        }
        
        return false;
    }
    
    private static boolean isPrimitiveArray(Class clazz){
        for(int i = 0; i < arrayClasses.length; i++){
            if(arrayClasses[i] == clazz) return true;
        }
        
        return false;
    }
    
    @SuppressWarnings("unused")
	private static boolean isSpecificClass(Class clazz){
        return isPrimitive(clazz) || isPrimitiveArray(clazz);
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
