package com.xp.commonpart.util;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



public class SpringBeanProxyInitServlet extends HttpServlet {
private static final long serialVersionUID = -3754547957057400569L;
	
	private static ApplicationContext context = null;
	private static ServletContext application = null;
	
	@Override
	public void init() throws ServletException {
		
		super.init();
		
		application = getServletContext();
		context = WebApplicationContextUtils.getWebApplicationContext(application);
		if(context==null){
			throw new RuntimeException("Application environment initialization error!");
		}
		SpringBeanProxy.setApplicationContext(context);
		
		//--------------------- 定时�? 验证licence线程
//		//启动 定时�? 验证licence线程
//		//扩展定时器类ThreadTimer (与系统时间无关的定时�?)
//		ThreadTimer thread = new ThreadTimer(new ThreadUseRunnable(),0,3600000,"timerThread");
//		thread.start();
//		if(thread.started){
//			System.out.println("已开启定时器�?");
//		}
		
	}
	
	public static ServletContext  getWebServletContext(){
		return application;
	}
	
	public static ApplicationContext getApplicationContext(){
		return context;
	}
	
}
