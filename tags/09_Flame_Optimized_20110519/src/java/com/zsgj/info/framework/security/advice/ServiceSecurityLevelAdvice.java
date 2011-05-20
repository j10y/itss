package com.zsgj.info.framework.security.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import com.zsgj.info.framework.exception.ServiceException;

/**
 * 服务调用安全级别拦截
 * Action不能直接通过getBean(String Name)方法调用底层Dao，
 * 如需要使用Dao，必须通过一套服务。
 * @Class Name ServiceSecurityLevelAdvice
 * @Author zhangpeng
 * @Create In Mar 6, 2008
 */
public class ServiceSecurityLevelAdvice implements MethodBeforeAdvice{

	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		
		if(arg0.getClass().getSuperclass() != null){
			String baseClassName = arg0.getClass().getSuperclass().getName();
			if(baseClassName.indexOf("Action") != -1 && arg0.getName().equals("getBean")){
				if(String.valueOf(arg1[0]).indexOf("Dao") != -1 ||
				   String.valueOf(arg1[0]).indexOf("DAo") != -1 ||
				   String.valueOf(arg1[0]).indexOf("DAO") != -1 ||
				   String.valueOf(arg1[0]).indexOf("dao") != -1){
					throw new ServiceException(Long.valueOf("10000010001").longValue()); 
				}
			}
		}else{
			if(arg0.getClass().getName().indexOf("Action") != -1 && arg0.getName().equals("getBean")){
				if(String.valueOf(arg1[0]).indexOf("Dao") != -1 ||
				   String.valueOf(arg1[0]).indexOf("DAo") != -1 ||
				   String.valueOf(arg1[0]).indexOf("DAO") != -1 ||
				   String.valueOf(arg1[0]).indexOf("dao") != -1){
					throw new ServiceException(Long.valueOf("10000010001").longValue()); 
				}
			}
		}
	}
	
}
