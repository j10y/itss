/**
 * @Probject Name: 10_InfoFramework_1
 * @Path: com.digitalchina.info.framework.web.adapter.struts2BaseAroundInterceptor.java
 * @Create By 张鹏
 * @Create In 2009-7-27 下午03:38:58
 * TODO
 */
package com.zsgj.info.framework.web.adapter.struts2;

import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.service.Service;

/**
 * @Class Name BaseAroundInterceptor
 * @Author 张鹏
 * @Create In 2009-7-27
 */
public abstract class BaseAroundInterceptor implements Interceptor {

	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
	 */
	public void init() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		String result = null;
		
		before(invocation);
		result = invocation.invoke();
		after(invocation,result);
		return null;
	}
	
	/**
	 * 返回spring管理的服务service
	 * 
	 * @Methods Name getBean
	 * @Create In 2008-3-3 By peixf
	 * @param name
	 * @return Object
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}

	/**
	 * 获取基础服务
	 * 
	 * @Methods Name getBaseService
	 * @Create In 2008-4-11 By peixf
	 * @return Service
	 */
	protected Service getService() {
		return (Service) getBean("baseService");
	}

	/**
	 * 获取资源文件信息
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            资源文件Key
	 * @param defaultValue
	 *            默认信息
	 * @return String
	 */
	protected String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, new Object[0], ContextHolder
					.getInstance().getLocal());

			return (message != null && !message.equals("") ? message
					: defaultValue);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	protected abstract void after(ActionInvocation dispatcher,String result) throws Exception;
	
	protected abstract void before(ActionInvocation invocation) throws Exception;

}
