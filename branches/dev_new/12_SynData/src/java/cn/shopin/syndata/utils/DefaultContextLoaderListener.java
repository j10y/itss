package cn.shopin.syndata.utils;

import java.util.Locale;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class DefaultContextLoaderListener extends ContextLoaderListener {

	@SuppressWarnings("static-access")
	public void contextInitialized(ServletContextEvent event) {
		
		super.contextInitialized(event);
		
		WebApplicationContext context = WebApplicationContextUtils.   
	    getWebApplicationContext(event.getServletContext()); 
		ContextHolder.getInstance().setApplicationContext(context);  
		ContextHolder.getInstance().setLocal(Locale.getDefault());
		
	}
}
