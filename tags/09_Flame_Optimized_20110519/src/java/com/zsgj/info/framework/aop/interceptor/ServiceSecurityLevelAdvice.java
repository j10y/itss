package com.zsgj.info.framework.aop.interceptor;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class ServiceSecurityLevelAdvice implements MethodBeforeAdvice{

	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		String baseClassName = arg0.getClass().getSuperclass().getName();
		
		if(baseClassName.indexOf("Action") != -1){
			
		}
	}
	
}
