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
 * Bean�����࣬��HttpServletRequest�ֶλ�ȡ�������ֵ������ָ������bean�ĸ������ԡ�
 * ����Ƕ�׵�Object���͵����ԣ��ֶγ�ʼ���������󣬲�����¼�����id���Ը���ֵ���ײ�ʵ��ʹ��spring��bean��װ����
 * @Class Name BeanUtil
 * @Author xiaofeng
 * @Create In 2008-3-25
 */
public class BeanUtil {
	
	/**
	 * ������ת����Map��������������ƽ���Ϊkey������ֵ��Ϊvalue��
	 * ���ܲ��ֽӿ���map��ʽ���������ͷ�µõ����Ƕ��󣬿����ø÷�����������ת����
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
	 * ���2�ڼ书�ܣ�������ת����Map��ָ������ǰ׺��"$"�Ӷ�����������ƽ���Ϊkey������ֵ��Ϊvalue��
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
	 * ��Map�е�ֵ�Զ������ʽ���أ���������������String�����Զ�ת����
	 * ͬʱ���ڹ�������ר��Long��string���Ͳ��������Զ��������ת������Ӧ������
	 * ����������ʽ���أ��Լ���ǰ�˴�����
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
                        	
                        	// paramValue = paramValue.trim();//�������ݼ�ȥ�ո���
                        	 
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
                             else { //���ڹ�������ֻͨ��ҳ�����ֵ���ù��������id����
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
												//begin���ǰ����Ϊĳ��ԭ��û�д���ID�����Ǵ�����"����Ա/admin/��������"�������ı�
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
	 * ��request��Ĳ����Զ������ʽ���أ�ʵ������ActionForm����
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
	                        if(paramValue!=null && !paramValue.equals("")) { //�������������޴����ԣ����޸�ʵ�壬�Է�ֹ�ƻ�ԭ����ֵ
	                        	 paramValue = paramValue.trim();//�������ݼ�ȥ�ո���
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
	                             else { //���ڹ�������ֻͨ��ҳ�����ֵ���ù��������id����
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
	                        if(paramValue!=null && !paramValue.equals("")) { //�������������޴����ԣ����޸�ʵ�壬�Է�ֹ�ƻ�ԭ����ֵ
	                        	 paramValue = paramValue.trim();//�������ݼ�ȥ�ո���
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
	                             else { //���ڹ�������ֻͨ��ҳ�����ֵ���ù��������id����
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
	  * ��request��Ĳ����Զ������ʽ���أ�ʵ������ActionForm���ܣ����ǵײ��������ı��봦��
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
	                        if(paramValue!=null && !paramValue.equals("")) { //�������������޴����ԣ����޸�ʵ�壬�Է�ֹ�ƻ�ԭ����ֵ
	                        	 paramValue = paramValue.trim();//�������ݼ�ȥ�ո���
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
	                             else { //���ڹ�������ֻͨ��ҳ�����ֵ���ù��������id����
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
